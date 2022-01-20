CREATE DATABASE IF NOT EXISTS springsecond;
USE springsecond;
CREATE TABLE IF NOT EXISTS products(id BIGINT PRIMARY KEY AUTO_INCREMENT, name varchar(100),price DOUBLE(5,2));
INSERT INTO products(name, price) VALUES('Bike',599.99);
INSERT INTO products(name, price) VALUES('Umbrella',37.80);
INSERT INTO products(name, price) VALUES('Hammer',19.99);