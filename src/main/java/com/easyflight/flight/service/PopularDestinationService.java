package com.easyflight.flight.service;

import com.easyflight.flight.entity.Flight;
import com.easyflight.flight.entity.PopularDestination;
import com.easyflight.flight.entity.Price;
import com.easyflight.flight.entity.query.ResultPage;
import com.easyflight.flight.entity.query.Route;
import com.easyflight.flight.entity.query.TimeSpan;
import com.easyflight.flight.repository.PopularDestinationRepository;
import com.easyflight.flight.request.FlightRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Created by Victor Ikoro on 1/9/2018.
 */

@Service
public class PopularDestinationService {

    private FlightService flightService;
    private PopularDestinationRepository popularDestinationRepository;
    private String popularDestinations;


    @Autowired
    public PopularDestinationService(FlightService flightService,
                                     PopularDestinationRepository popularDestinationRepository,
                                     @Value("${flights.destinations.popular:}") String popularDestinations) {
        this.flightService = flightService;
        this.popularDestinationRepository = popularDestinationRepository;
        this.popularDestinations = popularDestinations;
    }

    public List<PopularDestination> getPopularDestinations(){
        return popularDestinationRepository.findAll();
    }

    @Scheduled(fixedDelayString = "${flights.job.scheduleInterval}")
    public void popularDestinationsJob() {
        popularDestinationRepository
                .findAll()
                .forEach(p -> {
                    popularDestinationRepository.delete(p);
                });

        String[] destinations = popularDestinations.split(",");
        Arrays
                .stream(destinations)
                .map(destination ->{
                    String[] route = destination.split("_");
                    try {
                        return buildPopularDestination(route[0], route[1]);
                    } catch (ParseException e) {
                        return null;
                    }
                })
                .filter(popularDestination -> popularDestination != null && popularDestination.getLeastPrice() > 0)
                .forEach(popularDestination -> {
                    //Replace old ones in DB
                    List<PopularDestination> old = popularDestinationRepository.findByFromAndTo(popularDestination.getFrom(), popularDestination.getTo());
                    if (old.size() > 0) {
                        popularDestinationRepository.delete(old.get(0));
                    }
                    popularDestinationRepository.save(popularDestination);
                });
    }

    private PopularDestination buildPopularDestination(String from, String to) throws ParseException {
        List<Flight> allFlights =  new ArrayList<>();
        Route route = new Route("",from, to);
        FlightRequest flightRequest =  new FlightRequest(route,null, new ResultPage(0,100));

        //Get all flights for the next 7 days
        for(int i = 0; i < 8; i++){
            TimeSpan timeSpan = new TimeSpan(
                    LocalDate.now().plusDays(i).format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    "00:00",
                    "23:59");
            flightRequest.setTime(timeSpan);
            allFlights.addAll(flightService.getOneWayFlights(flightRequest).getContent());
        }
        OptionalDouble lowestPrice = allFlights
                .parallelStream()
                .mapToDouble((a) -> {
                    Comparator comparator = (p1, p2) -> {
                        Price price1 = Price.fromMap((Map) p1), price2 = Price.fromMap((Map) p2);
                        return (price1).getCost().compareTo(price2.getCost());
                    };
                    Collections.sort(a.getPrices(), comparator);
                    Optional lowestPriceA = a.getPrices()
                            .stream()
                            .map(p -> Price.fromMap((Map) p))
                            .filter(price -> ((Price) price).getCost() > 0)
                            .findFirst();
                    return lowestPriceA.isPresent() ? ((Price) lowestPriceA.get()).getCost() : 0;
                })
                .filter((value -> value > 0))
                .sorted()
                .findFirst();
        PopularDestination popularDestination = new PopularDestination();
        popularDestination.setFrom(from);
        popularDestination.setTo(to);
        popularDestination.setLeastPrice(lowestPrice.orElse(0));
        return popularDestination;

    }
}
