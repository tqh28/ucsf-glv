package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.SOM_BFA_ReconApproveTrend;
import org.ucsf.glv.webapp.repository.SOM_BFA_Variables;
import org.ucsf.glv.webapp.repository.SP_SOM_GLV_Summary_AARolling;
import org.ucsf.glv.webapp.repository.VW_SOM_AA_Dashboard;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DashboardService {

    @Inject
    private Jdbc jdbc;

    @Inject
    private VW_SOM_AA_Dashboard dashboard;

    @Inject
    private SOM_BFA_ReconApproveTrend reconApproveTrend;

    @Inject
    private SOM_BFA_Variables variables;
    
    @Inject
    private SP_SOM_GLV_Summary_AARolling summaryAARolling;

    @Inject
    private ObjectMapper mapper;

    public String getDashboardData(String userId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        Connection connection = jdbc.getConnection();
        List<HashMap<String, Object>> res = null;
        try {
            res = dashboard.getDashboardData(connection, userId);
        } finally {
            connection.close();
        }
        return mapper.writeValueAsString(res);
    }

    public String getMonthlyTrendPercent(String deptId, int fy, int fp)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        Connection connection = jdbc.getConnection();
        int trendPercent;
        try {
            trendPercent = reconApproveTrend.getMonthlyTrendPercent(connection, deptId, fy, fp);
        } finally {
            connection.close();
        }
        
        HashMap<String, Integer> result = new HashMap<>();
        result.put("monthlyPercentage", trendPercent);
        return mapper.writeValueAsString(result);
    }

    public String getUserData(String userId) throws SQLException {
        Connection connection = jdbc.getConnection();
        connection.setAutoCommit(false);
        try {
            int fp = variables.getFPFYDefault(connection, "DefaultFPMax");
            int fy = variables.getFPFYDefault(connection, "DefaultFY");
            
//            if (fp < 7) {
//                fp += 6;
//                fy -= 1;
//            } else {
//                fp -= 6;
//            }
            
            // call store procedure
            summaryAARolling.execute(connection, userId, "127037", "127037", "SFCMP", "%", userId, "(default)", fy, fp, 1);
            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.close();
        }
        
        return "";
    }

}
