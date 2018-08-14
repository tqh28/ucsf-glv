package org.ucsf.glv.webapp.config.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.inject.Singleton;

@Singleton
public class Jdbc {

    public Jdbc() {
        try {
            Class.forName(DatabaseCredentials.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(DatabaseCredentials.URL, DatabaseCredentials.USER, DatabaseCredentials.PASS);
        return connection;
    }

}
