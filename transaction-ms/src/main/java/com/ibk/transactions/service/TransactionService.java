package com.ibk.transactions.service;

import com.ibk.transactions.dto.CreateTransactionRequestDTO;
import com.ibk.transactions.dto.TransactionResponseDTO;
import com.ibk.transactions.entity.Transaction;
import com.ibk.transactions.entity.TransactionStatus;
import com.ibk.transactions.exceptions.TransactionNotFoundException;
import com.ibk.transactions.kafka.producer.TransactionProducer;
import com.ibk.transactions.repository.TransactionRepository;
import com.ibk.transactions.repository.TransactionStatusRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionStatusRepository transactionStatusRepository;

    @Autowired
    private TransactionProducer transactionProducer;

    @Transactional
    public Transaction createTransaction(CreateTransactionRequestDTO request) {

        TransactionStatus pendingStatus = transactionStatusRepository.findByName("Pendiente");
        Transaction transaction = new Transaction();
        transaction.setAccountExternalIdDebit(request.accountExternalIdDebit());
        transaction.setAccountExternalIdCredit(request.accountExternalIdCredit());
        transaction.setTransferTypeId(request.transferTypeId());
        transaction.setValue(request.value());
        transaction.setTransactionStatus(pendingStatus);

        // Guardar la transacción inicialmente
        Transaction savedTransaction = transactionRepository.save(transaction);

        // Enviar la transacción al microservicio de anti fraude para validación
        transactionProducer.sendTransaction(request);

        return savedTransaction;
    }

    @Transactional(readOnly = true)
    public TransactionResponseDTO getTransaction(Long id) {
        Optional<Transaction> transactionOptional = transactionRepository.findById(id);
        if (transactionOptional.isPresent()) {
            Transaction transaction = transactionOptional.get();
            return new TransactionResponseDTO(
                    transaction.getId(),
                    transaction.getTransferTypeId(),
                    transaction.getTransactionStatus().getName(),
                    transaction.getValue(),
                    transaction.getCreatedAt(),
                    transaction.getUpdatedAt()
            );
        } else {
            throw new TransactionNotFoundException(id);
        }
    }

    // Método para recibir la respuesta del microservicio de anti fraude

}



