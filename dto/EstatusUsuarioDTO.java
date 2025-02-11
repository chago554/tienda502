package com.utsem.app.dto;

import java.util.UUID;

import com.utsem.app.enums.Perfiles;

public class EstatusUsuarioDTO {
	Boolean accesoCorrecto = false;
	String mensajes = "";
	String menu = "";
	String nombreCompleto ="";
	Perfiles perfil;
	UUID uuid;
	
	////
	
	
	
	public EstatusUsuarioDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getNombreCompleto() {
		return nombreCompleto;
	}

	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
	}

	public Perfiles getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfiles perfil) {
		this.perfil = perfil;
	}

	public EstatusUsuarioDTO(String mensajes) {
		super();
		this.mensajes = mensajes;
	}

	public Boolean getAccesoCorrecto() {
		return accesoCorrecto;
	}
	public void setAccesoCorrecto(Boolean accesoCorrecto) {
		this.accesoCorrecto = accesoCorrecto;
	}
	public String getMensajes() {
		return mensajes;
	}
	public void setMensajes(String mensajes) {
		this.mensajes = mensajes;
	}
	public String getMenu() {
		return menu;
	}
	public void setMenu(String menu) {
		this.menu = menu;
	}
}
