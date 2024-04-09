package com.juliomesquita.security.infra.dtos;

import com.juliomesquita.security.infra.entities.Profile;
import lombok.Builder;

import java.util.UUID;
@Builder
public record UserDTO(
        UUID id,
        String name,
        String cpf,
        String email,
        String profile
) {
}
