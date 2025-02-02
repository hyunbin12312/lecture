package com.kh.secom.auth.util;

import java.io.IOException;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.kh.secom.auth.service.UserServiceImpl;
import com.kh.secom.exception.AccessTokenExpiredException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
	// OncePerRequestFilter는 스프링 프레임워크에서 제공하는 요청이 들어왔을때 딱 한 번 돌릴 필터를 제공하는 클래스
	// 추상클래스이기때문에 OncePerRequestFilter에 있는 클래스를 그대로 가져와야함

	private final JwtUtil tokenUtil;
	private final UserServiceImpl userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

		log.info("토큰필터 : {}", authorization);

		if (authorization == null || authorization.startsWith("bearer ")) {
			log.error("authorization이 존재하지 않음");
			filterChain.doFilter(request, response);
			return;
		}

		// 토큰의 필요한 부분만 자르기 split, substr 등
		String token = authorization.split(" ")[1];
		// 이거 공백문자 안넣어줘서 한참 씨름함;
		// 1. 이거 내 비밀키로 만든건가?
		// 2. 이거 유효기간은 지나지 않았나?
		// 라이브러리에서 메서드를 호출해서 검증한다. JwtUtil에서 만듦.

		try {
			Claims claims = tokenUtil.parseJwt(token);

			String username = claims.getSubject();

			log.info("토큰주인아이디 : {} ", username);

			// 사용자 정보 가져오기, 인증된 사용자의 정보를 담고있는 객체
			UserDetails userDetails = userService.loadUserByUsername(username);

			// 인자값으로 userDetails와, 비밀번호, 권한 순서로 넣어줌
			// 토큰인증방식을통해 토큰을 갖고있다 = 검증된 사용자 라는 뜻이 되니 비밀번호엔 따로 값을 안넣고 null로 지정
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
					userDetails, null, userDetails.getAuthorities());

			// 세부설정. 사용자의 원격주소, MAC주소, 세션 ID등이 포함 될 수 있음
			authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
			
			// SecurityContextHolder이 갖고있는 SecurityContext에 권한을 담는다
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
		} catch (ExpiredJwtException e) {
			log.info("AccessToken이 만료되었습니다.");
			//throw new AccessTokenExpiredException("토큰이 만료되었습니다.");
			// 위 처럼 예외처리를 하면 클라이언트한테 응답을 못보내줌
			// 스프링에서 컨트롤러에 일어난 예외를 처리하기때문에 컨트롤러도 못간 filter단계에서는 응답을 보낼수없다.
			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED );
			response.getWriter().write("Expired Token");
			
			return;
		} catch (JwtException e) {
			log.info("Token 검증에 실패했습니다.");
			//throw new AccessTokenExpiredException("잘못된 요청.");
			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED );
			response.getWriter().write("잘못된요청");
			
			return;
		}

		filterChain.doFilter(request, response);
		
	}

}
