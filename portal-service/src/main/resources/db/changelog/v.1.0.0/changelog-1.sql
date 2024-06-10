--liquibase formatted sql
--changeset Chizhara:1
CREATE TABLE users (
    id VARCHAR(36) PRIMARY KEY,
    username VARCHAR(63) UNIQUE NOT NULL,
    name VARCHAR(63) NOT NULL,
    surname VARCHAR(63) NOT NULL,
    email VARCHAR(320) NOT NULL
);

CREATE TABLE companies (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(67) UNIQUE NOT NULL,
    inn DECIMAL(10,0) UNIQUE NOT NULL,
    kpp DECIMAL(9,0) UNIQUE NOT NULL,
    address VARCHAR(255) NOT NULL,
    ogrn DECIMAL(13,0) NOT NULL
);

CREATE TABLE companies_users (
    company_id VARCHAR(36) REFERENCES companies(id) NOT NULL,
    user_id VARCHAR(36) REFERENCES users(id) NOT NULL,
    role VARCHAR(20) NOT NULL,
    PRIMARY KEY (company_id, user_id)
)