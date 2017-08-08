package com.easyflight.flight.request;

import org.hibernate.validator.constraints.NotBlank;

/**
 * Created by Victor.Ikoro on 8/8/2017.
 */
public class UserFlightRequest {

    @NotBlank(message = "field required")
    private String flightId;

    public String getFlightId() {
        return flightId;
    }

    public void setFlightId(String flightId) {
        this.flightId = flightId;
    }
}
