CREATE TABLE transactions (
      id SERIAL PRIMARY KEY,
      account_external_id_debit UUID NOT NULL,
      account_external_id_credit UUID NOT NULL,
      transfer_type_id INT NOT NULL,
      value DECIMAL(10, 2) NOT NULL,
      transaction_status_id INT NOT NULL,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      FOREIGN KEY (transaction_status_id) REFERENCES transaction_status(id)
);
