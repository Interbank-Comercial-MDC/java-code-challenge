package com.challenge.transaction.controller.converter;

import com.challenge.transaction.consumer.dto.TransactionStateDTO;
import com.challenge.transaction.controller.request.TransactionRequest;
import com.challenge.transaction.controller.response.TransactionResponse;
import com.challenge.transaction.model.Transaction;
import com.challenge.transaction.repository.entity.TransactionStatusEnum;
import com.challenge.transaction.repository.entity.TransactionTypeEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TransactionResponseMapper {

  @Mapping(
      source = "tranferTypeId",
      target = "transactionType.name",
      qualifiedByName = "mapNameTransactionType")
  @Mapping(
      source = "transactionStatusId",
      target = "transactionStatus.name",
      qualifiedByName = "mapNameTransactionStatus")
  TransactionResponse toTransactionResponseFromTransaction(Transaction transaction);

  Transaction toTransactionFromTransactionRequest(TransactionRequest transactionRequest);

  @Mapping(source = "transactionId", target = "transactionExternalId")
  Transaction toTransactionFromTransactionStateDTO(TransactionStateDTO transactionStateDTO);

  @Named("mapNameTransactionType")
  default String mapNameTransactionType(Integer tranferTypeId) {
    return TransactionTypeEnum.fromCode(tranferTypeId).getDescription();
  }

  @Named("mapNameTransactionStatus")
  default String mapNameTransactionStatus(Integer transactionStatusId) {
    return TransactionStatusEnum.fromCode(transactionStatusId).getDescription();
  }
}
