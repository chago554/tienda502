package com.utsem.app.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utsem.app.dto.DetalleVentaDTO;
import com.utsem.app.dto.ProductoDTO;
import com.utsem.app.dto.VentaDTO;
import com.utsem.app.model.Producto;
import com.utsem.app.repository.ProductoRepository;
import jakarta.servlet.http.HttpSession;

@Service
public class VentaService {
	@Autowired
	ProductoRepository productoRepository;
	@Autowired
	ModelMapper modelMapper;

	public String buscarProducto(HttpSession sesion, String codigo) {
		Optional<Producto> productoBD = productoRepository.findByCodigo(codigo);
		if (productoBD.isPresent()) {
			// Accedemos a la venta del usuario y la asignamos a ventaDTO
			VentaDTO ventaDTO = (VentaDTO) sesion.getAttribute("venta");

			Boolean existe = false;
			for (DetalleVentaDTO detalle : ventaDTO.getDetalles()) {
				if (detalle.getProducto().getUuid().equals(productoBD.get().getUuid())) {
					existe = true;
					detalle.setCantidad(detalle.getCantidad() + 1);
					detalle.setSubtotal(detalle.getProducto().getPrecio() * detalle.getCantidad());
				}
			}

			if (!existe) {

				// Creamos un nuevo detalle de venta
				DetalleVentaDTO detalleVentaDTO = new DetalleVentaDTO();

				// Cargamos en el detalle toda la información del producto
				detalleVentaDTO.setProducto(modelMapper.map(productoBD.get(), ProductoDTO.class));

				/*
				 * Si no hubiese mapper se tiene que hacer de la siguiente manera
				 * productoDTO.setDescripcion(productoBD.get().getDescripcion());
				 * productoDTO.setPrecio(productoBD.get().getPrecio());
				 * productoDTO.setExistencias(productoBD.get().getExistencias());
				 * productoDTO.setCodigo(productoBD.get().getCodigo());
				 * productoDTO.setUuid(productoBD.get().getUuid());
				 */

				// Asignamos la cantidad y el subtotal
				detalleVentaDTO.setCantidad(1f);
				detalleVentaDTO.setSubtotal(productoBD.get().getPrecio());

				// Agrego el nuevo detalle a los detalles de la venta
				ventaDTO.getDetalles().add(detalleVentaDTO);
			}
			return "¡Producto agregado a la venta!";

		}
		return "¡Código no encontrado!";
	}
}
