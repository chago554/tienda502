package com.utsem.app.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.utsem.app.model.DetalleVenta;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Long> {
	Optional<DetalleVenta> findByUuid(UUID uuid);
	List<DetalleVenta> findByVentaUuid(UUID uuid);
}
