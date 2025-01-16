package com.utsem.app.dto;

public class EstatusUsuarioDTO {
	Boolean acceroCorrecto = false;
	String mensajes = "";
	String menu;
	public Boolean getAcceroCorrecto() {
		return acceroCorrecto;
	}
	public void setAcceroCorrecto(Boolean acceroCorrecto) {
		this.acceroCorrecto = acceroCorrecto;
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
