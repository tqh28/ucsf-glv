package org.ucsf.glv.webapp.repository.impl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.SOM_BFA_Variables;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.Inject;

public class SOM_BFA_VariablesImpl implements SOM_BFA_Variables {

    @Inject
    private Jdbc jdbc;

    @Inject
    private ConvertData convertData;

    @Override
    public int getFPFYDefault(String var) throws SQLException {
        String sql = "SELECT Integer FROM SOM_BFA_Variables WHERE variable= ?";
        PreparedStatement preparedStatement = jdbc.getPrepareStatement(sql);
        preparedStatement.setString(1, var);

        ResultSet rs = preparedStatement.executeQuery();
        int result = (int) convertData.getObjectFromResultSet(rs);

        rs.close();
        preparedStatement.close();
        
        return result;
    }

}