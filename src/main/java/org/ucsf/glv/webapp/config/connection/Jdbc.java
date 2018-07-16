package org.ucsf.glv.webapp.config.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import javax.inject.Singleton;

@Singleton
public class Jdbc {
    private Connection connection;

    public Jdbc() {
        try {
            Class.forName(DatabaseCredentials.DRIVER);
            connection = DriverManager.getConnection(DatabaseCredentials.URL, DatabaseCredentials.USER,
                    DatabaseCredentials.PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Statement getStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public PreparedStatement getPrepareStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public String test() {
        return "jdbc";
    }
}
