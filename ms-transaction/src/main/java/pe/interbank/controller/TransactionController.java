package pe.interbank.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pe.interbank.model.DTO.TransactionRequest;
import pe.interbank.model.Transaction;
import pe.interbank.service.TransactionService;
import pe.interbank.service.impl.TransactionServiceImpl;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

  private static final Logger logger = org.slf4j.LoggerFactory.getLogger(TransactionController.class);

  @Autowired
  TransactionService transactionService;

  @GetMapping("/{accountExternalId}")
  public Mono<Transaction> getTransaction(@PathVariable("accountExternalId") String accountExternalId) {
    return transactionService.getTransaction(accountExternalId);
  }

  @PostMapping
  public Mono<Transaction> sendTransaction(@RequestBody TransactionRequest transactionRq) {
    return transactionService.sendTransaction(transactionRq);
  }

}
