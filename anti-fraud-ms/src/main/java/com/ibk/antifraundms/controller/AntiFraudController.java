package com.ibk.antifraundms.controller;

import com.ibk.antifraundms.dto.FraudCheckRequestDTO;
import com.ibk.antifraundms.service.FraudCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/fraud-check")
public class AntiFraudController {

    @Autowired
    private FraudCheckService fraudCheckService;

    @PostMapping
    public String checkFraud(@RequestBody FraudCheckRequestDTO request) {
        return fraudCheckService.checkFraud(request);
    }
}