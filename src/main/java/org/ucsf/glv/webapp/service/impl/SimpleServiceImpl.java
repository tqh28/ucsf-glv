package org.ucsf.glv.webapp.service.impl;

import org.ucsf.glv.webapp.service.AnotherService;
import org.ucsf.glv.webapp.service.SimpleService;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class SimpleServiceImpl implements SimpleService {	
	@Inject
	AnotherService anotherService;

	@Override
	public String getMessage() {
		return "Howdy, " + " " + anotherService.provideName();
	}
}