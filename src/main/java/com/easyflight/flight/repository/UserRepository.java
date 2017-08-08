package com.easyflight.flight.repository;

import com.easyflight.flight.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Victor.Ikoro on 8/8/2017.
 */
@Repository
public interface UserRepository extends MongoRepository<User, String>, QueryDslPredicateExecutor<User> {

    List<User> findByEmailOrId(String id, String email);
}
