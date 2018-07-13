package org.ucsf.glv.webapp.config.guice;

import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.ucsf.glv.webapp.repository.glverification.DashboardRepo;
import org.ucsf.glv.webapp.repository.glverification.ReviewAndVerifyPayrollRepo;
import org.ucsf.glv.webapp.repository.glverification.ReviewAndVerifyTransactionsRepo;
import org.ucsf.glv.webapp.service.glverification.DashboardService;
import org.ucsf.glv.webapp.service.glverification.ReviewAndVerifyPayrollService;
import org.ucsf.glv.webapp.service.glverification.ReviewAndVerifyTransactionsService;

import com.google.inject.Injector;;

public class HK2toGuiceModule extends AbstractBinder {

    private Injector guiceInjector;

    public HK2toGuiceModule(Injector guiceInjector) {
        this.guiceInjector = guiceInjector;
    }

    @Override
    protected void configure() {
        bindFactory(new ServiceFactory<DashboardRepo>(guiceInjector, DashboardRepo.class)).to(DashboardRepo.class);
        bindFactory(new ServiceFactory<DashboardService>(guiceInjector, DashboardService.class)).to(DashboardService.class);
        
        bindFactory(new ServiceFactory<ReviewAndVerifyPayrollRepo>(guiceInjector, ReviewAndVerifyPayrollRepo.class)).to(ReviewAndVerifyPayrollRepo.class);
        bindFactory(new ServiceFactory<ReviewAndVerifyPayrollService>(guiceInjector, ReviewAndVerifyPayrollService.class)).to(ReviewAndVerifyPayrollService.class);
        
        bindFactory(new ServiceFactory<ReviewAndVerifyTransactionsRepo>(guiceInjector, ReviewAndVerifyTransactionsRepo.class)).to(ReviewAndVerifyTransactionsRepo.class);
        bindFactory(new ServiceFactory<ReviewAndVerifyTransactionsService>(guiceInjector, ReviewAndVerifyTransactionsService.class)).to(ReviewAndVerifyTransactionsService.class);
    }

    private static class ServiceFactory<T> implements Factory<T> {

        private final Injector guiceInjector;

        private final Class<T> serviceClass;

        public ServiceFactory(Injector guiceInjector, Class<T> serviceClass) {

            this.guiceInjector = guiceInjector;
            this.serviceClass = serviceClass;
        }

        @Override
        public T provide() {
            return guiceInjector.getInstance(serviceClass);
        }

        @Override
        public void dispose(T versionResource) {
        }
    }
}