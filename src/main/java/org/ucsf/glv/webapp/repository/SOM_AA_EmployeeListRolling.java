package org.ucsf.glv.webapp.repository;

import java.sql.SQLException;

public interface SOM_AA_EmployeeListRolling {

    public void deleteBySessionUserId(String sessionUserId) throws SQLException;
}
