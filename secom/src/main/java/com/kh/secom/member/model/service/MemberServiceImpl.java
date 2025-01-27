package com.kh.secom.member.model.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kh.secom.exception.DuplicateUserException;
import com.kh.secom.exception.InvalidParameterException;
import com.kh.secom.member.model.mapper.MemberMapper;
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

}