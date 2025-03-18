package com.utsem.app.dto;

import java.util.UUID;

public class ProductoDTO {
	String descripcion;
	String codigo;
	Float precio;
	Float existencias;
	UUID uuid = UUID.randomUUID();
	String SantiagoJesusLaureanoFlores = "SantiagoJesúsLaureanoFlores";
	Boolean exito = false;
	String mensaje ="";
	///
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Float getPrecio() {
		return precio;
	}
	public void setPrecio(Float precio) {
		this.precio = precio;
	}
	public Float getExistencias() {
		return existencias;
	}
	public void setExistencias(Float existencias) {
		this.existencias = existencias;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	
	public String getSantiagoJesusLaureanoFlores() {
		return SantiagoJesusLaureanoFlores;
	}
	public void setSantiagoJesusLaureanoFlores(String santiagoJesusLaureanoFlores) {
		SantiagoJesusLaureanoFlores = santiagoJesusLaureanoFlores;
	}
	public Boolean getExito() {
		return exito;
	}
	public void setExito(Boolean exito) {
		this.exito = exito;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	
	
	

}
