//En el controller se ponen los endpoints
package com.utsem.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utsem.app.dto.EstatusUsuarioDTO;
import com.utsem.app.dto.LoginDTO;
import com.utsem.app.dto.UsuarioDTO;
import com.utsem.app.service.UsuarioService;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("Usuarios")
public class UsuarioController {
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("listar")
	public List<UsuarioDTO> getListar() {
		return usuarioService.lista();
	}
	
	@PostMapping("login")
	public EstatusUsuarioDTO postLogin(@RequestBody LoginDTO datos, HttpSession sesion) {
		System.out.println(sesion.getId());
		return usuarioService.login(datos, sesion);
	}
	


}
