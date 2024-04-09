package com.juliomesquita.security.infra.dtos;

import lombok.Builder;

@Builder
public record AuthenticationRequest(
        String cpf,
        String password
) {
}
