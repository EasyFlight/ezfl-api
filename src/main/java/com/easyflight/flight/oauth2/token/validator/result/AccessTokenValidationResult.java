package com.easyflight.flight.oauth2.token.validator.result;

import java.util.Map;

/**
 * Created by Victor Ikoro on 7/29/2017.
 */
public class AccessTokenValidationResult {

    private boolean valid;
    private final Map<String, ?> tokenInfo;

    public AccessTokenValidationResult(boolean valid, Map<String, ?> tokenInfo){

        this.valid = valid;
        this.tokenInfo = tokenInfo;
    }

    public boolean isValid() {
        return valid;
    }

    public Map<String, ?> getTokenInfo() {
        return tokenInfo;
    }
}
