//el services es el que va a chambear
package com.utsem.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.utsem.app.dto.EstatusUsuarioDTO;
import com.utsem.app.dto.LoginDTO;
import com.utsem.app.dto.RespuestaDTO;
import com.utsem.app.dto.UsuarioDTO;
import com.utsem.app.dto.UsuarioDTOPassword;
import com.utsem.app.enums.Perfiles;
import com.utsem.app.model.Usuario;
import com.utsem.app.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class UsuarioService {

	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	ModelMapper modelMapper;

	// listar
	public List<UsuarioDTO> lista() {
		/*
		 * List<UsuarioDTO> listaDTO = new ArrayList<>(); List<Usuario> listaBD =
		 * usuarioRepository.findAll(); for (Usuario usuario : listaBD) {
		 * listaDTO.add(modelMapper.map(usuario, UsuarioDTO.class)); } return listaDTO;
		 */
		return usuarioRepository.findAll().stream().map(usuario -> modelMapper.map(usuario, UsuarioDTO.class))
				.collect(Collectors.toList());
	}

	// login
	public EstatusUsuarioDTO login(LoginDTO datosAcceso, HttpSession sesion) {
		EstatusUsuarioDTO respuesta = new EstatusUsuarioDTO();
		Optional<Usuario> usuarioBD = usuarioRepository.findByUsernameAndPassword(datosAcceso.getUsername(),
				datosAcceso.getPassword());

		if (usuarioBD.isPresent()) {
			respuesta.setAccesoCorrecto(true);
			respuesta.setMensajes("¡Acceso correcto!");
			respuesta.setMenu(usuarioBD.get().getPerfil().toString().toLowerCase());
			respuesta.setNombreCompleto(usuarioBD.get().getNombrCompleto());
			respuesta.setPerfil(usuarioBD.get().getPerfil());
			respuesta.setUuid(usuarioBD.get().getUuid());
			sesion.setAttribute("estatusUsuario", respuesta); // me va a servir para que se contenga el estatus del
																// usuario para su sesion
		} else {
			respuesta.setMensajes("¡Acceso denegado!");
		}
		return respuesta;
	}

	// perfies
	public Perfiles[] perfiles(HttpSession sesion) {
		return Perfiles.values();
	}

	// crear usuarios
	public UsuarioDTO crearUsuario(UsuarioDTOPassword usuarioDTOPassword, HttpSession session) {
		UsuarioDTO dto = new UsuarioDTO();
		Optional<Usuario> existeUsername = usuarioRepository.findByUsername(usuarioDTOPassword.getUsername());
		if (existeUsername.isPresent()) {
			dto.setExito(false);
			dto.setMensaje("¡Ya existe este username!");
			return dto;
		}
		Usuario usuario = modelMapper.map(usuarioDTOPassword, Usuario.class);
		usuario.setPassword(usuarioDTOPassword.getPassword());
		usuarioRepository.save(usuario);
		dto = modelMapper.map(usuario, UsuarioDTO.class);
		dto.setExito(true);
		dto.setMensaje("¡Usuario creado con éxito!");
		return dto;
	}

	// eliminar usuario
	public RespuestaDTO eliminarUser(UUID uuid) {
		Optional<Usuario> userExiste = usuarioRepository.findByUuid(uuid);
		RespuestaDTO respuesta = new RespuestaDTO();
		Long id;
		if (userExiste.isPresent()) {

			try {
				id = usuarioRepository.findByUuid(uuid).get().getId();
				usuarioRepository.deleteById(id);
				respuesta.setExito(true);
				respuesta.setMensaje("¡Usuario eliminado con exito!");
				return respuesta;
			} catch (DataIntegrityViolationException e) {
				respuesta.setExito(false);
				respuesta.setMensaje("¡No se puede eliminar este usuario!");
				return respuesta;
			}

		}
		respuesta.setExito(false);
		respuesta.setMensaje("¡No existe este usuario!");
		return respuesta;
	}

	// buscar usuario
	public UsuarioDTO buscarUser(HttpSession sesion, UUID uuid) {
		Optional<Usuario> existeUser = usuarioRepository.findByUuid(uuid);
		UsuarioDTO usuarioDTO = new UsuarioDTO();

		if (existeUser.isPresent()) {
			return usuarioDTO = modelMapper.map(usuarioRepository.findByUuid(existeUser.get().getUuid()),
					UsuarioDTO.class);
		}
		usuarioDTO.setExito(false);
		usuarioDTO.setMensaje("¡Este usuario no existe!");
		return usuarioDTO;
	}

	// modificar usuario
	public RespuestaDTO modificarUsuario(HttpSession sesion, UsuarioDTO usuarioDTO) {
		Optional<Usuario> existeUser = usuarioRepository.findByUuid(usuarioDTO.getUuid());
		Usuario usuario = existeUser.get();
		RespuestaDTO dto = new RespuestaDTO(); 

		if (existeUser.isPresent()) {

			if (usuarioDTO.getNombre().isBlank() || usuarioDTO.getPaterno().isBlank()
					|| usuarioDTO.getMaterno().isBlank() || usuarioDTO.getUsername().isBlank()) {
				dto.setExito(false);
				dto.setMensaje("Rellene los campos correctamente");
				return dto;
			}

			if (!usuario.getUsername().equals(usuarioDTO.getUsername())
					&& usuarioRepository.findByUsername(usuarioDTO.getUsername()).isPresent()) {
				dto.setExito(false);
				dto.setMensaje("El nombre de usuario ya está en uso.");
				return dto;
			}

			usuario.setNombre(usuarioDTO.getNombre());
			usuario.setPaterno(usuarioDTO.getPaterno());
			usuario.setMaterno(usuarioDTO.getMaterno());
			usuario.setUsername(usuarioDTO.getUsername());
			usuarioRepository.save(usuario);
			dto.setExito(true);
			dto.setMensaje("¡Modificación exitosa!");
			return dto;
		}

		dto.setExito(false);
		dto.setMensaje("¡No existe este usuario!");
		return dto;
	}
	
	
	//cambiar perfil
	public RespuestaDTO cambiarPerfil (HttpSession sesion, UsuarioDTO usuarioDTO) {
		Optional<Usuario> existeUser = usuarioRepository.findByUuid(usuarioDTO.getUuid());
		Usuario usuario = existeUser.get();
		RespuestaDTO dto = new RespuestaDTO();

		if (existeUser.isPresent()) {
			usuario.setPerfil(usuarioDTO.getPerfil());
			usuarioRepository.save(usuario);
			dto.setExito(true);
			dto.setMensaje("¡Modificación exitosa!");
			return dto;
		}
		dto.setExito(false);
		dto.setMensaje("¡No existe este usuario!");
		return dto;
		
	}
	

}
