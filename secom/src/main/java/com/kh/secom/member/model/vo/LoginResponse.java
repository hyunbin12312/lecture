package com.kh.secom.member.model.vo;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@AllArgsConstructor
@Builder
public class LoginResponse {

	private String username;
	private Map<String, String> tokens;
	
}
