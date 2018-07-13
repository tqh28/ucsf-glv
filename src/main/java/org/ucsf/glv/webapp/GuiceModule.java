package org.ucsf.glv.webapp;

import org.ucsf.glv.webapp.service.AnotherService;
import org.ucsf.glv.webapp.service.SimpleService;
import org.ucsf.glv.webapp.service.impl.AnotherServiceImpl;
import org.ucsf.glv.webapp.service.impl.SimpleServiceImpl;

import com.google.inject.AbstractModule;

public class GuiceModule extends AbstractModule {
	@Override
	protected void configure() {
		bind(SimpleService.class).to(SimpleServiceImpl.class);
		bind(AnotherService.class).to(AnotherServiceImpl.class);
	}
}