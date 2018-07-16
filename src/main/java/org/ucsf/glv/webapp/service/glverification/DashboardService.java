package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.SQLException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.repository.glverification.DashboardRepo;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DashboardService {

    @Inject
    private DashboardRepo dashboardRepo;

    @Inject
    private ObjectMapper mapper;

    public String getDashboardData(String sessionUserId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        String json = mapper.writeValueAsString(dashboardRepo.getDashboardData(sessionUserId));
        return json;
    }

}
