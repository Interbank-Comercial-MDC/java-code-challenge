package com.lgutierrez.transactionService.service;

import com.lgutierrez.transactionService.dto.*;

import java.util.UUID;

public interface TransactionService {

    TransactionDTO registerTransaction(TransactionDTO transactionDTO) throws Exception;

    TransactionDTO getTransactionById(UUID transactionId);
}
