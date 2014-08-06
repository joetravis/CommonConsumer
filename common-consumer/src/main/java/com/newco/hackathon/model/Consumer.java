package com.newco.hackathon.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.elasticsearch.annotations.Document;

@Entity
@Table(schema = "consumer", name = "consumer")
@Document(indexName = "consumer", type = "consumer", shards = 1, replicas = 0, indexStoreType = "memory", refreshInterval = "-1")
public class Consumer {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @org.springframework.data.annotation.Id
    @Id
    @Column(name = "id", nullable = true)
    @GeneratedValue()
    private Long id;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public Long getId() {
        return id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

}
