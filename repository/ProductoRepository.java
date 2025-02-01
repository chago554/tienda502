package com.utsem.app.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.utsem.app.model.Producto;

public interface ProductoRepository extends JpaRepository<Producto, Long>{
	Optional<Producto> findByCodigo(String codigo);
}
