package org.ucsf.glv.webapp.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;

public interface SOM_AA_Dashboard {

    public void deleteBySessionUserId(Connection connection, String userId) throws SQLException;

    public void modifyByQuery(Connection connection, String query, String param1, Timestamp param2) throws SQLException;
    
    public void modifyByQuery(Connection connection, String query, String param1, String param2, Timestamp param3, int param4) throws SQLException;
    
    public void modifyByQuery(Connection connection, String query, BigDecimal param1, BigDecimal param2, String param3) throws SQLException;
}
