package com.utsem.app.dto;

public class VentaDetalleDTO {
	DetalleVentaDTO detalleVentaDTO;
    VentaDTO ventaDTO;
    String programador = "Santiago";
	String mensaje;
	Boolean exito = false;
    
	public DetalleVentaDTO getDetalleVentaDTO() {
		return detalleVentaDTO;
	}
	public void setDetalleVentaDTO(DetalleVentaDTO detalleVentaDTO) {
		this.detalleVentaDTO = detalleVentaDTO;
	}
	public VentaDTO getVentaDTO() {
		return ventaDTO;
	}
	public void setVentaDTO(VentaDTO ventaDTO) {
		this.ventaDTO = ventaDTO;
	}
	public String getProgramador() {
		return programador;
	}
	public void setProgramador(String programador) {
		this.programador = programador;
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
    
    
    

}
