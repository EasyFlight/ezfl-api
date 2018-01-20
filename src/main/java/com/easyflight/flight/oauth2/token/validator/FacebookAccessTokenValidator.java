package com.easyflight.flight.oauth2.token.validator;

import com.easyflight.flight.oauth2.token.validator.result.AccessTokenValidationResult;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * Created by Victor Ikoro on 1/20/2018.
 */
public class FacebookAccessTokenValidator implements AccessTokenValidator {

    private String[] clientIds;
    private String checkTokenUrl;
    private String appToken;

    private RestTemplate restTemplate = new RestTemplate();

    public FacebookAccessTokenValidator(String clientId, String clientSecret) {
        appToken =  String.join("|", clientId,clientSecret);
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
        Map<String, ?> response = getFacebookResponse(accessToken);
        boolean validationResult = validateResponse(response);
        return new AccessTokenValidationResult(validationResult, response);
    }

    private boolean validateResponse(Map<String, ?> response) throws AuthenticationException {
        Map data = (Map) response.get("data");
        if(data == null){
            return false;
        }
        String appId = String.valueOf(data.get("app_id"));
        for (String clientId: clientIds) {
            if (StringUtils.equals(appId, clientId.trim())) {
                return true;
            }
        }
        return false;
    }

    private Map<String, ?> getFacebookResponse(String accessToken) {
        HttpEntity<Object> requestEntity = new HttpEntity<>(new HttpHeaders());
        Map map = restTemplate.getForEntity(checkTokenUrl +"?input_token=" + accessToken +"&access_token=" + appToken, Map.class).getBody();
        return (Map<String, Object>) map;
    }

    public void setClientIds(String[] clientIds) {
        this.clientIds = clientIds;
    }

    public void setCheckTokenUrl(String checkTokenUrl) {
        this.checkTokenUrl = checkTokenUrl;
    }


}
