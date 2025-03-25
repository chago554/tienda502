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
import com.utsem.app.dto.VentaDetalleDTO;
import com.utsem.app.enums.Perfiles;
import com.utsem.app.repository.DetalleVentaRepository;
import com.utsem.app.repository.VentaRepository;
import com.utsem.app.service.DetalleVentaService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("detalleVenta")
public class DetalleVentaController {
	@Autowired
	VentaRepository ventaRepository;
	@Autowired
	DetalleVentaRepository detalleVentaRepository;
	@Autowired
	DetalleVentaService detalleVentaService;

	// listar detalles de una sola venta

	@PostMapping("verDetalles")
	public List<DetalleVentaDTO> verDetalles(HttpSession sesion, @RequestBody UUID uuid) {
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil() .equals(Perfiles.Administrador)) || ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Ventas)) {
			return detalleVentaService.verDetalles(sesion, uuid);
		}
		return new ArrayList<>();
	}

	// modificar un detalle de la venta
	@PostMapping("modificarDetalleVenta")
	public DetalleVentaDTO modificarDetalleVenta(HttpSession sesion, @RequestBody VentaDetalleDTO ventaDetalleDTO) {
		DetalleVentaDTO dto = new DetalleVentaDTO();
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador)) || ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Ventas)) {
			return (DetalleVentaDTO) detalleVentaService.modificarDetalleVenta(sesion, ventaDetalleDTO);
		}
		dto.setExito(false);
		dto.setMensaje("¡Acceso denegado!");
		return dto;
	}

	// eliminar el detalle
	@PostMapping("eliminarDetalle")
	public RespuestaDTO eliminarDetalle(HttpSession sesion, @RequestBody VentaDetalleDTO ventaDetalleDTO) {
		RespuestaDTO dto = new RespuestaDTO();
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador)) || ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Ventas)) {
			return detalleVentaService.eliminarDetalle(sesion, ventaDetalleDTO);
		}
		dto.setExito(false);
		dto.setMensaje("¡Acceso denegado!");
		return dto;
	}

	// eliminar una venta
	@PostMapping("eliminarVenta")
	public RespuestaDTO eliminarVenta(HttpSession sesion, @RequestBody UUID uuid) {
		RespuestaDTO dto = new RespuestaDTO();
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador)) || ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Ventas)) {
			return detalleVentaService.eliminarVenta(sesion, uuid);
		}
		dto.setExito(false);
		dto.setMensaje("¡Acceso denegado!");
		return dto;
	}

}
