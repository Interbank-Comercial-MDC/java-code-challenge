package com.interbank.anti.fraud.core.service;

import com.interbank.anti.fraud.core.dto.FraudDetailRequestDto;

public interface AntiFraudService {
    void registerOperation(FraudDetailRequestDto fraudDetailRequestDto);

    void validOperation(Long id);
}
