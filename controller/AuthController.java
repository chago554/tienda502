package com.utsem.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utsem.app.dto.EstatusUsuarioDTO;
import com.utsem.app.dto.LoginDTO;
import com.utsem.app.enums.Perfiles;
import com.utsem.app.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("auth")
public class AuthController {
	@Autowired
	UsuarioService usuarioService;
	//login
	@PostMapping("login")
	public EstatusUsuarioDTO postLogin(@RequestBody LoginDTO datos, HttpSession sesion) {
		System.out.println(sesion.getId());
		return usuarioService.login(datos, sesion);
	}
	
	//estatus
	@PostMapping("estatus")
	public EstatusUsuarioDTO postEstatus(HttpSession sesion) {
		return sesion.getAttribute("estatusUsuario") != null ? (EstatusUsuarioDTO)sesion.getAttribute("estatusUsuario") : new EstatusUsuarioDTO();
	}
	
	//logout
	@PostMapping("logout")
	public EstatusUsuarioDTO posLogout(HttpSession sesion) {
		sesion.invalidate();
		return new EstatusUsuarioDTO("Sesion finalizada");
	}
	
	@PostMapping("consultarPerfiles")
	public Perfiles[] consultarPerfiles(HttpSession sesion) {
		return usuarioService.perfiles(sesion);
	}
	
}
