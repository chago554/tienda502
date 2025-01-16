//el services es el que va a chambear
package com.utsem.app.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utsem.app.dto.UsuarioDTO;
import com.utsem.app.model.Usuario;
import com.utsem.app.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	ModelMapper modelMapper;
	
	public List<UsuarioDTO> lista(){
		List<UsuarioDTO> listaDTO = new ArrayList<>();
		List<Usuario> listaBD =  usuarioRepository.findAll();
		
				for (Usuario usuario : listaBD) {
			listaDTO.add(modelMapper.map(usuario, UsuarioDTO.class));
		}
		return listaDTO;
	}
}
