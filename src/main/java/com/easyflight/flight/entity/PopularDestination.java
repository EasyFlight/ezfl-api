package com.easyflight.flight.entity;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Victor Ikoro on 1/9/2018.
 */
@QueryEntity
@Document(collection = "popularDestinations")
public class PopularDestination {

    @Id
    private String id;
    private String from;
    private String to;
    private double leastPrice;


    public PopularDestination() {

    }

    @PersistenceConstructor
    public PopularDestination(String id, String from, String to, double leastPrice) {
        this.id = id;
        this.from = from;
        this.to = to;
        this.leastPrice = leastPrice;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public double getLeastPrice() {
        return leastPrice;
    }

    public void setLeastPrice(double leastPrice) {
        this.leastPrice = leastPrice;
    }
}
