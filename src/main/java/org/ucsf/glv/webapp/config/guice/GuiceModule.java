package org.ucsf.glv.webapp.config.guice;

import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.SOM_BFA_UserPreferences;
import org.ucsf.glv.webapp.repository.vw_Get_Deparments;
import org.ucsf.glv.webapp.repository.glverification.DashboardRepo;
import org.ucsf.glv.webapp.repository.glverification.ReviewAndVerifyPayrollRepo;
import org.ucsf.glv.webapp.repository.glverification.ReviewAndVerifyTransactionsRepo;
import org.ucsf.glv.webapp.repository.glverification.impl.DashboardRepoImpl;
import org.ucsf.glv.webapp.repository.glverification.impl.ReviewAndVerifyPayrollRepoImpl;
import org.ucsf.glv.webapp.repository.glverification.impl.ReviewAndVerifyTransactionsRepoImpl;
import org.ucsf.glv.webapp.repository.glvhome.HomeRepo;
import org.ucsf.glv.webapp.repository.glvhome.impl.HomeRepoImpl;
import org.ucsf.glv.webapp.repository.impl.SOM_BFA_UserPreferencesImpl;
import org.ucsf.glv.webapp.repository.impl.vw_Get_DeparmentsImpl;
import org.ucsf.glv.webapp.service.glverification.DashboardService;
import org.ucsf.glv.webapp.service.glverification.ReviewAndVerifyPayrollService;
import org.ucsf.glv.webapp.service.glverification.ReviewAndVerifyTransactionsService;
import org.ucsf.glv.webapp.service.glvhome.HomeService;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.AbstractModule;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {

        bind(Jdbc.class).toInstance(new Jdbc());
        bind(ObjectMapper.class).toInstance(new ObjectMapper());
        bind(ConvertData.class).toInstance(new ConvertData());
        
        bind(SOM_BFA_UserPreferences.class).to(SOM_BFA_UserPreferencesImpl.class);
        bind(vw_Get_Deparments.class).to(vw_Get_DeparmentsImpl.class);

        bind(HomeRepo.class).to(HomeRepoImpl.class);
        bind(HomeService.class).toInstance(new HomeService());

        bind(DashboardRepo.class).to(DashboardRepoImpl.class);
        bind(DashboardService.class).toInstance(new DashboardService());

        bind(ReviewAndVerifyPayrollRepo.class).to(ReviewAndVerifyPayrollRepoImpl.class);
        bind(ReviewAndVerifyPayrollService.class).toInstance(new ReviewAndVerifyPayrollService());

        bind(ReviewAndVerifyTransactionsRepo.class).to(ReviewAndVerifyTransactionsRepoImpl.class);
        bind(ReviewAndVerifyTransactionsService.class).toInstance(new ReviewAndVerifyTransactionsService());
    }

}