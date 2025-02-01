package com.utsem.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utsem.app.dto.VentaDTO;
import com.utsem.app.service.VentaService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("ventas")
public class VentaController {
	@Autowired
	VentaService ventaService;
	@PostMapping("buscarProducto")
	public String buscarProducto(@RequestBody String codigo, HttpSession sesion) {
		if(sesion.getAttribute("venta")==null) {
			sesion.setAttribute("venta", new VentaDTO());	
		}
		return ventaService.buscarProducto(sesion, codigo);
	}
	
	@PostMapping("cargaCarrito")
	public VentaDTO cargaCarrito(HttpSession sesion) {
		if(sesion.getAttribute("venta")==null) {
			sesion.setAttribute("venta", new VentaDTO());	
		}	
		return (VentaDTO)sesion.getAttribute("venta");
	}
}
