package com.interbank.challenge.service;
;
import com.interbank.challenge.reporitory.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserDetailsServiceImpl implements ReactiveUserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        return usuarioRepository.findByUsername(username)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("User not found")))
                .map(user -> org.springframework.security.core.userdetails.User
                        .withUsername(user.getUsername())
                        .password(user.getPassword())
                        //.roles(user.getRoleDocument().getRole())  validar usar roles o authorites
                        .authorities(user.getRoleDocument().getRole())
                        .build());
    }
}
