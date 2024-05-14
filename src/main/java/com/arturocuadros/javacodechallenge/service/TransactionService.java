package com.arturocuadros.javacodechallenge.service;

import com.arturocuadros.javacodechallenge.dto.TransactionRequest;
import com.arturocuadros.javacodechallenge.dto.TransactionSaveResponse;
import com.arturocuadros.javacodechallenge.dto.TransactionUpdateResponse;
import com.arturocuadros.javacodechallenge.exception.TransactionNotFoundException;
import com.arturocuadros.javacodechallenge.model.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> findAll();
    TransactionSaveResponse save(TransactionRequest request);
    TransactionUpdateResponse update(String transactionId, TransactionRequest request);
}
