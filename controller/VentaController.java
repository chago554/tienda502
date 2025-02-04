package com.utsem.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utsem.app.dto.EstatusUsuarioDTO;
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
	public String buscarProducto(@RequestBody String codigo, HttpSession sesion) {

		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador)) || ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Ventas)) {
			return ventaService.buscarProducto(sesion, codigo);
		} else {
			return "¡Acceso denegado!";
		}
	}

	@PostMapping("cargaCarrito")
	public VentaDTO cargaCarrito(HttpSession sesion) {
		
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador)) || ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Ventas)) {
			return ventaService.cargarCarrito(sesion);
		}
		return null;
	}
}
