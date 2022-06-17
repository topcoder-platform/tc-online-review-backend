package com.topcoder.onlinereview.component.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JWTTokenGenerator {
    private static final Logger LOGGER = LoggerFactory.getLogger(JWTTokenGenerator.class);
    private static Map<String, Date> MACHINE_TOKEN_DATE_CACHE = new HashMap();
    private static Map<String, String> MACHINE_TOKEN_CACHE = new HashMap();
    private static Client HTTP_CLIENT = ClientBuilder.newBuilder().build();
    private final String clientId;
    private final String clientSecret;
    private final String audience;
    private final String authDomain;
    private final int tokenExpireTime;
    private final String auth0ProxyServerUrl;
    private static Map<String, JWTTokenGenerator> instances = new HashMap();

    private JWTTokenGenerator(String clientId, String clientSecret, String audience, String authDomain, String auth0ProxyServerUrl) {
        if (clientId != null && clientId.trim().length() != 0) {
            if (clientSecret != null && clientSecret.trim().length() != 0) {
                if (audience != null && audience.trim().length() != 0) {
                    if (authDomain != null && authDomain.trim().length() != 0) {
                        this.clientId = clientId;
                        this.clientSecret = clientSecret;
                        this.authDomain = authDomain;
                        this.audience = audience;
                        this.auth0ProxyServerUrl = auth0ProxyServerUrl;
                        this.tokenExpireTime = 1440;
                    } else {
                        throw new IllegalArgumentException("The authDomain should be non-null and non-empty string");
                    }
                } else {
                    throw new IllegalArgumentException("The audience should be non-null and non-empty string");
                }
            } else {
                throw new IllegalArgumentException("The clientSecret should be non-null and non-empty string");
            }
        } else {
            throw new IllegalArgumentException("The clientId should be non-null and non-empty string");
        }
    }

    public static JWTTokenGenerator getInstance(String clientId, String clientSecret, String audience, String authDomain) throws Exception {
        return getInstance(clientId, clientSecret, audience, authDomain, 1440, "");
    }

    public static JWTTokenGenerator getInstance(String clientId, String clientSecret, String audience, String authDomain, int tokenExpireTime, String auth0ProxyServerUrl) throws Exception {
        String key = clientId + audience + authDomain;
        JWTTokenGenerator instance = (JWTTokenGenerator)instances.get(key);
        if (instance != null) {
            return instance;
        } else {
            instance = new JWTTokenGenerator(clientId, clientSecret, audience, authDomain, auth0ProxyServerUrl);
            instances.put(key, instance);
            return instance;
        }
    }

    public String getMachineToken() throws Exception {
        return this.getMachineToken(this.clientId, this.clientSecret, this.audience, this.authDomain, this.auth0ProxyServerUrl);
    }

    public String getMachineToken(String audience, String authDomain) throws Exception {
        return this.getMachineToken(this.clientId, this.clientSecret, audience, authDomain, "");
    }

    private Integer getTokenExpiryTime(String token) throws Exception {
        DecodedJWT decodedJWT = null;
        Integer tokenExpiryTime = 0;
        if (token != null) {
            try {
                decodedJWT = JWT.decode(token);
            } catch (JWTDecodeException var6) {
                throw new InvalidTokenException(token, "Error occurred in decoding token. " + var6.getLocalizedMessage(), var6);
            }

            Date tokenExpiryDate = decodedJWT.getExpiresAt();
            Long tokenExpiryTimeInMilliSeconds = tokenExpiryDate.getTime() - (new Date()).getTime() - 60000L;
            tokenExpiryTime = (int)Math.floor((double)(tokenExpiryTimeInMilliSeconds / 1000L));
        }

        return tokenExpiryTime;
    }

    private String getMachineToken(String clientId, String clientSecret, String audience, String domain, String auth0ProxyServerUrl) throws Exception {
        if (audience != null && audience.trim().length() != 0) {
            if (domain != null && domain.trim().length() != 0) {
                String mapKey = clientId + clientSecret;
                Date date = (Date)MACHINE_TOKEN_DATE_CACHE.get(mapKey);
                String token = (String)MACHINE_TOKEN_CACHE.get(mapKey);
                Boolean isAppCachedTokenExpired = false;
                DecodedJWT decodedJWT = null;
                if (token != null && this.getTokenExpiryTime(token) <= 0) {
                    isAppCachedTokenExpired = true;
                    LOGGER.info("Application cached token expired");
                }

                if (token == null || isAppCachedTokenExpired) {
                    String auth0Url = "https://" + domain + "/oauth/token";
                    String authServerUrl = auth0ProxyServerUrl != null && auth0ProxyServerUrl.trim().length() > 0 ? auth0ProxyServerUrl : auth0Url;
                    WebTarget target = HTTP_CLIENT.target(authServerUrl);
                    Map<String, String> postMap = new HashMap();
                    postMap.put("client_id", clientId);
                    postMap.put("client_secret", clientSecret);
                    postMap.put("grant_type", "client_credentials");
                    postMap.put("audience", audience);
                    postMap.put("auth0_url", auth0Url);
                    // chore
                    StringBuilder rBody = new StringBuilder();
                    Entity.json(postMap).getEntity().forEach((key, value) -> rBody.append("," + key + ":" + value));
                    LOGGER.info(rBody.toString());
                    Response response = target.request(new String[]{"application/json"}).post(Entity.json(postMap));
                    if (response.getStatusInfo().getStatusCode() != 200) {
                        LOGGER.error("Unable to get token: {}", response);
                        throw new Exception("Unable to get the token, the returned status code is: " + response.getStatusInfo().getStatusCode());
                    }

                    JsonNode node = (JsonNode)response.readEntity(JsonNode.class);
                    token = node.path("access_token").asText();
                    LOGGER.info("Fetched token from URL: {}", authServerUrl);
                    MACHINE_TOKEN_CACHE.put(mapKey, token);
                    LOGGER.info("App cached token expires in seconds: {}", this.getTokenExpiryTime(token));
                }

                return token;
            } else {
                throw new IllegalArgumentException("The domain should be non-null and non-empty string");
            }
        } else {
            throw new IllegalArgumentException("The audience should be non-null and non-empty string");
        }
    }
}