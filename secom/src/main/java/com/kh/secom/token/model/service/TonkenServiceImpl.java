package com.kh.secom.token.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.kh.secom.auth.util.JwtUtil;
import com.kh.secom.token.model.dto.RefreshTokenDTO;
import com.kh.secom.token.model.mapper.TokenMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TonkenServiceImpl implements TokenService {

	private final JwtUtil tokenUtil;
	private final TokenMapper mapper;

	@Override
	public Map<String, String> generateToken(String username, Long userNo) {

		// 1. AccessToken 만들기
		// 2. RefreshToken 만들기
		Map<String, String> tokens = createTokens(username);
		
		// 3. RefreshToken을 DB에 저장하기
		saveToken(tokens.get("refreshToken"), userNo);
		
		// 4. 만료기간이 지난 RefreshToken이 있을 수 있으니 지우기

		// 5. 사용자가 RefreshToken을 증명하려 할 때 DB가서 조회하기

		return tokens;
	}

	// 1, 2번을 수행하는 메서드
	private Map<String, String> createTokens(String username) {
		String accessToken = tokenUtil.getAccessToken(username);
		String refreshToken = tokenUtil.getRefreshToken(username);

		Map<String, String> tokens = new HashMap();
		tokens.put("accessToken", accessToken);
		tokens.put("refreshToken", refreshToken);

		return tokens;
	}

	private void saveToken(String refreshToken, Long userNo) {

		RefreshTokenDTO token = RefreshTokenDTO.builder()
												.token(refreshToken)
												.userNo(userNo)
												.expiration(System.currentTimeMillis() + 3600000L * 72)
												.build();
		
		mapper.saveToken(token);
		
	}

}
