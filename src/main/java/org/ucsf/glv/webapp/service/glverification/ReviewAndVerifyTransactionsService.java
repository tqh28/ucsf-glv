package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.SQLException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.repository.SOM_AA_TransactionSummary;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ReviewAndVerifyTransactionsService {
    
    @Inject
    private SOM_AA_TransactionSummary transactionSummary;
    
    @Inject
    private ObjectMapper mapper;

    public String getTransactionsData(String sessionUserId, String reconGroupTitle)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        return mapper.writeValueAsString(transactionSummary.getReviewAndVerifyTransactions(sessionUserId, reconGroupTitle));
    }
}
