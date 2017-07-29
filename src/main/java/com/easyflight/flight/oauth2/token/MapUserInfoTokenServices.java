package com.easyflight.flight.oauth2.token;

/**
 * Created by Victor Ikoro on 7/13/2017.
 */


import org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices;

import java.util.Map;


public class MapUserInfoTokenServices extends UserInfoTokenServices {


    public MapUserInfoTokenServices(String userInfoEndpointUrl, String clientId) {
        super(userInfoEndpointUrl, clientId);
    }

    protected Object getPrincipal(Map<String, Object> map) {
        //Return user object map
        return map;
    }

}