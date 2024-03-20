package pe.interbank.secure.transaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.interbank.secure.transaction.dto.*;
import pe.interbank.secure.transaction.model.*;
import pe.interbank.secure.transaction.repository.*;

import javax.security.auth.login.AccountNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository transactionRepository;
  private final AccountRepository accountRepository;
  private final TransactionStatusRepository transactionStatusRepository;
  private final TransactionTypeRepository transactionTypeRepository;
  private final TransferTypeRepository transferTypeRepository;

  @Override
  @Transactional
  public TransactionResponseDto createTransaction(TransactionRequestDto transactionRequestDto) throws Exception {

    Account debitAccount = accountRepository.findById(transactionRequestDto.accountExternalIdDebit()).orElseThrow(() -> new Exception("Debit Account not found."));
    Account creditAccount = accountRepository.findById(transactionRequestDto.accountExternalIdCredit()).orElseThrow(() -> new Exception("Credit Account not found."));
    TransactionStatus transactionStatus = transactionStatusRepository.findById(1).orElseThrow(() -> new Exception("Transaction Status not found."));
    TransferType transferType = transferTypeRepository.findById(transactionRequestDto.transferTypeId()).orElseThrow(() -> new Exception("Transfer Type not found."));
    TransactionType transactionType = transactionTypeRepository.findById(transactionRequestDto.transactionTypeId()).orElseThrow(() -> new Exception("Transaction Type not found."));

    Transaction transaction = Transaction.builder()
                                  .transactionExternalId(UUID.randomUUID())
                                  .debitAccount(debitAccount)
                                  .creditAccount(creditAccount)
                                  .value(transactionRequestDto.value())
                                  .createdAt(LocalDateTime.now())
                                  .transactionStatus(transactionStatus)
                                  .transactionType(transactionType)
                                  .transferType(transferType)
                                  .build();

    transactionRepository.save(transaction);

    antiFraudValidate(transaction);

    return TransactionResponseDto.builder()
                        .transactionExternalId(transaction.getTransactionExternalId())
                        .transactionStatus(transaction.getTransactionStatus().getName())
                        .build();
  }

  @Override
  public TransactionDetailsResponseDto getTransaction(UUID transactionExternalId) throws Exception {

    var transaction = transactionRepository.findById(transactionExternalId).orElseThrow(() -> new Exception("Transaction not found."));

    return TransactionDetailsResponseDto.builder()
      .transactionExternalId(transaction.getTransactionExternalId())
      .transactionType(TransactionTypeDto.builder().name(transaction.getTransactionType().getName()).build())
      .transactionStatus(TransactionStatusDto.builder().name(transaction.getTransactionStatus().getName()).build())
      .value(transaction.getValue())
      .createdAt(transaction.getCreatedAt())
      .build();
  }

  @Override
  public void antiFraudValidate(Transaction transaction) {

    if(transaction.getValue().compareTo(BigDecimal.valueOf(1000.00)) <= 0) {

      transaction.setTransactionStatus(TransactionStatus.builder().transactionStatusId(2).name("aprobado").build());
      transactionRepository.save(transaction);
    } else {

      transaction.setTransactionStatus(TransactionStatus.builder().transactionStatusId(3).name("rechazado").build());
      transactionRepository.save(transaction);
    }
  }

  @Override
  public void sendToAntiFraudService(Transaction transaction) {

  }
}
