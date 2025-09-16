package com.pluto.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishListDto {
	private String name;
	private Integer status;
	private Integer classificationId;
	private Integer mealId;
	private Integer pageNo;
	private Integer pageSize;
}
