package com.juliomesquita.security.infra.dtos;

import lombok.Builder;

@Builder
public record AuthenticationResponse(
        String token
) {
}
