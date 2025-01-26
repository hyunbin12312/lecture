package com.kh.secom.member.model.service;

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

	@Override
	public void save(MemberDTO requestMember) { //일반 사용자용 가입 메서드
		
		// 빈 문자열인지 유효성검사
		if("".equals(requestMember.getUserId()) || 
		   "".equals(requestMember.getUserPwd())) {
			log.info("빈 문자열은 안됨");
			throw new InvalidParameterException("유효하지 않은 값입니다.");
		}
		
		// DB에 이미 사용자가 입력한 사용자가 존재하면 안됨
		Member searched = mapper.findByUserId(requestMember.getUserId());
		if(searched != null) {
			throw new DuplicateUserException("이미 존재하는 아이디입니다.");
		}
		
		
		mapper.save(requestMember);
		
	}
	
	
	
}
