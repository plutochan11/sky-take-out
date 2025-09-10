package com.pluto.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class Admin {
	private Integer id;
	private String username;
	private String password;
	private String name;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;
}
