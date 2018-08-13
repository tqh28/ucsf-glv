package org.ucsf.glv.webapp.repository.impl;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ucsf.glv.webapp.repository.SOM_BFA_ReconEmployeeGLV;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class SOM_BFA_ReconEmployeeGLVImpl implements SOM_BFA_ReconEmployeeGLV {

    @Inject
    private ConvertData convertData;

    @Override
    public List<HashMap<String, Object>> getVerifyPayroll(Connection connection, String deptId, String businessUnit, String fiscalYear,
            String fiscalMonth) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT uniqueid, PositionTitleCategory, Employee_Id, Employee_name, ReconComment, DeptCd, FundCd, ProjectCd, FunctionCd, FlexCd, DeptSite, PlanTitleCdTitle, ReconUser, ReconDate, S05_Nov, ReconStatusCd ")
                        .append("FROM SOM_BFA_ReconEmployeeGLV ")
                        .append("WHERE (DeptLevel1Cd = ? OR DeptLevel2Cd = ? OR DeptLevel3Cd = ? OR DeptLevel4Cd = ? OR DeptLevel5Cd = ? OR DeptLevel6Cd = ?) AND ReconStatusCd IN (0, 1000, 3000) AND FiscalYear = ? AND FiscalPeriod = ? AND BusinessUnitCd = ? ")
                        .append("ORDER BY PositionTitleCategory ASC");

        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
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
        List<HashMap<String, Object>> result = convertData.convertResultSetToListHashMap(rs);
        rs.close();
        preparedStatement.close();
        return result;
    }

    @Override
    public List<HashMap<String, Object>> getListCategorySumary(Connection connection, String userId, int fiscalYear)
            throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT ISNULL(PositionTitleCategory, 'Total') AS PositionTitleCategory, ").append(
                        "SUM(FTEM01) AS FTEM01,SUM(FTEM02) AS FTEM02,SUM(FTEM03) AS FTEM03,SUM(FTEM04) AS FTEM04, ")
                        .append("SUM(FTEM05) AS FTEM05,SUM(FTEM06) AS FTEM06,SUM(FTEM07) AS FTEM07,SUM(FTEM08) AS FTEM08, ")
                        .append("SUM(FTEM09) AS FTEM09,SUM(FTEM10) AS FTEM10,SUM(FTEM11) AS FTEM11,SUM(FTEM12) AS FTEM12, ")
                        .append("SUM(SalM01) AS SalM01,SUM(SalM02) AS SalM02,SUM(SalM03) AS SalM03,SUM(SalM04) AS SalM04, ")
                        .append("SUM(SalM05) AS SalM05,SUM(SalM06) AS SalM06,SUM(SalM07) AS SalM07,SUM(SalM08) AS SalM08, ")
                        .append("SUM(SalM09) AS SalM09,SUM(SalM10) AS SalM10,SUM(SalM11) AS SalM11,SUM(SalM12) AS SalM12 ")
                        .append("FROM vw_SOM_AA_EmployeeCategorySummary ")
                        .append("WHERE SessionUserid = ? AND FiscalYear = ? ")
                        .append("GROUP BY PositionTitleCategory WITH ROLLUP");
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        preparedStatement.setString(1, userId);
        preparedStatement.setInt(2, fiscalYear);
        ResultSet rs = preparedStatement.executeQuery();

        List<HashMap<String, Object>> result = convertData.convertResultSetToListHashMap(rs);
        rs.close();
        preparedStatement.close();
        return result;
    }

    @Override
    public List<HashMap<String, Object>> getExpenseDetail(Connection connection, String userId, String empName, int start, int length)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        StringBuilder whereCondition = new StringBuilder("SessionUserid = ? ");
        boolean isEmpNameNull = true;
        if (empName != null && !empName.isEmpty()) {
            isEmpNameNull = false;
            whereCondition.append("AND Employee_Name = ? ");
        }

        StringBuilder sql = new StringBuilder(
                "SELECT uniqueid, PositionTitleCategory, Employee_Name, Employee_Id, RecType, DeptCd, FundCd, ProjectCd, FunctionCd, FlexCd, PositionTitleCd, EmpChanged, M01, M02, M03, M04, M05, M06, M07, M08, M09, M10, M11, M12 ")
                        .append("FROM SOM_AA_EmployeeListRolling ").append("WHERE ").append(whereCondition)
                        .append("ORDER by PositionTitleCategory, Employee_Name, Sort1, Sort2, PositionTitleCd, DeptCd, FundCd, ProjectCd, FunctionCd, FlexCd ");
        if (length != 0) {
            sql.append(String.format("OFFSET %d ROWS FETCH NEXT %d ROWS ONLY", start, length));
        }
        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        preparedStatement.setString(1, userId);
        if (!isEmpNameNull) {
            preparedStatement.setString(2, empName);
        }

        ResultSet rs = preparedStatement.executeQuery();
        List<HashMap<String, Object>> result = convertData.convertResultSetToListHashMap(rs);
        rs.close();
        preparedStatement.close();
        return result;
    }

    @Override
    public int countExpenseDetail(Connection connection, String userId, String empName, int start, int length)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        StringBuilder whereCondition = new StringBuilder("SessionUserid = ? ");
        boolean isEmpNameNull = true;
        if (empName != null && !empName.isEmpty()) {
            isEmpNameNull = false;
            whereCondition.append("AND Employee_Name = ? ");
        }

        StringBuilder sql = new StringBuilder("SELECT COUNT(*) AS numrows FROM SOM_AA_EmployeeListRolling WHERE ")
                .append(whereCondition);

        PreparedStatement preparedStatement = connection.prepareStatement(sql.toString());
        preparedStatement.setString(1, userId);
        if (!isEmpNameNull) {
            preparedStatement.setString(2, empName);
        }

        ResultSet rs = preparedStatement.executeQuery();
        int totalRecords = (int) convertData.getObjectFromResultSet(rs);
        rs.close();
        preparedStatement.close();
        return totalRecords;
    }

}
