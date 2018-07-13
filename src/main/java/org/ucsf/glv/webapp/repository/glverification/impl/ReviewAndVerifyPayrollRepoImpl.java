package org.ucsf.glv.webapp.repository.glverification.impl;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.glverification.ReviewAndVerifyPayrollRepo;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Singleton;

@Singleton
public class ReviewAndVerifyPayrollRepoImpl implements ReviewAndVerifyPayrollRepo {

    public List<HashMap<String, Object>> getPayrollData(String deptId, String businessUnit, String fiscalYear, String fiscalMonth)
            throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT uniqueid, PositionTitleCategory, Employee_Id, Employee_name, ReconComment, DeptCd, FundCd, ProjectCd, FunctionCd, FlexCd, DeptSite, PlanTitleCdTitle, ReconUser, ReconDate, S05_Nov, ReconStatusCd ")
                .append("FROM SOM_BFA_ReconEmployeeGLV ")
                .append("WHERE (DeptLevel1Cd = ? OR DeptLevel2Cd = ? OR DeptLevel3Cd = ? OR DeptLevel4Cd = ? OR DeptLevel5Cd = ? OR DeptLevel6Cd = ?) AND ReconStatusCd IN (0, 1000, 3000) AND FiscalYear = ? AND FiscalPeriod = ? AND BusinessUnitCd = ? ")
                .append("ORDER BY PositionTitleCategory ASC");

        PreparedStatement preparedStatement = Jdbc.getPrepareStatement(sql.toString());
        preparedStatement.setString(1, deptId);
        preparedStatement.setString(2, deptId);
        preparedStatement.setString(3, deptId);
        preparedStatement.setString(4, deptId);
        preparedStatement.setString(5, deptId);
        preparedStatement.setString(6, deptId);
        preparedStatement.setString(7, fiscalYear);
        preparedStatement.setString(8, fiscalMonth);
        preparedStatement.setString(9, businessUnit);

        ResultSet rs = preparedStatement.executeQuery();

        List<HashMap<String, Object>> result = ConvertData.convertResultSetToListHashMap(rs);
        rs.close();
        preparedStatement.close();
        return result;
    }

    public List<HashMap<String, Object>> getPayrollFTEData(String sessionUserId, int fiscalYear) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT ISNULL(PositionTitleCategory, 'Total') AS PositionTitleCategory, ")
                        .append("SUM(FTEM01) AS FTEM01,SUM(FTEM02) AS FTEM02,SUM(FTEM03) AS FTEM03,SUM(FTEM04) AS FTEM04, ")
                        .append("SUM(FTEM05) AS FTEM05,SUM(FTEM06) AS FTEM06,SUM(FTEM07) AS FTEM07,SUM(FTEM08) AS FTEM08, ")
                        .append("SUM(FTEM09) AS FTEM09,SUM(FTEM10) AS FTEM10,SUM(FTEM11) AS FTEM11,SUM(FTEM12) AS FTEM12, ")
                        .append("SUM(SalM01) AS SalM01,SUM(SalM02) AS SalM02,SUM(SalM03) AS SalM03,SUM(SalM04) AS SalM04, ")
                        .append("SUM(SalM05) AS SalM05,SUM(SalM06) AS SalM06,SUM(SalM07) AS SalM07,SUM(SalM08) AS SalM08, ")
                        .append("SUM(SalM09) AS SalM09,SUM(SalM10) AS SalM10,SUM(SalM11) AS SalM11,SUM(SalM12) AS SalM12 ")
                        .append("FROM vw_SOM_AA_EmployeeCategorySummary ")
                        .append("WHERE SessionUserid = ? AND FiscalYear = ? ")
                        .append("GROUP BY PositionTitleCategory WITH ROLLUP");
        PreparedStatement preparedStatement = Jdbc.getPrepareStatement(sql.toString());
        preparedStatement.setString(1, sessionUserId);
        preparedStatement.setInt(2, fiscalYear);
        ResultSet rs = preparedStatement.executeQuery();

        List<HashMap<String, Object>> result = ConvertData.convertResultSetToListHashMap(rs);

        rs.close();
        preparedStatement.close();

        return result;
    }

    public HashMap<String, Object> getPayrollExpenseData(String sessionUserId, String empName, int start, int length)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        StringBuilder whereCondition = new StringBuilder("SessionUserid = ? ");
        boolean isEmpNameNull = true;
        if (empName != null && !empName.isEmpty()) {
            isEmpNameNull = false;
            whereCondition.append("AND Employee_Name = ? ");
        }
        
        StringBuilder countSql = new StringBuilder("SELECT COUNT(*) AS numrows FROM SOM_AA_EmployeeListRolling WHERE ")
                .append(whereCondition);

        StringBuilder dataSql = new StringBuilder(
                "SELECT uniqueid, PositionTitleCategory, Employee_Name, Employee_Id, RecType, DeptCd, FundCd, ProjectCd, FunctionCd, FlexCd, PositionTitleCd, EmpChanged, M01, M02, M03, M04, M05, M06, M07, M08, M09, M10, M11, M12 ")
                        .append("FROM SOM_AA_EmployeeListRolling ")
                        .append("WHERE ").append(whereCondition)
                        .append("ORDER by PositionTitleCategory, Employee_Name, Sort1, Sort2, PositionTitleCd, DeptCd, FundCd, ProjectCd, FunctionCd, FlexCd ");
        if (length != 0) {
            dataSql.append(String.format("OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", start, length));
        }
        PreparedStatement dataSqlStatement = Jdbc.getPrepareStatement(dataSql.toString());
        PreparedStatement countSqlStatement = Jdbc.getPrepareStatement(countSql.toString());
        dataSqlStatement.setString(1, sessionUserId);
        countSqlStatement.setString(1, sessionUserId);
        if (!isEmpNameNull) {
            dataSqlStatement.setString(2, empName);
            countSqlStatement.setString(2, empName);
        }

        ResultSet dataResultSet = dataSqlStatement.executeQuery();
        List<HashMap<String, Object>> dataHashMapList = ConvertData.convertResultSetToListHashMap(dataResultSet);
        
        ResultSet countResultSet = countSqlStatement.executeQuery();
        int totalRecords = ConvertData.getNumberOfSelectCountQuery(countResultSet);

        dataResultSet.close();
        countResultSet.close();
        dataSqlStatement.close();
        countSqlStatement.close();
        
        HashMap<String, Object> result = new HashMap<String, Object>();
        result.put("data", dataHashMapList);
        result.put("recordsTotal", totalRecords);
        result.put("recordsFiltered", totalRecords);

        return result;
    }
}
