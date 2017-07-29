package com.easyflight.flight.api;

import com.easyflight.flight.entity.Flight;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Victor Ikoro on 7/29/2017.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {


    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Object getUser(OAuth2Authentication authentication){
        return  authentication.getPrincipal();
    }
}
