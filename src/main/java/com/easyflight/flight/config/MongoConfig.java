package com.easyflight.flight.config;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * Created by johnson on 6/22/17.
 */
@EnableMongoRepositories(basePackages = "com.easyflight.flight")
@Configuration
public class MongoConfig extends AbstractMongoConfiguration {

    @Value( "${mongo.client.uri}" )
    private String mongoClientUri;
    @Value( "${mongo.client.database}" )
    private String database;

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Override
    public Mongo mongo() throws Exception {
        MongoClientURI uri = new MongoClientURI(mongoClientUri);
        return new MongoClient(uri);
    }
}
