package com.easyflight.flight.service;

import com.easyflight.flight.entity.Flight;
import com.easyflight.flight.entity.QFlight;
import com.easyflight.flight.entity.RoutePredicate;
import com.easyflight.flight.entity.query.TimeSpan;
import com.easyflight.flight.repository.FlightsRepository;
import com.easyflight.flight.request.FlightRequest;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by johnson on 6/22/17.
 */
@Service
public class FlightService {

    private FlightsRepository flightsRepository;
    private DateFormat dateFormat
            = new SimpleDateFormat("dd/MM/yyyy");
    private DateFormat timeFormat
            = new SimpleDateFormat("HH:mm");

    @Autowired
    public FlightService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public Page<Flight> getOneWayFlights(FlightRequest request)
            throws ParseException {
        QFlight qFlight = createNewFlightQuery();

        Predicate flightSearchPredicate
                = getOneWayFlightSearchPredicate(qFlight, request);
        PageRequest pageRequest = getPageRequest(request);

        Page<Flight> flights = flightsRepository
                .findAll(flightSearchPredicate, pageRequest);

        //Sort Prices
        flights.getContent()
                .forEach(flight -> {
                    List prices = flight.getPrices();
                    prices.sort((a, b) -> {
                        Double priceA = Double.valueOf(String.valueOf(((Map) a).get("cost")));
                        Double priceB = Double.valueOf(String.valueOf(((Map) b).get("cost")));
                        if (priceB == 0) //All 'No Sit' prices should be last
                            return -1;
                        if (priceA == 0)
                            return 1;
                        return priceA.compareTo(priceB);
                    });

                });
        return flights;
    }

    public Flight getFlightById(String id) {
        return flightsRepository.findOne(id);
    }


    private QFlight createNewFlightQuery() {
        return new QFlight("flight");
    }

    private PageRequest getPageRequest(FlightRequest request) {
        Sort.Order departureTimeOrder = new Sort.Order(Sort.Direction.ASC, "departureTime");
        Sort.Order airlineOrder = new Sort.Order(Sort.Direction.ASC, "departureTime");
        Sort sort = new Sort(departureTimeOrder, airlineOrder);
        return new PageRequest(
                request.getPageNumber(),
                request.getPageSize(), sort);
    }

    private Predicate getOneWayFlightSearchPredicate(
            QFlight qFlight, FlightRequest request)
            throws ParseException {

        RoutePredicate routePredicate
                = getRouteSearchPredicate(request, qFlight);

        BooleanExpression dateSearchExpression
                = getDateSearchExpression(request, qFlight);

        return getFlightSearchPredicate(
                routePredicate, dateSearchExpression);
    }

    private Predicate getFlightSearchPredicate(
            RoutePredicate routePredicate,
            BooleanExpression dateSearchExpression) {

        BooleanExpression searchExpression
                = dateSearchExpression
                .and(routePredicate.getFromPredicate())
                .and(routePredicate.getToPredicate());

        if (routePredicate.getAirlinePredicate() != null) {
            searchExpression
                    = searchExpression.and(
                    routePredicate.getAirlinePredicate());
        }

        return searchExpression;
    }

    private RoutePredicate getRouteSearchPredicate(FlightRequest request, QFlight qFlight) {
        Predicate airlinePredicate
                = request.getAirline() != null
                && !request.getAirline().isEmpty()
                ? qFlight.airline.eq(request.getAirline()) : null;
        Predicate fromPredicate = qFlight.from.eq(request.getFrom());
        Predicate toPredicate = qFlight.to.eq(request.getTo());
        return new RoutePredicate(airlinePredicate,
                fromPredicate,
                toPredicate);
    }

    private BooleanExpression getDateSearchExpression(FlightRequest request, QFlight qFlight) throws ParseException {
        Date flightDate = getFormattedFlightDate(request);
        TimeSpan searchTime = request.getTime();

        flightDate = setTimeTo(searchTime.getStartTime(), flightDate);
        Date searchTimeLimit
                = setTimeTo(searchTime.getEndTime(), flightDate);

        BooleanExpression flightsAfterStartTime
                = qFlight.arrivalTime.eq(flightDate).or(qFlight.arrivalTime.after(flightDate));
        BooleanExpression flightsBeforeEndTime
                = qFlight.arrivalTime.eq(searchTimeLimit).or(qFlight.arrivalTime.before(searchTimeLimit));

        return flightsAfterStartTime.and(flightsBeforeEndTime);
    }

    private Date setTimeTo(String time, Date date) throws ParseException {
        Calendar targetDate = Calendar.getInstance();
        targetDate.setTime(date);

        Calendar targetTime = Calendar.getInstance();
        targetTime.setTime(timeFormat.parse(time));

        targetDate.set(Calendar.HOUR_OF_DAY,
                targetTime.get(Calendar.HOUR_OF_DAY));
        targetDate.set(Calendar.MINUTE,
                targetTime.get(Calendar.MINUTE));

        return targetDate.getTime();
    }

    private Date getFormattedFlightDate(FlightRequest request) throws ParseException {
        return dateFormat.parse(request.getDate());
    }
}
