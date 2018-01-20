package com.easyflight.flight.oauth2.token.matcher;

/**
 * Created by Victor Ikoro on 1/20/2018.
 */
public interface AccessTokenMatcher {
    AccessTokenMatcher ALL = accessToken -> true;

    boolean matches(String accessToken);
}
