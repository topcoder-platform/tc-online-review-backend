package com.appirio.tech.core.api.v3.util.jwt;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTTokenValidatorTest {

    /**
     * Test validateJWTToken
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void test_validateJWTToken() throws Exception {
        List<String> validIssuers = new ArrayList<String>();
        validIssuers.add("https://api.topcoder-dev.com");
        JWTTokenValidator validator = JWTTokenValidator.getInstance(validIssuers);
        
        JWTToken token = new JWTToken();
        token.setIssuer("https://api.topcoder-dev.com");
        token.setUserId("40153938");
        token.setEmail("mtwomey@beakstar.com");
        token.setHandle("suser1");
        token.setRoles(new ArrayList<String>(){{add("administrator");add("Topcoder User");}});
        token.setExpirySeconds(24*60*6*10000);
        String tokenText = token.generateToken("secret");
        
        DecodedJWT decodedToken = validator.validateJWTToken(tokenText, "secret".getBytes());
        assertEquals("The user should be equal", "40153938", decodedToken.getClaims().get("userId").asString());
    }
    
    /**
     * Test validateJWTToken
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = IllegalArgumentException.class)
    public void test_validateJWTToken_null_token() throws Exception {
        List<String> validIssuers = new ArrayList<String>();
        validIssuers.add("https://api.topcoder-dev.com");
        JWTTokenValidator validator = JWTTokenValidator.getInstance(validIssuers);

        DecodedJWT decodedToken = validator.validateJWTToken(null, "secret".getBytes());
    }
    
    /**
     * Test validateJWTToken
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = TokenExpiredException.class)
    public void test_validateJWTToken_TokenExpiredException() throws Exception {
        List<String> validIssuers = new ArrayList<String>();
        validIssuers.add("https://api.topcoder-dev.com");
        JWTTokenValidator validator = JWTTokenValidator.getInstance(validIssuers);

        JWTToken token = new JWTToken();
        token.setIssuer("https://api.topcoder-dev.com");
        token.setUserId("40153938");
        token.setEmail("mtwomey@beakstar.com");
        token.setHandle("suser1");
        token.setRoles(new ArrayList<String>(){{add("administrator");add("Topcoder User");}});
        token.setExpirySeconds(1);
        String tokenText = token.generateToken("secret");
        
        Thread.sleep(2000);
        DecodedJWT decodedToken = validator.validateJWTToken(tokenText, "secret".getBytes());
    }
    
    /**
     * Test validateJWTToken
     *
     * @throws Exception if any error occurs
     */
    @Test(expected = InvalidTokenException.class)
    public void test_validateJWTToken_JWTException() throws Exception {
        List<String> validIssuers = new ArrayList<String>();
        validIssuers.add("https://api.topcoder-dev.com");
        JWTTokenValidator validator = JWTTokenValidator.getInstance(validIssuers);
        DecodedJWT decodedToken = validator.validateJWTToken("dummy token", "secret".getBytes());
    }
    
}
