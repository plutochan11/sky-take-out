package com.pluto.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
public class MealAddDto {
	private Integer id;
	private String name;
	private Integer status;
	private Map<Integer, Integer> dishes;
}
