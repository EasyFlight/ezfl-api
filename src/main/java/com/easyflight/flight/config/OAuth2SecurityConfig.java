package com.easyflight.flight.config;

import com.easyflight.flight.oauth2.manager.WhitelistOAuth2AuthenticationManager;
import com.easyflight.flight.oauth2.token.CompositeTokenServices;
import com.easyflight.flight.oauth2.token.FacebookTokenServices;
import com.easyflight.flight.oauth2.token.GoogleTokenServices;
import com.easyflight.flight.oauth2.token.matcher.AccessTokenMatcher;
import com.easyflight.flight.oauth2.token.matcher.GoogleAccessTokenMatcher;
import com.easyflight.flight.oauth2.token.validator.FacebookAccessTokenValidator;
import com.easyflight.flight.oauth2.token.validator.GoogleAccessTokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * Created by Victor Ikoro on 7/29/2017.
 */
@Configuration
public class OAuth2SecurityConfig {


    private String googleUserInfoUri;
    private String googleTokenInfoUri;
    private String googleClientId;
    private String googleTokenClientsWhitelist;
    private final String facebookUserInfoUri;
    private final String facebookTokenInfoUri;
    private final String facebookClientId;
    private final String facebookSecret;
    private final String facebookTokenClientsWhitelist;

    public OAuth2SecurityConfig(
            @Value("${google.resources.userInfoUri}") String googleUserInfoUri,
            @Value("${google.resources.tokenInfoUri}") String googleTokenInfoUri,
            @Value("${google.client.id}") String googleClientId,
            @Value("${token.clients.google.whitelist}") String googleTokenClientsWhitelist,
            @Value("${facebook.resources.userInfoUri}") String facebookUserInfoUri,
            @Value("${facebook.resources.tokenInfoUri}") String facebookTokenInfoUri,
            @Value("${facebook.client.id}") String facebookClientId,
            @Value("${facebook.client.secret}") String facebookSecret,
            @Value("${token.clients.facebook.whitelist}") String facebookTokenClientsWhitelist
    ) {
        this.googleUserInfoUri = googleUserInfoUri;
        this.googleTokenInfoUri = googleTokenInfoUri;
        this.googleClientId = googleClientId;
        this.googleTokenClientsWhitelist = googleTokenClientsWhitelist;

        this.facebookUserInfoUri = facebookUserInfoUri;
        this.facebookTokenInfoUri = facebookTokenInfoUri;
        this.facebookClientId = facebookClientId;
        this.facebookSecret = facebookSecret;
        this.facebookTokenClientsWhitelist = facebookTokenClientsWhitelist;
    }

    @Bean
    public ResourceServerTokenServices tokenServices(){
        CompositeTokenServices compositeTokenServices = new CompositeTokenServices();

        GoogleAccessTokenValidator googleAccessTokenValidator =  new GoogleAccessTokenValidator();
        googleAccessTokenValidator.setClientIds(googleTokenClientsWhitelist.split(","));
        googleAccessTokenValidator.setCheckTokenUrl(googleTokenInfoUri);
        GoogleTokenServices googleTokenServices = new GoogleTokenServices(googleAccessTokenValidator);
        googleTokenServices.setUserInfoUrl(googleUserInfoUri);

        FacebookAccessTokenValidator facebookAccessTokenValidator = new FacebookAccessTokenValidator(facebookClientId, facebookSecret);
        facebookAccessTokenValidator.setClientIds(googleTokenClientsWhitelist.split(","));
        facebookAccessTokenValidator.setCheckTokenUrl(googleTokenInfoUri);
        FacebookTokenServices facebookTokenServices = new FacebookTokenServices(facebookAccessTokenValidator);
        facebookTokenServices.setUserInfoUrl(facebookUserInfoUri);

        compositeTokenServices.addResourceServerTokenServices(googleTokenServices, new GoogleAccessTokenMatcher());
        compositeTokenServices.addResourceServerTokenServices(facebookTokenServices, AccessTokenMatcher.ALL);
        return compositeTokenServices;
    }



}
