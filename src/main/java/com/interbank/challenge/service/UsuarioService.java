package com.interbank.challenge.service;
;
import com.interbank.challenge.entity.UsuarioDocument;
import reactor.core.publisher.Mono;

public interface UsuarioService {

    Mono<UsuarioDocument> findByUsername(String username);
    Mono<UsuarioDocument> saveUser(UsuarioDocument user);
    boolean comparePasswords(String rawPassword, String encodedPassword);
}
