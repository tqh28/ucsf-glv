package org.ucsf.glv.webapp.service.glverification;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.SOM_AA_TransactionSummary;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class ReviewAndVerifyTransactionsService {

    @Inject
    private Jdbc jdbc;
    
    @Inject
    private SOM_AA_TransactionSummary transactionSummary;
    
    @Inject
    private ObjectMapper mapper;

    public String getTransactionsData(String userId, String reconGroupTitle)
            throws SQLException, JsonGenerationException, JsonMappingException, IOException {
        Connection connection = jdbc.getConnection();
        List<HashMap<String, Object>> res;
        try {
            res = transactionSummary.getReviewAndVerifyTransactions(connection, userId, reconGroupTitle);
        } finally {
            connection.close();
        }
        return mapper.writeValueAsString(res);
    }
}
