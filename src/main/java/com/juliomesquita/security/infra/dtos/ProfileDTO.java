package com.juliomesquita.security.infra.dtos;

import com.juliomesquita.security.infra.entities.Permission;
import lombok.Builder;

@Builder
public record ProfileDTO(
        String name,
        String description,
        Permission permission
) {
}
