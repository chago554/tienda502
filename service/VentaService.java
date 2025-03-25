package com.utsem.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utsem.app.dto.DetalleVentaDTO;
import com.utsem.app.dto.EstatusUsuarioDTO;
import com.utsem.app.dto.ProductoDTO;
import com.utsem.app.dto.RespuestaDTO;
import com.utsem.app.dto.VentaDTO;
import com.utsem.app.model.DetalleVenta;
import com.utsem.app.model.Producto;
import com.utsem.app.model.Usuario;
import com.utsem.app.model.Venta;
import com.utsem.app.repository.DetalleVentaRepository;
import com.utsem.app.repository.ProductoRepository;
import com.utsem.app.repository.UsuarioRepository;
import com.utsem.app.repository.VentaRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class VentaService {
	@Autowired
	ProductoRepository productoRepository;
	@Autowired
	ModelMapper modelMapper;
	@Autowired
	UsuarioRepository usuarioRepository;
	@Autowired
	VentaRepository ventaRepository;
	@Autowired
	DetalleVentaRepository detalleVentaRepository;

	// buscar producto
	public RespuestaDTO buscarProducto(HttpSession sesion, String codigo) {
		RespuestaDTO respuesta = new RespuestaDTO();
		/*
		 * Optional<DetalleDTO> busqueda = miVenta.getDetalles().stream() .filter(det ->
		 * det.getProducto().getUuid().equals(pro.getUuid())).findFirst();
		 * 
		 * if (busqueda.isEmpty()) { DetalleDTO miDetalle = new DetalleDTO();
		 * miDetalle.setProducto(pro); miDetalle.setCantidad(1f);
		 * miDetalle.setPrecio(pro.getPrecio()); miDetalle.setSubtotal(pro.getPrecio());
		 * miVenta.getDetalles().add(miDetalle); } if (busqueda.isPresent()) {
		 * busqueda.get().setCantidad(busqueda.get().getCantidad() + 1);
		 * busqueda.get().setSubtotal(busqueda.get().getCantidad() *
		 * busqueda.get().getPrecio()); }
		 */

		Optional<Producto> productoBD = productoRepository.findByCodigo(codigo);
		VentaDTO ventaDTO = (VentaDTO) sesion.getAttribute("venta");
		if (sesion.getAttribute("venta") == null) {
			sesion.setAttribute("venta", new VentaDTO());
		}

		if (productoBD.isPresent()) {

			if (productoBD.get().getExistencias() > 0) {
				// Accedemos a la venta del usuario y la asignamos a ventaDTO

				if (ventaDTO.getRealizada()) {
					sesion.setAttribute("venta", new VentaDTO());
					ventaDTO = (VentaDTO) sesion.getAttribute("venta");

				}

				Boolean existe = false;

				for (DetalleVentaDTO detalle : ventaDTO.getDetalles()) {

					if (detalle.getProducto().getUuid().equals(productoBD.get().getUuid())) {
						existe = true;

						if (productoBD.get().getExistencias() > detalle.getCantidad()) {
							detalle.setCantidad(detalle.getCantidad() + 1);
							detalle.setSubtotal(detalle.getProducto().getPrecio() * detalle.getCantidad());
							break;
						}
						respuesta.setExito(false);
						respuesta.setMensaje("¡No hay suficientes productos!");
						return respuesta;
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
				// todo lo qu se viene es lo mismo, pero de diferentes formas
				/*
				 * Float suma= 0f; for (DetalleVentaDTO detalle: ventaDTO.getDetalles()) { suma
				 * =+ detalle.getSubtotal(); }
				 * 
				 * ventaDTO.setTotal((float)ventaDTO.getDetalles().stream().mapToDouble(
				 * DetalleVentaDTO::getSubtotal).sum());
				 */

				ventaDTO.setTotal(
						(float) ventaDTO.getDetalles().stream().mapToDouble(detalle -> detalle.getSubtotal()).sum());
				respuesta.setExito(true);
				respuesta.setMensaje("¡Producto agregado a la venta!");
				return respuesta;
			}
			respuesta.setExito(false);
			respuesta.setMensaje("¡No hay suficientes productos!");
			return respuesta;

		}
		respuesta.setExito(false);
		respuesta.setMensaje("¡Código no encontrado!");
		return respuesta;
	}

	// cargar carrito
	public VentaDTO cargarCarrito(HttpSession sesion) {
		if (sesion.getAttribute("venta") == null) {
			sesion.setAttribute("venta", new VentaDTO());
		}
		return (VentaDTO) sesion.getAttribute("venta");
	}

	// realizar venta
	public String realizarVenta(HttpSession sesion) {

		VentaDTO ventaDTO = (VentaDTO) sesion.getAttribute("venta");

		if (!ventaDTO.getRealizada()) {
			EstatusUsuarioDTO usuarioDTO = (EstatusUsuarioDTO) sesion.getAttribute("estatusUsuario");
			Usuario usuario = usuarioRepository.findByUuid(usuarioDTO.getUuid()).get();
			Venta venta = modelMapper.map(ventaDTO, Venta.class);

			venta.setUsuario(usuario);
			ventaRepository.save(venta);

			ventaDTO.getDetalles().stream().forEach(detDTO -> {
				DetalleVenta detalle = modelMapper.map(detDTO, DetalleVenta.class);
				detalle.setProducto(productoRepository.findByUuid(detDTO.getProducto().getUuid()).get());

				Producto producto = productoRepository.findByUuid(detDTO.getProducto().getUuid()).get();
				producto.setExistencias(producto.getExistencias() - detalle.getCantidad());
				productoRepository.save(producto);

				detalle.setVenta(venta);
				detalleVentaRepository.save(detalle);
			});

			ventaDTO.setRealizada(true);

			return "¡Venta exitosa!";
		} else {
			return "Usuario travieso";
		}
	}

	// eliminar detalle

	public String eliminarDetalle(HttpSession sesion, UUID uuid) {

		VentaDTO ventaDTO = (VentaDTO) sesion.getAttribute("venta");

		Optional<DetalleVentaDTO> detalle = ventaDTO.getDetalles().stream().filter(det -> det.getUuid().equals(uuid))
				.findFirst();

		if (detalle.isPresent()) {
			ventaDTO.getDetalles().remove(detalle.get());
			ventaDTO.setTotal((float) ventaDTO.getDetalles().stream().mapToDouble(det -> det.getSubtotal()).sum());
			return "Detalle elimnado exitosamente";
		} else {
			return "Usuario travieso";
		}
	}

	// modificar

	public RespuestaDTO modificarDetalle(HttpSession sesion, DetalleVentaDTO modDetalle) {
		RespuestaDTO respuesta = new RespuestaDTO();

		// Obtener la venta en curso de la sesión
		VentaDTO ventaDTO = (VentaDTO) sesion.getAttribute("venta");
		if (ventaDTO == null || ventaDTO.getDetalles() == null) {
			respuesta.setExito(false);
			respuesta.setMensaje("¡No hay una venta en curso!");
			return respuesta;
		}

		// Buscar el detalle que se desea modificar
		Optional<DetalleVentaDTO> detalle = ventaDTO.getDetalles().stream()
				.filter(det -> det.getUuid().equals(modDetalle.getUuid())).findFirst();

		// Verificar si el detalle existe
		if (!detalle.isPresent()) {
			respuesta.setExito(false);
			respuesta.setMensaje("¡No existe el detalle!");
			return respuesta;
		}

		// Obtener el producto asociado al detalle
		Optional<Producto> productoBD = productoRepository.findByCodigo(detalle.get().getProducto().getCodigo());
		if (!productoBD.isPresent()) {
			respuesta.setExito(false);
			respuesta.setMensaje("¡Producto no encontrado!");
			return respuesta;
		}

		// Verificar si hay suficientes existencias para la nueva cantidad
		if (productoBD.get().getExistencias() < modDetalle.getCantidad()) {
			respuesta.setExito(false);
			respuesta.setMensaje("¡No hay suficientes productos!");
			return respuesta;
		}

		// Modificar el detalle
		detalle.get().setCantidad(modDetalle.getCantidad());
		detalle.get().setSubtotal(detalle.get().getCantidad() * detalle.get().getProducto().getPrecio());

		// Recalcular el total de la venta
		ventaDTO.setTotal((float) ventaDTO.getDetalles().stream().mapToDouble(det -> det.getSubtotal()).sum());

		// falta mantener los producto en la bd, si se agregan o se devuelven que se
		// vuelvan sumar o restar en la bd

		// Respuesta exitosa
		respuesta.setExito(true);
		respuesta.setMensaje("¡Detalle modificado correctamente!");
		return respuesta;
	}

	// listar ventas
	public List<VentaDTO> listarVentas(HttpSession sesion) {
		return ventaRepository.findAll().stream().sorted((d1, d2) -> d2.getId().compareTo(d1.getId()))
				.map(venta -> modelMapper.map(venta, VentaDTO.class)).collect(Collectors.toList());
	}

	
}
