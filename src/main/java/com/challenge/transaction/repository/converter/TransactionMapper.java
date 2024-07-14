package com.challenge.transaction.repository.converter;

import com.challenge.transaction.model.Transaction;
import com.challenge.transaction.repository.entity.TransactionEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
  Transaction toModelFromEntity(TransactionEntity transactionEntity);

  TransactionEntity toEntityFromModel(Transaction transaction);
}
