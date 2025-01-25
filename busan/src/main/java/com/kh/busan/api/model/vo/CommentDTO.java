package com.kh.busan.api.model.vo;

import java.io.Serializable;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@ToString
public class CommentDTO implements Serializable{
	private Long foodNo;
	// NotBlank를 사용하기위해서 Spring security를 같이 사용해야함
	@NotBlank(message="ID는 필수입력값 입니다.")
	private String writer;
	private String content;
}
