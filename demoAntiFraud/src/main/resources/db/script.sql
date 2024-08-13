-- paso 1: crear la base de datos
create database antifraudedb;
-- paso 2: seleccionar la base de datos y ejecutar las siguientes queries:
-- drop table financial_transaction;
CREATE TABLE financial_transaction
(
    id                         serial primary key,
    account_external_id_debit  character varying(100) NOT NULL,
    account_external_id_credit character varying(100) NOT NULL,
    transaction_external_id    character varying(100) NOT NULL,
    transaction_status_id      smallint  default 1,
    created_at                 TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transfer_type_id           smallint,
    value                      smallint
);

insert
into financial_transaction (account_external_id_debit,
                            account_external_id_credit,
                            transaction_status_id,
                            transaction_external_id,
                            transfer_type_id,
                            value)
values ('76495890-bbcc-4052-81da-7b486ef9a8ee',
        'bc779d30-a0fc-4b06-8536-ee627a53f3bb',
        1,
        '5291c915-15f1-4f4c-94ae-1a06715f14fc',
        1,
        150);

insert
into financial_transaction (account_external_id_debit,
                            account_external_id_credit,
                            transaction_status_id,
                            transaction_external_id,
                            transfer_type_id,
                            value)
values ('76495890-bbcc-4052-81da-7b486ef9a8ee',
        'bc779d30-a0fc-4b06-8536-ee627a53f3bb',
        2,
        '5291c915-15f1-4f4c-94ae-1a06715f14fc',
        2,
        200);

insert
into financial_transaction (account_external_id_debit,
                            account_external_id_credit,
                            transaction_status_id,
                            transaction_external_id,
                            transfer_type_id,
                            value)
values ('76495890-bbcc-4052-81da-7b486ef9a8ee',
        'bc779d30-a0fc-4b06-8536-ee627a53f3bb',
        3,
        '5291c915-15f1-4f4c-94ae-1a06715f14fc',
        3,
        300);


select *
from financial_transaction;