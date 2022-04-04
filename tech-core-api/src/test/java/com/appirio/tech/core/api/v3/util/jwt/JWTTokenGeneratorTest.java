package com.appirio.tech.core.api.v3.util.jwt;

import static org.junit.Assert.assertNotNull;


import org.junit.Test;
import java.lang.System;

/**
 * Test JWTTokenGenerator
 * 
 * 
 * @author TCCoder
 * @version 1.0
 *
 */
public class JWTTokenGeneratorTest {
    private static final String CLIENT_ID = System.getProperty("TEST_AUTH0_CLIENT_ID", "5fctfjaLJHdvM04kSrCcC8yn0I4t1JTd");
    private static final String CLIENT_SECRET = System.getProperty("TEST_AUTH0_CLIENT_SECRET", " ");
    private static final String AUDIENCE = System.getProperty("TEST_AUTH0_AUDIENCE", "https://www.topcoder.com");
    private static final String AUTH_DOMAIN = System.getProperty("TEST_AUTH0_DOMAIN", "topcoder-newauth.auth0.com");

    /**
     * Test getInstance with null client id
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getInstance_null_clientId() throws Exception {
        JWTTokenGenerator generator = JWTTokenGenerator.getInstance(null, CLIENT_SECRET,
                AUDIENCE, AUTH_DOMAIN);
    }

    /**
     * Test getInstance with emppty client id
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getInstance_empty_clientId() throws Exception {
        JWTTokenGenerator generator = JWTTokenGenerator.getInstance("  ", CLIENT_SECRET,
                AUDIENCE, AUTH_DOMAIN);
    }

    /**
     * Test getInstance with null client secret
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getInstance_null_clientSecret() throws Exception {
        JWTTokenGenerator generator = JWTTokenGenerator.getInstance("null", null, AUDIENCE, AUTH_DOMAIN);
    }

    /**
     * Test getInstance with empty client secret
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getInstance_empty_clientSecret() throws Exception {
        JWTTokenGenerator generator = JWTTokenGenerator.getInstance("null", "   ", AUDIENCE, AUTH_DOMAIN);
    }

    /**
     * Test getInstance with null audience
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getInstance_null_audience() throws Exception {
        JWTTokenGenerator generator = JWTTokenGenerator.getInstance("null", "du", null, AUTH_DOMAIN);
    }

    /**
     * Test getInstance with empty audience
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getInstance_empty_audience() throws Exception {
        JWTTokenGenerator generator = JWTTokenGenerator.getInstance("null", "du", "  ", AUTH_DOMAIN);
    }

    /**
     * Test getInstance with null domain
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getInstance_null_domain() throws Exception {
        JWTTokenGenerator generator = JWTTokenGenerator.getInstance("null", "du", "au", null);
    }

    /**
     * Test getInstance with empty auth domain
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getInstance_empty_adomain() throws Exception {
        JWTTokenGenerator generator = JWTTokenGenerator.getInstance("null", "du", "au", "  ");
    }

    /**
     * Test getMatchineToken
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void test_getMatchineToken() throws Exception {
        JWTTokenGenerator generator = JWTTokenGenerator.getInstance(CLIENT_ID,
                CLIENT_SECRET, AUDIENCE, AUTH_DOMAIN);

        String token = generator.getMachineToken();
        assertNotNull("Should not be null", token);
    }

    /**
     * Test getMatchineToken
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void test_getMatchineToken_with_audience_authDomain() throws Exception {
        JWTTokenGenerator generator = JWTTokenGenerator.getInstance(CLIENT_ID,
                CLIENT_SECRET, AUDIENCE, AUTH_DOMAIN);

        String token = generator.getMachineToken(AUDIENCE, AUTH_DOMAIN);
        assertNotNull("Should not be null", token);
    }

    /**
     * Test getMatchineToken
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getMatchineToken_with_null_audience() throws Exception {
        JWTTokenGenerator generator = JWTTokenGenerator.getInstance(CLIENT_ID,
                CLIENT_SECRET, AUDIENCE, AUTH_DOMAIN);

        String token = generator.getMachineToken(null, AUTH_DOMAIN);
        assertNotNull("Should not be null", token);
    }

    /**
     * Test getMatchineToken
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getMatchineToken_with_empty_audience() throws Exception {
        JWTTokenGenerator generator = JWTTokenGenerator.getInstance(CLIENT_ID,
                CLIENT_SECRET, AUDIENCE, AUTH_DOMAIN);

        String token = generator.getMachineToken("   ", AUTH_DOMAIN);
        assertNotNull("Should not be null", token);
    }

    /**
     * Test getMatchineToken
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getMatchineToken_with_null_authDomain() throws Exception {
        JWTTokenGenerator generator = JWTTokenGenerator.getInstance(CLIENT_ID,
                CLIENT_SECRET, AUDIENCE, AUTH_DOMAIN);

        String token = generator.getMachineToken(AUDIENCE, null);
        assertNotNull("Should not be null", token);
    }

    /**
     * Test getMatchineToken
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_getMatchineToken_with_empty_authDomain() throws Exception {
        JWTTokenGenerator generator = JWTTokenGenerator.getInstance(CLIENT_ID,
                CLIENT_SECRET, AUDIENCE, AUTH_DOMAIN);

        String token = generator.getMachineToken(AUDIENCE, "   ");
        assertNotNull("Should not be null", token);
    }
}
