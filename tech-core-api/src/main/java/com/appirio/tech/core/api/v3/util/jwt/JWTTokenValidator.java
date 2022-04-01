package com.appirio.tech.core.api.v3.util.jwt;

import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.auth0.jwk.GuavaCachedJwkProvider;
import com.auth0.jwk.Jwk;
import com.auth0.jwk.JwkProvider;
import com.auth0.jwk.UrlJwkProvider;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

/**
 * JWTTokenValidator is used to validate the jwt token
 * 
 * 
 * @author TCCoder
 * @version 1.0
 *
 */
public class JWTTokenValidator {
    
    /**
     * The logger
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(JWTTokenValidator.class);

    /**
     * The instances field 
     */
    private static Map<List<String>, JWTTokenValidator> instances = new HashMap<List<String>, JWTTokenValidator>();
    
    /**
     * The validIssuers field
     */
    private final List<String> validIssuers;
    
    /**
     * Create JWTTokenValidator
     *
     * @param validIssuers the validIssuers to use
     */
    private JWTTokenValidator(List<String> validIssuers) {
        this.validIssuers = new ArrayList<String>(validIssuers);
    }
    
    /**
     * Get instance, it's expected that the items of the passed in validIssuers will not change
     *
     * @param validIssuers the validIssuers to use
     * @return the JWTTokenValidator result
     */
    public static JWTTokenValidator getInstance(List<String> validIssuers) {
        JWTTokenValidator instance = instances.get(validIssuers);
        
        if (instance != null) {
            return instance;
        }
        
        instance = new JWTTokenValidator(validIssuers);
        
        instances.put(validIssuers, instance);
        
        return instance;
    }
    
    /**
     * Validate jwt token
     *
     * @param token the token to use
     * @param secret the secret to use
     * @throws JWTException if any error occurs
     * @return the DecodedJWT result
     */
    public DecodedJWT validateJWTToken(String token, byte[] secret) throws JWTException {
        if (token == null) {
            throw new IllegalArgumentException("token must be specified.");
        }

        DecodedJWT decodedJWT = null;

        // Decode only first to get the algorithm
        try {
            decodedJWT = JWT.decode(token);
        } catch (JWTDecodeException e) {
            throw new InvalidTokenException(token, "Error occurred in decoding token. " + e.getLocalizedMessage(), e);
        }

        String algorithm = decodedJWT.getAlgorithm();
        Algorithm alg = null;

        // Create the algorithm
        if ("RS256".equals(algorithm)) {

            // Validate the issuer
            if (decodedJWT.getIssuer() == null || !validIssuers.contains(decodedJWT.getIssuer())) {
                throw new InvalidTokenException(token, "Invalid issuer: " + decodedJWT.getIssuer());
            }

            // Create the JWK provider with caching
            JwkProvider urlJwkProvider = new UrlJwkProvider(decodedJWT.getIssuer());
            JwkProvider jwkProvider = new GuavaCachedJwkProvider(urlJwkProvider);

            // Get the public key and create the algorithm
            try {
                Jwk jwk = jwkProvider.get(decodedJWT.getKeyId());
                RSAPublicKey publicKey = (RSAPublicKey) jwk.getPublicKey();

                alg = Algorithm.RSA256(publicKey, null);
            } catch (Exception e) {
                throw new JWTException(token, "Error occurred in creating algorithm. " + e.getLocalizedMessage(), e);
            }
        } else if ("HS256".equals(algorithm)) {

            // Create the algorithm
            try {
                alg = Algorithm.HMAC256(secret);
            } catch (Exception e) {
                throw new JWTException(token, "Error occurred in creating algorithm. " + e.getLocalizedMessage(), e);
            }
        } else {
            throw new JWTException(token, "Algorithm not supported: " + algorithm);
        }

        // Verify
        try {
            Verification verification = JWT.require(alg);

            JWTVerifier verifier = verification.build();
            decodedJWT = verifier.verify(token);
            LOGGER.debug("Decoded JWT token" + decodedJWT);
        } catch (com.auth0.jwt.exceptions.TokenExpiredException e) {
            throw new TokenExpiredException(token, "Token is expired.", e);
        } catch (SignatureVerificationException | IllegalStateException e) {
            throw new InvalidTokenException(token, "Token is invalid. " + e.getLocalizedMessage(), e);
        } catch (Exception e) {
            throw new JWTException(token, "Error occurred in verifying token. " + e.getLocalizedMessage(), e);
        }

        return decodedJWT;
    }
}
