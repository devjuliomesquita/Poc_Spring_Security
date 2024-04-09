package com.juliomesquita.security.infra.dtos;

import lombok.Builder;

@Builder
public record ProfileDTO(
        String name,
        String description
) {
}
