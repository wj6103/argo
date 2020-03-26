package com.silkrode.ai.mongodb.service.impl;

import com.silkrode.ai.common.dto.DataDto;
import com.silkrode.ai.mongodb.repository.MongoDBRepository;
import com.silkrode.ai.mongodb.service.MongoDBService;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @implNote  : Implement mongoDB service including CURD and count functions.
 * @param : dataDto from mongoDB controller.
 * @return : return mongoDB default message.
 */
@Service
public class MongoDBServiceImpl implements MongoDBService {
    private final MongoDBRepository mongoDBRepository;

    public MongoDBServiceImpl(MongoDBRepository mongoDBRepository) {
        this.mongoDBRepository = mongoDBRepository;
    }

    @Override
    public Object insert(DataDto dataDto) {
        List<Map<String, Object>> records = new ArrayList<>();
        Map<String, Object> record = (Map<String, Object>) dataDto.getData();
        records.add(record);
        return mongoDBRepository.save(records, dataDto.getCollection());
    }

    @Override
    public Object insertMany(DataDto dataDto) {
        return mongoDBRepository.save(
                (List<Map<String, Object>>) dataDto.getData(),
                dataDto.getCollection()
        );
    }

    @Override
    public Object update(DataDto dataDto) {
        return mongoDBRepository.updateOne(
                createQuery(dataDto.getFilter()),
                createUpdate((Map<String, Object>) dataDto.getData()),
                dataDto.getCollection()
        );
    }

    @Override
    public Object updateMany(DataDto dataDto) {
        return mongoDBRepository.updateMany(
                createQuery(dataDto.getFilter()),
                createUpdate((Map<String, Object>) dataDto.getData()),
                dataDto.getCollection()
        );
    }

    @Override
    public Object query(DataDto dataDto) {
        Query query = createQuery(dataDto.getFilter(), dataDto.getProjection(), dataDto.getSort(), dataDto.getLimit(), dataDto.getSkip());
        return mongoDBRepository.list(query, dataDto.getCollection());
    }

    @Override
    public Object delete(DataDto dataDto) {
        return mongoDBRepository.delete(
                createQuery(dataDto.getFilter()),
                dataDto.getCollection()
        );
    }

    @Override
    public Object deleteMany(DataDto dataDto) {
        return mongoDBRepository.deleteMany(
                createQuery(dataDto.getFilter()),
                dataDto.getCollection()
        );
    }

    @Override
    public Integer count(DataDto dataDto) {
        return mongoDBRepository.count(
                createQuery(dataDto.getFilter()),
                dataDto.getCollection()
        );
    }

    private Query createQuery(Map<String, Object> filter) {
        return createQuery(filter, null, null, null, null);
    }

    private Query createQuery(Map<String, Object> filter, Map<String, Boolean> projection, Map<String, Boolean> sort, Integer limit, Integer skip) {
        Query query = new Query();
        // Criteria
        if (filter.size() != 0) {
            Criteria criteria = new Criteria();
            List<Criteria> andCriteriaList = new ArrayList<>();
            filter.forEach((key, val) -> {
                if ("$or".equals(key)) {
                    andCriteriaList.add(createOrCriteria((List<Map<String, Object>>) val));
                } else {
                    if (val instanceof Map) {
                        andCriteriaList.add(createMapCriteria(key, (Map<String, Object>) val));
                    } else {
                        andCriteriaList.add(Criteria.where(key).is(val));
                    }
                }
            });
            int size = andCriteriaList.size();
            Criteria[] andCriteriaArray = andCriteriaList.toArray(new Criteria[size]);
            criteria.andOperator(andCriteriaArray);
            query.addCriteria(criteria);
        }
        // Projection
        if (projection != null) {
            projection.forEach((field, condition) -> {
                if (condition) {
                    query.fields().include(field);
                } else {
                    query.fields().exclude(field);
                }
            });
        }
        // Sort
        if (sort != null) {
            query.with(createSort(sort));
        }
        // Limit
        if (limit != null) {
            query.limit(limit);
        }
        // Skip
        if (skip != null) {
            query.skip(skip);
        }
        return query;
    }

    private Criteria createOrCriteria(List<Map<String, Object>> condition) {
        ArrayList<Criteria> criteriaArrayList = new ArrayList<>();
        condition.forEach((item) -> item.forEach((k, v) -> {
            if (v instanceof Map) {
                criteriaArrayList.add(createMapCriteria(k, (Map<String, Object>) v));
            } else if (v instanceof String) {
                criteriaArrayList.add(Criteria.where(k).is(v));
            }
        }));
        int size = criteriaArrayList.size();
        Criteria[] andCriteriaArray = criteriaArrayList.toArray(new Criteria[size]);
        return new Criteria().orOperator(andCriteriaArray);
    }

    private Criteria createMapCriteria(String field, Map<String, Object> condition) {
        List<Criteria> mapCriteriaList = new ArrayList<>();
        condition.forEach((k, v) -> {
            if ("$gte".equals(k)) {
                mapCriteriaList.add(Criteria.where(field).gte(v));
            } else if ("$lte".equals(k)) {
                mapCriteriaList.add(Criteria.where(field).lte(v));
            } else if ("$regex".equals(k)) {
                mapCriteriaList.add(Criteria.where(field).regex((String) v));
            }
        });
        int size = mapCriteriaList.size();
        Criteria[] mapCriteriaArray = mapCriteriaList.toArray(new Criteria[size]);
        return new Criteria().andOperator(mapCriteriaArray);
    }

    private Sort createSort(Map<String, Boolean> sort) {
        List<Sort.Order> orders = new ArrayList<>();
        sort.forEach((field, order) -> {
            Sort.Direction direction;
            direction = (order ? Sort.Direction.ASC : Sort.Direction.DESC);
            orders.add(new Sort.Order(direction, field));
        });
        return new Sort(orders);
    }

    private Update createUpdate(Map<String, Object> data) {
        Update update = new Update();
        data.forEach(update::set);
        return update;
    }
}
