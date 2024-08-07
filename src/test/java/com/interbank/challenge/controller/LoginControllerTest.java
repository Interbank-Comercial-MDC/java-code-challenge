package com.interbank.challenge.controller;

import com.interbank.challenge.config.jwt.JwtUtil;
import com.interbank.challenge.entity.RoleDocument;
import com.interbank.challenge.entity.UsuarioDocument;
import com.interbank.challenge.entity.dto.request.SigninRequest;
import com.interbank.challenge.entity.dto.response.MessageResponse;
import com.interbank.challenge.entity.dto.response.SigninResponse;
import com.interbank.challenge.service.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UsuarioService usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("crear un token - debe devolver token")
    void createAuthenticationToken_ShouldReturnToken() {
        SigninRequest signinRequest = new SigninRequest();
        signinRequest.setUsername("prueba");
        signinRequest.setPassword("prueba123");

        UsuarioDocument user = new UsuarioDocument();
        user.setId("testId");
        user.setUsername(signinRequest.getUsername());
        user.setPassword("$2a$10$7d1E6Pe0Uv5A2l7OQ2iF/OuK4a1yLXwh56o5jGTHkA5Vijyz/ZGSO");
        user.setEmail("prueba@example.com");
        user.setLocked(false);
        user.setDisable(false);

        RoleDocument role = new RoleDocument();
        role.setRole("USER_ADMIN");
        user.setRoleDocument(role);

        when(usuarioService.findByUsername(signinRequest.getUsername())).thenReturn(Mono.just(user));
        when(usuarioService.comparePasswords(signinRequest.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(signinRequest.getUsername())).thenReturn("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcnVlYmEiLCJpYXQiOjE3MjMwMDQ0OTEsImV4cCI6MTcyMzAwNDc5MX0.XKTk7TJm1WOtrZSjmDsHR3XmwEIk_Qf8AK4FtlTyXRI");

        ResponseEntity<?> responseEntity = loginController.createAuthenticationToken(signinRequest).block();

        assertNotNull(responseEntity);
        assertInstanceOf(SigninResponse.class, responseEntity.getBody());

        SigninResponse signinResponse = (SigninResponse) responseEntity.getBody();
        assertEquals("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJwcnVlYmEiLCJpYXQiOjE3MjMwMDQ0OTEsImV4cCI6MTcyMzAwNDc5MX0.XKTk7TJm1WOtrZSjmDsHR3XmwEIk_Qf8AK4FtlTyXRI", signinResponse.getAccessToken());
        assertEquals("Bearer", signinResponse.getTokenType());
        assertEquals("testId", signinResponse.getId());
        assertEquals("prueba", signinResponse.getUsername());
        assertEquals("prueba@example.com", signinResponse.getEmail());
        assertFalse(signinResponse.getLocked());
        assertFalse(signinResponse.getDisable());
        assertEquals("USER_ADMIN", signinResponse.getRole());
    }

    @Test
    @DisplayName("Guarda usuario - devuelve mensaje regsitro correcto")
    void saveUser_ShouldReturnSuccessMessage() {
        UsuarioDocument user = new UsuarioDocument();

        user.setUsername("testUser");
        user.setPassword("testPassword");

        when(usuarioService.findByUsername(anyString())).thenReturn(Mono.empty());
        when(usuarioService.saveUser(any(UsuarioDocument.class))).thenReturn(Mono.just(user));

        ResponseEntity<?> responseEntity = loginController.saveUser(user).block();

        assertNotNull(responseEntity);
        assertInstanceOf(MessageResponse.class, responseEntity.getBody());

        MessageResponse messageResponse = (MessageResponse) responseEntity.getBody();
        assertEquals("Successful registration", messageResponse.getMessageResponse());
    }
}
