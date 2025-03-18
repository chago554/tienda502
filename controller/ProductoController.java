package com.utsem.app.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utsem.app.dto.EstatusUsuarioDTO;
import com.utsem.app.dto.ProductoDTO;
import com.utsem.app.dto.RespuestaDTO;
import com.utsem.app.enums.Perfiles;
import com.utsem.app.service.ProductoService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("producto")
public class ProductoController {
	@Autowired
	ProductoService productoService;

	@PostMapping("listar")
	public List<ProductoDTO> listar(HttpSession sesion) {
		return productoService.listar(sesion);
	}

	@PostMapping("crearProducto")
	public ProductoDTO crearProducto(@RequestBody ProductoDTO productoDTO, HttpSession sesion) {
		if (sesion.getAttribute("estatusUsuario") != null
				&& (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil()
						.equals(Perfiles.Administrador)
						|| ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil()
								.equals(Perfiles.Almacen))) {
			return productoService.crearProducto(productoDTO, sesion);
		}
		ProductoDTO dto = new ProductoDTO();
		dto.setExito(false);
		dto.setMensaje("¡Acceso denegado!");
		return dto;

	}

	@PostMapping("eliminarProducto")
	public String eliminarProducto(HttpSession sesion, @RequestBody UUID uuid) {
		if (sesion.getAttribute("estatusUsuario") != null
				&& (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil()
						.equals(Perfiles.Administrador)
						|| ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil()
								.equals(Perfiles.Almacen))) {
			return productoService.eliminarProducto(sesion, uuid);
		}
		return "Acceso denegado";
	}

	
	@PostMapping("buscarProducto")
	public ProductoDTO buscarProducto(HttpSession sesion, @RequestBody UUID uuid) {
		ProductoDTO dto = new ProductoDTO();

		if (sesion.getAttribute("estatusUsuario") != null
				&& (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil()
						.equals(Perfiles.Administrador)
						|| ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil()
								.equals(Perfiles.Almacen))) {
			return productoService.buscarProducto(sesion, uuid);
		}
		dto.setExito(false);
		dto.setMensaje("¡Acceso denegado!");
		return dto;
	}

	
	
	@PostMapping("modificarProducto")
	public RespuestaDTO modificarProducto(HttpSession sesion, @RequestBody ProductoDTO productoDTO) {
		RespuestaDTO dto = new RespuestaDTO();

		if (sesion.getAttribute("estatusUsuario") != null
				&& (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil()
						.equals(Perfiles.Administrador)
						|| ((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil()
								.equals(Perfiles.Almacen))) {
			return productoService.modificarProducto(sesion, productoDTO);
		}
		dto.setExito(false);
		dto.setMensaje("¡Acceso denegado!");
		return dto;
	}

}
