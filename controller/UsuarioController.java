//En el controller se ponen los endpoints
package com.utsem.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utsem.app.dto.EstatusUsuarioDTO;
import com.utsem.app.dto.LoginDTO;
import com.utsem.app.dto.RespuestaDTO;
import com.utsem.app.dto.UsuarioDTO;
import com.utsem.app.dto.UsuarioDTOPassword;
import com.utsem.app.enums.Perfiles;
import com.utsem.app.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("Usuarios")
public class UsuarioController {
	@Autowired	
	UsuarioService usuarioService;
	@Autowired
	ModelMapper modelMapper;
	
	@PostMapping("listar")
	public List<UsuarioDTO> getListar() {
		return usuarioService.lista();
	}
	
	@PostMapping("login")
	public EstatusUsuarioDTO postLogin(@RequestBody LoginDTO datos, HttpSession sesion) {
		return usuarioService.login(datos, sesion);
	}
	
	@PostMapping("crearUsuario") 
	public UsuarioDTO crearUsuario(@RequestBody  UsuarioDTOPassword usuarioDTOPassword, HttpSession sesion) {
         return usuarioService.crearUsuario(usuarioDTOPassword, sesion);
	}
	

	
	@PostMapping("eliminarUser")
 	public RespuestaDTO eliminarUser(HttpSession sesion ,@RequestBody UUID uuid) {
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador))) {
			return usuarioService.eliminarUser(uuid);
		}
		RespuestaDTO respuesta = new RespuestaDTO();
		respuesta.setExito(false);
		respuesta.setMensaje("Accedo denegado");
		return respuesta;
	}
	
	@PostMapping("buscarUser")
	public UsuarioDTO buscarUser(HttpSession sesion,  @RequestBody UUID uuid) {
		UsuarioDTO dto =  new UsuarioDTO();
		
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador))) {
			return usuarioService.buscarUser(sesion, uuid);
		}
		dto.setExito(false);
		dto.setMensaje("¡Acceso denegado!");
		return dto;
	}
	
	@PostMapping("modificarUsuario")
	public RespuestaDTO modificarUsuario(HttpSession sesion, @RequestBody UsuarioDTO usuarioDTO) {
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador))) {
			return usuarioService.modificarUsuario(sesion, usuarioDTO);
		}
		RespuestaDTO dto = new RespuestaDTO();
		dto.setExito(false);
		dto.setMensaje("Acceso denegado");
		return dto;
	}
	
	@PostMapping("cambiarPerfil")
	public RespuestaDTO cambiarPerfil(HttpSession sesion, @RequestBody UsuarioDTO usuarioDTO) {
		if (sesion.getAttribute("estatusUsuario") != null && (((EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario")).getPerfil().equals(Perfiles.Administrador))) {
			return usuarioService.modificarUsuario(sesion, usuarioDTO);
		}
		RespuestaDTO dto = new RespuestaDTO();
		dto.setExito(false);
		dto.setMensaje("Acceso denegado");
		return dto;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
