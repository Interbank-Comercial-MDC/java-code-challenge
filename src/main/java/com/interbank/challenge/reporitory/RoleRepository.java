package com.interbank.challenge.reporitory;

import com.interbank.challenge.entity.RoleDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends ReactiveMongoRepository<RoleDocument,String> {

}
