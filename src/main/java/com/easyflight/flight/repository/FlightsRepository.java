package com.easyflight.flight.repository;

import com.easyflight.flight.entity.Flight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

/**
 * Created by johnson on 6/22/17.
 */
@Repository
public interface FlightsRepository extends MongoRepository<Flight, String> , QueryDslPredicateExecutor<Flight> {
}