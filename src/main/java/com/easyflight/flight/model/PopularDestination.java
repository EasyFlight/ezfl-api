package com.easyflight.flight.model;

/**
 * Created by Victor Ikoro on 1/9/2018.
 */
public class PopularDestination {
    private String from;
    private String to;
    private double leastPrice;

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

    public double getLeastPrice() {
        return leastPrice;
    }

    public void setLeastPrice(double leastPrice) {
        this.leastPrice = leastPrice;
    }
}
