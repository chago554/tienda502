package com.utsem.app.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VentaDTO {
	Float total;
	LocalDateTime fecha;
	UUID uuid =UUID.randomUUID();
	List<DetalleVentaDTO> detalles =  new ArrayList<>();
	UsuarioDTO usuario;
	
	
	public Float getTotal() {
		return total;
	}
	public void setTotal(Float total) {
		this.total = total;
	}
	public LocalDateTime getFecha() {
		return fecha;
	}
	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public List<DetalleVentaDTO> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<DetalleVentaDTO> detalles) {
		this.detalles = detalles;
	}
	public UsuarioDTO getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
}
