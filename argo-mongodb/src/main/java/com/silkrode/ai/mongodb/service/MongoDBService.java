package com.silkrode.ai.mongodb.service;

import com.silkrode.ai.common.dto.DataDto;
/**
 * @implSpec  : define mongoDB service
 */
public interface MongoDBService {

    Object insert(DataDto dataDto);

    Object insertMany(DataDto dataDto);

    Object update(DataDto dataDto);

    Object updateMany(DataDto dataDto);

    Object query(DataDto dataDto);

    Object delete(DataDto dataDto);

    Object deleteMany(DataDto dataDto);

    Object count(DataDto dataDto);
}
