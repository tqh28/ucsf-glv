package org.ucsf.glv.webapp.repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface SOM_AA_TransactionSummary {

    public List<HashMap<String, Object>> getReviewAndVerifyTransactions(String userId, String reconGroupTitle) throws SQLException;

    public void deleteBySessionUserId(String sessionUserId) throws SQLException;
}
