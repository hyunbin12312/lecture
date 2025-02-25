package com.kh.secom.board.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.kh.secom.board.model.dto.BoardDTO;
import com.kh.secom.board.model.service.BoardService;

import lombok.extern.slf4j.Slf4j;

/**
 * BoardController의 단위테스트를 진행할 클래스
 * 
 * 단위테스트에서는 내부 로직만 테스트를 하기 때문에,
 * 실제 외부 의존성을 사용하지 않고 가짜(mock) 객체를 주입해서 테스트한다.
 */

@Slf4j
public class BoardControllerUnitTest {

	/**
	 * 테스트 시, 실제 BoardService 대신에 가짜 객체를 만들어서 사용함
	 * 가짜 데이터를 반환하도록 미리 동작을 정의해서 사용할 예정
	 */
	@Mock
	private BoardService service;
	
	/**
	 * 가짜(Mock)객체를 BoardController에 주입하는 애노테이션
	 */
	@InjectMocks
	private BoardController boardController;
	
	
	@BeforeEach
	public void setup() {
		// 가짜(mock)객체들을 boardController에 알아서 담아준다.
		MockitoAnnotations.openMocks(this);
	}
	
	@Test
	public void testFindId() {
		/*
		 * 테스트를 위한 가짜 객체를 생성(Board)
		 * 가짜 객체의 boardTitle 필드에 임의의 값을 대입해보자.
		 * 
		 * 참고로 여기서 임의의 값은 나중에 검증하는 기대값 대입
		 */
		
		BoardDTO board = new BoardDTO();
		board.setBoardTitle("11번 게시글 제목");

		// DB에 있는것 대신 위에 작성한 board를 반환하도록 세팅
		when(service.findById(11L)).thenReturn(board);
		// BoardController 내부에서 service.findById(11L)를 호출했을 때,
		// 실제 DB에서 조회한 값이 아닌 테스트를 위해 만든 가짜 board를 반환하도록 세팅
		
		var response = boardController.findById(11L);
		log.info("response : {}", response);
		
		
	}
	
	
	/*
	 * 1. 실패하는 코드를 작성한다. red
	 *  - 예외처리 등을 생각한다.
	 * 
	 * 2. 테스트를 통과할 수 있도록 최소한의 코드만 작성 green
	 *  - 예외처리 방법을 작성한다.
	 * 
	 * 3. 코드 정리하고 개선하기. refactoring
	 */
	
	
}
