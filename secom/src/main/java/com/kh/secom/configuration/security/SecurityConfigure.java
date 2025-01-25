package com.kh.secom.configuration.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfigure {
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		// method명이 객체이름이 되어 등록되기 때문에 절때로 같은이름의 Bean 메소드가 존재하면 안된다.
		
		/* 곧 사라질 문법임
		return httpSecurity
				.formLogin().disable()
				.build();
		*/
		
		/*
		return httpSecurity
				.formLogin(new Customizer<FormLoginConfigurer<HttpSecurity>>() {
					@Override
					public void customize(FormLoginConfigurer<HttpSecurity> t) {
						t.disable();
					}
				}).httpBasic(null).csrf(null).cors(null).build();
		*/
		
		// AbstractHttpConfigurer 이 부모클래스로서 모든 객체와 메소드를 갖고있음
		return httpSecurity
				.formLogin(AbstractHttpConfigurer::disable) // form 로그인 방식은 사용하지 않겠다.
				.httpBasic(AbstractHttpConfigurer::disable) // httpBasic 사용하지 않겠따.
				.csrf(AbstractHttpConfigurer::disable)
				/*
				 * <form>
				 *   <input type="hidden" value="" />
				 * </form>
				 * 형식으로 값을 몰래 받을 때 html에서 조작이 가능한 부분을 막겠는가?
				 * => 이제 React를 사용하기때문에 없어도됨
				 */
				.cors(AbstractHttpConfigurer::disable) // 이건 따로 필터를 만들어줘야함.
									// 뒷단은 나중에 작업하기로 해놔서 일단 꺼놓고 React와 붙일때 사용
				.build();
		
	}
	
	// Bean으로 등록해서 security에서 주입받아 사용할 수 있도록 한다.
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// 로그인 인증 관련 담당
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

}
