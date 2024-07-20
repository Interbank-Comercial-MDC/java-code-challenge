package com.nttdata.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class Util {

    public static String toJsonString(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        return mapper.writeValueAsString(object);
    }

    @Getter
    @AllArgsConstructor
    public enum transferType {
        TRANSFER(1, "TRANSFER"),
        DEPOSIT(2, "DEPOSIT"),
        ITF(3, "ITF");

        private final Integer code;
        private final String name;

    }

    @Getter
    @AllArgsConstructor
    public enum TransactionStatus {
        PENDING,
        APPROVED,
        REJECTED
    }

}
