package com.juliomesquita.security.infra.dtos;

import lombok.Builder;

@Builder
public record RegisterRequest(
        String name,
        String cpf,
        String email,
        String password,
        String confirmToPassword
) {
}
