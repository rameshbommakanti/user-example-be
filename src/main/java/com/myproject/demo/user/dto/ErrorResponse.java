package com.myproject.demo.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
	
	private String error;
	private Integer status;
	
	

}
