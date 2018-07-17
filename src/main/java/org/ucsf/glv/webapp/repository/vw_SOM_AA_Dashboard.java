package org.ucsf.glv.webapp.repository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public interface vw_SOM_AA_Dashboard {

    public List<HashMap<String, Object>> getDashboardData(String sessionUserId)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException;

}
