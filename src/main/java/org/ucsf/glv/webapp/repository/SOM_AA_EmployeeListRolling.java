package org.ucsf.glv.webapp.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

public interface SOM_AA_EmployeeListRolling {
    
    public HashMap<String, BigDecimal> getCountAndSumM01FromByQuery(Connection connection, String query, String param1) throws SQLException; 

    public void deleteBySessionUserId(Connection connection, String sessionUserId) throws SQLException;

    public void modifyByQuery(Connection connection, String query) throws SQLException;

    public void modifyByQuery(Connection connection, String query, String param1) throws SQLException;

    public void modifyByQuery(Connection connection, String query, String param1, Timestamp param2, String param3) throws SQLException;

}
