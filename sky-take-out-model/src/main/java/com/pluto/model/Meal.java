package com.pluto.model;

import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Meal {
	private Integer id;
	private String name;
	private Integer status;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
