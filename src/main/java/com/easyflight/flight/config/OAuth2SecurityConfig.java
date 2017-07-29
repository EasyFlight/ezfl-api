package com.easyflight.flight.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

/**
 * Created by Victor.Ikoro on 6/29/2017.
 */

@EnableOAuth2Client
@Configuration
public class OAuth2SecurityConfig extends WebSecurityConfigurerAdapter {



}
