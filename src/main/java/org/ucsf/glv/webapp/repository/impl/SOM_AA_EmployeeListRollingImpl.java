package org.ucsf.glv.webapp.repository.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.SOM_AA_EmployeeListRolling;

import com.google.inject.Inject;

public class SOM_AA_EmployeeListRollingImpl implements SOM_AA_EmployeeListRolling {
    
    @Inject
    private Jdbc jdbc;

    @Override
    public void deleteBySessionUserId(String sessionUserId) throws SQLException {
        String sql = "DELETE FROM SOM_AA_EmployeeListRolling WHERE SessionUserid = ?";
        PreparedStatement preparedStatement = jdbc.getPrepareStatement(sql);
        preparedStatement.setString(1, sessionUserId);
        preparedStatement.executeUpdate();
    }

}
