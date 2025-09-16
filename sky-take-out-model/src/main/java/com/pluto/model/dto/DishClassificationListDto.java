package com.pluto.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DishClassificationListDto {
	private String name;
	private Integer status;
	private Integer pageNo = 1;
	private Integer pageSize = 10;
}
