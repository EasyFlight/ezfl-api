package com.easyflight.flight.request;

import com.easyflight.flight.entity.query.ResultPage;
import com.easyflight.flight.entity.query.Route;
import com.easyflight.flight.entity.query.TimeSpan;

/**
 * Created by johnson on 6/22/17.
 */
public class FlightRequest {
    private Route route;
    private ResultPage page;
    private TimeSpan time;

    public FlightRequest(Route route, TimeSpan time, ResultPage page) {
        this.route = route;
        this.page = page;
        this.time = time;
    }

    public String getAirline() {
        return route.getAirline();
    }

    public void setAirline(String airline) {
        route.setAirline(airline);
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public ResultPage getPage() {
        return page;
    }

    public void setPage(ResultPage page) {
        this.page = page;
    }

    public String getFrom() {
        return route.getFrom();
    }

    public void setFrom(String from) {
        route.setFrom(from);
    }

    public String getTo() {
        return route.getTo();
    }

    public void setTo(String to) {
        route.setTo(to);
    }

    public String getDate() {
        return time.getDate();
    }

    public void setDate(String date) {
        time.setDate(date);
    }

    public Integer getPageNumber() {
        return page.getPageNumber();
    }

    public void setPageNumber(Integer pageNumber) {
        page.setPageNumber(pageNumber);
    }

    public Integer getPageSize() {
        return page.getPageSize();
    }

    public void setPageSize(Integer pageSize) {
        page.setPageSize(pageSize);
    }

    public TimeSpan getTime() {
        return time;
    }

    public void setTime(TimeSpan time) {
        this.time = time;
    }
}
