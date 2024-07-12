package com.reto.interbank.ms_transaction.service.constans;

import java.util.UUID;

public class ServiceConstans{
    public static final String STATUS_PENDIENTE = "PENDIENTE";
    public static final String TOPIC_SEND_FRAUD = "topicSendFraud";
    
    public static String getTransactionLogMessage(UUID transactionId) {
        return String.format("Transacción con ID %s:", transactionId);
    }
    
    public static String getSendFraudLogMessage(String message) {
        return String.format("Enviando mensaje a %s: %s", TOPIC_SEND_FRAUD, message);
    }
    
    public static final String TOPIC_UPDATE_TRANSACTION_STATUS = "topicUpdateTransactionStatus";

}