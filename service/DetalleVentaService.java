package com.utsem.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utsem.app.dto.DetalleVentaDTO;
import com.utsem.app.dto.ProductoDTO;
import com.utsem.app.dto.RespuestaDTO;
import com.utsem.app.dto.VentaDetalleDTO;
import com.utsem.app.model.DetalleVenta;
import com.utsem.app.model.Producto;
import com.utsem.app.model.Venta;
import com.utsem.app.repository.DetalleVentaRepository;
import com.utsem.app.repository.ProductoRepository;
import com.utsem.app.repository.VentaRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class DetalleVentaService {
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	VentaRepository ventaRepository;
	@Autowired
	DetalleVentaRepository detalleVentaRepository;
	@Autowired
	ProductoRepository productoRepository;

	// listar los detalles de cada venta
	public List<DetalleVentaDTO> verDetalles(HttpSession sesion, UUID uuid) {
		Optional<Venta> total = ventaRepository.findByUuid(uuid);
		List<DetalleVenta> detalles = detalleVentaRepository.findByVentaUuid(uuid);
		Float totalVenta = total.get().getTotal();
		List<DetalleVentaDTO> detallesDTO = detalles.stream().map(detalle -> {
			DetalleVentaDTO dto = modelMapper.map(detalle, DetalleVentaDTO.class);
			dto.setTotal(totalVenta);
			return dto;
		}).collect(Collectors.toList());
		return detallesDTO;
	}

	// modifcar los detalles de una venta ya creada
	public DetalleVentaDTO modificarDetalleVenta(HttpSession sesion, VentaDetalleDTO ventaDetalleDTO) {
		DetalleVentaDTO respuesta = new DetalleVentaDTO();

		// Validar venta existente
		Optional<Venta> ventaExiste = ventaRepository.findByUuid(ventaDetalleDTO.getVentaDTO().getUuid());
		if (!ventaExiste.isPresent()) {
			respuesta.setExito(false);
			respuesta.setMensaje("¡No existe la venta!");
			return respuesta;
		}

		// Validar detalle existente
		Optional<DetalleVenta> detalleExiste = detalleVentaRepository
				.findByUuid(ventaDetalleDTO.getDetalleVentaDTO().getUuid());
		if (!detalleExiste.isPresent()) {
			respuesta.setExito(false);
			respuesta.setMensaje("¡No existe el detalle!");
			return respuesta;
		}

		// Validar producto existente
		Optional<Producto> productoExiste = productoRepository
				.findByCodigo(detalleExiste.get().getProducto().getCodigo());
		if (!productoExiste.isPresent()) {
			respuesta.setExito(false);
			respuesta.setMensaje("¡Producto no encontrado!");
			return respuesta;
		}

		// Validar existencias suficientes
		Float diferenciaCantidad = ventaDetalleDTO.getDetalleVentaDTO().getCantidad()
				- detalleExiste.get().getCantidad();
		if (productoExiste.get().getExistencias() < diferenciaCantidad) {
			respuesta.setExito(false);
			respuesta.setMensaje("¡No hay suficientes productos!");
			return respuesta;
		}

		// Actualizar existencias del producto
		productoExiste.get().setExistencias((productoExiste.get().getExistencias() - diferenciaCantidad));
		productoRepository.save(productoExiste.get());

		// Actualizar detalle
		detalleExiste.get().setCantidad(ventaDetalleDTO.getDetalleVentaDTO().getCantidad());
		detalleExiste.get()
				.setSubtotal(detalleExiste.get().getProducto().getPrecio() * detalleExiste.get().getCantidad());
		detalleVentaRepository.save(detalleExiste.get());

		// calcular total
		List<DetalleVenta> detalleVenta = detalleVentaRepository.findByVentaUuid(ventaExiste.get().getUuid());
		Float total = 0f;
		for (DetalleVenta cadaDetalle : detalleVenta) {
			total += cadaDetalle.getSubtotal();
		}
		ventaExiste.get().setTotal(total);
		ventaRepository.save(ventaExiste.get());

		respuesta.setCantidad(ventaDetalleDTO.getDetalleVentaDTO().getCantidad());
		respuesta.setProducto(modelMapper.map(detalleExiste.get().getProducto(), ProductoDTO.class));
		respuesta.setSubtotal(detalleExiste.get().getSubtotal());
		respuesta.setExito(true);
		respuesta.setMensaje("¡Detalle modificado correctamente!");

		return respuesta;
	}

	// eliminar un detalle de una venta ya realizada
	public RespuestaDTO eliminarDetalle(HttpSession sesion, VentaDetalleDTO ventaDetalleDTO) {
		RespuestaDTO respuesta = new RespuestaDTO();

		Optional<Venta> ventaExiste = ventaRepository.findByUuid(ventaDetalleDTO.getVentaDTO().getUuid());
		if (!ventaExiste.isPresent()) {
			respuesta.setExito(false);
			respuesta.setMensaje("¡No existe la venta!");
			return respuesta;
		}

		Optional<DetalleVenta> detalleExiste = detalleVentaRepository
				.findByUuid(ventaDetalleDTO.getDetalleVentaDTO().getUuid());
		if (!detalleExiste.isPresent()) {
			respuesta.setExito(false);
			respuesta.setMensaje("¡No existe el detalle!");
			return respuesta;
		}

		try {

			DetalleVenta detalle = detalleExiste.get();
			Producto producto = detalle.getProducto();

			// Actualizar existencias del producto
			producto.setExistencias(producto.getExistencias() + detalle.getCantidad());
			productoRepository.save(producto);

			// Eliminar el detalle de la venta
			detalleVentaRepository.delete(detalle);

			// Recalcular el total de la venta
			List<DetalleVenta> detallesVenta = detalleVentaRepository.findByVentaUuid(ventaExiste.get().getUuid());
			float total = (float) detallesVenta.stream().mapToDouble(DetalleVenta::getSubtotal).sum();
			Venta venta = ventaExiste.get();
			venta.setTotal(total);
			ventaRepository.save(venta);

			respuesta.setExito(true);
			respuesta.setMensaje("¡Detalle eliminado exitosamente!");
		} catch (Exception e) {
			respuesta.setExito(false);
			respuesta.setMensaje("Error al eliminar el detalle: " + e.getMessage());
		}

		return respuesta;
	}

	// eliminar venta
	public RespuestaDTO eliminarVenta(HttpSession sesio, UUID uuid) {
		RespuestaDTO respuesta = new RespuestaDTO();

		Optional<Venta> existeVenta = ventaRepository.findByUuid(uuid);
		if (existeVenta.isPresent()) {
			try {
				ventaRepository.delete(existeVenta.get());
				respuesta.setExito(true);
				respuesta.setMensaje("¡Venta eliminada!");

			} catch (Exception e) {
				respuesta.setExito(false);
				respuesta.setMensaje("Error al eliminar esta venta");
			}
		} else {
			respuesta.setExito(false);
			respuesta.setMensaje("Esta venta no existe...");
		}
		return respuesta;
	}


}
