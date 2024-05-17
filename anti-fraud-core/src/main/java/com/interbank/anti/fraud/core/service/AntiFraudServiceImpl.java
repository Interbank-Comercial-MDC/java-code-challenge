package com.interbank.anti.fraud.core.service;

import com.interbank.anti.fraud.core.domain.Transaction;
import com.interbank.anti.fraud.core.dto.FraudDetailRequestDto;
import com.interbank.anti.fraud.core.kafka.publiser.KafkaProducerService;
import com.interbank.anti.fraud.core.mapper.AntiFraudMapper;
import com.interbank.anti.fraud.core.repository.AntiFraudRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class AntiFraudServiceImpl implements AntiFraudService {
    private static final String RECIBIDO = "RECIBIDO";
    private static final String RECHAZADO = "RECHAZADO";
    private static final String APROBADO = "APROBADO";
    private static final String VALUE_FRAUD = "1000";


    private final AntiFraudRepository antiFraudRepository;
    private final KafkaProducerService kafkaProducerService;

    @Override
    public void registerOperation(FraudDetailRequestDto fraudDetailRequestDto) {
        log.info("[ANTI-FRAUD] Register init...");
        Transaction transaction = AntiFraudMapper.MAPPER.buildToAntiFraudDetail(fraudDetailRequestDto);
        transaction.setCreatedAt(LocalDateTime.now());

        Transaction transactionDetail = antiFraudRepository.save(transaction);

        kafkaProducerService.sendMessage(transactionDetail.getId().toString());

        log.info("[ANTI-FRAUD] Register end");
    }

    @Override
    public void validOperation(Long id) {
        Transaction transaction = antiFraudRepository.findById(id).orElse(new Transaction());

        updateStatus(transaction, RECIBIDO);

        log.info("[ANTI-FRAUD] validate init...");
        validFraudTransaction(transaction);

    }

    private void updateStatus(Transaction transaction, String status) {
        transaction.getTransactionStatus().setName(status);

        antiFraudRepository.saveAndFlush(transaction);
    }

    private void validFraudTransaction(Transaction transaction) {
        if (VALUE_FRAUD.equals(transaction.getTransactionStatus().getName())) {
            updateStatus(transaction, RECHAZADO);
            log.info("[ANTI-FRAUD] validate end. id: {}, status: {}", transaction.getId(), RECHAZADO);
        } else {
            updateStatus(transaction, APROBADO);
            log.info("[ANTI-FRAUD] validate end. id: {}, status: {}", transaction.getId(), APROBADO);
        }
    }

}
