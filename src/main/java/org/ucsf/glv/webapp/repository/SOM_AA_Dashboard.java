package org.ucsf.glv.webapp.repository;

import java.sql.SQLException;

public interface SOM_AA_Dashboard {

    public void deleteBySessionUserId(String userId) throws SQLException;
}
