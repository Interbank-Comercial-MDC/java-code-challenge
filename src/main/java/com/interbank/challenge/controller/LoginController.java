package com.interbank.challenge.controller;

import com.interbank.challenge.config.jwt.JwtUtil;
import com.interbank.challenge.entity.UsuarioDocument;
import com.interbank.challenge.entity.dto.request.SigninRequest;
import com.interbank.challenge.entity.dto.response.MessageResponse;
import com.interbank.challenge.entity.dto.response.SigninResponse;
import com.interbank.challenge.service.UsuarioService;
import com.interbank.challenge.util.Constantes;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ReactiveUserDetailsService CustomUserDetailsService;

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/signin")
    @Operation(summary = "Metodo signin", description = "Devuelve el token en jwt")
    public Mono<ResponseEntity<?>> createAuthenticationToken(@Valid @RequestBody SigninRequest signinRequest) {
        return usuarioService.findByUsername(signinRequest.getUsername())
                .flatMap(userDetails -> {
                    if (userDetails != null && usuarioService.comparePasswords(signinRequest.getPassword(), userDetails.getPassword())) {
                        String jwt = jwtUtil.generateToken(signinRequest.getUsername());
                        return Mono.just(ResponseEntity.ok(SigninResponse.builder()
                                .accessToken(jwt)
                                .tokenType(Constantes.BEARER)
                                .id(userDetails.getId())
                                .username(signinRequest.getUsername())
                                .email(userDetails.getEmail())
                                .locked(userDetails.getLocked())
                                .disable(userDetails.getDisable())
                                .role(userDetails.getRoleDocument().getRole())
                                .build()));
                    } else {
                        return Mono.just(ResponseEntity.status(401).body(MessageResponse.builder()
                                .codeResponse(401)
                                .messageResponse(Constantes.INVALID_CREDENTIAL)
                                .build()));
                    }
                });
    }

    @PostMapping("/signup")
    @Operation(summary = "Metodo signup", description = "Registra un Usuario en la base de datos")
    public Mono<ResponseEntity<MessageResponse>> saveUser(@RequestBody UsuarioDocument user) {

        return usuarioService.findByUsername(user.getUsername())
                .flatMap(existingUser -> {
                    return Mono.just(ResponseEntity.status(HttpStatus.CONFLICT).body(MessageResponse.builder()
                            .codeResponse(HttpStatus.CONFLICT.value())
                            .messageResponse("User already exists")
                            .build()));
                })
                .switchIfEmpty(
                        usuarioService.saveUser(user)
                                .map(savedUser -> ResponseEntity.status(HttpStatus.CREATED).body(MessageResponse.builder()
                                        .codeResponse(HttpStatus.CREATED.value())
                                        .messageResponse("Successful registration")
                                        .build()))
                                .onErrorResume(e -> {
                                    System.err.println("Error saving user: " + e.getMessage());
                                    e.printStackTrace();
                                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageResponse.builder()
                                            .codeResponse(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                            .messageResponse("Service error")
                                            .build()));
                                })
                );
    }

    @PostMapping("/listuser")
    @Operation(summary = "Metodo listuser", description = "Lista de Usuario en la base de datos")
    public Mono<ResponseEntity<?>> listuser(@RequestHeader("Authorization") String token, @Valid @RequestBody SigninRequest signinReques) {

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (jwtUtil.validateToken(token, signinReques.getUsername())) {
            return Mono.just(ResponseEntity.status(HttpStatus.OK).body(MessageResponse.builder()
                    .codeResponse(HttpStatus.OK.value())
                    .messageResponse("Successful registration")
                    .build()));
        } else {
            return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(MessageResponse.builder()
                    .codeResponse(HttpStatus.INTERNAL_SERVER_ERROR.value())
                    .messageResponse("Service error")
                    .build()));
        }
    }

}