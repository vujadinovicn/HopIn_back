package com.hopin.HopIn.tokens;

import java.time.LocalDateTime;

import com.hopin.HopIn.entities.User;
import com.hopin.HopIn.enums.SecureTokenType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class SecureToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty
	private String token;

	private LocalDateTime expirationDate;

	private SecureTokenType type;

	@ManyToOne
	private User user;

	public SecureToken() {
	}

	public SecureToken(int id, @NotEmpty String token, @NotEmpty LocalDateTime expirationDate,
			@NotEmpty SecureTokenType type, User user) {
		super();
		this.id = id;
		this.token = token;
		this.expirationDate = expirationDate;
		this.type = type;
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public SecureTokenType getType() {
		return type;
	}

	public void setType(SecureTokenType type) {
		this.type = type;
	}

	public boolean isExpired() {
		return this.expirationDate.isBefore(LocalDateTime.now());
	}

}
