package org.ucsf.glv.webapp.config.guice;

import org.ucsf.glv.webapp.repository.glverification.DashboardRepo;
import org.ucsf.glv.webapp.repository.glverification.impl.DashboardRepoImpl;
import org.ucsf.glv.webapp.service.glverification.DashboardService;
import org.ucsf.glv.webapp.service.glverification.impl.DashboardServiceImpl;

import com.google.inject.AbstractModule;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DashboardRepo.class).to(DashboardRepoImpl.class);
        bind(DashboardService.class).to(DashboardServiceImpl.class);
    }
}