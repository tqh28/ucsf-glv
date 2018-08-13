package org.ucsf.glv.webapp.repository.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import org.ucsf.glv.webapp.repository.VW_COA_SOM_ProjectUses;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class VW_COA_SOM_ProjectUsesImpl implements VW_COA_SOM_ProjectUses {

    @Inject
    private ConvertData convertData;

    @Override
    public List<HashMap<String, Object>> getProjectUseList(Connection connection) throws SQLException {
        StringBuilder sql = new StringBuilder("SELECT ProjectUseTitle, ProjectUseShort ")
                .append("FROM vw_COA_SOM_ProjectUses ")
                .append("GROUP BY ProjectUseShort, ProjectUseTitle");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sql.toString());
        List<HashMap<String, Object>> result = convertData.convertResultSetToListHashMap(rs);
        rs.close();
        statement.close();
        return result;
    }

}
