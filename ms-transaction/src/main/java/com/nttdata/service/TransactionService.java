package com.nttdata.service;

import com.nttdata.controller.request.TransactionReq;
import com.nttdata.controller.response.CreateTransactionRes;
import com.nttdata.controller.response.TransactionRes;
import com.nttdata.entity.Transaction;
import com.nttdata.helper.Util;
import com.nttdata.repository.TransactionRepository;
import com.nttdata.service.mapper.TransactionMapper;
import com.nttdata.topic.client.AntifraudProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TransactionService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private final TransactionRepository transactionRepository;
    private final AntifraudProducer antifraudProducer;

    public TransactionService(TransactionRepository transactionRepository, AntifraudProducer antifraudProducer) {
        this.transactionRepository = transactionRepository;
        this.antifraudProducer = antifraudProducer;
    }

    public CreateTransactionRes createTransaction(TransactionReq request) {
        try {
            Transaction transaction = TransactionMapper.TransactionRequestMapperToTransaction(request);
            Transaction transactionSaved = transactionRepository.save(transaction);
            antifraudProducer.sendTransactionCreatedMessage(transactionSaved);
            CreateTransactionRes response = new CreateTransactionRes();
            response.setMensaje("Transaccion creada correctamente id : " + transactionSaved.getId());
            return response;
        } catch (Exception e) {
            logger.info(e.getMessage());
            CreateTransactionRes response = new CreateTransactionRes();
            response.setMensaje("Transaction no pudo ser realizada");
            return response;
        }
    }

    public TransactionRes getTransaction(String transactionId) {
        Optional<Transaction> optionalTransaction = transactionRepository.findById(transactionId);
        Transaction transaction = optionalTransaction.orElse(null);

        return TransactionRes.builder()
                .transactionExternalId(transaction.getId())
                .transactionType(TransactionRes.TransactionType.builder().name(
                        Util.transferType.TRANSFER.getCode().compareTo(transaction.getTransferTypeId()) == 0
                                ? Util.transferType.TRANSFER.getName() : "NOTYPE"
                ).build())
                .transactionStatus(TransactionRes.TransactionStatus.builder().name(transaction.getStatus()).build())
                .value(transaction.getValue())
                .createdAt(transaction.getCreatedAt()).build();
    }

    public void updateApprovedTransaction(String code) {
        int filasAfectadas = transactionRepository.updateStatusById(code, Util.TransactionStatus.APPROVED.name());
        if (filasAfectadas > 0)
            logger.info("Se actualizo el estado a APPROVED de la transaccion {} : ", code);
        else
            logger.info("No actualizo el estado a APPROVED de la transaccion {} : ", code);
    }

    public void updateRejectedTransaction(String code) {
        try {
            int filasAfectadas = transactionRepository.updateStatusById(code, Util.TransactionStatus.REJECTED.name());
            if (filasAfectadas > 0)
                logger.info("Se actualizo el estado a REJECTED de la transaccion {} : ", code);
            else
                logger.info("No actualizo el estado a REJECTED de la transaccion {} : ", code);
        } catch (Exception e) {
            logger.info(e.getMessage());
        }

    }

}