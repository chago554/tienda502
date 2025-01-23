package com.utsem.app.dto;

public class EstatusUsuarioDTO {
	Boolean accesoCorrecto = false;
	String mensajes = "";
	String menu = "";
	
	
	
	public EstatusUsuarioDTO() {
		super();
		// TODO Auto-generated constructor stub
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
