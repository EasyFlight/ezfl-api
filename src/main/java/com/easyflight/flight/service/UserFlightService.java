package com.easyflight.flight.service;

import com.easyflight.flight.entity.Flight;
import com.easyflight.flight.entity.QUserFlight;
import com.easyflight.flight.entity.User;
import com.easyflight.flight.entity.UserFlight;
import com.easyflight.flight.enums.ErrorCodes;
import com.easyflight.flight.exception.DuplicateException;
import com.easyflight.flight.exception.NotFoundException;
import com.easyflight.flight.repository.UserFlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by Victor.Ikoro on 8/8/2017.
 */
@Service
public class UserFlightService {

    private UserFlightRepository userFlightRepository;
    private FlightService flightService;
    private UserService userService;

    @Autowired
    public UserFlightService(UserFlightRepository userFlightRepository, FlightService flightService, UserService userService) {
        this.userFlightRepository = userFlightRepository;
        this.flightService = flightService;
        this.userService = userService;
    }


    public Page<UserFlight> getPaginatedUserFlights(String userId, Integer pageNumber, Integer pageSize) {
        User user = getUser(userId);
        PageRequest pageRequest = new PageRequest(pageNumber, pageSize);
        Page<UserFlight> userFlights =  userFlightRepository.findAll(QUserFlight.userFlight.userId.eq(user.getId()), pageRequest);
        userFlights.getContent().forEach(userFlight -> {
            Flight flight = flightService.getFlightById(userFlight.getFlightId());
            if (flight == null) {
                userFlight.setExpired(true);

            } else {
                userFlight.setExpired(flight.getDepartureTime().before(new Date()));
            }
            userFlight.setFlight(flight);
        });
        return userFlights;
    }

    public void deleteUserFlight(String userId, String userFlightId) {
        User user = getUser(userId);
        List<UserFlight> userFlightList = userFlightRepository.findByIdAndUserId(userFlightId, user.getId());
        if (userFlightList.size() > 0) {
            userFlightRepository.delete(userFlightList.get(0));
        }
    }

    public UserFlight saveUserFlight(String userId, String flightId) {
        User user = getUser(userId);
        Flight flight = flightService.getFlightById(flightId);
        if (flight == null) {
            throw new NotFoundException(ErrorCodes.FLIGHT_NOT_FOUND.name(), "Flight does not exist");
        }
        UserFlight userFlight = new UserFlight();
        userFlight.setAirline(flight.getAirline());
        userFlight.setFlightId(flightId);
        userFlight.setFrom(flight.getFrom());
        userFlight.setTo(flight.getTo());
        userFlight.setFlightNumber(flight.getFlightNumber());
        userFlight.setUserId(user.getId());

        try {
            return userFlightRepository.save(userFlight);
        } catch (DuplicateKeyException exception) {
            throw new DuplicateException(ErrorCodes.FLIGHT_ALREADY_EXISTS.name(), "Flight already exists");
        }
    }

    private User getUser(String userId) {
        User user = userService.getUser(userId);
        if (user == null) {
            throw new NotFoundException(ErrorCodes.USER_NOT_FOUND.name(), "User not found");
        }
        return user;
    }

}
