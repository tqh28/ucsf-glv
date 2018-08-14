package org.ucsf.glv.webapp.repository;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;

public interface SOM_AA_EmployeeListRolling {

    public HashMap<String, BigDecimal> getCountAndSumM01FromByQuery(Connection connection, String query, String param1)
            throws SQLException;

    public void deleteBySessionUserId(Connection connection, String sessionUserId) throws SQLException;

    public void modifyByQuery(Connection connection, String query) throws SQLException;

    public void modifyByQuery(Connection connection, String query, String param1) throws SQLException;

    public void modifyByQuery(Connection connection, String query, String param1, Timestamp param2, String param3)
            throws SQLException;

    public void modifyByQuery(Connection connection, String query, String param1, Timestamp param2, int param3,
            int param4, int param5, int param6, int param7, String param8, String param9) throws SQLException;
}
