package com.utsem.app.dto;

import java.util.UUID;
import com.utsem.app.enums.Perfiles;

public class UsuarioDTO {
	String username;
	String nombre;
	String paterno;
	String materno;
	Perfiles perfil;
	UUID uuid = UUID.randomUUID();
	String mensaje ="";;
	Boolean exito = false;
	String SantiagoJesusLaureanoFlores = "";

	// el DTO necesita los setters y getters
	public String getNombreCompleto() {
		return nombre + " " + paterno + " " + materno;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPaterno() {
		return paterno;
	}

	public void setPaterno(String paterno) {
		this.paterno = paterno;
	}

	public String getMaterno() {
		return materno;
	}

	public void setMaterno(String materno) {
		this.materno = materno;
	}

	public Perfiles getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfiles perfil) {
		this.perfil = perfil;
	}

	public UUID getUuid() {
		return uuid;
	}

	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Boolean getExito() {
		return exito;
	}

	public void setExito(Boolean exito) {
		this.exito = exito;
	}

	public String getSantiagoJesusLaureanoFlores() {
		return SantiagoJesusLaureanoFlores;
	}

	public void setSantiagoJesusLaureanoFlores(String santiagoJesusLaureanoFlores) {
		SantiagoJesusLaureanoFlores = santiagoJesusLaureanoFlores;
	}


}
