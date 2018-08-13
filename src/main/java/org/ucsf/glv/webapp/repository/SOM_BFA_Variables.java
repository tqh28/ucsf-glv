package org.ucsf.glv.webapp.repository;

import java.sql.Connection;
import java.sql.SQLException;

public interface SOM_BFA_Variables {

    public int getFPFYDefault(Connection connection, String var) throws SQLException;
}
