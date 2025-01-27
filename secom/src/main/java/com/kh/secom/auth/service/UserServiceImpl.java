package com.kh.secom.auth.service;

import java.util.Collections;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.member.model.mapper.MemberMapper;
import com.kh.secom.member.model.vo.Member;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserDetailsService{
	// AuthenticationManager가 실질적으로 사용자의 정보를 조회하는데 사용할 클래스

	private final MemberMapper mapper;
	
	@Override
	public UserDetails loadUserByUsername(String username){

		// 1. UserDetailsService를 implements해서 loadUserByUsername 메서드를 Override 하고

		// Username을 PK, 혹은 유저의 ID와 같이 중복값이 없는 값을 생각해야한다.
		
		// AuthenticationManager의 authenticate메소드를 부를때 보냈던 Token의 첫번째 인자값, 유저가 입력한 ID를 전달받는다.
		Member user = mapper.findByUserId(username);
		// 2. DB에 있는 사용자 정보와 비교하고 
		
		if(user == null) {
			throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
		} 
		// if문을 넘었다는건 사용자가 입력한 값이 DB에 존재한다는뜻

		// 이제 사용자가 입력한 비밀번호와, 테이블에 있는 암호화된 비밀번호가 맞는지 확인해야함.
		// AuthenticationManager가 해주는데, 그전에 DB에서 조회한 Member을 갖고 객체를 만들어 전달을 해줘야함
		// Override된 상태라 UserDetails 형태로 돌려줘야함. 지금 갖고있는 Member 클래스,MemberDTO와 다름.
		// UserDetails에는 username, password, role 세가지밖에 못넣기때문에 Member와 MemberDTO의 값은 담을수없다.
		// MemberDTO를 고쳐도 되지만, 새로 만들것.
		
		// UserDetails를 쓰기로 다같이 약속한거라
		
		// 3. UserDetails라는 타입의 객체에 사용자 정보를(컬럼에 조회된 값) 담아 return 한다.
		return CustomUserDetails.builder()
								.userNo(user.getUserNo())
								.username(user.getUserId())
								.password(user.getUserPwd())
								.authorities(Collections.singletonList(new SimpleGrantedAuthority(user.getRole())))
								.build();
		// 여기까지 값을 모두 들고가서 두번째 인자인 비밀번호(평문)과 DB에 있는 비밀번호(암호문)의 검증을 시작
		
		
	}
	
	
	
}
