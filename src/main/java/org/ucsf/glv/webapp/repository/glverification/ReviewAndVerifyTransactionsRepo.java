package org.ucsf.glv.webapp.repository.glverification;

import java.sql.SQLException;

public interface ReviewAndVerifyTransactionsRepo {

    public String getTransactionsData(String sessionUserId, String reconGroupTitle) throws SQLException;

}
