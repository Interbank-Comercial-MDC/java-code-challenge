package com.interbank.challenge.service;
;
import com.interbank.challenge.entity.UsuarioDocument;
import com.interbank.challenge.reporitory.RoleRepository;
import com.interbank.challenge.reporitory.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public Mono<UsuarioDocument> findByUsername(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public Mono<UsuarioDocument> saveUser(UsuarioDocument user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.getRoleDocument().setDate(LocalDate.now());
        return usuarioRepository.save(user);
    }

    @Override
    public boolean comparePasswords(String rawPassword, String encodedPassword) {
        return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
    }
}
