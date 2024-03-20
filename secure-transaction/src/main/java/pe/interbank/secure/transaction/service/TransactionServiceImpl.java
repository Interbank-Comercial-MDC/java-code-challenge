package pe.interbank.secure.transaction.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.interbank.secure.transaction.dto.TransactionRequestDto;
import pe.interbank.secure.transaction.model.*;
import pe.interbank.secure.transaction.repository.*;

import javax.security.auth.login.AccountNotFoundException;
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
  public Transaction createTransaction(TransactionRequestDto transactionRequestDto) throws Exception {

    Account debitAccount = accountRepository.findById(transactionRequestDto.accountExternalIdDebit()).orElseThrow(() -> new Exception());
    Account creditAccount = accountRepository.findById(transactionRequestDto.accountExternalIdCredit()).orElseThrow(() -> new Exception());
    TransactionStatus transactionStatus = transactionStatusRepository.findById(1).orElseThrow(() -> new Exception());
    TransactionType transactionType = transactionTypeRepository.findById(1).orElseThrow(() -> new Exception());
    TransferType transferType = transferTypeRepository.findById(1).orElseThrow(() -> new Exception());

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

    return transaction;
  }

  @Override
  public Transaction getTransaction(String transactionExternalId) {
    return null;
  }

  @Override
  public void sendToAntiFraudService(Transaction transaction) {

  }
}
