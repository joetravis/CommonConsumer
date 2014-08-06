package com.newco.hackathon.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.newco.hackathon.model.Consumer;

public interface ConsumerRepository extends
        PagingAndSortingRepository<Consumer, Long> {
}
