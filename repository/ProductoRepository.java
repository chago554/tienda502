package com.utsem.app.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.utsem.app.model.Producto;
import java.util.UUID;


public interface ProductoRepository extends JpaRepository<Producto, Long>{
	Optional<Producto> findByCodigo(String codigo);
	Optional<Producto> findByUuid(UUID uuid);
	Optional<Producto> findByDescripcion(String descripcion);
	long countByUuid(UUID uuid);
}
