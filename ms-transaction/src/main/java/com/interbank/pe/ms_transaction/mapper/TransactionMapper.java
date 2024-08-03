package com.interbank.pe.ms_transaction.mapper;

import com.interbank.pe.ms_transaction.data.entities.Transaction;
import com.interbank.pe.ms_transaction.models.TransactionRequest;
import com.interbank.pe.ms_transaction.models.TransactionResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "typeCode", source = "request.tranferTypeId")
    @Mapping(target = "statusCode", ignore = true)
    @Mapping(target = "value", source = "request.value")
    @Mapping(target = "createDate", source = "localDate")
    @Mapping(target = "accountExternalIdDebit", source = "request.accountExternalIdDebit")
    @Mapping(target = "accountExternalIdCredit", source = "request.accountExternalIdCredit")
    Transaction buildTransaction(TransactionRequest request, Date localDate);

    @Mapping(target = "transactionExternalId", source = "transaction.id")
    @Mapping(target = "transactionTypeResponse.name", source = "transactionTypeName")
    @Mapping(target = "transactionStatusResponse.name", source = "transactionStatusName")
    @Mapping(target = "value", source = "transaction.value")
    @Mapping(target = "createdAt", source = "transaction.createDate")
    TransactionResponse buildTransactionResponse(Transaction transaction, String transactionTypeName, String transactionStatusName);

}
