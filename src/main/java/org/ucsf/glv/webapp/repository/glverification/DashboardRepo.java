package org.ucsf.glv.webapp.repository.glverification;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public interface DashboardRepo {

    public List<HashMap<String, Object>> getDashboardData(String sessionUserId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException;

}
