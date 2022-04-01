/**
 * 
 */
package com.appirio.tech.core.auth;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.appirio.tech.core.api.v3.TCID;
import com.appirio.tech.core.api.v3.util.jwt.InvalidTokenException;
import com.appirio.tech.core.api.v3.util.jwt.JWTException;
import com.appirio.tech.core.api.v3.util.jwt.JWTToken;
import com.appirio.tech.core.api.v3.util.jwt.TokenExpiredException;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;

/**
 * JWTAuthenticator is used to authenticate the token
 * 
 * <p>
 * Changes in the version 1.1 (Topcoder - Support Machine To Machine Token In Core API v1.0)
 * - parse the scope and machine fields from the token
 * </p>
 * 
 * * <p>
 * Changes in the version 1.2 (Topcoder Core API - Remove Env Variable Usage)
 * - Added the valid issuers field in constructor
 * </p>
 * 
 * * <p>
 * Changes in the version 1.3 (Topcoder Core API - Remove Env Variable Usage)
 * - Changes for reading validIssuer from constructor
 * </p>
 * 
 * @author TCCoder
 * @version 1.3
 *
 */
public class JWTAuthenticator implements Authenticator<String, AuthUser> {

	private static final Logger logger = LoggerFactory.getLogger(JWTAuthenticator.class);

	public static final String DEFAULT_AUTH_DOMAIN = "topcoder-dev.com";

	private String secret;
	
	private String authDomain;
	
	private List<String> validIssuers; 
	
	public JWTAuthenticator(String authDomain, String secret,List<String> validIssuers) {
		if(secret==null || secret.length()==0)
			throw new IllegalArgumentException("secret must be specified.");
		
		this.secret = secret;
		this.validIssuers = validIssuers;
		this.authDomain = (authDomain==null || authDomain.length()==0) ? DEFAULT_AUTH_DOMAIN : authDomain;
	}
	
	/**
	 * Authenticate the token
	 * 
	 * @param token the token
	 * @return the AuthUser instance
	 */
	@Override
	public Optional<AuthUser> authenticate(String token) throws AuthenticationException {
		try {
			JWTToken jwt = verifyToken(token);
			AuthUser user = new AuthUser();
			user.setUserId(new TCID(jwt.getUserId()));
			user.setToken(token);
			user.setHandle(jwt.getHandle());
			user.setEmail(jwt.getEmail());
			user.setRoles(jwt.getRoles());
			user.setAuthDomain(authDomain);

			if (jwt.getScope() != null) {
			    user.setScope(Arrays.asList(jwt.getScope().split(" ")));
			    if (JWTToken.CLIENT_CREDENTIALS.equals(jwt.getGrantType()) && jwt.getUserId() == null 
			            && (jwt.getRoles() == null || jwt.getRoles().isEmpty())) {
			        user.setMachine(true);
			        user.setHandle(jwt.getAuthorizedParty());
			    }
			}
			return Optional.of(user);
		} catch (TokenExpiredException | InvalidTokenException e) {
			logger.info(String.format("Authentication failed with: %s, token: %s", e.getLocalizedMessage(), token));
			if(e instanceof TokenExpiredException)
				throw e; // re-throw TokenExpiredException to tell JWTAuthProvider an expiration occurred.
			
			return Optional.empty();
		} catch (JWTException e) {
			logger.error("Error occurred in authentication with error: " + e.getLocalizedMessage(), e);
			throw new AuthenticationException("Error occurred in authentication.", e);
		}
	}
	
	protected JWTToken verifyToken(String token) throws JWTException {
		JWTToken jwt = new JWTToken(token, getSecret(),this.validIssuers);
		if(!jwt.isValidIssuerFor(getAuthDomain())) {
			throw new InvalidTokenException(token, String.format("The issuer is invalid: %s", jwt.getIssuer()));
		}
		return jwt;
	}
	
	protected String getSecret() {
		return secret;
	}

	protected void setSecret(String secret) {
		this.secret = secret;
	}

	public String getAuthDomain() {
		return authDomain;
	}

	public void setAuthDomain(String authDomain) {
		this.authDomain = authDomain;
	}
}
