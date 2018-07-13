package org.ucsf.glv.webapp.service.glverification.impl;

import java.io.IOException;
import java.sql.SQLException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.repository.glverification.ReviewAndVerifyTransactionsRepo;
import org.ucsf.glv.webapp.service.glverification.ReviewAndVerifyTransactionsService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ReviewAndVerifyTransactionsServiceImpl implements ReviewAndVerifyTransactionsService {
    
    @Inject
    private ReviewAndVerifyTransactionsRepo reviewAndVerifyTransactionsRepo;
    
    private ObjectMapper mapper = new ObjectMapper();

    public String getTransactionsData(String sessionUserId, String reconGroupTitle)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        return mapper.writeValueAsString(reviewAndVerifyTransactionsRepo.getTransactionsData(sessionUserId, reconGroupTitle));
    }
}
