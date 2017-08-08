package com.easyflight.flight.repository;

import com.easyflight.flight.entity.UserFlight;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import java.util.List;

/**
 * Created by Victor.Ikoro on 8/8/2017.
 */
public interface UserFlightRepository extends MongoRepository<UserFlight, String>, QueryDslPredicateExecutor<UserFlight> {
    List<UserFlight> findByIdAndUserId(String id, String userId);
}
