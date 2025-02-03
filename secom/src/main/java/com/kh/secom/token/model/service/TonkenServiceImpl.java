package com.kh.secom.token.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.auth.util.JwtUtil;
import com.kh.secom.token.model.dto.RefreshTokenDTO;
import com.kh.secom.token.model.mapper.TokenMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
		// 5. 사용자가 RefreshToken을 증명하려 할 때 DB가서 조회하기
		

		// 4. 만료기간이 지난 RefreshToken이 있을 수 있으니 지우기
		deleteExpiredRefreshToken(userNo);

		return tokens;
	}

	private void deleteExpiredRefreshToken(Long userNo) {
		
		Map<String, Long> params = new HashMap();
		
		params.put("userNo", userNo);
		params.put("currentTime", System.currentTimeMillis());

		mapper.deleteExpiredRefreshToken(params);
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

	@Override
	public Map<String, String> refreshTokens(String refreshToken) {

		RefreshTokenDTO token = mapper.findByToken(refreshToken);
		
		// 존재하는지, 없는지 / 만료했는지 검증
		if(token == null || token.getExpiration() < System.currentTimeMillis()) {
			throw new RuntimeException("알 수 없는 RefreshToken 입니다.");
		}
		
		// generateToken 메서드를 사용하기위해 userName과 userNo가 필요해서 가져옴
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();
		
		return generateToken(user.getUsername(), user.getUserNo());
	}

}
