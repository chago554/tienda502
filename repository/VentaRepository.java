package com.utsem.app.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utsem.app.model.Venta;

public interface VentaRepository extends JpaRepository<Venta, Long>{
	Optional<Venta> findByUuid(UUID uuid);

}
