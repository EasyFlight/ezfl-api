package com.easyflight.flight.api;

import com.easyflight.flight.entity.Flight;
import com.easyflight.flight.entity.query.ResultPage;
import com.easyflight.flight.entity.query.Route;
import com.easyflight.flight.entity.query.TimeSpan;
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

    @RequestMapping(value = "/oneway",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Page<Flight> getOneWayFlights(
                            @RequestParam String airline,
                            @RequestParam String from,
                            @RequestParam String to,
                            @RequestParam String date,
                            @RequestParam String startTime,
                            @RequestParam String endTime,
                            @RequestParam Integer pageNumber,
                            @RequestParam Integer pageSize) throws ParseException {
        Route route = new Route(airline,from,to);
        TimeSpan timeSpan = new TimeSpan(date,startTime,endTime);
        ResultPage page = new ResultPage(pageNumber,pageSize);
        FlightRequest request = new FlightRequest(route,timeSpan,page);
        return flightService.getOneWayFlights(request);
    }

    @RequestMapping(value = "/return",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Page<Flight> getReturnFlights(
            @RequestParam String airline,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String depatureDate,
            @RequestParam String returnDate,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam Integer pageNumber,
            @RequestParam Integer pageSize) throws ParseException {
        return null;
    }
}