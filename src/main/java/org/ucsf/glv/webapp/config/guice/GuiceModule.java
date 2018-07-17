package org.ucsf.glv.webapp.config.guice;

import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.SOM_AA_TransactionSummary;
import org.ucsf.glv.webapp.repository.SOM_BFA_ReconEmployeeGLV;
import org.ucsf.glv.webapp.repository.SOM_BFA_UserPreferences;
import org.ucsf.glv.webapp.repository.SOM_BFA_Variables;
import org.ucsf.glv.webapp.repository.vw_COA_SOM_Departments;
import org.ucsf.glv.webapp.repository.vw_Get_Deparments;
import org.ucsf.glv.webapp.repository.vw_SOM_AA_Dashboard;
import org.ucsf.glv.webapp.repository.impl.SOM_AA_TransactionSummaryImpl;
import org.ucsf.glv.webapp.repository.impl.SOM_BFA_ReconEmployeeGLVImpl;
import org.ucsf.glv.webapp.repository.impl.SOM_BFA_UserPreferencesImpl;
import org.ucsf.glv.webapp.repository.impl.SOM_BFA_VariablesImpl;
import org.ucsf.glv.webapp.repository.impl.vw_COA_SOM_DepartmentsImpl;
import org.ucsf.glv.webapp.repository.impl.vw_Get_DeparmentsImpl;
import org.ucsf.glv.webapp.repository.impl.vw_SOM_AA_DashboardImpl;
import org.ucsf.glv.webapp.service.glverification.DashboardService;
import org.ucsf.glv.webapp.service.glverification.ReviewAndVerifyPayrollService;
import org.ucsf.glv.webapp.service.glverification.ReviewAndVerifyTransactionsService;
import org.ucsf.glv.webapp.service.glverification.TopMenuService;
import org.ucsf.glv.webapp.service.glvhome.HomeService;
import org.ucsf.glv.webapp.util.ConvertData;

import com.google.inject.AbstractModule;

public class GuiceModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(Jdbc.class).toInstance(new Jdbc());
        bind(ObjectMapper.class).toInstance(new ObjectMapper());
        bind(ConvertData.class).toInstance(new ConvertData());

        bind(SOM_AA_TransactionSummary.class).to(SOM_AA_TransactionSummaryImpl.class);
        bind(SOM_BFA_UserPreferences.class).to(SOM_BFA_UserPreferencesImpl.class);
        bind(SOM_BFA_ReconEmployeeGLV.class).to(SOM_BFA_ReconEmployeeGLVImpl.class);
        bind(SOM_BFA_Variables.class).to(SOM_BFA_VariablesImpl.class);
        bind(vw_COA_SOM_Departments.class).to(vw_COA_SOM_DepartmentsImpl.class);
        bind(vw_Get_Deparments.class).to(vw_Get_DeparmentsImpl.class);
        bind(vw_SOM_AA_Dashboard.class).to(vw_SOM_AA_DashboardImpl.class);

        bind(HomeService.class).toInstance(new HomeService());
        bind(TopMenuService.class).toInstance(new TopMenuService());
        bind(DashboardService.class).toInstance(new DashboardService());
        bind(ReviewAndVerifyPayrollService.class).toInstance(new ReviewAndVerifyPayrollService());
        bind(ReviewAndVerifyTransactionsService.class).toInstance(new ReviewAndVerifyTransactionsService());
    }

}