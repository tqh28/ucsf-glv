package org.ucsf.glv.webapp.repository.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.SOM_AA_Dashboard;

import com.google.inject.Inject;

public class SOM_AA_DashboardImpl implements SOM_AA_Dashboard {
    
    @Inject
    private Jdbc jdbc;

    @Override
    public void deleteBySessionUserId(String userId) throws SQLException {
        String sql = "DELETE FROM SOM_AA_Dashboard WHERE SessionUserid = ?";
        PreparedStatement preparedStatement = jdbc.getPrepareStatement(sql);
        preparedStatement.setString(1, userId);
        preparedStatement.executeUpdate();
    }

}
