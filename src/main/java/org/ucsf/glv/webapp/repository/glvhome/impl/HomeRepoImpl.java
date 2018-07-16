package org.ucsf.glv.webapp.repository.glvhome.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.glvhome.HomeRepo;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class HomeRepoImpl implements HomeRepo {

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

}
