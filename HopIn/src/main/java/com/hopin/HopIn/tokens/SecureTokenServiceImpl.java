package com.hopin.HopIn.tokens;

import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.enums.SecureTokenType;

import net.bytebuddy.utility.RandomString;

@Service
public class SecureTokenServiceImpl implements ISecureTokenService {
	
	@Autowired
	private SecureTokenRepository allTokens;

	@Override
	public SecureToken createToken(User user, SecureTokenType type) {
		SecureToken token = new SecureToken();
		token.setToken(RandomString.make(64));
		token.setUser(user);
		token.setExpirationDate(Date.from(Instant.now().plus(1, ChronoUnit.MINUTES)).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
		token.setType(type);
		
		this.allTokens.save(token);
		this.allTokens.flush();
		
		return token;
	}

	@Override
	public SecureToken findByToken(String token) {
		return this.allTokens.findByToken(token).orElse(null);
	}

	@Override
	public boolean isValid(SecureToken token) {
		System.out.println(token.getToken());
		if (token == null)
			return false;
		
		User user = token.getUser();
		System.out.println(user.getId());
		if (user == null || (token.getType() == SecureTokenType.REGISTRATION && user.isActivated())) {
			return false;
		}
		
		return true;
	}

}
