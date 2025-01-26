package com.kh.secom.auth.service;

import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.auth.util.JwtUtil;
import com.kh.secom.member.model.vo.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

	// AuthenticationManager가 제 역할을 수행해서, 성공 했다. <== 여기까지가 1절
	private final AuthenticationManager authenticationManager;
	
	private final JwtUtil jwt;
	
	@Override
	public Map<String, String> login(MemberDTO requestMember) {

		// 사용자 인증
		// 매개변수에 들어가는 AuthenticationToken이 있어야함
		// 여기엔 기본생성자가 없다. 생성자 호출을 할 때 꼭 첫번째 인자로 사용자의 id, 두번째 인자로 pwd를 입력해줘야함 
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(requestMember.getUserId(), 
													 	requestMember.getUserPwd())
				);
		
		// UsernamePasswordAuthenticationToken
		/*
		 * 사용자가 입력한 username과 password를 검증하는 용도로 사용하는 클래스
		 * 주로, SpringSecurity에서 인증을 시도할 때 사용함
		 * 
		 * 객체(UsernamePasswordAuthenticationToken)생성 이후 인자값으로 아이디 패스워드를 적고
		 * AuthenticationManager의 authenticate 메소드에 만들어진 객체 전달.
		 * 
		 * 이후
		 * 아이디를 갖고 조회하고, 조회결과가 없으면 예외발생, 있다면 matches를 사용해서 비밀번호까지 검증
		 */
		
		CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();
		
		log.info("로그인 절차 성공!");
		log.info("조회된 사용자 정보 : {}", user);

		String accessToken = jwt.getAccessToken(user.getUsername());
		
		log.info("Access Token : {}", accessToken);
		
		return null;

	}

}
