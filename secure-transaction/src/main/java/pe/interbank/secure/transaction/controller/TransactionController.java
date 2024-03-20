package pe.interbank.secure.transaction.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.interbank.secure.transaction.dto.TransactionDetailsResponseDto;
import pe.interbank.secure.transaction.dto.TransactionRequestDto;
import pe.interbank.secure.transaction.dto.TransactionResponseDto;
import pe.interbank.secure.transaction.service.TransactionService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

  private final TransactionService transactionService;

  @PostMapping
  public HttpEntity<TransactionResponseDto> createTransaction(@RequestBody TransactionRequestDto transactionRequestDto) throws Exception {

    TransactionResponseDto response = transactionService.createTransaction(transactionRequestDto);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{transactionExternalId}")
  public HttpEntity<TransactionDetailsResponseDto> getTransaction(@PathVariable UUID transactionExternalId) throws Exception {

    TransactionDetailsResponseDto response = transactionService.getTransaction(transactionExternalId);

    return ResponseEntity.ok(response);
  }
}
