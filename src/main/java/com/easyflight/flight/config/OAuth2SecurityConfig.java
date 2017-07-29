package com.easyflight.flight.config;

import com.easyflight.flight.oauth2.token.GoogleTokenServices;
import com.easyflight.flight.oauth2.token.validator.GoogleAccessTokenValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * Created by Victor Ikoro on 7/29/2017.
 */
@Configuration
public class OAuth2SecurityConfig {


    private String googleUserInfoUri;
    private String googleTokenInfoUri;
    private String googleClientId;

    public OAuth2SecurityConfig(
            @Value("${google.resources.userInfoUri}") String googleUserInfoUri,
            @Value("${google.resources.tokenInfoUri}") String googleTokenInfoUri,
            @Value("${google.client.id}") String googleClientId) {
        this.googleUserInfoUri = googleUserInfoUri;
        this.googleTokenInfoUri = googleTokenInfoUri;
        this.googleClientId = googleClientId;
    }

    @Bean
    public ResourceServerTokenServices tokenServices(){
        GoogleAccessTokenValidator validator =  new GoogleAccessTokenValidator();
        validator.setClientId(googleClientId);
        validator.setCheckTokenUrl(googleTokenInfoUri);
        GoogleTokenServices tokenServices = new GoogleTokenServices(validator);
        tokenServices.setUserInfoUrl(googleUserInfoUri);
        return tokenServices;
    }
}
