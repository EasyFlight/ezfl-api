package com.easyflight.flight.api;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * Created by Victor.Ikoro on 7/6/2017.
 */

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @RequestMapping(value = "/",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Principal user(Principal p) {
        return p;
    }
}
