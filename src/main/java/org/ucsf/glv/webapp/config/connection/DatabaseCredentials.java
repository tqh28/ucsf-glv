package org.ucsf.glv.webapp.config.connection;

public class DatabaseCredentials {

    public static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    public static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=GLVData";
//    public static final String URL = "jdbc:sqlserver://localhost:49270;databaseName=GLVData";
    public static final String DIALECT = "org.hibernate.dialect.SQLServer2012Dialect";
    public static final String USER = "sa";
    public static final String PASS = "qwerty@123";

}
