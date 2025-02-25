package com.kh.secom.test.controller;

import org.springframework.stereotype.Controller;

@Controller
public class TestController {
	
	// 이 메서드가 잘 돌아가는지 확인할것임.
	public String testPrint(String argument) {
		// 인자값으로 받은 매개변수를 그대로 돌려주는 메서드
		return argument;
	}

	public int testPlus(int firstNum, int secondNum) {
		return (firstNum + secondNum);
	}
	
	public void testException(int pk) {
		
		if(pk < 1) {
			throw new IllegalArgumentException();
		}
		
	}
	
}
