package org.ucsf.glv.webapp.service.glverification;

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
public class ReviewAndVerifyTransactionsService {
    
    @Inject
    private ReviewAndVerifyTransactionsRepo reviewAndVerifyTransactionsRepo;
    
    @Inject
    private ObjectMapper mapper;

    public String getTransactionsData(String sessionUserId, String reconGroupTitle)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        return mapper.writeValueAsString(reviewAndVerifyTransactionsRepo.getTransactionsData(sessionUserId, reconGroupTitle));
    }
}
