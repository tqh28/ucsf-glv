package org.ucsf.glv.webapp.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

public interface SOM_AA_TransactionSummary {

    public List<HashMap<String, Object>> getReviewAndVerifyTransactions(Connection connection, String userId, String reconGroupTitle) throws SQLException;

    public void deleteBySessionUserId(Connection connection, String sessionUserId) throws SQLException;
    
    public void insertData(Connection connection, String query, String sessionUserId, Timestamp sesstionTime) throws SQLException;
}
