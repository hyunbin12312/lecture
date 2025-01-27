package com.kh.secom;

public class Organize {
	
	// 25 01 27 정리내용
	// 정규표현식으로 유효값검사를 하고싶을 때
	
	// 사용하기위해
	// implementation 'org.springframework.boot:spring-boot-starter-validation'
	// build.gradle에 위 implementation 작성
	
	// DTO 가서 @Pattern 애노테이션 사용해서 정규표현식을 정함.
	// 컨트롤러를 가서 불러오는 메서드의 매개변수 자리에 @Valid 애노테이션 적기.( MemberController에 달았음)
	// GlobalExceptionHandler 클래스에서 extends 중인 ResponseEntityExceptionHandler를 지우고 해야함.
	// @NotBlank 애노테이션(공백안됨), @Size 애노테이션(들어올 값의 길이 정하기)
	// 위 애노테이션만 작성하면 Service단에서 작업하던 if문들을 없앨수있음
	// 뒤에 message를 사용해서 예외 발생시 보낼 값을 정할수있따.
	
	// 값을 제한하는게 끝났다면, 잘못된 값이 들어왔을때 MethodArgumentNotValidException 예외사항이 발생한다.
	// 이 예외사항을 만들어놓은 GlobalExceptionHandle에서 처리할수있도록 메서드를 만들어준다.
	
	
	// accessToken 뿐만 아니라 RefreshToken도 돌려줘야하기때문에 JwtUtil 클래스에서 만들어준다.
	// 과정 중 줄일수 있는부분은 줄임
	
	// Login 기능과 Token 발행을 분리
	// token 패키지 만들고 기능 분리 후 메서드 작성
	
	
	
	
}
