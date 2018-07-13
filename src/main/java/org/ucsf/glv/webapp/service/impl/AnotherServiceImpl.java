package org.ucsf.glv.webapp.service.impl;

import org.ucsf.glv.webapp.service.AnotherService;

public class AnotherServiceImpl implements AnotherService {
	@Override
	public String provideName() {
		return "foo";
	}
}