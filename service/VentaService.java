package com.utsem.app.service;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.utsem.app.dto.DetalleVentaDTO;
import com.utsem.app.dto.EstatusUsuarioDTO;
import com.utsem.app.dto.ProductoDTO;
import com.utsem.app.dto.VentaDTO;
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

	public String buscarProducto(HttpSession sesion, String codigo) {
		  
		
/*
 * Optional<DetalleDTO> busqueda = miVenta.getDetalles().stream()
					.filter(det -> det.getProducto().getUuid().equals(pro.getUuid())).findFirst();

			if (busqueda.isEmpty()) {
				DetalleDTO miDetalle = new DetalleDTO();
				miDetalle.setProducto(pro);
				miDetalle.setCantidad(1f);
				miDetalle.setPrecio(pro.getPrecio());
				miDetalle.setSubtotal(pro.getPrecio());
				miVenta.getDetalles().add(miDetalle);
			}
			if (busqueda.isPresent()) {
				busqueda.get().setCantidad(busqueda.get().getCantidad() + 1);
				busqueda.get().setSubtotal(busqueda.get().getCantidad() * busqueda.get().getPrecio());
			}
 */
		
		
		
		Optional<Producto> productoBD = productoRepository.findByCodigo(codigo);
		if (sesion.getAttribute("venta") == null) {
			sesion.setAttribute("venta", new VentaDTO());
		}
		if (productoBD.isPresent()) {
			// Accedemos a la venta del usuario y la asignamos a ventaDTO
			VentaDTO ventaDTO = (VentaDTO) sesion.getAttribute("venta");
			Boolean existe = false;
			for (DetalleVentaDTO detalle : ventaDTO.getDetalles()) {
				if (detalle.getProducto().getUuid().equals(productoBD.get().getUuid())) {
					existe = true;
					detalle.setCantidad(detalle.getCantidad() + 1);
					detalle.setSubtotal(detalle.getProducto().getPrecio() * detalle.getCantidad());
					break;
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
			//todo lo qu se viene es lo mismo, pero de diferentes formas
	
			
			/*
			Float suma= 0f;
			for (DetalleVentaDTO  detalle: ventaDTO.getDetalles()) {
				suma =+ detalle.getSubtotal();
			}*/
			
		//	ventaDTO.setTotal((float)ventaDTO.getDetalles().stream().mapToDouble(DetalleVentaDTO::getSubtotal).sum());
			
			ventaDTO.setTotal((float)ventaDTO.getDetalles().stream().mapToDouble(detalle -> detalle.getSubtotal()).sum());
			return "¡Producto agregado a la venta!";
		}
		return "¡Código no encontrado!";
	}
	
	public VentaDTO cargarCarrito(HttpSession sesion) {
		if (sesion.getAttribute("venta") == null) {
			sesion.setAttribute("venta", new VentaDTO());
		}
		return (VentaDTO) sesion.getAttribute("venta");
	}
	
	public String realizarVenta(HttpSession sesion) {
		VentaDTO ventaDTO = (VentaDTO) sesion.getAttribute("venta");
		EstatusUsuarioDTO usuarioDTO = (EstatusUsuarioDTO)sesion.getAttribute("estatusUsuario");
		Usuario usuario = usuarioRepository.findByUuid(usuarioDTO.getUuid()).get();
		Venta venta = modelMapper.map(ventaDTO, Venta.class);
		venta.setUsuario(usuario);
		ventaRepository.save(venta);
		return "¡Venta exitosa!";
	}
	
}
