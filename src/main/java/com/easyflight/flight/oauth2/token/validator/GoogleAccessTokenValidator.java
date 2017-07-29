package com.easyflight.flight.oauth2.token.validator;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Victor Ikoro on 7/29/2017.
 */
public class GoogleAccessTokenValidator  implements AccessTokenValidator, InitializingBean {

    private String clientId;
    private String checkTokenUrl;

    private RestTemplate restTemplate = new RestTemplate();

    public GoogleAccessTokenValidator() {
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler() {
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if (response.getRawStatusCode() == 400) {
                    throw new InvalidTokenException("The provided token is invalid");
                }
            }
        });
    }

    @Override
    public AccessTokenValidationResult validate(String accessToken) {
        Map<String, ?> response = getGoogleResponse(accessToken);
        boolean validationResult = validateResponse(response);
        return new AccessTokenValidationResult(validationResult, response);
    }

    private boolean validateResponse(Map<String, ?> response) throws AuthenticationException {
        String aud = (String) response.get("aud");
        if (!StringUtils.equals(aud, clientId)) {
            return false;
        }
        return true;
    }

    private Map<String, ?> getGoogleResponse(String accessToken) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(new HttpHeaders());
        Map map = restTemplate.getForEntity(checkTokenUrl + "?access_token=" + accessToken, Map.class).getBody();
        return (Map<String, Object>) map;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setCheckTokenUrl(String checkTokenUrl) {
        this.checkTokenUrl = checkTokenUrl;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }
}
