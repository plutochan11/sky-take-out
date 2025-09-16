package com.pluto.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class DishClassification {
	private Integer id;
	private String name;
	private Integer status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
