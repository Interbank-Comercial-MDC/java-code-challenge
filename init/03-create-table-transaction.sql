CREATE TABLE transaction (
    id SERIAL PRIMARY KEY,
    type_code INT NOT NULL,
    status_code INT NOT NULL DEFAULT 1,
    value DOUBLE PRECISION NOT NULL,
    create_date DATE NOT NULL,
    account_external_id_debit VARCHAR(255),
    account_external_id_credit VARCHAR(255),
    
    FOREIGN KEY (status_code) REFERENCES transaction_status(id),
    FOREIGN KEY (type_code) REFERENCES transaction_type(id)
);
