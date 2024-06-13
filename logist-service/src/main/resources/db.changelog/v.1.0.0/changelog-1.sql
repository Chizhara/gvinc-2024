--liquibase formatted sql
--changeset Chizhara:1
CREATE TABLE IF NOT EXISTS drivers
(
    id       UUID PRIMARY KEY,
    username VARCHAR(63) NOT NULL,
    name     VARCHAR(63) NOT NULL,
    surname  VARCHAR(63) NOT NULL
);

CREATE TABLE IF NOT EXISTS companies
(
    id UUID PRIMARY KEY
);

CREATE TABLE IF NOT EXISTS drivers_companies
(
    driver_id  UUID REFERENCES drivers (id),
    company_id UUID REFERENCES companies (id),
    PRIMARY KEY (driver_id, company_id)
);

CREATE TABLE IF NOT EXISTS transports
(
    id         UUID PRIMARY KEY,
    gos_number VARCHAR(10) NOT NULL,
    company_id UUID REFERENCES companies (id)
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
    start_point  POINT NOT NULL,
    end_point    POINT NOT NULL,
    transport_id UUID REFERENCES transports (id),
    driver_id    UUID REFERENCES drivers (id)
);

CREATE TABLE IF NOT EXISTS tasks_cargos
(
    task_id  UUID REFERENCES tasks (id),
    cargo_id UUID REFERENCES cargos (id),
    count    INT NOT NULL
);
