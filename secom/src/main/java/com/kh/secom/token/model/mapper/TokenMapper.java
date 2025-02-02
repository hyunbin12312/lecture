package com.kh.secom.token.model.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.kh.secom.token.model.dto.RefreshTokenDTO;

@Mapper
public interface TokenMapper {

	@Insert("INSERT INTO TB_REFRESH_TOKEN VALUES(#{userNo}, #{token}, #{expiration})")
	void saveToken(RefreshTokenDTO refreshToken);

	//여기서 조건절에 refreshToken을 걸어주지않으면 여러개 행중에 어떤걸 받아올지 모르기때문에 에러가 발생함
	@Select("SELECT USER_NO userNo, TOKEN token, EXPIRED_AT expiration FROM TB_REFRESH_TOKEN WHERE TOKEN = #{refreshToken}")
	RefreshTokenDTO findByToken(String refreshToken);

	void deleteExpiredRefreshToken(Map<String, Long> params);
	
}
			