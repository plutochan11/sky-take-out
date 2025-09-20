package com.pluto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Admin {
	private Integer id;
	private String username;
	private String password;
	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
