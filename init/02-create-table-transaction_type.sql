CREATE TABLE transaction_type (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);


INSERT INTO transaction_type (name) VALUES
('External');
