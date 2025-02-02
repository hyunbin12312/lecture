package com.kh.secom.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.kh.secom.auth.util.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfigure {
	
	private final JwtFilter filter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// method명이 객체이름이 되어 등록되기 때문에 절때로 같은이름의 Bean 메소드가 존재하면 안된다.

		/*
		 * 곧 사라질 문법임 
		 * return httpSecurity .formLogin().disable() .build();
		 */

		/*
		 * return httpSecurity .formLogin(new
		 * Customizer<FormLoginConfigurer<HttpSecurity>>() {
		 * 
		 * @Override public void customize(FormLoginConfigurer<HttpSecurity> t) {
		 * t.disable(); } }).httpBasic(null).csrf(null).cors(null).build();
		 */

		// AbstractHttpConfigurer 이 부모클래스로서 모든 객체와 메소드를 갖고있음
		return httpSecurity.formLogin(AbstractHttpConfigurer::disable) // form 로그인 방식은 사용하지 않겠다.
				.httpBasic(AbstractHttpConfigurer::disable) // httpBasic 사용하지 않겠따.
				.csrf(AbstractHttpConfigurer::disable)
				/*
				 * <form> <input type="hidden" value="" /> </form> 형식으로 값을 몰래 받을 때 html에서 조작이
				 * 가능한 부분을 막겠는가? => 이제 React를 사용하기때문에 없어도됨
				 */
				.cors(AbstractHttpConfigurer::disable) // 이건 따로 필터를 만들어줘야함.
				// 뒷단은 나중에 작업하기로 해놔서 일단 꺼놓고 React와 붙일때 사용
				// @EnableMethodSecurity 를 추가해 사용할 수 있는 method가 인자값으로 들어감
				.authorizeHttpRequests(requests -> {
					// permitAll 은 뭐가 돌아오든, 권한이 있던 없던 다 받아주라는 의미
					requests.requestMatchers("/members", "/members/login").permitAll();
					// Put 요청으로 들어오는 /members 는 hasRole을 이용해서 DB의 ROLE 컬럼에 USER라는 값이 있어야 가능하게
					//requests.requestMatchers(HttpMethod.PUT, "/members").hasRole("USER"); // ROLE_USER 값이 있어야함
					requests.requestMatchers(HttpMethod.PUT, "/members").authenticated(); // 인증을 해야만 사용 가능함.
					// 예시
					requests.requestMatchers("/admin/**").hasRole("ADMIN");
					requests.requestMatchers(HttpMethod.DELETE, "/members").authenticated();
					requests.requestMatchers(HttpMethod.POST, "/members/**").authenticated();
				})
				/*
				 *sessionManagement : 세션 관리에 대한 설정을 지정할 수 있음
				 *sessionCreationPolicy : 정책을 설정
				 */
				.sessionManagement(sessionManagement -> 
								   sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class) // filter를 Username... 앞에서 사용하겠다.
				.build();

	}

	// Bean으로 등록해서 security에서 주입받아 사용할 수 있도록 한다.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// 로그인 인증 관련 담당
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
