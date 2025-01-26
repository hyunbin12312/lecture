package com.kh.secom.auth.model.vo;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.kh.secom.member.model.vo.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@Builder
@ToString
public class CustomUserDetails implements UserDetails {
	
	// 3. 하나하나 적어주는방법 / lombok을 사용하면 편하기때문에 이것도 괜찮은듯.
	private Long userNo;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	
	// 1. 필드로 VO를 넣어버리는 방법
	//private Member member;

	// 2. UserDetails만 땡겨오면 되기때문에 필드로 넣어버릴수있음.
	/*
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return null;
	}
	*/
	
}
