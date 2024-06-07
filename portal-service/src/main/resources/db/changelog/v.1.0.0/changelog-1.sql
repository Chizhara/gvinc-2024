--liquibase formatted sql
--changeset Chizhara:1
CREATE TABLE users (
    id UUID PRIMARY KEY,
    username VARCHAR(67)
);

CREATE TABLE companies (
    id UUID PRIMARY KEY,
    name VARCHAR(67)
);

CREATE TABLE companies_users (
    company_id UUID REFERENCES companies(id),
    user_id UUID REFERENCES users(id),
    PRIMARY KEY (company_id, user_id)
)