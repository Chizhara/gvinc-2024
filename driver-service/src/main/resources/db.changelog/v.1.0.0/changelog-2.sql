--liquibase formatted sql
--changeset Chizhara:1
CREATE TABLE route_points (
    id UUID PRIMARY KEY,
    route_id UUID REFERENCES routes(id),
    lat FLOAT NOT NULL,
    lon FLOAT NOT NULL
)