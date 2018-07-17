package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.SQLException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.repository.vw_SOM_AA_Dashboard;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DashboardService {

    @Inject
    private vw_SOM_AA_Dashboard dashboard;

    @Inject
    private ObjectMapper mapper;

    public String getDashboardData(String sessionUserId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        String json = mapper.writeValueAsString(dashboard.getDashboardData(sessionUserId));
        return json;
    }

}
