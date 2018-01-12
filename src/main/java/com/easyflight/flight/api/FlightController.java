package com.easyflight.flight.api;

import com.easyflight.flight.entity.Flight;
import com.easyflight.flight.entity.PopularDestination;
import com.easyflight.flight.entity.query.ResultPage;
import com.easyflight.flight.entity.query.Route;
import com.easyflight.flight.entity.query.TimeSpan;
import com.easyflight.flight.enums.ErrorCodes;
import com.easyflight.flight.exception.NotFoundException;
import com.easyflight.flight.request.FlightRequest;
import com.easyflight.flight.service.FlightService;
import com.easyflight.flight.service.PopularDestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * Created by johnson on 6/22/17.
 */
@RestController
@RequestMapping(value = "/api/v1/flights")
public class FlightController {

    private FlightService flightService;
    private PopularDestinationService popularDestinationService;
    @Autowired
    public FlightController(FlightService flightService, PopularDestinationService popularDestinationService) {
        this.flightService = flightService;
        this.popularDestinationService = popularDestinationService;
    }

    @RequestMapping(value = "/oneway",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<Flight> getOneWayFlights(
                            @RequestParam(required=false,defaultValue = "") String airline,
                            @RequestParam String from,
                            @RequestParam String to,
                            @RequestParam String date,
                            @RequestParam(required=false,defaultValue = "00:00") String startTime,
                            @RequestParam(required=false,defaultValue = "23:59") String endTime,
                            @RequestParam(required=false, defaultValue = "1") Integer pageNumber,
                            @RequestParam(required=false, defaultValue = "20") Integer pageSize) throws ParseException {
        Route route = new Route(airline,from,to);
        TimeSpan timeSpan = new TimeSpan(date,startTime,endTime);
        ResultPage page = new ResultPage(pageNumber - 1,pageSize);
        FlightRequest request = new FlightRequest(route,timeSpan,page);
        return flightService.getOneWayFlights(request);
    }

    @RequestMapping(value = "/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Flight getFlight(@PathVariable String id) {
        Flight flight = flightService.getFlightById(id);
        if (flight == null) {
            throw new NotFoundException(ErrorCodes.FLIGHT_NOT_FOUND.name(), "Flight not found");
        }
        return flight;
    }

    @RequestMapping(value = {"/popular"} )
    public List<PopularDestination> getPopularDestinations(){
        return popularDestinationService.getPopularDestinations();
    }
}