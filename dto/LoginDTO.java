package com.utsem.app.dto;

public class LoginDTO {
	String username;
	String password;
	String programador = "Santiago Jesus Laureano Flores";
	
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
	public String getProgramador() {
		return programador;
	}
	public void setProgramador(String programador) {
		this.programador = programador;
	}
	
}
