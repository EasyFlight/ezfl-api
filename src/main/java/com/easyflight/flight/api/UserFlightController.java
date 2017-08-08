package com.easyflight.flight.api;

import com.easyflight.flight.entity.UserFlight;
import com.easyflight.flight.oauth2.UserInfoPrincipal;
import com.easyflight.flight.request.UserFlightRequest;
import com.easyflight.flight.service.UserFlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Victor.Ikoro on 8/8/2017.
 */
@RestController
@RequestMapping(value = "/user/flights")
public class UserFlightController {

    private UserFlightService userFlightService;

    @Autowired
    public UserFlightController(UserFlightService userFlightService) {
        this.userFlightService = userFlightService;
    }

    @RequestMapping(value = "",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Page<UserFlight> getUserFlights(OAuth2Authentication authentication,
                                    @RequestParam(required = false, defaultValue = "0") Integer pageNumber,
                                    @RequestParam(required = false, defaultValue = "20") Integer pageSize) {
        UserInfoPrincipal principal = (UserInfoPrincipal) authentication.getPrincipal();
        return userFlightService.getPaginatedUserFlights(principal.getEmail(), pageNumber, pageSize);
    }

    @RequestMapping(value = "",
            method = RequestMethod.POST)
    public ResponseEntity saveFlight(OAuth2Authentication authentication,
                                     @RequestBody @Validated UserFlightRequest request) {
        UserInfoPrincipal principal = (UserInfoPrincipal) authentication.getPrincipal();
        userFlightService.saveUserFlight(principal.getEmail(), request.getFlightId());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{flightId}",
            method = RequestMethod.DELETE)
    public ResponseEntity deleteFlight(OAuth2Authentication authentication,
                                       @PathVariable String flightId) {
        UserInfoPrincipal principal = (UserInfoPrincipal) authentication.getPrincipal();
        userFlightService.deleteUserFlight(principal.getEmail(), flightId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
