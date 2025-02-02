package com.kh.secom.token.model.service;

import java.util.Map;

public interface TokenService {

	Map<String, String> generateToken(String username, Long userNo);

	// 결과적으론 generateToken 메서드와 같이 토큰을 발행해줄것이기때문에 Map형태로 반환
	Map<String, String> refreshTokens(String refreshToken);
	
}
