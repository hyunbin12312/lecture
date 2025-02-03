package com.kh.secom.auth.util;

import java.util.Base64;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtUtil {

	// H7oIaBujRo6fA/a0wdk09iY6STECQZbemMj8bcs5xBMzd0IYxzT+hNQd+fgXvsyz3qHF3DIwuYXUE9m7w5tkDw==
	// helFW4EUOeoVUpijDo3859T8JkLNlaiy
	// application.yml 혹은 application.properties 파일에 정의된 속성의 값들을
	// @Value 애노테이션으로 값을 갖다쓸수있음

	// 얘한테 값을 담아온 후 init 메서드에서 SecretKey에 담는다.
	@Value("${jwt.secret}")
	private String secretKey;
	
	// javax.crypto.SecretKey 타입의 필드로 JWT서명에 사용할 수 있다.
	private SecretKey key;

	// JWT의 만료기간 : 3600000 * 24 <- 밀리초단위, 1일
	private long ACCESS_TOKEN_EXPIRED = 3600000L * 24;
	// RefreshToken의 만료기간 : 3600000 * 72 <- 밀리초단위, 3일
	private long REFRESH_TOKEN_EXPIRED = 3600000L * 72;

	@PostConstruct // Bean 초기화 시 필요한 추가 설정들을 할 수 있음
	public void init() { // initialize의 약자, 초기화할때 사용할 메서드

		// secretKey키가 생성되자마자 init메서드를 불러 key객체를 만들수있게 하는 애노테이션이 @PostConstruct
		
		byte[] keyArr = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyArr); // SecretKey 타입의 객체를 반환해줌

		long now = System.currentTimeMillis() + 3600000L * 24;

	}
	
	
	// 같은 뜻을 담는 길이가 너무 길다면, 줄여주는 방법을 생각해보자
	public Date buildExpirationDate(long date) {
		long now = System.currentTimeMillis();
		return new Date(now + date);
	}
	
	public String getAccessToken(String username) {
		return Jwts.builder().subject(username) // 토큰 만들 때 구분할 항목, 예민한 정보는 담으면 안된다.
				.issuedAt(new Date()) // 발급일
				.expiration(buildExpirationDate(ACCESS_TOKEN_EXPIRED)) // 만료일
				//.expiration(new Date()) // 만료일
				.signWith(key) // SecretKey 타입으로 만든 key 의 값 // 비밀키로 만든 서명
				.compact();
	}

	public String getRefreshToken(String username) {
		return Jwts.builder().subject(username).issuedAt(new Date())
				.expiration(buildExpirationDate(REFRESH_TOKEN_EXPIRED)).signWith(key).compact();
	}
	
	public Claims parseJwt(String token) {
		return Jwts.parser()
				   .verifyWith(key)
				   .build()
				   .parseSignedClaims(token)
				   .getPayload();
	}
	

}
