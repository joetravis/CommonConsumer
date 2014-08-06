CREATE SCHEMA IF NOT EXISTS consumer_admin;
CREATE SCHEMA IF NOT EXISTS consumer;

CREATE TABLE IF NOT EXISTS consumer_admin.users(id BIGINT PRIMARY KEY, name varchar(20));

CREATE TABLE IF NOT EXISTS consumer.address(
  id BIGINT PRIMARY KEY,
  line1 varchar(64),
  line2 varchar(64),
  city varchar(64),
  state varchar(2),
  postal_code varchar(15));

CREATE TABLE IF NOT EXISTS consumer.consumer(
    id BIGINT PRIMARY KEY,
    first_name varchar(30),
    last_name varchar(30),
    ssn_hash varchar(128),
    dob DATE,
    email varchar(64),
    address_id BIGINT,
    FOREIGN KEY (address_id) REFERENCES address(id));

