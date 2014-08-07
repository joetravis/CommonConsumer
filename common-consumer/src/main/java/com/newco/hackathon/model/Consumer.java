package com.newco.hackathon.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(schema = "consumer", name = "consumer")
@Document(indexName = "consumer", type = "consumer", shards = 1, replicas = 0, indexStoreType = "memory", refreshInterval = "-1")
public class Consumer {

    @org.springframework.data.annotation.Id
    @Id
    @Column(name = "id", nullable = true)
    @GeneratedValue()
    private Long id;

    @Column(name = "first_name", nullable = false)
    @NotNull(message = "firstName cannot be null.")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @NotNull(message = "lastName cannot be null.")
    private String lastName;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "consumer", targetEntity = Address.class)
    private Address address;

    @Column(name = "ssn_hash", nullable = true)
    private String ssn;

    @Column(name = "dob", nullable = true)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date dob;

    @Column(name = "email", nullable = true)
    private String email;

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getSsn() {
        return ssn;
    }

    public void setSsn(String ssn) {
        this.ssn = ssn;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
