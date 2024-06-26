--liquibase formatted sql
--changeset Chizhara:1

ALTER TABLE routes_events
    ADD COLUMN company_id UUID;

ALTER TABLE tasks
    ADD COLUMN create_time TIMESTAMP;
ALTER TABLE tasks
    ADD COLUMN company_id UUID REFERENCES companies(id);