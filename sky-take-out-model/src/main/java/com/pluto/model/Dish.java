package com.pluto.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Dish {
	private Integer id;
	private String name;
	private Double price;
	private String image;
	private String description;
	private Short status;
	private Integer classificationId;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
