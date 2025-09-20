package com.pluto.model.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class MealAddDto {
	private Integer id;
	private String name;
	private Integer status;
	private Map<Integer, Integer> dishIdsAndQuantities;
}
