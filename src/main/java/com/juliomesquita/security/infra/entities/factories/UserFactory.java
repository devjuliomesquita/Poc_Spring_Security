package com.juliomesquita.security.infra.entities.factories;

import com.juliomesquita.security.infra.dtos.RegisterRequest;
import com.juliomesquita.security.infra.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserFactory {
    private final PasswordEncoder passwordEncoder;

    public User createUser(RegisterRequest request) {
        return User
                .builder()
                .id(UUID.randomUUID())
                .name(request.name())
                .cpf(request.cpf())
                .email(request.email())
                .password(this.passwordEncoder.encode(request.password()))
                .build();
    }

}
