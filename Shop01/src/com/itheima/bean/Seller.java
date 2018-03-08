package com.itheima.bean;

public class Seller {
	private String sid;
	private String username;
	private String password;
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "Seller [sid=" + sid + ", username=" + username + ", password=" + password + "]";
	}
	
}
