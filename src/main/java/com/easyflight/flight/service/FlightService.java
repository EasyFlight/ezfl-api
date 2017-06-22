package com.easyflight.flight.service;

import com.easyflight.flight.entity.Flight;
import com.easyflight.flight.entity.QFlight;
import com.easyflight.flight.repository.FlightsRepository;
import com.easyflight.flight.request.FlightRequest;
import com.querydsl.core.types.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by johnson on 6/22/17.
 */
@Service
public class FlightService {

    private FlightsRepository flightsRepository;
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Autowired
    public FlightService(FlightsRepository flightsRepository) {
        this.flightsRepository = flightsRepository;
    }

    public Page<Flight> getFlights(FlightRequest request) throws ParseException {
        Date requestDate = dateFormat.parse(request.getDate());
        Date end = plusOneDay(requestDate);
        QFlight qFlight = new QFlight("flight");

        Predicate from = qFlight.from.eq(request.getFrom());
        Predicate to = qFlight.to.eq(request.getTo());
        Predicate predicate = qFlight
                .date.between(requestDate,end)
                .and(from).and(to);
        PageRequest pageRequest
                = new PageRequest(
                        request.getPageNumber(),
                        request.getPageSize());

        return flightsRepository.findAll(predicate, pageRequest);
    }

    private Date plusOneDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH,1);
        return calendar.getTime();
    }
}
