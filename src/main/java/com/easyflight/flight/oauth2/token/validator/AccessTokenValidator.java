package com.easyflight.flight.oauth2.token.validator;

/**
 * Created by Victor Ikoro on 7/29/2017.
 */
public interface AccessTokenValidator {

    AccessTokenValidationResult validate(String accessToken) ;

}
