package com.utsem.app.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;


import com.utsem.app.dto.ProductoDTO;
import com.utsem.app.dto.RespuestaDTO;
import com.utsem.app.model.Producto;
import com.utsem.app.repository.ProductoRepository;

import jakarta.servlet.http.HttpSession;

@Service
public class ProductoService {
	@Autowired
	ProductoRepository productoRepository;
	@Autowired
	ModelMapper modelMapper;

	// listar
	public List<ProductoDTO> listar(HttpSession session) {
		return productoRepository.findAll().stream().map(producto -> modelMapper.map(producto, ProductoDTO.class))
				.collect(Collectors.toList());
	}

	// crear producto
	public ProductoDTO crearProducto(ProductoDTO productoDTO, HttpSession sesion) {
		ProductoDTO dto = new ProductoDTO();

		Optional<Producto> existeCodigo = productoRepository.findByCodigo(productoDTO.getCodigo());
		Optional<Producto> existeDesc = productoRepository.findByDescripcion(productoDTO.getDescripcion());

		if (existeCodigo.isPresent()) {
			dto.setExito(false);
			dto.setMensaje("¡Ese código ya existe!");
			return dto;
		}

		if (existeDesc.isPresent()) {
			dto.setExito(false);
			dto.setMensaje("¡Esa descripcion ya existe!");
			return dto;
		}
		Producto producto = modelMapper.map(productoDTO, Producto.class);
		productoRepository.save(producto);
		productoDTO.setExito(true);
		productoDTO.setMensaje("¡Producto creado exitosamente!");
		return productoDTO;
	}

	// eliminar producto
	public String eliminarProducto(HttpSession session, UUID uuid) {
		Optional<Producto> existeProducto = productoRepository.findByUuid(uuid);
		if (existeProducto.isPresent()) {

			try {
				productoRepository.deleteById(existeProducto.get().getId());
				return "¡Producto eliminado exitosamente!";
			} catch (DataIntegrityViolationException e) {
				return "¡No puedes eliminar este producto!";
			}
		}
		return "¡No existe ese producto!";
	}
	
	//buscar producto
	public ProductoDTO buscarProducto(HttpSession sesion, UUID uuid) {
		ProductoDTO productoDTO = new ProductoDTO();
		Optional<Producto> existeProducto = productoRepository.findByUuid(uuid);
		
		if(existeProducto.isPresent()) {
			Producto producto = existeProducto.get();
			return modelMapper.map(producto, ProductoDTO.class);
		}
		productoDTO.setExito(false);
		productoDTO.setMensaje("¡No existe este producto!");
		return productoDTO;
	}

	


	//modificar producto
	public RespuestaDTO modificarProducto (HttpSession sesion, ProductoDTO productoDTO) {
		Optional<Producto> existeProducto = productoRepository.findByUuid(productoDTO.getUuid());
		Producto producto = existeProducto.get();
		RespuestaDTO dto = new RespuestaDTO();
		
		if(existeProducto.isPresent()) {
			
			
			if(productoDTO.getDescripcion().isBlank() || productoDTO.getCodigo().isBlank() ) {
				dto.setExito(false);
				dto.setMensaje("¡Rellene todos los campos!");
				return dto;
			}
			
			producto.setCodigo(productoDTO.getCodigo());
			producto.setDescripcion(productoDTO.getDescripcion());
			producto.setPrecio(productoDTO.getPrecio());
			producto.setExistencias(productoDTO.getExistencias());
			
			productoRepository.save(producto);
			dto.setExito(true);
			dto.setMensaje("¡Producto actualizado exitosamente!");
			return dto;
			
		}
		dto.setExito(false);
		dto.setMensaje("¡Este producto no existe!");
		return dto;
		
	}
	
}
