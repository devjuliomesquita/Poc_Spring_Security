package com.juliomesquita.security.infra.dtos;

import com.juliomesquita.security.infra.entities.User;
import lombok.Builder;

import java.util.List;
@Builder
public record UserResponse(
        List<UserDTO> users
) {
}
