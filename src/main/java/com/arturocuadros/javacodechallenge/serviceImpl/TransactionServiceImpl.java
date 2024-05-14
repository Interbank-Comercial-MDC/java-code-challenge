package com.arturocuadros.javacodechallenge.serviceImpl;

import com.arturocuadros.javacodechallenge.dto.TransactionRequest;
import com.arturocuadros.javacodechallenge.dto.TransactionSaveResponse;
import com.arturocuadros.javacodechallenge.dto.TransactionUpdateResponse;
import com.arturocuadros.javacodechallenge.exception.TransactionNotFoundException;
import com.arturocuadros.javacodechallenge.model.Transaction;
import com.arturocuadros.javacodechallenge.model.TransactionStatus;
import com.arturocuadros.javacodechallenge.repository.TransactionRepository;
import com.arturocuadros.javacodechallenge.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public List<Transaction> findAll() {
        return transactionRepository.findAll();
    }

    @Override
    public TransactionSaveResponse save(TransactionRequest request) {
        Transaction transaction = new Transaction();
        transaction.setTransactionExternalId(UUID.randomUUID().toString());
        transaction.setAccountExternalIdDebit(request.getAccountExternalIdDebit());
        transaction.setAccountExternalIdCredit(request.getAccountExternalIdCredit());
        transaction.setValue(request.getValue());
        transaction.setTranferTypeId(1);
        transaction.setCreatedAt(new Date());
        // savedTransaction.setTransferType(TransferType.valueOf(request.getTranferTypeId()));
        transaction.setTransferType(request.getTransferType());
        transaction.setStatus(TransactionStatus.PENDING);
        transactionRepository.save(transaction);

        // Send Anti-Fraud event
        // antiFraudService.sendApprovalEvent(transaction.getExternalId());

        TransactionSaveResponse response = new TransactionSaveResponse();
        response.setAccountExternalIdDebit(transaction.getAccountExternalIdDebit());
        response.setAccountExternalIdCredit(transaction.getAccountExternalIdCredit());
        response.setTranferTypeId(transaction.getTranferTypeId());
        response.setValue(transaction.getValue());
        return response;
    }

    @Override
    public TransactionUpdateResponse update(String transactionId, TransactionRequest request) {
        Transaction transaction = Optional
                .ofNullable(transactionRepository.findByTransactionExternalId(transactionId))
                .orElseThrow(() -> new TransactionNotFoundException("Transaction not found"));

        if (transaction.getValue() >= 1000){
            transaction.setAccountExternalIdDebit(request.getAccountExternalIdDebit());
            transaction.setAccountExternalIdCredit(request.getAccountExternalIdCredit());
            transaction.setValue(request.getValue());
            transaction.setTranferTypeId(2);
            transaction.setCreatedAt(new Date());
            // savedTransaction.setTransferType(TransferType.valueOf(request.getTranferTypeId()));
            transaction.setTransferType(request.getTransferType());
            transaction.setStatus(TransactionStatus.REJECTED);
            transactionRepository.save(transaction);

        } else {
            transaction.setAccountExternalIdDebit(request.getAccountExternalIdDebit());
            transaction.setAccountExternalIdCredit(request.getAccountExternalIdCredit());
            transaction.setValue(request.getValue());
            transaction.setTranferTypeId(3);
            transaction.setCreatedAt(new Date());
            // savedTransaction.setTransferType(TransferType.valueOf(request.getTranferTypeId()));
            transaction.setTransferType(request.getTransferType());
            transaction.setStatus(TransactionStatus.APPROVED);
            transactionRepository.save(transaction);
        }

        TransactionUpdateResponse response = new TransactionUpdateResponse();
        response.setTransactionExternalId(transaction.getTransactionExternalId());
        response.setTransferType(transaction.getTransferType());
        response.setTransactionStatus(transaction.getStatus());
        response.setValue(transaction.getValue());
        return response;
    }
}
