package com.silkrode.ai.mongodb.repository.impl;

import com.silkrode.ai.mongodb.repository.MongoDBRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
/**
 * @implNote  : The repository of mongoDB.
 * @param : input data and query condition from mongoDBServiceImpl.
 * @return : return mongoDB default message.
 */
@Repository
public class MongoDBRepositoryImpl implements MongoDBRepository {
    private final MongoTemplate mongoTemplate;

    public MongoDBRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Object save(List records, String collection) {
        return mongoTemplate.insert(records, collection);
    }

    public Object updateOne(Query query, Update update, String collection) {
        return mongoTemplate.updateFirst(query, update, collection);
    }

    public Object updateMany(Query query, Update update, String collection) {
        return mongoTemplate.updateMulti(query, update, collection);
    }

    public Object list(Query query, String collection) {
        return mongoTemplate.find(query, Map.class, collection);
    }

    public Object delete(Query query, String collection) {
        return mongoTemplate.findAndRemove(query, Map.class, collection);
    }

    public Object deleteMany(Query query, String collection) {
        return mongoTemplate.findAllAndRemove(query, collection);
    }

    public Integer count(Query query, String collection) {
        return (int) mongoTemplate.count(query, collection);
    }
}
