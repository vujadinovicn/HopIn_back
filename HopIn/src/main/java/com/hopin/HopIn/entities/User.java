package com.hopin.HopIn.entities;

import java.io.UnsupportedEncodingException;

import com.hopin.HopIn.dtos.UserDTO;
import com.hopin.HopIn.dtos.UserDTOOld;
import com.hopin.HopIn.enums.UserType;
import com.hopin.HopIn.enums.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Table(name = "users")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotEmpty
//	@Pattern(regexp = "^([a-zA-Zčćđžš ]*)$")
	private String name;

	@NotEmpty
//	@Pattern(regexp = "^([a-zA-Zčćđžš ]*)$")
	private String surname;

	@NotEmpty
	@Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
	private String email;

	@NotEmpty
	private String password;

	@NotEmpty
	@Pattern(regexp = "^([a-zA-Z0-9 \\s,'-]*)$")
	private String address;

	@NotEmpty
	@Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*")
	private String telephoneNumber;

	@Lob
	private byte[] profilePicture;

	@NotNull
	private boolean isActivated;

	@NotNull
	private boolean isBlocked;

	private Role role;

	@Transient
	private String jwt;

	public User() {
	}

	public User(int id, String name, String surname, String email, String password, String address,
			String telephoneNumber, byte[] profilePicture) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
		this.profilePicture = profilePicture;
		this.isActivated = false;
		this.isBlocked = false;
	}

	public User(int id, @NotEmpty @Pattern(regexp = "^([a-zA-Zčćđžš ]*)$") String name,
			@NotEmpty @Pattern(regexp = "^([a-zA-Zčćđžš ]*)$") String surname,
			@NotEmpty @Pattern(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$") String email,
			@NotEmpty @Pattern(regexp = "^([0-9a-zA-Z]{6,}$)") String password,
			@NotEmpty @Pattern(regexp = "^([a-zA-Z0-9 \\s,'-]*)$") String address,
			@NotEmpty @Pattern(regexp = "^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*") String telephoneNumber,
			byte[] profilePicture, @NotNull boolean isActivated, @NotNull boolean isBlocked, Role role, String jwt) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.telephoneNumber = telephoneNumber;
		this.profilePicture = profilePicture;
		this.isActivated = isActivated;
		this.isBlocked = isBlocked;
		this.role = role;
		this.jwt = jwt;
	}

	public User(UserDTO dto) {
		super();
		this.name = dto.getName();
		this.surname = dto.getSurname();
		this.email = dto.getEmail();
		this.password = dto.getPassword();
		this.address = dto.getAddress();
		this.telephoneNumber = dto.getTelephoneNumber();
		this.setProfilePicture(dto.getProfilePicture());
		this.password = dto.getPassword();
		this.isActivated = false;
	}
	
	public void copy(UserDTO dto) {
		this.name = dto.getName();
		this.surname = dto.getSurname();
		this.email = dto.getEmail();
		this.password = dto.getPassword();
		this.address = dto.getAddress();
		this.telephoneNumber = dto.getTelephoneNumber();
		this.setProfilePicture(dto.getProfilePicture());
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getProfilePicture(){
		if (this.profilePicture == null) 
			return null;
		String s;
		try {
			s = "data:image/jpeg;base64, ";
			s = s + new String(this.profilePicture, "UTF-8");
//					Base64.getEncoder().encodeToString(this.profilePicture, "UTF-8");
			return s;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void setProfilePicture(String profilePicture) {
		if (profilePicture == null)
			profilePicture = "s";
		String[] picture = profilePicture.split(",");
		if (picture.length >= 2) {
			// byte[] decoded = Base64.getDecoder().decode(picture[1], "-8");
			byte[] decoded;
			try {
				decoded = picture[1].getBytes("UTF-8");
				this.profilePicture = decoded;
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public String getTelephoneNumber() {
		return telephoneNumber;
	}

	public void setTelephoneNumber(String telephoneNumber) {
		this.telephoneNumber = telephoneNumber;
	}

	public boolean isActivated() {
		return isActivated;
	}

	public void setActivated(boolean isActivated) {
		this.isActivated = isActivated;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

}
