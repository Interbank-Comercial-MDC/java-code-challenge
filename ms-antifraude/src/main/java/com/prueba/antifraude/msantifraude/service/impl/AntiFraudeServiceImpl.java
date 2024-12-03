package com.prueba.antifraude.msantifraude.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.prueba.antifraude.msantifraude.Kafka.KafkaConsumer;
import com.prueba.antifraude.msantifraude.model.TransactionNew;
import com.prueba.antifraude.msantifraude.model.TransactionStatus;
import com.prueba.antifraude.msantifraude.model.TransactionType;
import com.prueba.antifraude.msantifraude.model.TransactionValidate;
import com.prueba.antifraude.msantifraude.repository.TransactionRepository;
import com.prueba.antifraude.msantifraude.service.AntiFraudeService;
import com.prueba.antifraude.msantifraude.util.StatusTransaction;
import com.prueba.antifraude.msantifraude.util.TipoTransaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;


@Service
public class AntiFraudeServiceImpl implements AntiFraudeService {

    @Autowired
    TransactionRepository transacRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private Logger logger = LoggerFactory.getLogger(KafkaConsumer.class);

    public AntiFraudeServiceImpl(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Mono<Void> validaAntifraude(TransactionNew transaction) throws ParseException {
        JsonNode jsonNode = null;

        String status = transaction.getValue()> 1000 ?
                StatusTransaction.RECHAZADO.getDescription() : StatusTransaction.APROBADO.getDescription();

        TransactionNew txExist = transacRepository.findById(transaction.getAccountExternalId().toString()).get();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String createdFormateado = dateFormat.format(txExist.getCreatedAt());

        TransactionValidate statusUpdate = TransactionValidate.builder()
                .transactionExternalId(transaction.getAccountExternalId().toString())
                .transactionType(TransactionType.builder().name(
                        transaction.getTranferTypeId() == 1 ?
                                TipoTransaction.CREDITO.getDescription() : TipoTransaction.DEBITO.getDescription()
                ).build())
                .transactionStatus(TransactionStatus.builder().name(status).build())
                .value(transaction.getValue())
                .createdAt(createdFormateado)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            ObjectMapper mapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(statusUpdate);
            jsonNode = mapper.readTree(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (jsonNode != null) kafkaTemplate.send("topic-valida-transaction", jsonNode.toString());
        logger.info("TRANSACTION ENVIADA VALIDADA: " + jsonNode.toString());
        return null;
    }
}
