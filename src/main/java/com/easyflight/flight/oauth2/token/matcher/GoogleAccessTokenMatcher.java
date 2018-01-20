package com.easyflight.flight.oauth2.token.matcher;

import org.springframework.util.StringUtils;

/**
 * Created by Victor Ikoro on 1/20/2018.
 */
public class GoogleAccessTokenMatcher implements AccessTokenMatcher {
    @Override
    public boolean matches(String accessToken) {
        return StringUtils.hasText(accessToken) && accessToken.startsWith("ya29.");
    }
}
