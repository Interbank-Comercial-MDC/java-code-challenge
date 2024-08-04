package com.interbank.challenge.reporitory;

import com.interbank.challenge.entity.UsuarioDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface UsuarioRepository extends ReactiveMongoRepository<UsuarioDocument,String> {

    Mono<UsuarioDocument> findByUsername(String username);

}
