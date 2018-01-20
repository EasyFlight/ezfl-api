package com.easyflight.flight.config;

import com.easyflight.flight.oauth2.manager.WhitelistOAuth2AuthenticationManager;
import com.easyflight.flight.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;

/**
 * Created by Victor Ikoro on 7/29/2017.
 */
@Configuration
@EnableResourceServer
@EnableWebSecurity
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {


    private final String tokenClientsWhitelist;
    private String googleClientId;
    private ResourceServerTokenServices tokenServices;
    private UserService userService;

    @Autowired
    public ResourceServerConfig(
            @Value("${google.client.id}") String googleClientId,
            @Value("${token.clients.whitelist.google}") String googleTokenClientsWhitelist,
            @Value("${token.clients.whitelist.facebook}") String facebookTokenClientsWhitelist,
            ResourceServerTokenServices tokenServices,
            UserService userService) {
        this.googleClientId = googleClientId;
        this.userService = userService;
        this.tokenClientsWhitelist = String.join(",", googleTokenClientsWhitelist, facebookTokenClientsWhitelist);
        this.tokenServices = tokenServices;
    }

    @Override
    public void configure(final HttpSecurity http) throws Exception {
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/v1/flights/**").permitAll().anyRequest().hasRole("USER");
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId(googleClientId);
        resources.authenticationManager(authenticationManager());
    }

    private OAuth2AuthenticationManager authenticationManager(){
        WhitelistOAuth2AuthenticationManager manager = new WhitelistOAuth2AuthenticationManager();
        manager.setResourceIds(tokenClientsWhitelist.split(","));
        manager.setTokenServices(tokenServices);
        manager.setUserService(userService);
        return manager;
    }


}
