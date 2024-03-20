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

    var response = transactionService.createTransaction(transactionRequestDto);

    return ResponseEntity.ok(TransactionResponseDto.builder()
                                        .transactionExternalId(response.getTransactionExternalId())
                                        .status("OK")
                                        .build());
  }

  @GetMapping("/{transactionExternalId}")
  public HttpEntity<TransactionDetailsResponseDto> getTransaction(@PathVariable UUID transactionExternalId) {

    return ResponseEntity.ok(TransactionDetailsResponseDto.builder().build());
  }
}
