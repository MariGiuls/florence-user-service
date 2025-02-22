CREATE DATABASE IF NOT EXISTS florence;

USE florence;

CREATE TABLE IF NOT EXISTS address (
    id INT NOT NULL AUTO_INCREMENT,
    street VARCHAR(255) NOT NULL,
    street_number VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    cap VARCHAR(255) NOT NULL,
    province VARCHAR(255) NOT NULL,
    country VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS users (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    surname VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    fiscal_code VARCHAR(255) UNIQUE NOT NULL,
    address_id INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE CASCADE
);

INSERT INTO address (id, street, street_number, city, cap, province, country)
VALUES (1234, 'Via Pisa', '245', 'Ardea', '00040', 'RM', 'Italia');

INSERT INTO users (id, name, surname, email, fiscal_code, address_id)
VALUES (1234, 'Pino', 'Otti', 'pino.otti@email.com', 'PNTT12345678', 1234);

INSERT INTO address (id, street, street_number, city, cap, province, country)
VALUES (2345, 'Piazza Venezia', '12', 'Milano', '20100', 'MI', 'Italia');

INSERT INTO users (id, name, surname, email, fiscal_code, address_id)
VALUES (2345, 'Tuna', 'Piuma', 'toni.piuma@email.com', 'TNPM87654321', 2345);
