package com.utsem.app.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.utsem.app.dto.UsuarioDTO;
import com.utsem.app.service.UsuarioService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("Usuarios")
public class UsuarioController {
	@Autowired
	UsuarioService usuarioService;
	
	@GetMapping("listar")
	public List<UsuarioDTO> getListar() {
		return usuarioService.lista();
	}
	

}
