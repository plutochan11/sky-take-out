package com.pluto.model.dto;

public record AdminListDto(String name, Integer pageNo, Integer pageSize) {
	public AdminListDto {
		if (pageNo == null) pageNo = 0;
		if (pageSize == null) pageSize = 10;
	}

	public AdminListDto(String name) {
		this(name, 0, 10);
	}

	public AdminListDto(Integer pageNo, Integer pageSize) {
		this(null, pageNo - 1, pageSize);
	}

}
