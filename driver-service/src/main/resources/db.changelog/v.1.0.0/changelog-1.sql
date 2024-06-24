--liquibase formatted sql
--changeset Chizhara:1
CREATE TABLE IF NOT EXISTS companies
(
    id UUID PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS transports
(
    id         UUID PRIMARY KEY,
    gos_number VARCHAR(10) NOT NULL
);

CREATE TABLE IF NOT EXISTS cargos
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(127) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS tasks
(
    id           UUID PRIMARY KEY,
    start_point_lat  FLOAT NOT NULL,
    start_point_lon  FLOAT NOT NULL,
    end_point_lat  FLOAT NOT NULL,
    end_point_lon  FLOAT NOT NULL,
    transport_id UUID REFERENCES transports (id),
    driver_id    UUID NOT NULL
);

CREATE TABLE IF NOT EXISTS tasks_cargos
(
    task_id  UUID REFERENCES tasks (id),
    cargo_id UUID REFERENCES cargos (id),
    count    INT NOT NULL
);

CREATE TABLE IF NOT EXISTS routes(
    id UUID PRIMARY KEY,
    task_id UUID REFERENCES tasks(id)
);

CREATE TABLE IF NOT EXISTS routes_events(
    id UUID PRIMARY KEY,
    route_id UUID REFERENCES routes(id),
    time TIMESTAMP NOT NULL,
    type VARCHAR(20) NOT NULL
);
