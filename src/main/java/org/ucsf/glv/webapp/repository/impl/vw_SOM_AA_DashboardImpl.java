package org.ucsf.glv.webapp.repository.impl;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.vw_SOM_AA_Dashboard;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class vw_SOM_AA_DashboardImpl implements vw_SOM_AA_Dashboard {

    @Inject
    private Jdbc jdbc;

    @Inject
    private ConvertData convertData;

    @Override
    public List<HashMap<String, Object>> getDashboardData(String userId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {

        StringBuilder sql = new StringBuilder(
                "SELECT ReconGroupTitle, PercentCompleted, PercentNotcompleted, TotalSelectedAmount, TotalSelectedCount, TotalActivityAmount, TotalActivityCount, TotalNotVerifiedAmount, TotalNotVerifiedCount ")
                        .append("FROM vw_SOM_AA_Dashboard ").append("WHERE SessionUserid = ? ")
                        .append("ORDER BY CASE WHEN ReconGroupTitle = 'Total' THEN 1 ELSE 0 END, ReconGroupTitle ASC");
        PreparedStatement prepareStatement = jdbc.getPrepareStatement(sql.toString());
        prepareStatement.setString(1, userId);

        ResultSet rs = prepareStatement.executeQuery();

        List<HashMap<String, Object>> json = convertData.convertResultSetToListHashMap(rs);

        rs.close();
        prepareStatement.close();

        return json;
    }

}
