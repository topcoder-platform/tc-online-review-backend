package com.appirio.tech.core.api.v3;


import java.util.Arrays;
import java.util.List;

import org.junit.Assert;

import com.appirio.tech.core.api.v3.dropwizard.APIApplication;

import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

/**
 * 
 * <p>
 * Changes in the version 1.1 (Topcoder Core API - Remove Env Variable Usage)
 * 
 * - Added method to read secret and issuers from implementation class
 * </p>
 * 
 * @author TCCODER
 * @version 1.1
 * 
 */
public class TestApplication extends APIApplication<TestConfiguration> {

	public static final String PROP_KEY_JWT_SECRET = "secret";
	
	public static final String PROP_KEY_JWT_VALID_ISSUERS = "validIssuers";
	
	@Override
	public void initialize(Bootstrap<TestConfiguration> bootstrap) {
		super.initialize(bootstrap);
	}
	
	@Override
	public void run(TestConfiguration configuration, Environment environment)
			throws Exception {
		super.run(configuration, environment);
		
		// See resources/initializer_test.yml
		String foo = System.getProperty("FOO");
		Assert.assertEquals("foo", foo);
	}
	
	@Override
	public String getSecret() {
		return System.getProperty(PROP_KEY_JWT_SECRET);
	}

	@Override
	public List<String> getValidIssuers() {
		return Arrays.asList(System.getProperty("validIssuers"));
	}

}
