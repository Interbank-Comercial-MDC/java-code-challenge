package com.interbank.anti.fraud.app.controller;

import com.interbank.anti.fraud.app.facade.AntiFraudFacade;
import com.interbank.anti.fraud.core.dto.FraudDetailRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/anti-fraud")
public class AntiFraudController {
    private final AntiFraudFacade antiFraudFacade;

    @PostMapping("/register")
    public ResponseEntity<Void> registerOperation(@RequestBody FraudDetailRequestDto fraudDetailRequestDto) {
        antiFraudFacade.registerOperation(fraudDetailRequestDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
