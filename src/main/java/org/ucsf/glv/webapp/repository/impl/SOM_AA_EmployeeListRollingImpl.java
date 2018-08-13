package org.ucsf.glv.webapp.repository.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;

import org.ucsf.glv.webapp.repository.SOM_AA_EmployeeListRolling;

public class SOM_AA_EmployeeListRollingImpl implements SOM_AA_EmployeeListRolling {

    @Override
    public void deleteBySessionUserId(Connection connection, String sessionUserId) throws SQLException {
        String sql = "DELETE FROM SOM_AA_EmployeeListRolling WHERE SessionUserid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, sessionUserId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void modifyByQuery(Connection connection, String query) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeUpdate(query);
        statement.close();
    }

    @Override
    public void modifyByQuery(Connection connection, String query, String param1) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, param1);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void modifyByQuery(Connection connection, String query, String param1, Timestamp param2, String param3) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, param1);
        preparedStatement.setTimestamp(2, param2);
        preparedStatement.setString(3, param3);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public HashMap<String, BigDecimal> getCountAndSumM01FromByQuery(Connection connection, String query, String param1) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, param1);
        ResultSet rs = preparedStatement.executeQuery();
        HashMap<String, BigDecimal> result = new HashMap<>();
        if (rs.next()) {
            BigDecimal countPayroll = rs.getBigDecimal("countPayroll");
            BigDecimal sumPayroll = rs.getBigDecimal("sumPayroll");
            result.put("countPayroll", countPayroll);
            result.put("sumPayroll", sumPayroll);
        }
        rs.close();
        preparedStatement.close();
        return result;
    }

}
