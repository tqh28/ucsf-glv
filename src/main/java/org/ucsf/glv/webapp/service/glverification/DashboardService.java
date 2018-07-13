package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.SQLException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public interface DashboardService {

    public String getDashboardData(String sessionUserId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException;

}
