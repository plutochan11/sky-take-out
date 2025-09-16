package com.pluto.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class Dish {
	private Integer id;
	private String name;
	private Double price;
	private String image;
	private String description;
	private Short status;
	private Integer classificationId;
	private Integer mealId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
