package com.utsem.app.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.utsem.app.model.Usuario;
public interface UsuarioRepository  extends JpaRepository<Usuario, Long>{
	Optional<Usuario> findByUsernameAndPassword(String username, String password);
}	