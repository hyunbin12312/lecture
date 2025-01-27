package com.kh.secom.member.model.vo;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ChangePasswordDTO {

	@NotBlank(message="현재 비밀번호를 꼭 입력해주세요.")
	private String currentPassword;
	
	@NotBlank(message="바꿀 비밀번호를 꼭 입력해주세요.")
	private String newPassword;
	
}
