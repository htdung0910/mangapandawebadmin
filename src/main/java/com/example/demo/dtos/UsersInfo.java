package com.example.demo.dtos;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="Users")
public class UsersInfo implements Serializable{
	@Id
	private String username;
	private String fullname;
	private String password;
	private boolean isadmin;
	@OneToMany(mappedBy = "user")
	private Set<BookProcesses> bookProcesses = new HashSet<BookProcesses>();
	public UsersInfo(String username, String fullname, String password, boolean isAdmin) {
		super();
		this.username = username;
		this.fullname = fullname;
		this.password = password;
		this.isadmin = isAdmin;
	}
	public UsersInfo() {
		super();
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isAdmin() {
		return isadmin;
	}
	public void setAdmin(boolean isAdmin) {
		this.isadmin = isAdmin;
	}
	public Set<BookProcesses> getBookProcesses() {
		return bookProcesses;
	}
	public void setBookProcesses(Set<BookProcesses> bookProcesses) {
		this.bookProcesses = bookProcesses;
	}
	
	
}
