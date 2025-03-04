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
	
	// 정리
	/*
	 * 사용자에게서 id와 pwd가 들어오면 Filter를 거침, 
	 * 여기서 여러가지 필터를 거쳐야하지만 몇가지는 끄기위해 disable로 필터를 꺼놨음
	 * 
	 * RequestHandler(Controller)가 요청을 받아줘야함. requestDTo를 만들어놔서 Validation을 사용해 유효성 검사를 진행함
	 * -> 입력받은값이 잘못됐다면 예외발생. GlobalException으로 가서 만들어놓은 예외처리 로직을 돌림
	 * -> Json형태의 ResponseEntity가 사용자에게 돌아감
	 * 
	 * -> 입력받은 값이 유효성검사에 통과했다면 Service(AuthenticationService)로 보냄, 여기서 인증을 진행함
	 * 1. 아이디가 DB에 존재하는가
	 * 2. 비밀번호가 DB에 저장되어있는 암호화 된 비밀번호와 같은가
	 * 
	 * 이 과정을 AuthenticationManager가 진행해줌
	 * UsernamepasswordAuthenticationToken(id, pwd) 객체를 생성해서
	 * AuthenticationManager가 갖고있는 authenticate 메서드를 호출해 보내줌
	 * -> AuthenticationManager.authenticate(UsernamepasswordAuthenticationToken(Id, Pwd);
	 * 
	 * 
	 * Admin ID AccessToken
	 * "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNzk0ODUzNywiZXhwIjoxNzM4MDM0OTM3fQ.CwBQQYJZzs431rXqYgudhDyvejQCEiIhqZpoTxma5h7_tQNq-SRvAqhA2Ubg8EIi2DjLXCaV3nBBC0uqiCpprw"
	 * 
	 */
	
	/*
	 * 명확한 책임의 분리
	 * 
	 * 인증, 인가 과정을 Spring Security에게 전부 넘김
	 * 
	 * Filter를 사용해서 클라이언트의 요청과 dispatcher Servlet 사이의 Filter들의 Chain을 이용해서 검증과정을 만듬
	 * 
	 * 직접만든 JwtTokenFilter 라는걸 사용해서 우리가 발급한 정상적인 토큰인가, 잘못된 토큰인가를 검증해
	 * 
	 * 정상적인 토큰이라면 권한을 주고, 잘못된 토큰이라면 예외를 발생시켜 필터에서 거르게 만듬.
	 * 
	 */
	
	
}
