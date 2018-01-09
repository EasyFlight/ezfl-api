package com.easyflight.flight.service;

import com.easyflight.flight.entity.Flight;
import com.easyflight.flight.entity.query.ResultPage;
import com.easyflight.flight.entity.query.Route;
import com.easyflight.flight.entity.query.TimeSpan;
import com.easyflight.flight.model.PopularDestination;
import com.easyflight.flight.request.FlightRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Victor Ikoro on 1/9/2018.
 */

@Service
public class PopularDestinationService {

    private FlightService flightService;

    private String popularDestinations;


    @Autowired
    public PopularDestinationService(FlightService flightService,
                                     @Value("${flights.destination.popular:}") String popularDestinations) {
        this.flightService = flightService;
        this.popularDestinations = popularDestinations;
    }

    public List<PopularDestination> getPopularDestinations(){
        String[] destinations = popularDestinations.split(",");
        List<PopularDestination> popularDestinations = Arrays
                .stream(destinations)
                .map(destination ->{
                    String[] route = destination.split("_");
                    return buildPopularDestination(route[0], route[1]);
                })
                .filter(popularDestination -> popularDestination != null)
                .collect(Collectors.toList());
    }

    private PopularDestination buildPopularDestination(String from, String to) throws ParseException {
        List<Flight> allFlights =  new ArrayList<>();
        Route route = new Route("",from, to);
        FlightRequest flightRequest =  new FlightRequest(route,null, new ResultPage(0,100));

        //Get all flights for the next 7 days
        for(int i = 0; i < 8; i++){
            TimeSpan timeSpan = new TimeSpan(
                    LocalDate.now().plusDays(i).format(DateTimeFormatter.BASIC_ISO_DATE),
                    "00:00",
                    "23:59");
            flightRequest.setTime(timeSpan);
            allFlights.addAll(flightService.getOneWayFlights(flightRequest).getContent());
        }
        allFlights
                .stream()
                .map()
    }
}
