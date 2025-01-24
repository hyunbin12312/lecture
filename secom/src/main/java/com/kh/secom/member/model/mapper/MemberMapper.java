package com.kh.secom.member.model.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.kh.secom.member.model.vo.Member;
import com.kh.secom.member.model.vo.MemberDTO;

@Mapper
public interface MemberMapper {

	Member findByUserId(String userId);

	void save(MemberDTO requestMember);


}
