package com.kh.secom.board.model.service;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.auth.service.AuthenticationService;
import com.kh.secom.board.model.dto.BoardDTO;
import com.kh.secom.board.model.mapper.BoardMapper;
import com.kh.secom.exception.InvalidParameterException;
import com.kh.secom.token.model.service.FileService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardServiceImpl implements BoardService {
	
	private final BoardMapper mapper;
	private final FileService fileService;
	private final AuthenticationService authService;
	
	
	@Override
	public void save(BoardDTO board, MultipartFile file) {

		log.info("게시글정보 : {} \n파일 정보: {}", board, file);
		
		CustomUserDetails user = authService.getAuthenticatedUser();
		authService.validWriter(board.getBoardWriter(), user.getUsername());
		
		if(file != null && !file.isEmpty()) {
			// 파일 저장하고, 저장 위치 세팅하기
			String filePath = fileService.store(file);
			board.setBoardFileUrl(filePath);
		} else {
			board.setBoardFileUrl(null);
		}
		
		board.setBoardWriter(String.valueOf(user.getUserNo()));
		
		mapper.save(board);
		
	}

	@Override
	public List<BoardDTO> findAll(int page) {
		int size = 3;
		RowBounds rowBounds = new RowBounds(page * size, size);
		return mapper.findAll(rowBounds);
	}
	
	private BoardDTO getBoardOrThrow(Long boardNo) {
		// board 상세보기, 예외처리까지
		BoardDTO board = mapper.findById(boardNo);
		if(board == null) {
			throw new InvalidParameterException("올바르지 않은 게시글 번호입니다.");
		}
		return board;
	}

	@Override
	public BoardDTO findById(Long boardNo) {
		return getBoardOrThrow(boardNo);
	}
	
	@Override
	public BoardDTO update(BoardDTO board, MultipartFile file) {
		BoardDTO exsitingBoard = getBoardOrThrow(board.getBoardNo());
		CustomUserDetails user = authService.getAuthenticatedUser();
		authService.validWriter(board.getBoardWriter(), user.getUsername());
		
		exsitingBoard.setBoardTitle(board.getBoardTitle());
		exsitingBoard.setBoardContent(board.getBoardContent());
		
		if(file != null && !file.isEmpty()) {
			String filePath = fileService.store(file);
			exsitingBoard.setBoardFileUrl(filePath);
		} else {
			exsitingBoard.setBoardFileUrl(null);
		}
		
		mapper.update(exsitingBoard);
		
		return null;
	}

	@Override
	public void deleteById(Long boardNo) {
		BoardDTO exsitingBoard = getBoardOrThrow(boardNo);
		CustomUserDetails user = authService.getAuthenticatedUser();
		authService.validWriter(exsitingBoard.getBoardWriter(), user.getUsername());
		
		mapper.deleteById(boardNo);
		
	}

}
