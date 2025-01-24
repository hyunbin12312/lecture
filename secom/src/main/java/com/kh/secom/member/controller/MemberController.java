package com.kh.secom.member.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
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

	private final MemberService MemberService;
	
	// 새롭게 데이터를 만들어내는 요청 == POST
	@PostMapping
	public ResponseEntity<?> save(MemberDTO requestMember){
		
		log.info("요청한 사용자의 데이터 : {}", requestMember);
		
		return null;
	}
	
}
