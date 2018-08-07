package org.ucsf.glv.webapp.config.guice;

import org.codehaus.jackson.map.ObjectMapper;
import org.ucsf.glv.webapp.config.connection.Jdbc;
import org.ucsf.glv.webapp.repository.SOM_AA_Dashboard;
import org.ucsf.glv.webapp.repository.SOM_AA_EmployeeListRolling;
import org.ucsf.glv.webapp.repository.SOM_AA_TransactionSummary;
import org.ucsf.glv.webapp.repository.SOM_BFA_ReconApproveTrend;
import org.ucsf.glv.webapp.repository.SOM_BFA_ReconEmployeeGLV;
import org.ucsf.glv.webapp.repository.SOM_BFA_SavedChartFieldFilters;
import org.ucsf.glv.webapp.repository.SOM_BFA_UserPreferences;
import org.ucsf.glv.webapp.repository.SOM_BFA_Variables;
import org.ucsf.glv.webapp.repository.SP_SOM_GLV_Summary_AARolling;
import org.ucsf.glv.webapp.repository.VW_COA_SOM_Departments;
import org.ucsf.glv.webapp.repository.VW_COA_SOM_Funds_Tree;
import org.ucsf.glv.webapp.repository.VW_COA_SOM_ProjectUses;
import org.ucsf.glv.webapp.repository.VW_Get_Deparments;
import org.ucsf.glv.webapp.repository.VW_SOM_AA_Dashboard;
import org.ucsf.glv.webapp.repository.VW_SOM_BFA_ReconGroups;
import org.ucsf.glv.webapp.repository.impl.SOM_AA_DashboardImpl;
import org.ucsf.glv.webapp.repository.impl.SOM_AA_EmployeeListRollingImpl;
import org.ucsf.glv.webapp.repository.impl.SOM_AA_TransactionSummaryImpl;
import org.ucsf.glv.webapp.repository.impl.SOM_BFA_ReconApproveTrendImpl;
import org.ucsf.glv.webapp.repository.impl.SOM_BFA_ReconEmployeeGLVImpl;
import org.ucsf.glv.webapp.repository.impl.SOM_BFA_SavedChartFieldFiltersImpl;
import org.ucsf.glv.webapp.repository.impl.SOM_BFA_UserPreferencesImpl;
import org.ucsf.glv.webapp.repository.impl.SOM_BFA_VariablesImpl;
import org.ucsf.glv.webapp.repository.impl.SP_SOM_GLV_Summary_AARollingImpl;
import org.ucsf.glv.webapp.repository.impl.VW_COA_SOM_DepartmentsImpl;
import org.ucsf.glv.webapp.repository.impl.VW_COA_SOM_Funds_TreeImpl;
import org.ucsf.glv.webapp.repository.impl.VW_COA_SOM_ProjectUsesImpl;
import org.ucsf.glv.webapp.repository.impl.VW_Get_DeparmentsImpl;
import org.ucsf.glv.webapp.repository.impl.VW_SOM_AA_DashboardImpl;
import org.ucsf.glv.webapp.repository.impl.VW_SOM_BFA_ReconGroupsImpl;
import org.ucsf.glv.webapp.service.glverification.DashboardService;
import org.ucsf.glv.webapp.service.glverification.EditMyFilterService;
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
        bind(SOM_BFA_ReconApproveTrend.class).to(SOM_BFA_ReconApproveTrendImpl.class);
        bind(SOM_BFA_SavedChartFieldFilters.class).to(SOM_BFA_SavedChartFieldFiltersImpl.class);
        bind(SOM_AA_Dashboard.class).to(SOM_AA_DashboardImpl.class);
        bind(SOM_AA_EmployeeListRolling.class).to(SOM_AA_EmployeeListRollingImpl.class);

        bind(VW_COA_SOM_ProjectUses.class).to(VW_COA_SOM_ProjectUsesImpl.class);
        bind(VW_COA_SOM_Departments.class).to(VW_COA_SOM_DepartmentsImpl.class);
        bind(VW_Get_Deparments.class).to(VW_Get_DeparmentsImpl.class);
        bind(VW_SOM_AA_Dashboard.class).to(VW_SOM_AA_DashboardImpl.class);
        bind(VW_SOM_BFA_ReconGroups.class).to(VW_SOM_BFA_ReconGroupsImpl.class);
        bind(VW_COA_SOM_Funds_Tree.class).to(VW_COA_SOM_Funds_TreeImpl.class);

        bind(SP_SOM_GLV_Summary_AARolling.class).to(SP_SOM_GLV_Summary_AARollingImpl.class);

        bind(HomeService.class).toInstance(new HomeService());
        bind(TopMenuService.class).toInstance(new TopMenuService());
        bind(DashboardService.class).toInstance(new DashboardService());
        bind(ReviewAndVerifyPayrollService.class).toInstance(new ReviewAndVerifyPayrollService());
        bind(ReviewAndVerifyTransactionsService.class).toInstance(new ReviewAndVerifyTransactionsService());
        bind(EditMyFilterService.class).toInstance(new EditMyFilterService());
    }

}