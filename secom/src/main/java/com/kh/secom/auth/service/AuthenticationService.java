package com.kh.secom.auth.service;

import java.util.Map;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.member.model.vo.MemberDTO;

public interface AuthenticationService {

	// AccessToken, RefreshToken 두개를 한곳에 담아서 보내야하기때문에 Map 사용
	Map<String, String> login(MemberDTO requestMember);

	CustomUserDetails getAuthenticatedUser();
	
	void validWriter(String writer, String username);
	
}
