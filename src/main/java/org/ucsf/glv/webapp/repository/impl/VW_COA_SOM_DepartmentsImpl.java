package org.ucsf.glv.webapp.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.VW_COA_SOM_Departments;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class VW_COA_SOM_DepartmentsImpl implements VW_COA_SOM_Departments {

    @Inject
    private Jdbc jdbc;

    @Inject
    private ConvertData convertData;

    @Override
    public List<HashMap<String, Object>> getControlPointList() throws SQLException {
        Statement statement = jdbc.getStatement();
        StringBuilder sql = new StringBuilder(
                "SELECT ((DeptCd + '-') + REPLACE( SUBSTRING(DeptTitle, CHARINDEX('_', DeptTitle), LEN(DeptTitle) ), '_', '') ) AS DeptTitle, DeptCd ")
                        .append("FROM vw_COA_SOM_Departments ")
                        .append("WHERE DeptLevel = 1 AND ISNUMERIC(deptcd) <>0 AND (DeptCd NOT IN (200000, 200003, 200004, 634000))");
        ResultSet rs = statement.executeQuery(sql.toString());
        List<HashMap<String, Object>> result = convertData.convertResultSetToListHashMap(rs);

        rs.close();
        statement.close();
        return result;
    }

    @Override
    public List<HashMap<String, Object>> getListDepartmentByDeptId(String deptId) throws SQLException {
        StringBuilder sql = new StringBuilder(
                "SELECT DeptTreeTitleAbbrev, DeptCd FROM vw_COA_SOM_Departments WHERE Deptcd <>'------' ");
        PreparedStatement preparedStatement = null;
        if (deptId != null && !deptId.equals("")) {
            sql.append(
                    "AND (DeptLevel1Cd = ? OR DeptLevel2Cd = ? OR DeptLevel3Cd = ? OR DeptLevel4Cd = ? OR DeptLevel5Cd = ? OR DeptLevel6Cd = ?) ORDER BY depttreecd");
            preparedStatement = jdbc.getPrepareStatement(sql.toString());
            preparedStatement.setString(1, deptId);
            preparedStatement.setString(2, deptId);
            preparedStatement.setString(3, deptId);
            preparedStatement.setString(4, deptId);
            preparedStatement.setString(5, deptId);
            preparedStatement.setString(6, deptId);
        } else {
            preparedStatement = jdbc.getPrepareStatement(sql.toString());
        }
        ResultSet rs = preparedStatement.executeQuery();
        List<HashMap<String, Object>> result = convertData.convertResultSetToListHashMap(rs);

        rs.close();
        preparedStatement.close();

        return result;
    }

}