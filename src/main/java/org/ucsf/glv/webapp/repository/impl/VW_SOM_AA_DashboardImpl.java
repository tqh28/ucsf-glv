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
import org.ucsf.glv.webapp.repository.VW_SOM_AA_Dashboard;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class VW_SOM_AA_DashboardImpl implements VW_SOM_AA_Dashboard {

    @Inject
    private ConvertData convertData;

    @Override
    public List<HashMap<String, Object>> getDashboardData(Connection connection, String userId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        StringBuilder sql = new StringBuilder(
                "SELECT ReconGroupTitle, PercentCompleted, PercentNotcompleted, TotalSelectedAmount, TotalSelectedCount, TotalActivityAmount, TotalActivityCount, TotalNotVerifiedAmount, TotalNotVerifiedCount ")
                        .append("FROM vw_SOM_AA_Dashboard ").append("WHERE SessionUserid = ? ")
                        .append("ORDER BY CASE WHEN ReconGroupTitle = 'Total' THEN 1 ELSE 0 END, ReconGroupTitle ASC");
        PreparedStatement prepareStatement = connection.prepareStatement(sql.toString());
        prepareStatement.setString(1, userId);

        ResultSet rs = prepareStatement.executeQuery();
        List<HashMap<String, Object>> json = convertData.convertResultSetToListHashMap(rs);
        rs.close();
        prepareStatement.close();
        return json;
    }

}
