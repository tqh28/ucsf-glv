package org.ucsf.glv.webapp.repository.glverification;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public interface ReviewAndVerifyTransactionsRepo {

    public List<HashMap<String, Object>> getTransactionsData(String sessionUserId, String reconGroupTitle) throws SQLException;

}
