package com.challenge.transaction.controller;

import com.challenge.transaction.controller.converter.TransactionResponseMapper;
import com.challenge.transaction.controller.request.TransactionRequest;
import com.challenge.transaction.controller.response.TransactionResponse;
import com.challenge.transaction.service.ITransactionService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class TransactionController {
  private final ITransactionService transactionService;
  private final TransactionResponseMapper transactionResponseMapper;

  @ResponseStatus(HttpStatus.OK)
  @GetMapping("/{transactionId}")
  public TransactionResponse getTransaction(@PathVariable Integer transactionId) {
    return transactionResponseMapper.toTransactionResponseFromTransaction(
        transactionService.getTransaction(transactionId));
  }

  @ResponseStatus(HttpStatus.CREATED)
  @PostMapping
  public TransactionResponse createTransaction(
      @Valid @RequestBody TransactionRequest transactionRequest) {
    return transactionResponseMapper.toTransactionResponseFromTransaction(
        transactionService.createTransaction(
            transactionResponseMapper.toTransactionFromTransactionRequest(transactionRequest)));
  }
}
