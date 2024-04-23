package com.juliomesquita.security.infra.entities.factories;

import com.juliomesquita.security.infra.entities.Token;
import com.juliomesquita.security.infra.entities.User;
import com.juliomesquita.security.infra.entities.enums.TokenType;
import org.springframework.stereotype.Component;

@Component
public class TokenFactory {
    public Token createToken(User user, String token){
        return Token
                .builder()
                .value(token)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .user(user)
                .build();
    }
}
