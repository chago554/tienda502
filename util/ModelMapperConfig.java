//Este paquete se usa para poner todas las utils que se utilizan en el proyecto
package com.utsem.app.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component 
@Configuration
public class ModelMapperConfig {
	
	@Bean //componente disponible en todo el proyecto
	ModelMapper modelMapper() {
		return new ModelMapper();
	}
	

}
