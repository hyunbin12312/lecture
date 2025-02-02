package com.kh.secom.member.model.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.exception.DuplicateUserException;
import com.kh.secom.exception.InvalidParameterException;
import com.kh.secom.exception.MissmatchPasswordException;
import com.kh.secom.member.model.mapper.MemberMapper;
import com.kh.secom.member.model.vo.ChangePasswordDTO;
import com.kh.secom.member.model.vo.Member;
import com.kh.secom.member.model.vo.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

	private final MemberMapper mapper;
	private final PasswordEncoder passwordEncoder;

	@Override
	public void save(MemberDTO requestMember) {

		// 빈 문자열인지 유효성검사
		if ("".equals(requestMember.getUserId()) || "".equals(requestMember.getUserPwd())) {
			// log.info("빈 문자열은 안됨");
			throw new InvalidParameterException("유효하지 않은 값입니다.");
		}

		// DB에 이미 사용자가 입력한 사용자가 존재하면 안됨
		Member searched = mapper.findByUserId(requestMember.getUserId());
		if (searched != null) {
			throw new DuplicateUserException("이미 존재하는 아이디입니다.");
		}

		// 비밀번호가 평문이라 그냥 들어가면 안됨
		// + ROLE == USER라고 저장할 예정
		// passwordEncoder 가 필요함

		Member member = Member.builder().userId(requestMember.getUserId())
				.userPwd(passwordEncoder.encode(requestMember.getUserPwd())).role("ROLE_USER").build();

		mapper.save(member);
		log.info("회원가입 성공");

	}

	@Override
	public void changePassword(ChangePasswordDTO changeEntity) {
		
		/*
		// 비밀번호를 바꾸고싶음
		// 현재 비밀번호는 currentPassword인데,
		// currentPassword가 DB의 정보가 맞다면 newPassword로 바꾸고싶음.

		// DB에 있는 암호화된 비밀번호를 matches를 돌려서 사용자가 입력한 비밀번호를 Bcrypt를 돌렸을때 일치하는지 확인해야함.

		// 요청을 JwtFilter에서 거쳐왔기때문에 SecurityContextHolder이 갖고있는 SecurityContext에 담아놓은 정보
		// 사용가능
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		// 정보를 만들어놓은 커스텀유저디테일에 담음
		CustomUserDetails user = (CustomUserDetails) auth.getPrincipal();

		if (!(passwordEncoder.matches(changeEntity.getCurrentPassword(), user.getPassword()))) {
			throw new MissmatchPasswordException("비밀번호를 올바르게 입력해주세요.");
		}
		*/
		
		// 처리하는 메서드를 따로 만들었으니 메서드 호출해서 처리
		Long userNo = passwordMatches(changeEntity.getCurrentPassword());

		String encodedPassword = passwordEncoder.encode(changeEntity.getNewPassword());
		
		Map<String, String> changeRequest = new HashMap();
		changeRequest.put("userNo", String.valueOf(userNo));
		changeRequest.put("password", encodedPassword);
		
		
		mapper.changePassword(changeRequest);
		
	}

	@Override
	public void deleteByPassword(Map<String, String> password) {
		
		// 이 떄 비밀번호가 문자열(String)로 들어옴 인자값의 자료형 Map으로 변경
		log.info(password.get("password"));
		// 하위테이블에서 userNo를 사용하고있어서 DELETE 명령을 수행하지못하는 상황 발생

		// 1. 사용자가 입력한 비밀번호와 DB에 저장되어있는 비밀번호가 서로 일치하는지 검증한다.
		// 2. 사용자가 입력한 비밀번호는 password에 담아 가져왔고, DB에있는 비밀번호는 principal에 있으니 가서 찾는다.
		// 1,2번의 방식을 passwordMatches 메서드를 만들어서 처리
		Long userNo = passwordMatches(password.get("password"));
		
		mapper.deleteByPassword(userNo);
		
	}
	
	private Long passwordMatches(String password) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
		
		if(!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new MissmatchPasswordException("비밀번호가 일치하지 않습니다.");
		}
		
		return userDetails.getUserNo();
	}

}