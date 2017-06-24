package com.easyflight.flight.entity;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by johnson on 6/22/17.
 */
@QueryEntity
@Document(collection = "flights")
public class Flight {
    @Id
    private String id;
    private Date date;
    private Date departureTime;
    private Date arrivalTime;
    private String from;
    private String to;
    private String airline;
    private Map prices;
    private Date expiresAt;
    private String flightNumber;

    public Flight() {
    }

    @PersistenceConstructor
    public Flight(String id, Date date, Date departureTime, Date arrivalTime, String from, String to, String airline, Map prices, Date expiresAt, String flightNumber) {
        this.id = id;
        this.date = date;
        this.departureTime = departureTime;
        this.arrivalTime = arrivalTime;
        this.from = from;
        this.to = to;
        this.airline = airline;
        this.prices = prices;
        this.expiresAt = expiresAt;
        this.flightNumber = flightNumber;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
    }

    public Map getPrices() {
        return prices;
    }

    public void setPrices(Map prices) {
        this.prices = prices;
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }
}
