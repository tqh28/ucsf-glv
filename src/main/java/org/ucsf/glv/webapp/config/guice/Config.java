package org.ucsf.glv.webapp.config.guice;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

import com.google.inject.Guice;
import com.google.inject.Injector;

@ApplicationPath("")
public class Config extends ResourceConfig {

    public Config() {
        packages("org.ucsf.glv.webapp");
        Injector injector = Guice.createInjector(new GuiceModule());
        HK2toGuiceModule hk2Module = new HK2toGuiceModule(injector);
        register(hk2Module);
    }
}