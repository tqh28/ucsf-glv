package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.repository.SOM_BFA_ReconApproveTrend;
import org.ucsf.glv.webapp.repository.vw_SOM_AA_Dashboard;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DashboardService {

    @Inject
    private vw_SOM_AA_Dashboard dashboard;
    
    @Inject
    private SOM_BFA_ReconApproveTrend reconApproveTrend;

    @Inject
    private ObjectMapper mapper;

    public String getDashboardData(String sessionUserId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        String json = mapper.writeValueAsString(dashboard.getDashboardData(sessionUserId));
        return json;
    }
    
    public String getMonthlyTrendPercent(String deptId, int fy, int fp)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        int trendPercent = reconApproveTrend.getMonthlyTrendPercent(deptId, fy, fp);
        HashMap<String, Integer> result = new HashMap<>();
        result.put("monthlyPercentage", trendPercent);
        return mapper.writeValueAsString(result);
    }

}
