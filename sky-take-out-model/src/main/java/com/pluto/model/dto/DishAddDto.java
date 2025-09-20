package com.pluto.model.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DishAddDto {
	private String name;
	private Double price;
	private MultipartFile image;
	private String description;
	private Short status;
	private Integer classificationId;
}