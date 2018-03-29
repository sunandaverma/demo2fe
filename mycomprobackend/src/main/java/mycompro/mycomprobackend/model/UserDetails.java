package mycompro.mycomprobackend.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class UserDetails {
	@Id
	private String username; 
	@NotNull
	private String firstname;
	private String lastname;
	@NotNull
	private String password;
	@NotNull
	private String role;
	@Column(unique=true,nullable=false)
	private String email;
	@NotNull
	private String address;
	@NotNull
	private long mobile;
	private boolean online_status;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
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
	public long getMobile() {
		return mobile;
	}
	public void setMobile(long mobile) {
		this.mobile = mobile;
	}
	public boolean isOnline_status() {
		return online_status;
	}
	public void setOnline_status(boolean online_status) {
		this.online_status = online_status;
	}

}