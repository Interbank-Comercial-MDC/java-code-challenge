package com.interbank.challenge.util;

import com.interbank.challenge.entity.TransactionDocument;
import com.interbank.challenge.entity.dto.request.TransactionRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);

    @Mapping(source = "accountDebit", target = "accountExternalIdDebit")
    @Mapping(source = "accountCredit", target = "accountExternalIdCredit")
    @Mapping(source = "type", target = "tranferTypeId")
    @Mapping(source = "values", target = "value")
    @Mapping(source = "status", target = "transactionStatus")
    @Mapping(source = "date", target = "createdAt")
    TransactionDocument toTransactionDocument(TransactionRequest request);

    @Mapping(source = "accountExternalIdDebit", target = "accountDebit")
    @Mapping(source = "accountExternalIdCredit", target = "accountCredit")
    @Mapping(source = "tranferTypeId", target = "type")
    @Mapping(source = "value", target = "values")
    @Mapping(source = "transactionStatus", target = "status")
    @Mapping(source = "createdAt", target = "date")
    TransactionRequest toTransactionRequest(TransactionDocument document);
}
