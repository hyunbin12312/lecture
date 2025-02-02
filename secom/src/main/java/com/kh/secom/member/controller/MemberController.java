package com.kh.secom.member.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.secom.auth.service.AuthenticationService;
import com.kh.secom.member.model.service.MemberService;
import com.kh.secom.member.model.vo.ChangePasswordDTO;
import com.kh.secom.member.model.vo.LoginResponse;
import com.kh.secom.member.model.vo.MemberDTO;
import com.kh.secom.token.model.service.TokenService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value="members", produces="application/json; charset=UTF-8")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	private final AuthenticationService authService;
	private final TokenService tokenService;
	
	
	// 새롭게 데이터를 만들어내는 요청(INSERT) == POST
	@PostMapping // 받을때도 body에 있는 값을 받겠다 적어줌.
	public ResponseEntity<String> save(@Valid @RequestBody MemberDTO requestMember) {
		// 보편적으로 가장 좋은 반환 값, 새롭게 추가된 회원의 정보를 앞단에 전해주는게 가장 좋음

		log.info("요청한 사용자의 데이터 : {}", requestMember);

		memberService.save(requestMember);

		// 돌려줄 때 입력받은 값을 반환하면 좋지만, 회원가입의 경우에는 비밀번호 등이 유출되기에 성공, 실패 여부만
		return ResponseEntity.ok("회원가입에 성공했습니다");
	}

	@PostMapping("login")
	public ResponseEntity<?> login(@Valid @RequestBody MemberDTO requestMember) {

		// 로그인 구현

		/*
		 * 로그인에 성공 했을 때?? ==> 밑의 과정을 인증이라고 함 ==> 이거 원래 개발자가 했음 ==> 
		 * 이젠 Spring security가 해줌 
		 * 입력한 아이디 / 비밀번호(평문) DB의 아이디 / 비밀번호(암호문) <- passwordEncoder의 matches를 사용하면 됨
		 * 
		 */

		Map<String, String> tokens = authService.login(requestMember);

		// 로그인에 성공 했을 때
		// AccessToken
		// RefreshToken 반환

		/*
		 * UserDetails를 불러 써야하는데 이건 UserDetailsService가 갖고있음, 이건 우리가 직접 구현해야함
		 * 
		 * 1. UserDetailsService를 구현해야함 메소드를 하나 오버라이딩 할 예정 사용자가 입력한 username을 가지고 DB에 가서
		 * 조회 존재하지 않으면 예외 발생 존재한다면 조회된 정보를 가지고 UserDetails객체를 생성해서 반환한다.
		 * 
		 * 2. AuthenticationManager 인증 담당할 친구 DB에 있는 암호문 비밀번호가 사용자가 입력한 평문 비밀번호와 맞는가 대신
		 * 비교해줌 
		 * 
		 */

		LoginResponse response = LoginResponse.builder().username(requestMember.getUserId()).tokens(tokens).build();
		
		return ResponseEntity.ok(response);
	}
	
	
	// 비밀번호 변경 기능 구현
	// 기존 비밀번호 / 바꾸고싶은 비밀번호 를 입력받을것
	// 수정(Update)요청은 Put과 Fetch 매핑이 있음
	@PutMapping
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO changeEntity){
		//log.info("{}", changeEntity);
		memberService.changePassword(changeEntity);

		return ResponseEntity.ok("업데이트에 성공했습니다.");
	}

	// 회원 탈퇴 기능
	@DeleteMapping
	public ResponseEntity<String> deleteByPassword(@RequestBody Map<String, String> password){
		
		memberService.deleteByPassword(password);
		
		return ResponseEntity.ok("회원삭제에 성공하였습니다.");
	}
	
	// Refresh 토큰 검증해서 새로운 토큰 발급하기
	@PostMapping("refresh")
	public ResponseEntity<Map> refresh(@RequestBody Map<String, String> tokens){
		log.info("???");
		String refreshToken = tokens.get("refreshToken");
		
		Map<String, String> newTokens = tokenService.refreshTokens(refreshToken);

		return ResponseEntity.ok(newTokens);
	}
	
}
