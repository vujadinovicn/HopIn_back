package com.hopin.HopIn.tokens;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SecureTokenRepository extends JpaRepository<SecureToken, Integer> {
	public Optional<SecureToken> findByToken(String token);
}
