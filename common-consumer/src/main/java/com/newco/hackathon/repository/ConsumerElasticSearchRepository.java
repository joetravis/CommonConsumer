package com.newco.hackathon.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository;

import com.newco.hackathon.model.Consumer;

public interface ConsumerElasticSearchRepository extends
        ElasticsearchCrudRepository<Consumer, Long> {

    List<Consumer> findByFirstName(String firstName);

}
