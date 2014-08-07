package com.newco.hackathon.repository;

import com.newco.hackathon.model.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.newco.hackathon.model.Consumer;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ConsumerRepository extends
        PagingAndSortingRepository<Consumer, Long> {

    @Query("SELECT c FROM Consumer c JOIN c.address a"
            + " WHERE c.firstName = :firstName"
            + " AND c.lastName = :lastName"
            + " AND a.line1 = :line1"
            + " AND a.city = :city"
            + " AND a.state = :state"
            + " AND a.postalCode = :postalCode")
    public List<Consumer> findByConsumerAndAddress(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("line1") String line1,
            @Param("city") String city,
            @Param("state") String state,
            @Param("postalCode") String postalCode);
}
