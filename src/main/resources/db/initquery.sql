-- Crear la base de datos

create database antifraudedb;

-- Luego seleccionar la base de datos y ejecutar las siguientes queries:

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
values ('3109aeb4-ee23-43a9-994b-3f9af5a0d00e',
        '4995c6a9-447b-444d-a040-f356c920a2fd',
        1,
        '464c5eb9-ee12-4b6c-b354-a839aa54d5f9',
        1,
        130);

insert
into financial_transaction (account_external_id_debit,
                            account_external_id_credit,
                            transaction_status_id,
                            transaction_external_id,
                            transfer_type_id,
                            value)
values ('3119aeb4-ee23-43a9-994b-3f9af5a0d00e',
        '4985c6a9-447b-444d-a040-f356c920a2fd',
        2,
        '465c5eb9-ee12-4b6c-b354-a839aa54d5f9',
        2,
        150);

insert
into financial_transaction (account_external_id_debit,
                            account_external_id_credit,
                            transaction_status_id,
                            transaction_external_id,
                            transfer_type_id,
                            value)
values ('3129aeb4-ee23-43a9-994b-3f9af5a0d00e',
        '4285c6a9-447b-444d-a040-f356c920a2fd',
        3,
        '435c5eb9-ee12-4b6c-b354-a839aa54d5f9',
        3,
        1001);


select *
from financial_transaction;
