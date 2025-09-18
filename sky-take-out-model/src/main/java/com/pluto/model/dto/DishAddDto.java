package com.pluto.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@Builder
public class DishAddDto {
	private String name;
	private Double price;
	private MultipartFile image;
	private String description;
	private Short status;
	private Integer classificationId;
	private Integer mealId;
}
