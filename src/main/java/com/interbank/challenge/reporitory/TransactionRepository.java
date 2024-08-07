package com.interbank.challenge.reporitory;

import com.interbank.challenge.entity.TransactionDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends ReactiveMongoRepository<TransactionDocument,String> {

}

