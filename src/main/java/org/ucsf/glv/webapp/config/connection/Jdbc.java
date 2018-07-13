package org.ucsf.glv.webapp.config.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * connect database using jdbc
 */
public class Jdbc {

    private static Connection connection;

    static {
        try {
            Class.forName(DatabaseCredentials.DRIVER);
            connection = DriverManager.getConnection(DatabaseCredentials.URL, DatabaseCredentials.USER,
                    DatabaseCredentials.PASS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Statement getStatement() {
        try {
            return connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static PreparedStatement getPrepareStatement(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
