package com.juliomesquita.security.infra.controllers;

import com.juliomesquita.security.infra.dtos.UserDTO;
import com.juliomesquita.security.infra.dtos.UserResponse;
import com.juliomesquita.security.infra.entities.User;
import com.juliomesquita.security.infra.persistence.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/test")
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<String> ola() {
        return ResponseEntity.ok("Olá você conseguiu entrar na aplicação.");
    }

    @GetMapping("/users")
    public ResponseEntity<UserResponse> getUsers() {
        List<UserDTO> userList = this.userRepository.findAll().stream().map(this::toDTO).toList();
        return ResponseEntity.ok(UserResponse.builder().users(userList).build());
    }

    private UserDTO toDTO(User user) {
        return UserDTO
                .builder()
                .id(user.getId())
                .name(user.getName())
                .cpf(user.getCpf())
                .email(user.getEmail())
                .profile(user.getProfile().getName())
                .build();
    }
}
