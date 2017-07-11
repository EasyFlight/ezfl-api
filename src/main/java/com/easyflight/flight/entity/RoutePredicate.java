package com.easyflight.flight.entity;

import com.querydsl.core.types.Predicate;

/**
 * Created by johnson on 7/10/17.
 */
public class RoutePredicate {
    private Predicate airlinePredicate;
    private Predicate fromPredicate;
    private Predicate toPredicate;

    public RoutePredicate(Predicate fromPredicate,
                          Predicate toPredicate,
                          Predicate airlinePredicate) {
        this.airlinePredicate = airlinePredicate;
        this.fromPredicate = fromPredicate;
        this.toPredicate = toPredicate;
    }

    public Predicate getAirlinePredicate() {
        return airlinePredicate;
    }

    public void setAirlinePredicate(Predicate airlinePredicate) {
        this.airlinePredicate = airlinePredicate;
    }

    public Predicate getFromPredicate() {
        return fromPredicate;
    }

    public void setFromPredicate(Predicate fromPredicate) {
        this.fromPredicate = fromPredicate;
    }

    public Predicate getToPredicate() {
        return toPredicate;
    }

    public void setToPredicate(Predicate toPredicate) {
        this.toPredicate = toPredicate;
    }
}
