package org.ucsf.glv.webapp.repository.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.ucsf.glv.webapp.repository.SOM_AA_Dashboard;

public class SOM_AA_DashboardImpl implements SOM_AA_Dashboard {

    @Override
    public void deleteBySessionUserId(Connection connection, String userId) throws SQLException {
        String sql = "DELETE FROM SOM_AA_Dashboard WHERE SessionUserid = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, userId);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void modifyByQuery(Connection connection, String query, String sessionUserId, Timestamp sesstionTime) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, sessionUserId);
        statement.setTimestamp(2, sesstionTime);
        statement.executeUpdate();
        statement.close();
    }

    @Override
    public void modifyByQuery(Connection connection, String query, String param1, String param2, Timestamp param3, int param4)
            throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, param1);
        preparedStatement.setString(2, param2);
        preparedStatement.setTimestamp(3, param3);
        preparedStatement.setInt(4, param4);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    @Override
    public void modifyByQuery(Connection connection, String query, BigDecimal param1, BigDecimal param2, String param3) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setBigDecimal(1, param1);
        preparedStatement.setBigDecimal(2, param2);
        preparedStatement.setString(3, param3);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

}
