package com.utsem.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.utsem.app.model.Usuario;

public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{

}
