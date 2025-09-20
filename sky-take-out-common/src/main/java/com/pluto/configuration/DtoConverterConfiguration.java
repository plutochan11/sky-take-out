package com.pluto.configuration;

import com.pluto.util.CommonDtoConverter;
import com.pluto.util.DtoConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DtoConverterConfiguration {
	@Bean
	public DtoConverter dtoConverter() {
		return new CommonDtoConverter();
	}
}
