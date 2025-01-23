//el services es el que va a chambear
package com.utsem.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utsem.app.dto.EstatusUsuarioDTO;
import com.utsem.app.dto.LoginDTO;
import com.utsem.app.dto.UsuarioDTO;
import com.utsem.app.model.Usuario;
import com.utsem.app.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	ModelMapper modelMapper;
	
	
	public List<UsuarioDTO> lista(){
		/*	
		List<UsuarioDTO> listaDTO = new ArrayList<>();
		List<Usuario> listaBD =  usuarioRepository.findAll();
				for (Usuario usuario : listaBD) {
			listaDTO.add(modelMapper.map(usuario, UsuarioDTO.class));
		}
		return listaDTO;
		*/
			return usuarioRepository.findAll().stream().map(usuario -> modelMapper.map(usuario, UsuarioDTO.class)).collect(Collectors.toList());
	}
	
	public EstatusUsuarioDTO login(LoginDTO datosAcceso, HttpSession sesion) {
		EstatusUsuarioDTO respuesta = new EstatusUsuarioDTO();
		Optional<Usuario> usuarioBD = usuarioRepository.findByUsernameAndPassword(datosAcceso.getUsername(), datosAcceso.getPassword());
	
		if(usuarioBD.isPresent()) {
			respuesta.setAccesoCorrecto(true);
			respuesta.setMensajes("¡Acceso correcto!");
			respuesta.setMenu(usuarioBD.get().getPerfil().toString().toLowerCase());
			respuesta.setNombreCompleto(usuarioBD.get().getNombrCompleto());
			respuesta.setPerfil(usuarioBD.get().getPerfil());
			sesion.setAttribute("estatusUsuario", respuesta);  //me va a servir para que se contenga el estatus del usuario para su sesion
		}else {
			respuesta.setMensajes("¡Acceso denegado!");
		}	
		return respuesta;
	}
	
	
	
	
}
