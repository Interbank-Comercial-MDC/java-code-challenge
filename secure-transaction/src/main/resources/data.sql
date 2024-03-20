INSERT INTO account (account_external_id, account_number) VALUES (gen_random_uuid(), '1234567891011123');
INSERT INTO account (account_external_id, account_number) VALUES (gen_random_uuid(), '1234567891011124');
INSERT INTO account (account_external_id, account_number) VALUES (gen_random_uuid(), '1234567891011124');
INSERT INTO account (account_external_id, account_number) VALUES (gen_random_uuid(), '1234567891011124');

INSERT INTO transaction_status (name) VALUES ('pendiente');
INSERT INTO transaction_status (name) VALUES ('aprobado');
INSERT INTO transaction_status (name) VALUES ('rechazado');

INSERT INTO transaction_type (name) VALUES ('transferencia interna');
INSERT INTO transaction_type (name) VALUES ('transferencia externa');

INSERT INTO transfer_type (name) VALUES ('inmediata');
INSERT INTO transfer_type (name) VALUES ('diferida');