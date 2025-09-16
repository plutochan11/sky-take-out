package com.pluto.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishDto {
	private Integer id;
	private String name;
	private Double price;
	private String image;
	private String description;
	private Short status;
	private Integer classificationId;
	private Integer mealId;
}
