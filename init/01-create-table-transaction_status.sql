CREATE TABLE transaction_status (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT INTO transaction_status (name) VALUES
('pendiente'),
('aprobado'),
('rechazado');
