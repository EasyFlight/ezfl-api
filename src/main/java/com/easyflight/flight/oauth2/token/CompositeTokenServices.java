package com.easyflight.flight.oauth2.token;

import com.easyflight.flight.oauth2.token.matcher.AccessTokenMatcher;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by Victor Ikoro on 1/20/2018.
 */
public class CompositeTokenServices implements ResourceServerTokenServices, InitializingBean {

    private List<ResourceServerTokenServices> tokenServicesList;
    private List<AccessTokenMatcher> accessTokenMatchers;

    public CompositeTokenServices() {
        tokenServicesList = new ArrayList<>();
        accessTokenMatchers =  new ArrayList<>();
    }

    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public OAuth2Authentication loadAuthentication(String accessToken) throws AuthenticationException, InvalidTokenException {
        ResourceServerTokenServices tokenServices = matchTokenServices(accessToken);
        return tokenServices != null ? tokenServices.loadAuthentication(accessToken) :  null ;
    }

    @Override
    public OAuth2AccessToken readAccessToken(String accessToken) {
        ResourceServerTokenServices tokenServices = matchTokenServices(accessToken);
        return tokenServices != null ? tokenServices.readAccessToken(accessToken) :  null ;
    }

    public void addResourceServerTokenServices(ResourceServerTokenServices tokenServices, AccessTokenMatcher matcher){
        tokenServicesList.add(tokenServices);
        accessTokenMatchers.add(matcher);
    }
    private ResourceServerTokenServices matchTokenServices(String accessToken){
        Optional<AccessTokenMatcher> accessTokenMatcher = accessTokenMatchers
                .stream()
                .filter(matcher -> matcher.matches(accessToken))
                .findFirst();
        if(accessTokenMatcher.isPresent()){
            return tokenServicesList.get(accessTokenMatchers.indexOf(accessTokenMatcher.get()));
        }
        return null;
    }
}
