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
import org.ucsf.glv.webapp.repository.glverification.DashboardRepo;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Singleton;

@Singleton
public class DashboardRepoImpl implements DashboardRepo {

    @Override
    public List<HashMap<String, Object>> getDashboardData(String sessionUserId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {

        String sql = "SELECT ReconGroupTitle, PercentCompleted, PercentNotcompleted, TotalSelectedAmount, TotalSelectedCount, TotalActivityAmount, TotalActivityCount, TotalNotVerifiedAmount, TotalNotVerifiedCount FROM vw_SOM_AA_Dashboard WHERE SessionUserid = ? ORDER BY CASE WHEN ReconGroupTitle = 'Total' THEN 1 ELSE 0 END, ReconGroupTitle ASC";
        PreparedStatement prepareStatement = Jdbc.getPrepareStatement(sql);
        prepareStatement.setString(1, sessionUserId);

        ResultSet rs = prepareStatement.executeQuery();

        List<HashMap<String, Object>> json = ConvertData.convertResultSetToListHashMap(rs);

        rs.close();
        prepareStatement.close();

        return json;
    }

}
