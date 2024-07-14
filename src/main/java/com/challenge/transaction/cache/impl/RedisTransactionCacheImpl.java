package com.challenge.transaction.cache.impl;

import com.challenge.transaction.cache.NameSpaceCacheConstant;
import com.challenge.transaction.cache.TransactionCache;
import com.challenge.transaction.model.Transaction;
import com.challenge.transaction.repository.TransactionRepository;
import com.challenge.transaction.repository.converter.TransactionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedisTransactionCacheImpl implements TransactionCache {

  private final TransactionRepository transactionRepository;
  private final TransactionMapper transactionMapper;

  @Override
  @Cacheable(cacheNames = NameSpaceCacheConstant.TRANSACTION_OPERATION, key = "#transactionId")
  public Transaction getTransaction(Integer transactionId) {
    return transactionRepository
        .findById(transactionId)
        .map(transactionMapper::toModelFromEntity)
        .orElseThrow(() -> new RuntimeException("Transaction Not found"));
  }

  @Override
  @CacheEvict(value = NameSpaceCacheConstant.TRANSACTION_OPERATION, key = "#transactionExternalId")
  public void deleteTransactionCache(Integer transactionExternalId) {}
}
