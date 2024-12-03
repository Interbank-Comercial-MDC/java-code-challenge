package com.prueba.antifraude.msantifraude.service;

import com.prueba.antifraude.msantifraude.model.TransactionNew;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.text.ParseException;

public interface AntiFraudeService {

    Mono<Void> validaAntifraude(TransactionNew transaction) throws IOException, ParseException;
}
