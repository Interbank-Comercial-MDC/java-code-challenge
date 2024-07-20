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

    @Getter
    @AllArgsConstructor
    public enum transferType {
        TRANSFER(1, "TRANSFER"),
        DEPOSIT(2, "DEPOSIT"),
        ITF(3, "ITF");

        private final Integer code;
        private final String name;

    }

}
