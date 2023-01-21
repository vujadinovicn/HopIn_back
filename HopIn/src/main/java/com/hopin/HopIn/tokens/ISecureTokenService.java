package com.hopin.HopIn.tokens;

import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.enums.SecureTokenType;

public interface ISecureTokenService {
	
	public SecureToken findByToken(String token);
	
	public SecureToken createToken(User user, SecureTokenType type);

	public boolean isValid(SecureToken token);

	public void markAsUsed(SecureToken token);
	
}
