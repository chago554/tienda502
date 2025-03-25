package com.utsem.app.dto;

import java.util.UUID;

public class DetalleVentaDTO {
	ProductoDTO producto;
	Float subtotal;
	UUID uuid = UUID.randomUUID();
	Float cantidad;
	String programador = "Santiago Jesus Laureano Flores";
	Float total;
	
	VentaDTO ventaDTO;
	String mensaje = "";
	Boolean exito= false;
	
	public ProductoDTO getProducto() {
		return producto;
	}
	public void setProducto(ProductoDTO producto) {
		this.producto = producto;
	}	
	
	public Float getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(Float subtotal) {
		this.subtotal = subtotal;
	}
	public UUID getUuid() {
		return uuid;
	}
	public void setUuid(UUID uuid) {
		this.uuid = uuid;
	}
	public Float getCantidad() {
		return cantidad;
	}
	public void setCantidad(Float cantidad) {
		this.cantidad = cantidad;
	}
	public String getProgramador() {
		return programador;
	}
	public void setProgramador(String programador) {
		this.programador = programador;
	}
	public Float getTotal() {
		return total;
	}
	public void setTotal(Float total) {
		this.total = total;
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
	public VentaDTO getVentaDTO() {
		return ventaDTO;
	}
	public void setVentaDTO(VentaDTO ventaDTO) {
		this.ventaDTO = ventaDTO;
	}
	
	
	
}
