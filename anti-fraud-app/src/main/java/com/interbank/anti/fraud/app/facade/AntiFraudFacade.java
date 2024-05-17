package com.interbank.anti.fraud.app.facade;

import com.interbank.anti.fraud.core.dto.FraudDetailRequestDto;
import com.interbank.anti.fraud.core.service.AntiFraudService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AntiFraudFacade {

    private final AntiFraudService antiFraudService;

    public void registerOperation(FraudDetailRequestDto fraudDetailRequestDto) {
        this.antiFraudService.registerOperation(fraudDetailRequestDto);
    }

}
