package com.easyflight.flight.entity.query;

/**
 * Created by johnson on 6/22/17.
 */
public class Route {
    private String airline;
    private String from;
    private String to;

    public Route(String airline, String from, String to) {
        this.airline = airline;
        this.from = from;
        this.to = to;
    }

    public String getAirline() {
        return airline;
    }

    public void setAirline(String airline) {
        this.airline = airline;
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
}
