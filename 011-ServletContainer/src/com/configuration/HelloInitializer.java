package com.configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class HelloInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
 
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] { HelloConfiguration.class};
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] { "/" };
	}

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return null;
	}
 
}
