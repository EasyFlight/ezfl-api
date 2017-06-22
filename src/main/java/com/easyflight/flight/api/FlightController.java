package com.easyflight.flight.api;

import com.easyflight.flight.entity.Flight;
import com.easyflight.flight.request.FlightRequest;
import com.easyflight.flight.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

/**
 * Created by johnson on 6/22/17.
 */
@RestController
@RequestMapping(value = "/flights")
public class FlightController {

    private FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @RequestMapping(value = "/flight",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Page<Flight> getFlights(@RequestParam String from,
                            @RequestParam String to,
                            @RequestParam String date,
                            @RequestParam Integer pageNumber,
                            @RequestParam Integer pageSize) throws ParseException {
        FlightRequest request = new FlightRequest();
        request.setFrom(from);
        request.setTo(to);
        request.setDate(date);
        request.setPageNumber(pageNumber);
        request.setPageSize(pageSize);
        return flightService.getFlights(request);
    }
}
