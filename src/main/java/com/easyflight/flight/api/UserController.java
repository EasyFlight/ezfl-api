package com.easyflight.flight.api;

import com.easyflight.flight.entity.User;
import com.easyflight.flight.enums.ErrorCodes;
import com.easyflight.flight.exception.NotFoundException;
import com.easyflight.flight.oauth2.UserInfoPrincipal;
import com.easyflight.flight.request.UserRequest;
import com.easyflight.flight.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * Created by Victor Ikoro on 7/29/2017.
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User getUser(OAuth2Authentication authentication) {
        UserInfoPrincipal principal = (UserInfoPrincipal) authentication.getPrincipal();
        User user = userService.getUser(principal.getEmail());
        if (user == null) {
            throw new NotFoundException(ErrorCodes.USER_NOT_FOUND.name(), "User not found");
        }
        return user;
    }


    @RequestMapping(value = "",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity createUser(OAuth2Authentication authentication) {
        UserInfoPrincipal principal = (UserInfoPrincipal) authentication.getPrincipal();
        UserRequest request = new UserRequest();
        BeanUtils.copyProperties(principal, request);
        request.setFullName(principal.getName());
        userService.createUser(request);
        HttpHeaders headers = new HttpHeaders();
        headers.put(HttpHeaders.LOCATION, Collections.singletonList("/user"));
        return new ResponseEntity(
                headers,
                HttpStatus.CREATED);
    }


}
