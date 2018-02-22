package com.smartmarket.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.smartmarket.hibernate.EntityGenerator;
import com.smartmarket.utils.SecurityUtils;

@Entity
@Table(name="USER")
public class User extends EntityGenerator {

	private static final long serialVersionUID = 1L;
	
	@NotNull
	@Column(name="EMAIL")
	private String email;
	
	@NotNull
	@Column(name="PASSWORD")
	private String password;
	
	@Column(name="NAME")
	private String name;
	
	@Column(name="DEVICE_TOKEN")
	private String deviceToken;
	
	@Column(name="FLG_ACTIVE")
	private boolean active;
	
	@Lob
	@Basic(fetch=FetchType.LAZY)
	@Column(name="PROFILE_PICTURE")
	private byte[] profilePicture;
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = SecurityUtils.generateHash(password);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public byte[] getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getDeviceToken() {
		return deviceToken;
	}

	public void setDeviceToken(String deviceToken) {
		this.deviceToken = deviceToken;
	}


}
