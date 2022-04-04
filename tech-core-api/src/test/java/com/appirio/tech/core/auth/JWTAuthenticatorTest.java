package com.appirio.tech.core.auth;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import com.appirio.tech.core.api.v3.util.jwt.InvalidTokenException;
import com.appirio.tech.core.api.v3.util.jwt.JWTException;
import com.appirio.tech.core.api.v3.util.jwt.JWTToken;
import com.appirio.tech.core.api.v3.util.jwt.TokenExpiredException;

import io.dropwizard.auth.AuthenticationException;

/**
 * Test JWTAuthenticator
 * 
 * <p>
 * Changes in the version 1.1 (Topcoder - Support Machine To Machine Token In Core API v1.0)
 * - add the tests for the machine token
 * </p>
 * 
 * <p>
 * Changes in the version 1.2 (Topcoder Core API - Remove Env Variable Usage)
 * - Changes for reading validIssuer from constructor
 * </p>
 * 
 * @author TCCoder
 * @version 1.2
 *
 */
public class JWTAuthenticatorTest {
	
	private static List<String> validIssuers = Arrays.asList("http://tocoder.dev.com/");
    
    /**
     * Test verify token for matchine token
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testAuthenticateForMatchineToken_false() throws Exception {
        // data
        String authDomain = "DOMAIN-DUMMY";
        String secret = "SECRET-DUMMY";

        JWTToken jwt = new JWTToken();
        jwt.setUserId("USER-ID-DUMMY");
        jwt.setHandle("HANDLE-DUMMY");
        jwt.setEmail("EMAIL-DUMMY");
        List<String> roles = new ArrayList<String>();
        roles.add("ROLE1");
        roles.add("ROLE2");
        jwt.setRoles(roles);
        jwt.setIssuer(jwt.createIssuerFor(authDomain));
        String token = jwt.generateToken(secret);

        // test
        JWTAuthenticator authenticator = new JWTAuthenticator(authDomain, secret,validIssuers);
        AuthUser authUser = authenticator.authenticate(token).get();

        assertFalse("Should not be a machine token", authUser.isMachine());
    }
    
    /**
     * Test verify token for matchine token
     *
     * @throws Exception if any error occurs
     */
    @Test
    public void testAuthenticateForMatchineToken_true() throws Exception {
        // data
        String authDomain = "topcoder-dev.com";
        String secret = "secret";

        JWTToken jwt = new JWTToken();
        jwt.setHandle("HANDLE-DUMMY");
        jwt.setEmail("EMAIL-DUMMY");
        jwt.setScope("read:xxx write:yyy");
        jwt.setGrantType(JWTToken.CLIENT_CREDENTIALS);
        jwt.setIssuer(jwt.createIssuerFor(authDomain));
        jwt.setExpirySeconds(60*60*24*365);
        String token = jwt.generateToken(secret);

        // test
        JWTAuthenticator authenticator = new JWTAuthenticator(authDomain, secret,validIssuers);
        AuthUser authUser = authenticator.authenticate(token).get();

        assertTrue("Should be a machine token", authUser.isMachine());
    }

	@Test
	public void testVerifyToken() throws Exception {
		// data
		String authDomain = "DOMAIN-DUMMY";
		String secret = "SECRET-DUMMY";

		JWTToken jwt = new JWTToken();
		jwt.setUserId("USER-ID-DUMMY");
		jwt.setHandle("HANDLE-DUMMY");
		jwt.setEmail("EMAIL-DUMMY");
		List<String> roles = new ArrayList<String>();
		roles.add("ROLE1");
		roles.add("ROLE2");
		jwt.setRoles(roles);
		jwt.setIssuer(jwt.createIssuerFor(authDomain));
		String token = jwt.generateToken(secret);
		
		// test
		JWTAuthenticator authenticator = new JWTAuthenticator(authDomain, secret,validIssuers);
		JWTToken result = authenticator.verifyToken(token);
		
		// verify result
		assertNotNull(result);
		assertEquals(jwt.getUserId(), result.getUserId());
		assertEquals(jwt.getHandle(), result.getHandle());
		assertEquals(jwt.getEmail(),  result.getEmail());
		assertEquals(jwt.getRoles().size(), result.getRoles().size());
		for(int i=0; i<jwt.getRoles().size(); i++) {
			assertEquals(jwt.getRoles().get(i), result.getRoles().get(i));
		}
		assertEquals(jwt.getIssuer(), result.getIssuer());
	}
	
	@Test(expected=InvalidTokenException.class)
	public void testVerifyToken_ThrowsInvalidTokenExceptionWhenTokenIsInvalid() throws Exception {
		// data
		String authDomain = "DOMAIN-DUMMY";
		String secret = "SECRET-DUMMY";

		JWTToken jwt = new JWTToken();
		jwt.setUserId("USER-ID-DUMMY");
		jwt.setIssuer(jwt.createIssuerFor(authDomain));
		String token = jwt.generateToken(secret);
		String invalidToken = token.replaceFirst("\\.", "_");

		// test
		JWTAuthenticator authenticator = new JWTAuthenticator(authDomain, secret,validIssuers);
		authenticator.verifyToken(invalidToken);
	}

	@Test(expected=InvalidTokenException.class)
	public void testVerifyToken_ThrowsInvalidTokenExceptionWhenIssuerIsInvalid() throws Exception {
		// data
		String anotherDomain = "DOMAIN-OTHER";
		String secret = "SECRET-DUMMY";

		JWTToken jwt = new JWTToken();
		jwt.setUserId("USER-ID-DUMMY");
		jwt.setIssuer(jwt.createIssuerFor(anotherDomain));
		String tokenInOtherDomain = jwt.generateToken(secret);

		// test
		String authDomain = "DOMAIN-DUMMY";
		JWTAuthenticator authenticator = new JWTAuthenticator(authDomain, secret,validIssuers);
		authenticator.verifyToken(tokenInOtherDomain);
	}
	
	@Test(expected=TokenExpiredException.class)
	public void testVerifyToken_ThrowsTokenExpiredExceptionWhenTokenIsExpired() throws Exception {
		// data
		String authDomain = "DOMAIN-DUMMY";
		String secret = "SECRET-DUMMY";

		JWTToken jwt = new JWTToken();
		jwt.setUserId("USER-ID-DUMMY");
		jwt.setIssuer(jwt.createIssuerFor(authDomain));
		jwt.setExpirySeconds(0); // expired in 0 sec.
		String expiredToken = jwt.generateToken(secret);
		Thread.sleep(1000L);
		
		// test
		JWTAuthenticator authenticator = new JWTAuthenticator(authDomain, secret,validIssuers);
		authenticator.verifyToken(expiredToken);
	}
	
	@Test
	public void testAuthenticate() throws Exception {
		// data
		String authDomain = "DOMAIN-DUMMY";
		String secret = "SECRET-DUMMY";
		JWTToken jwt = new JWTToken();
		jwt.setUserId("USER-ID-DUMMY");
		jwt.setHandle("HANDLE-DUMMY");
		jwt.setEmail("EMAIL-DUMMY");
		List<String> roles = new ArrayList<String>();
		roles.add("ROLE1");
		roles.add("ROLE2");
		jwt.setRoles(roles);
		jwt.setIssuer(jwt.createIssuerFor(authDomain));
		String token = jwt.generateToken(secret);
		
		// test
		JWTAuthenticator authenticator = new JWTAuthenticator(authDomain, secret,validIssuers);
		Optional<AuthUser> result = authenticator.authenticate(token);
		
		
		// verify result: Optional
		assertNotNull(result);
		assertTrue("authenticated() result should be present. ", result.isPresent());
		
		// verify result: AuthUser
		AuthUser user = result.get();
		assertNotNull(user);
		assertNotNull(user.getUserId());
		assertEquals(jwt.getUserId(), user.getUserId().toString());
		assertEquals(jwt.getHandle(), user.getHandle());
		assertEquals(jwt.getEmail(),  user.getEmail());
		assertEquals(jwt.getRoles().size(), user.getRoles().size());
		for(int i=0; i<jwt.getRoles().size(); i++) {
			assertEquals(jwt.getRoles().get(i), user.getRoles().get(i));
		}
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testAuthenticate_ThrowsArgExceptionTokenIsNull() throws Exception {
		// data
		String authDomain = "DOMAIN-DUMMY";
		String secret = "SECRET-DUMMY";
		
		// test
		JWTAuthenticator authenticator = new JWTAuthenticator(authDomain, secret,validIssuers);
		authenticator.authenticate(null);
	}

	@Test
	public void testAuthenticate_ReturnsAbsentWhenInvalidToken() throws Exception {
		// cause InvalidTokenException in verifyToken(token)
		testAuthenticate_InvalidToken(new InvalidTokenException("Invalid token"));
	}

	@Test(expected=TokenExpiredException.class)
	public void testAuthenticate_ThrowsTokenExpiredExceptionWhenExpiredToken() throws Exception {
		// cause TokenExpiredException in verifyToken(token)
		testAuthenticate_InvalidToken(new TokenExpiredException("Expired token"));
	}

	@Test(expected=AuthenticationException.class)
	public void testAuthenticate_ThrowsAuthenticationExceptionWhenFatalError() throws Exception {
		// cause JWTException in verifyToken(token)
		testAuthenticate_InvalidToken(new JWTException("Bad Token", "Fatal Error"));
	}
	
	
	public void testAuthenticate_InvalidToken(final JWTException errorInVeriFyingToken) throws Exception {
		// data
		String authDomain = "DOMAIN-DUMMY";
		String secret = "SECRET-DUMMY";
		
		// test
		JWTAuthenticator authenticator = new JWTAuthenticator(authDomain, secret,validIssuers) {
			@Override protected JWTToken verifyToken(String token) throws JWTException {
				throw errorInVeriFyingToken;
		}};
		Optional<AuthUser> result = authenticator.authenticate("INVALID-TOKEN-DUMMY");
		
		// verify result: Optional
		assertNotNull(result);
		assertFalse("authenticated() result should be absent. ", result.isPresent());
	}

}
