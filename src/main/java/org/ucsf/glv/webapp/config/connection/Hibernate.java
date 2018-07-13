package org.ucsf.glv.webapp.config.connection;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.ucsf.glv.webapp.entity.AspnetUsers;

/**
 * connect database using hibernate
 */
public class Hibernate {
    private static SessionFactory sessionFactory;

    static {
        try {
            Properties prop = new Properties();
            prop.setProperty(Environment.DRIVER, DatabaseCredentials.DRIVER);
            prop.setProperty(Environment.URL, DatabaseCredentials.URL);
            prop.setProperty(Environment.USER, DatabaseCredentials.USER);
            prop.setProperty(Environment.PASS, DatabaseCredentials.PASS);
            prop.setProperty(Environment.DIALECT, DatabaseCredentials.DIALECT);

            sessionFactory = new Configuration()
                    .addProperties(prop)
                    .addAnnotatedClass(AspnetUsers.class)
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Session getSession() {
        return sessionFactory.openSession();
    }
}
