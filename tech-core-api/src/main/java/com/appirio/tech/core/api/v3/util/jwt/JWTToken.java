package com.appirio.tech.core.api.v3.util.jwt;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Represents the JWTToken
 * 
 * Changes in the version 1.1 (Topcoder - Support Machine To Machine Token In Core API v1.0)
 * - add the scope/grantType fields
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
public class JWTToken {

	private static final Logger logger = Logger.getLogger(JWTToken.class);
	

	public static final String CLAIM_USER_ID = "userId";
	public static final String CLAIM_HANDLE = "handle";
	public static final String CLAIM_EMAIL = "email";
	public static final String CLAIM_ROLES = "roles";

	public static final String CLAIM_ISSUER = "iss";
	public static final String CLAIM_ISSUED_TIME = "iat";
	public static final String CLAIM_EXPIRATION_TIME = "exp";
	public static final String CLAIM_AUTHORIZED_PARTY = "azp";
	
	/**
     * The CLAIM_SCOPE field
     */
	public static final String CLAIM_SCOPE = "scope";
	
	/**
     * The CLAIM_GRANT_TYPE field
     */
	public static final String CLAIM_GRANT_TYPE = "gty";
	
	/**
     * The CLIENT_CREDENTIALS field
     */
	public static final String CLIENT_CREDENTIALS = "client-credentials";

	public static final int DEFAULT_EXP_SECONDS = 10 * 60; // 10 mins

	public static final String ISSUER_TEMPLATE = "https://api.%s";

	public String userId;

	public String handle;

	public String email;

	public String issuer;

	public List<String> roles = new ArrayList<>();

	public Integer expirySeconds = DEFAULT_EXP_SECONDS;

	public String algorithm = "HS256";

	protected SecretEncoder encoder = new SecretEncoder();
	
	
    /**
     * Represents the scope attribute
     */
	private String scope;
	
	/**
     * The grant type field
     */
	private String grantType;
	
	/**
	 *  The Valid Issuers
	 */
	List<String> validIssuers;

	/**
	 * The AuthorizedParty
	 */
	private String authorizedParty;

	
	public JWTToken() {};

	public JWTToken(List<String> validIssuers) {
		this.validIssuers = validIssuers;
	}

	public JWTToken(String token, String secret,List<String> validIssuers) {
		this(validIssuers);
		verifyAndApply(token, secret);
	}

	/**
     * generate the token
     *
     * @return the token result
     */
	public String generateToken(String secret) {
		if (secret == null || secret.length() == 0)
			throw new IllegalArgumentException("secret must be specified");

		JWTCreator.Builder jwtBuilder = JWT.create();
		jwtBuilder.withClaim(CLAIM_USER_ID, getUserId());
		jwtBuilder.withClaim(CLAIM_HANDLE, getHandle());
		jwtBuilder.withClaim(CLAIM_EMAIL, getEmail());
		jwtBuilder.withArrayClaim(CLAIM_ROLES, getRoles().toArray(new String[getRoles().size()]));
		jwtBuilder.withClaim(CLAIM_ISSUER, getIssuer());
		jwtBuilder.withClaim(CLAIM_SCOPE, this.getScope());
		jwtBuilder.withClaim(CLAIM_GRANT_TYPE, this.getGrantType());
		jwtBuilder.withClaim(CLAIM_AUTHORIZED_PARTY, this.getAuthorizedParty());
		Date expireTime = new Date();
		expireTime.setTime(expireTime.getTime()
				+ (getExpirySeconds() != null ? getExpirySeconds() * 1000 : DEFAULT_EXP_SECONDS * 1000));
		jwtBuilder.withExpiresAt(expireTime);
		jwtBuilder.withJWTId(UUID.randomUUID().toString());
		jwtBuilder.withIssuedAt(new Date());

		String jwt = null;
		try {
			jwt = jwtBuilder.sign(Algorithm.HMAC256(secret));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return jwt;
	}

	public JWTToken verifyAndApply(String token, String secret) throws JWTException {
		return verifyAndApply(token, secret, this.encoder);
	}

	public JWTToken apply(String token) throws JWTException {
		return apply(parse(token));
	}

	protected JWTToken verifyAndApply(String token, String secret, SecretEncoder enc)
			throws JWTException {
		if (secret == null || secret.length() == 0)
			throw new IllegalArgumentException("secret must be specified.");
		if (enc == null)
			enc = new SecretEncoder();
		return verifyAndApply(token, enc.encode(secret));
	}

	protected JWTToken verifyAndApply(String token, byte[] secretBytes) throws JWTException {
		DecodedJWT decodedJWT = JWTTokenValidator.getInstance(this.validIssuers).validateJWTToken(token, secretBytes);
		this.algorithm = decodedJWT.getAlgorithm();

		return decodedJWTApply(decodedJWT);
	}

	@SuppressWarnings("unchecked")
	protected JWTToken apply(Map<String, Object> map) {
		if (map == null || map.size() == 0) {
			return this;
		}

		// Search for the custom claims
		for (Entry<String, Object> entry : map.entrySet()) {
			String key = entry.getKey();
			Object claim = entry.getValue();

			if (key.contains(CLAIM_USER_ID)) {
				setUserId((String) claim);
			} else if (key.contains(CLAIM_EMAIL)) {
				setEmail((String) claim);
			} else if (key.contains(CLAIM_HANDLE)) {
				setHandle((String) claim);
			} else if (key.contains(CLAIM_ROLES)) {
				setRoles((List<String>) claim);
			}
		}

		setIssuer((String) map.get(CLAIM_ISSUER));
		Integer iat = (Integer) map.get(CLAIM_ISSUED_TIME);
		Integer exp = (Integer) map.get(CLAIM_EXPIRATION_TIME);
		if (exp != null) {
			setExpirySeconds(calcExpirySeconds(exp, iat));
		}

		return this;
	}

	/**
     * Decode jwt token and apply
     *
     * @return the JWTToken
     */
	@SuppressWarnings("unchecked")
	protected JWTToken decodedJWTApply(DecodedJWT decodedJWT) {
		// Search for the custom claims
		for (Entry<String, Claim> entry : decodedJWT.getClaims().entrySet()) {
			String key = entry.getKey();
			Claim claim = entry.getValue();

			if (key.contains(CLAIM_USER_ID)) {
				setUserId(claim.asString());
			} else if (key.contains(CLAIM_EMAIL)) {
				setEmail(claim.asString());
			} else if (key.contains(CLAIM_HANDLE)) {
				setHandle(claim.asString());
			} else if (key.contains(CLAIM_ROLES)) {
				setRoles(claim.as(List.class));
			} else if (CLAIM_SCOPE.equalsIgnoreCase(key)) {
			    this.setScope(claim.asString());
			} else if (CLAIM_GRANT_TYPE.equalsIgnoreCase(key)) {
			    this.setGrantType(claim.asString());
			}
		}

		setIssuer(decodedJWT.getClaim(CLAIM_ISSUER).asString());
		setAuthorizedParty(decodedJWT.getClaim(CLAIM_AUTHORIZED_PARTY).asString());
		Integer iat = decodedJWT.getClaim(CLAIM_ISSUED_TIME).asInt();
		Integer exp = decodedJWT.getClaim(CLAIM_EXPIRATION_TIME).asInt();
		if (exp != null) {
			setExpirySeconds(calcExpirySeconds(exp, iat));
		}

		return this;
	}

	@SuppressWarnings("unchecked")
	protected Map<String, Object> parse(String token) throws JWTException {
		if (token == null || token.length() == 0)
			throw new IllegalArgumentException("token must be specified.");

		String[] pieces = token.split("\\.");
		if (pieces.length != 3)
			throw new InvalidTokenException(token, "Wrong number of segments in jwt: " + pieces.length);

		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = new String(Base64.decodeBase64(pieces[1]), "UTF-8");
			JsonNode jwtClaim = mapper.readValue(jsonString, JsonNode.class);
			return mapper.treeToValue(jwtClaim, Map.class);
		} catch (Exception e) {
			throw new InvalidTokenException(token, e);
		}
	}

	protected Integer calcExpirySeconds(Integer exp, Integer iat) {
		if (exp == null)
			return null;
		int issuedAt = iat != null ? iat : (int) (System.currentTimeMillis() / 1000L);
		return exp - issuedAt;
	}

	/**
	 * Creates iss field data from the domain.
	 * 
	 * @param authDomain
	 * @return
	 */
	public String createIssuerFor(String authDomain) {
		if (authDomain == null || authDomain.length() == 0)
			throw new IllegalArgumentException("authDomain must be specifeid.");
		return String.format(ISSUER_TEMPLATE, authDomain);
	}

	public boolean isValidIssuerFor(String authDomain) {
		if ("RS256".equals(this.algorithm)) {
			// For RS256, the issuer validation was done during verification
			return true;
		}
		
		return createIssuerFor(authDomain).equals(getIssuer());
	}

	public String getUserId() {
		return userId;
	}

	/**
	 * Set JWT claim "userId" (private).
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getHandle() {
		return handle;
	}

	/**
	 * Set JWT claim "handle" (private).
	 */
	public void setHandle(String handle) {
		this.handle = handle;
	}

	public String getEmail() {
		return email;
	}

	/**
	 * Set JWT claim "email" (private).
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	public String getIssuer() {
		return issuer;
	}

	/**
	 * Set JWT claim "roles" (private).
	 * 
	 * @param roles
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public List<String> getRoles() {
		return roles;
	}

	/**
	 * Set JWT claim "iss".
	 */
	public void setIssuer(String issuer) {
		this.issuer = issuer;
	}

	public Integer getExpirySeconds() {
		return expirySeconds;
	}

	/**
	 * Set JWT claim "exp" to current timestamp plus this value.
	 */
	public void setExpirySeconds(Integer expirySeconds) {
		this.expirySeconds = expirySeconds;
	}

	public String getAlgorithm() {
		return algorithm;
	}

	/**
	 * Set algorithm (default: HS256 [HMAC SHA-256])
	 */
	public void setAlgorithm(String algorithm) {
		this.algorithm = algorithm;
	}

	public static class SecretEncoder {
		public byte[] encode(String secret) {
			return secret != null ? secret.getBytes() : null;
		}
	}

	public static class Base64SecretEncoder extends SecretEncoder {
		public byte[] encode(String secret) {
			return secret != null ? Base64.decodeBase64(secret) : null;
		}
	}

    /**
     * Get scope
     * 
     * @return the scope
     */
    public String getScope() {
        return this.scope;
    }

    /**
     * Set scope
     * 
     * @return the scope to set
     */
    public void setScope(String scope) {
        this.scope = scope;
    }

	/**
	 * Get the authorized party
	 *
	 * @return the authorized party
	 */
	public String getAuthorizedParty() {
    	return this.authorizedParty;
	}

	/**
	 * Set authorized party
	 *
	 * @return the authorized party to set
	 */
	public void setAuthorizedParty(String authorizedParty) {
    	this.authorizedParty = authorizedParty;
	}

    /**
     * Get grantType
     * 
     * @return the grantType
     */
    public String getGrantType() {
        return this.grantType;
    }

    /**
     * Set grantType
     * 
     * @return the grantType to set
     */
    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }
}
