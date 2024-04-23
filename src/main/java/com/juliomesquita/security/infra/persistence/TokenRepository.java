package com.juliomesquita.security.infra.persistence;

import com.juliomesquita.security.infra.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {

    List<Token> findByUserIdAndExpiredFalseAndRevokedFalse(UUID userId);
    Optional<Token> findByValue(String value);
}
