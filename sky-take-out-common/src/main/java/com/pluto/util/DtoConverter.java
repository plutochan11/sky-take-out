package com.pluto.util;

public interface DtoConverter {
	/**
	 * Convert entity to DTO.
	 * @param dto the DTO object
	 * @param entityClass the target entity class
	 * @return the converted entity object
	 * @param <D> the DTO type
	 * @param <E> the entity type
	 */
	<D, E> E dtoToEntity(D dto, Class<E> entityClass) throws NoSuchMethodException;
}
