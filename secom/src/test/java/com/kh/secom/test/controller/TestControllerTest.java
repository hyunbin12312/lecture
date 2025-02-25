package com.kh.secom.test.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestControllerTest {

	private TestController tc = new TestController();

	// BeforeAll 애노테이션은 AfterAll과 세트임
	@BeforeAll
	public void init() {
		log.info("테스트 시작");
		// BeforeAll을 사용하기위해선 static키워드가 필요함. 정적메서드만 수행가능하기때문
		// static을 달고싶지 않다면 클래스에 @TestInstance(TestInstance.Lifecycle.PER_CLASS)를 작성해준다.

		// BeforeAll과 AfterAll에 static 키워드를 추가하던, 클래스에 @TestInstance를 추가하던 둘 중 하나는 해야함.
	}

	@Test
	public void test1() {
		// 공부하기위해 찾아보면 junit 4버전이 많겠지만
		// 요즘엔 junit 5버전으로 된 것을 보는게 좋다.

		// 아래 tr.testPrint를 실행하기위해 static 메서드를 호출해야함
		// assertEquals(응답결과, 수행하고자하는메서드, 수행실패시 메세지);
		assertEquals("안녕", tc.testPrint("안녕"), "반환하는 문자열이 일치하지 않습니다.");

		// 선택의영역, 세번째 인자값을 반환할 수 있다.
	}

	@Test
	public void test2() {
		assertEquals(3, tc.testPlus(1, 2));
	}

	@Test
	public void test3() {
		// 예외처리 상황 시 사용할수있는 메서드
		assertThrows(IllegalArgumentException.class, () -> tc.testException(5));

	}

	@AfterAll
	public void close() {
		log.info("테스트 종료");
	}

}
