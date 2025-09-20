package com.pluto.model.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DishDetailDto implements Serializable {
	private Integer id;
	private String name;
	private Double price;
	private String image;
	private String description;
	private Short status;
	private String classificationName;
	private String mealName;
}
