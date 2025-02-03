package com.kh.secom.comment.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kh.secom.auth.model.vo.CustomUserDetails;
import com.kh.secom.auth.service.AuthenticationService;
import com.kh.secom.board.model.service.BoardService;
import com.kh.secom.comment.model.dto.Comment;
import com.kh.secom.comment.model.mapper.CommentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	
	private final CommentMapper mapper;
	private final BoardService boardService;
	private final AuthenticationService authService;
	
	@Override
	public void insertComment(Comment comment) {
		// 게시글이 있는지 없는지 번호로 먼저 확인
		// BoardService에 만들어놓은 게시글 찾는 메서드 활용
		boardService.findById(comment.getRefBoardNo());
		
		// 요청한 사용자가 토큰에서 뽑힌 Subject랑 같은가??
		CustomUserDetails user = authService.getAuthenticatedUser();
		authService.validWriter(comment.getCommentWriter(), user.getUsername());
		
		comment.setCommentWriter(String.valueOf(user.getUserNo()));
		
		
		// Insert
		mapper.insertComment(comment);
		
	}

	@Override
	public List<Comment> findByBoardNo(Long boardNo) {
		
		return mapper.findByBoardNo(boardNo);
	}

}
