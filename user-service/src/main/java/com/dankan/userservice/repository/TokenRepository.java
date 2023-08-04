package com.dankan.userservice.repository;

import com.dankan.userservice.domain.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    public Optional<Token> findByUserId(Long userId);

    public Optional<Token> findTokenByAccessTokenAndRefreshToken(String accessToken, String refreshToken);
}
