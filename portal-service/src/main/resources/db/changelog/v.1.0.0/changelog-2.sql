--liquibase formatted sql
--changeset Chizhara:1
CREATE TABLE transports (
    id UUID PRIMARY KEY,
    vin VARCHAR(17) NOT NULL,
    release_date DATE NOT NULL,
    register_id VARCHAR(36) REFERENCES users(id) NOT NULL,
    company_id VARCHAR(36) REFERENCES companies(id) NOT NULL
)