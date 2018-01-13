package com.easyflight.flight.entity;

import com.querydsl.core.annotations.QueryEntity;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Map;
import java.util.Optional;

/**
 * Created by johnson on 6/22/17.
 */

public class Price {
    @Field("class")
    private String flightClass;
    private Double cost;

    public static Price fromMap(Map priceMap){
        Price price = new Price();
        Optional cost = Optional.of(priceMap.get("cost"));
        Optional flightClass = Optional.of(priceMap.get("class"));
        if(cost.isPresent()){
            price.setCost(Double.valueOf(cost.get().toString()));
        }
        if(flightClass.isPresent()){
            price.setFlightClass(flightClass.get().toString());
        }
        return price;
    }

    public String getFlightClass() {
        return flightClass;
    }

    public void setFlightClass(String flightClass) {
        this.flightClass = flightClass;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
}
