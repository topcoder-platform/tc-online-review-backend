package com.appirio.tech.core.api.v3.util.jwt;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.eclipse.jetty.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.exceptions.JWTDecodeException;

/**
 * JWTTokenGenerator is used to generate the machine token
 * 
 * 
 * @author TCCoder
 * @version 1.0
 *
 */
public class JWTTokenGenerator {
    /**
     * The logger
     */
    private final static Logger LOGGER = LoggerFactory.getLogger(JWTTokenGenerator.class);


    /**
     * The MACHINE_TOKEN_DATE_CACHE field
     */
    private static Map<String, Date> MACHINE_TOKEN_DATE_CACHE = new HashMap<String, Date>();


    /**
     * The MACHINE_TOKEN_CACHE field
     */
    private static Map<String, String> MACHINE_TOKEN_CACHE = new HashMap<String, String>();


    /**
     * The HTTP_CLIENT field
     */
    private static Client HTTP_CLIENT = ClientBuilder.newBuilder().build();
    
    /**
     * The clientId field
     */
    private final String clientId;

    /**
     * The clientSecret field
     */
    private final String clientSecret;

    /**
     * The audience field
     */
    private final String audience;

    /**
     * The authDomain field
     */
    private final String authDomain;
    
    /**
     * The tokenExpireTime field, in minutes
     */
    private final int tokenExpireTime;

    /**
     * The auth0 proxy server url field
     */
    private final String auth0ProxyServerUrl;
    
    /**
     * The instance field 
     */
    private static Map<String, JWTTokenGenerator> instances = new HashMap<String, JWTTokenGenerator>();
    
    
    /**
     * Create TokenUtil
     *
     * @param clientId the clientId to use
     * @param clientSecret the clientSecret to use
     * @param audience the audience to use
     * @param authDomain the authDomain to use
     */
    private JWTTokenGenerator(String clientId, String clientSecret, String audience, String authDomain, String auth0ProxyServerUrl) {
        if (clientId == null || clientId.trim().length() == 0) {
            throw new IllegalArgumentException("The clientId should be non-null and non-empty string");
        }
        if (clientSecret == null || clientSecret.trim().length() == 0) {
            throw new IllegalArgumentException("The clientSecret should be non-null and non-empty string");
        }
        if (audience == null || audience.trim().length() == 0) {
            throw new IllegalArgumentException("The audience should be non-null and non-empty string");
        }
        if (authDomain == null || authDomain.trim().length() == 0) {
            throw new IllegalArgumentException("The authDomain should be non-null and non-empty string");
        }
        

        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.authDomain = authDomain;
        this.audience = audience;
        this.auth0ProxyServerUrl = auth0ProxyServerUrl;
        this.tokenExpireTime = 60 * 24;
    }
    
    /**
     * Get instance, the token expiration time is default to 24 hours
     *
     * @param clientId the clientId to use
     * @param clientSecret the clientSecret to use
     * @param audience the audience to use
     * @param authDomain the authDomain to use
     * @throws Exception if any error occurs
     * @return the JWTTokenGenerator result
     */
    public static JWTTokenGenerator getInstance(String clientId, String clientSecret, String audience, String authDomain) throws Exception {
        return getInstance(clientId, clientSecret, audience, authDomain, 60 * 24, "");
    }
    
    /**
     * Get instance
     *
     * @param clientId the clientId to use
     * @param clientSecret the clientSecret to use
     * @param audience the audience to use
     * @param authDomain the authDomain to use
     * @param tokenExpireTime the tokenExpireTime to use, in minutes
     * @throws Exception if any error occurs
     * @return the JWTTokenGenerator result
     */
    public static JWTTokenGenerator getInstance(String clientId, String clientSecret, String audience, String authDomain, int tokenExpireTime, String auth0ProxyServerUrl) throws Exception {
        String key = clientId + audience + authDomain;
        JWTTokenGenerator instance = instances.get(key);
        if (instance != null) {
            return instance;
        }
        
        instance = new JWTTokenGenerator(clientId, clientSecret, audience, authDomain, auth0ProxyServerUrl);
        instances.put(key, instance);
        
        return instance;
    }
    
    /**
     * Get machine token
     *
     * @throws Exception if any error occurs
     * @return the String result
     */
    public String getMachineToken() throws Exception {
        return getMachineToken(this.clientId, this.clientSecret, this.audience, this.authDomain, this.auth0ProxyServerUrl);
    }
    
    /**
     * Get machine token
     *
     * @param audience the audience to use
     * @param authDomain the authDomain to use
     * @throws Exception if any error occurs
     * @return the String result
     */
    public String getMachineToken(String audience, String authDomain) throws Exception {
        return getMachineToken(this.clientId, this.clientSecret, audience, authDomain, "");
    }

    /**
     * Get token expiry time in seconds
     *
     * @param token JWT token
     * throws Exception if any error occurs
     * @return the Integer result 
     */
     private Integer getTokenExpiryTime(String token) throws Exception {
       DecodedJWT decodedJWT = null;
       Integer tokenExpiryTime = 0;
        if (token != null) { 
            try {
                decodedJWT = JWT.decode(token);
            } catch (JWTDecodeException e) {
                throw new InvalidTokenException(token, "Error occurred in decoding token. " + e.getLocalizedMessage(), e);
            }
           Date tokenExpiryDate =  decodedJWT.getExpiresAt();
           Long tokenExpiryTimeInMilliSeconds = tokenExpiryDate.getTime() - (new Date().getTime()) - 60*1000; // minus 60 seconds
           tokenExpiryTime = (int) Math.floor(tokenExpiryTimeInMilliSeconds / 1000); 
        }
        return tokenExpiryTime;
     }
    
    /**
     * Get machine token
     *
     * @param clientId the clientId to use
     * @param clientSecret the clientSecret to use
     * @param audience the audience to use
     * @param domain the domain to use
     * @param auth0ProxyServerUrl auth0 proxy server url 
     * @throws Exception if any error occurs
     * @return the String result
     */
    private String getMachineToken(String clientId, String clientSecret, String audience, String domain, String auth0ProxyServerUrl) throws Exception {
        if (audience == null || audience.trim().length() == 0) {
            throw new IllegalArgumentException("The audience should be non-null and non-empty string");
        }

        if (domain == null || domain.trim().length() == 0) {
            throw new IllegalArgumentException("The domain should be non-null and non-empty string");
        }

        String mapKey = clientId + clientSecret;
        Date date = MACHINE_TOKEN_DATE_CACHE.get(mapKey);

        String token;
        token = MACHINE_TOKEN_CACHE.get(mapKey);

        Boolean isAppCachedTokenExpired = false; 

        DecodedJWT decodedJWT = null;
        if (token != null) {  
           if (getTokenExpiryTime(token) <= 0) {
                isAppCachedTokenExpired = true;
                LOGGER.info("Application cached token expired");
           }
        }

        if ((token == null) || isAppCachedTokenExpired ) {

            String auth0Url = "https://" + domain + "/oauth/token" ;
            String authServerUrl = auth0ProxyServerUrl != null && auth0ProxyServerUrl.trim().length() > 0 ? auth0ProxyServerUrl : auth0Url;

            WebTarget target = HTTP_CLIENT.target(authServerUrl);

            Map<String, String> postMap = new HashMap<String, String>();
            postMap.put("client_id", clientId);
            postMap.put("client_secret", clientSecret);
            postMap.put("grant_type", "client_credentials");
            postMap.put("audience",  audience);
            postMap.put("auth0_url",  auth0Url);
            Response response = target.request(MediaType.APPLICATION_JSON).post(Entity.json(postMap));

            if (response.getStatusInfo().getStatusCode() != HttpStatus.OK_200) {
                LOGGER.error("Unable to get token: {}", response);
                throw new Exception("Unable to get the token, the returned status code is: " + response.getStatusInfo().getStatusCode());
            }

            JsonNode node = response.readEntity(JsonNode.class);
            token = node.path("access_token").asText();
            LOGGER.info("Fetched token from URL: {}", authServerUrl);
            MACHINE_TOKEN_CACHE.put(mapKey, token);
            LOGGER.info("App cached token expires in seconds: {}", getTokenExpiryTime(token));
        } 

        return token;
    }
}
