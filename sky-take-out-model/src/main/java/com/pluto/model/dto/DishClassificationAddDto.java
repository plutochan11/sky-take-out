package com.pluto.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DishClassificationAddDto {
	private Integer id;
	private String name;
	private Integer status;
	private List<Integer> dishIds;
}
