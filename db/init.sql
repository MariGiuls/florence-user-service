CREATE DATABASE IF NOT EXISTS users;

USE users;

CREATE TABLE IF NOT EXISTS address (
    id VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    streetNumber VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    cap VARCHAR(255) NOT NULL,
    province VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    fiscal_code VARCHAR(255) UNIQUE NOT NULL,
    address_id VARCHAR(255),
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE CASCADE
);

INSERT INTO address (id, street, streetNumber, city, cap, province, country)
VALUES ('000-000-000-001', 'Via Pisa', '245', 'Ardea', '00040', 'RM', 'Italia');

INSERT INTO users (id, name, surname, email, fiscal_code, address_id)
VALUES ('000-000-000-001', 'Pino', 'Otti', 'pino.otti@email.com', 'PNTT12345678', '000-000-000-001');

INSERT INTO address (id, street, streetNumber, city, cap, province, country)
VALUES ('000-000-000-002', 'Piazza Venezia', '12', 'Milano', '20100', 'MI', 'Italia');

INSERT INTO users (id, name, surname, email, fiscal_code, address_id)
VALUES ('000-000-000-002', 'Tuna', 'Piuma', 'toni.piuma@email.com', 'TNPM87654321', '000-000-000-002');
