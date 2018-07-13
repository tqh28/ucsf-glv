package org.ucsf.glv.webapp.config.guice;

import org.ucsf.glv.webapp.repository.glverification.DashboardRepo;
import org.ucsf.glv.webapp.repository.glverification.ReviewAndVerifyPayrollRepo;
import org.ucsf.glv.webapp.repository.glverification.ReviewAndVerifyTransactionsRepo;
import org.ucsf.glv.webapp.repository.glverification.impl.DashboardRepoImpl;
import org.ucsf.glv.webapp.repository.glverification.impl.ReviewAndVerifyPayrollRepoImpl;
import org.ucsf.glv.webapp.repository.glverification.impl.ReviewAndVerifyTransactionsRepoImpl;
import org.ucsf.glv.webapp.service.glverification.DashboardService;
import org.ucsf.glv.webapp.service.glverification.ReviewAndVerifyPayrollService;
import org.ucsf.glv.webapp.service.glverification.ReviewAndVerifyTransactionsService;
import org.ucsf.glv.webapp.service.glverification.impl.DashboardServiceImpl;
import org.ucsf.glv.webapp.service.glverification.impl.ReviewAndVerifyPayrollServiceImpl;
import org.ucsf.glv.webapp.service.glverification.impl.ReviewAndVerifyTransactionsServiceImpl;

import com.google.inject.AbstractModule;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(DashboardRepo.class).to(DashboardRepoImpl.class);
        bind(DashboardService.class).to(DashboardServiceImpl.class);
        
        bind(ReviewAndVerifyPayrollRepo.class).to(ReviewAndVerifyPayrollRepoImpl.class);
        bind(ReviewAndVerifyPayrollService.class).to(ReviewAndVerifyPayrollServiceImpl.class);
        
        bind(ReviewAndVerifyTransactionsRepo.class).to(ReviewAndVerifyTransactionsRepoImpl.class);
        bind(ReviewAndVerifyTransactionsService.class).to(ReviewAndVerifyTransactionsServiceImpl.class);
    }
}