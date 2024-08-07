package com.interbank.challenge.service;

import com.interbank.challenge.entity.RoleDocument;
import com.interbank.challenge.entity.UsuarioDocument;
import com.interbank.challenge.reporitory.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class UsuarioServiceImplTest {

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Guarda Usuario - regsitro exitoso")
    void saveUser_ShouldReturnSavedUser() {
        UsuarioDocument user = new UsuarioDocument();
        user.setUsername("prueba");
        user.setPassword("prueba123");

        RoleDocument roleDocument = new RoleDocument();
        roleDocument.setRole("ROLE_ADMIN");
        user.setRoleDocument(roleDocument);

        String encodedPassword = "encodedPassword";

        when(bCryptPasswordEncoder.encode("prueba123")).thenReturn(encodedPassword);
        when(usuarioRepository.save(any(UsuarioDocument.class))).thenReturn(Mono.just(user));
        Mono<UsuarioDocument> savedUserMono = usuarioService.saveUser(user);

        UsuarioDocument savedUser = savedUserMono.block();
        assertEquals("prueba", savedUser.getUsername());
        assertEquals(encodedPassword, savedUser.getPassword());
        assertNotNull(savedUser.getRoleDocument());
        assertEquals("ROLE_ADMIN", savedUser.getRoleDocument().getRole());
    }

    @Test
    @DisplayName("Validacion de password")
    void comparePasswords_ShouldReturnTrue() {
        when(bCryptPasswordEncoder.matches(any(), any())).thenReturn(true);

        boolean result = usuarioService.comparePasswords("rawPassword", "encodedPassword");

        assertTrue(result);
    }
}
