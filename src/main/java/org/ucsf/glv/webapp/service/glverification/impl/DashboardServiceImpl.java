package org.ucsf.glv.webapp.service.glverification.impl;

import java.io.IOException;
import java.sql.SQLException;

import javax.inject.Inject;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.repository.glverification.DashboardRepo;
import org.ucsf.glv.webapp.service.glverification.DashboardService;

import com.google.inject.Singleton;

@Singleton
public class DashboardServiceImpl implements DashboardService {

    @Inject
    private DashboardRepo dashboardRepo;

    private ObjectMapper mapper = new ObjectMapper();

    public String getDashboardData(String sessionUserId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        String json = mapper.writeValueAsString(dashboardRepo.getDashboardData(sessionUserId));
        return json;
    }

}
