package com.utsem.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utsem.app.dto.DetalleVentaDTO;
import com.utsem.app.dto.EstatusUsuarioDTO;
import com.utsem.app.dto.RespuestaDTO;
import com.utsem.app.dto.VentaDTO;
import com.utsem.app.enums.Perfiles;
import com.utsem.app.service.VentaService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("ventas")
public class VentaController {
	@Autowired
	VentaService ventaService;

	@PostMapping("buscarProducto")
	public RespuestaDTO buscarProducto(@RequestBody String codigo, HttpSession sesion) {
		RespuestaDTO dto = new RespuestaDTO();
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador)) || ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Ventas)) {
			return (RespuestaDTO) ventaService.buscarProducto(sesion, codigo);
		} else {
			dto.setExito(false);
			dto.setMensaje("¡Acceso denegado!");
			return dto;
		}
	}

	@PostMapping("cargaCarrito")
	public VentaDTO cargaCarrito(HttpSession sesion) {
		
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador)) || ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Ventas)) {
			return ventaService.cargarCarrito(sesion);
		}
		return null;
	}
	
	@PostMapping("limpiarVenta")
	public String limpiarVenta(HttpSession sesion) {
		
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador)) || ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Ventas)) {
			sesion.setAttribute("venta", new VentaDTO());
		}
		return "Acceso denegado";
	}
	
	@PostMapping("realizarVenta")
	public String realizarVenta(HttpSession sesion) {
		
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador)) || ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Ventas)) {
			return ventaService.realizarVenta(sesion); 
		}
		return "Acceso denegado";
	}
	
	@PostMapping("eliminarDetalle")
	public String eliminarDetalle(HttpSession sesion, @RequestBody UUID uuid) {
		
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador)) || ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Ventas)) {
			return ventaService.eliminarDetalle(sesion, uuid);
		} else {
			return "¡Acceso denegado!";
		}
	}
	
	@PostMapping("modificarDetalle")
	public RespuestaDTO modificarDetalle (HttpSession sesion, @RequestBody DetalleVentaDTO detalle) {
		RespuestaDTO dto = new RespuestaDTO();
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador)) || ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Ventas)) {
			return (RespuestaDTO) ventaService.modificarDetalle(sesion, detalle);
		} 
		dto.setExito(false);
		dto.setMensaje("¡Acceso denegado!");
		return dto;
		
	}
	
	@PostMapping("listarVentas")	
	public List<VentaDTO> listarVentas(HttpSession sesion) {
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador)) || ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Ventas)) {
		 return (List<VentaDTO>) ventaService.listarVentas(sesion);
		}
		return new ArrayList<>();
	}
	

	
}
