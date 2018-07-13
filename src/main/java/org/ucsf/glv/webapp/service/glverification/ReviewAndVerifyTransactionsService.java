package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.SQLException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

public interface ReviewAndVerifyTransactionsService {

    public String getTransactionsData(String sessionUserId, String reconGroupTitle)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException;
}
