package com.kh.secom.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kh.secom.member.model.service.MemberService;
import com.kh.secom.member.model.vo.MemberDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("members")
@RequiredArgsConstructor
public class MemberController {

	private final MemberService memberService;
	
	// 새롭게 데이터를 만들어내는 요청(INSERT) == POST
	@PostMapping			// 받을때도 body에 있는 값을 받겠다 적어줌.
	public ResponseEntity<?> save(@RequestBody MemberDTO requestMember){
		// 보편적으로 가장 좋은 반환 값, 새롭게 추가된 회원의 정보를 앞단에 전해주는게 가장 좋음
		
		log.info("요청한 사용자의 데이터 : {}", requestMember);
		
		memberService.save(requestMember);
		
		return null;
	}
	
}
