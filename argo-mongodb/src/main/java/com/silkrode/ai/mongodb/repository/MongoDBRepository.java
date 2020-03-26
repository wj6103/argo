package com.silkrode.ai.mongodb.repository;

import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;

/**
 * @implSpec : define mongoDB repository.
 */
public interface MongoDBRepository {

    Object save(List records, String collection);

    Object updateOne(Query query, Update update, String collection);

    Object updateMany(Query query, Update update, String collection);

    Object list(Query query, String collection);

    Object delete(Query query, String collection);

    Object deleteMany(Query query, String collection);

    Integer count(Query query, String collection);
}
