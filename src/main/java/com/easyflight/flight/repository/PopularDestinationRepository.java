package com.easyflight.flight.repository;

import com.easyflight.flight.entity.PopularDestination;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Victor.Ikoro on 1/11/2018.
 */
@Repository
public interface PopularDestinationRepository extends MongoRepository<PopularDestination, String>, QueryDslPredicateExecutor<PopularDestination> {
    List<PopularDestination> findByFromAndTo(String from, String to);
}


