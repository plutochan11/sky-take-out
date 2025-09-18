package com.pluto.util;

import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

// TODO Check out whether it's possible to be registered when there's a class named "xxxDto" as @ConditionalOnClass
@Component
public class CommonDtoConverter implements DtoConverter{

	@Override
	public <D, E> E dtoToEntity(D dto, Class<E> entityClass) throws NoSuchMethodException {
		Class<D> dtoClass = (Class<D>) dto.getClass();
		List<Field> dtoFields = List.of(dtoClass.getDeclaredFields());
		List<Field> entityFields = List.of(entityClass.getDeclaredFields());

		try {
			// Retrieve the no-argument constructor and initialise the entity object
			E entity = entityClass.getDeclaredConstructor().newInstance();

			dtoFields.forEach(field -> {
				field.setAccessible(true);
				String fieldName = field.getName();
				Object fieldValue = null;
				try {
					fieldValue = field.get(dto);
					Object finalFieldValue = fieldValue;
					entityFields.stream()
						  .filter(entityField -> entityField.getName().equals(fieldName))
						  .findFirst()
						  .ifPresent(entityField -> {
							  entityField.setAccessible(true);
							  try {
								  entityField.set(entity, finalFieldValue);
							  } catch (IllegalAccessException e) {
								  throw new RuntimeException(e);
							  }
						  });
				} catch (IllegalAccessException e) {
					throw new RuntimeException(e);
				}
			});

			return entity;
		} catch (NoSuchMethodException | InvocationTargetException | InstantiationException |
		         IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}
}
