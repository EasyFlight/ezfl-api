package com.easyflight.flight.oauth2.token;

import com.easyflight.flight.oauth2.UserInfoPrincipal;
import com.easyflight.flight.oauth2.token.validator.AccessTokenValidator;
import com.easyflight.flight.oauth2.token.validator.FacebookAccessTokenValidator;
import com.easyflight.flight.oauth2.token.validator.result.AccessTokenValidationResult;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static java.util.Collections.singleton;

/**
 * Created by Victor Ikoro on 1/20/2018.
 */
public class FacebookTokenServices  implements ResourceServerTokenServices, InitializingBean {

    private AccessTokenValidator  accessTokenValidator;
    private RestTemplate restTemplate = new RestTemplate();
    private AccessTokenConverter tokenConverter = new DefaultAccessTokenConverter();

    private String userInfoUrl;

    public FacebookTokenServices(FacebookAccessTokenValidator accessTokenValidator) {
        this.accessTokenValidator =  accessTokenValidator;
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        AccessTokenValidationResult validationResult = accessTokenValidator.validate(accessToken);
        if (!validationResult.isValid()) {
            throw new UnapprovedClientAuthenticationException("The token is not intended to be used for this application.");
        }
        Map<String, ?> tokenInfo = validationResult.getTokenInfo();
        return getAuthentication(tokenInfo, accessToken);
    }

    private OAuth2Authentication getAuthentication(Map<String, ?> tokenInfo, String accessToken) {
        OAuth2Request request = tokenConverter.extractAuthentication(tokenInfo).getOAuth2Request();
        Authentication authentication = getAuthenticationToken(accessToken);
        return new OAuth2Authentication(request, authentication);
    }

    private Authentication getAuthenticationToken(String accessToken) {
        Map<String, ?> userInfo = getUserInfo(accessToken);
        String idStr = (String) userInfo.get("id");
        if (idStr == null) {
            throw new InternalAuthenticationServiceException("Cannot get id from user info");
        }
        return new UsernamePasswordAuthenticationToken(UserInfoPrincipal.fromFacebook(userInfo), null, singleton(new SimpleGrantedAuthority("ROLE_USER")));
    }

    private Map<String, ?> getUserInfo(String accessToken) {
        Map map = restTemplate.getForEntity(userInfoUrl + "?fields=id,name,email,first_name,last_name,picture&access_token=" + accessToken, Map.class).getBody();
        return (Map<String, Object>) map;
    }


    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        throw new UnsupportedOperationException("Not supported: read access token");
    }

    public void setUserInfoUrl(String userInfoUrl) {
        this.userInfoUrl = userInfoUrl;
    }
}
