package com.lgutierrez.transactionService.mapper;

import com.lgutierrez.transactionService.dto.TransactionDTO;
import com.lgutierrez.transactionService.entity.Transaction;

public class TransactionMapper {
    public static TransactionDTO mapToTransactionDTO(Transaction transaction) {
        return new TransactionDTO(transaction.getAccount().getId(),
                                  null,
                                  transaction.getType().toString(),
                                  transaction.getAmount());
    }
}
