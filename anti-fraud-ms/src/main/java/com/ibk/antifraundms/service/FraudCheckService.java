package com.ibk.antifraundms.service;

import com.ibk.antifraundms.dto.FraudCheckRequestDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FraudCheckService {

    public String checkFraud(FraudCheckRequestDTO request) {
        if (request.value().compareTo(BigDecimal.valueOf(1000)) > 0) {
            return "RECHAZADO";
        }
        return "APROBADO";
    }
}