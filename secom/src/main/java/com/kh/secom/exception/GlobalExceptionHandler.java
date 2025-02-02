package com.kh.secom.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MissmatchPasswordException.class)
	public ResponseEntity<?> handlerMissmatchPassword(MissmatchPasswordException e){
		return ResponseEntity.badRequest().body(e.getMessage());
	}
	
	@ExceptionHandler(AccessTokenExpiredException.class)
	public ResponseEntity<?> handlerExpiredToken(AccessTokenExpiredException e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}
	
	@ExceptionHandler(JwtTokenException.class)
	public ResponseEntity<?> handlerInvalidToken(JwtTokenException e){
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
	}

	@ExceptionHandler(InvalidParameterException.class)
	public ResponseEntity<?> handleInvalidParameter(InvalidParameterException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(DuplicateUserException.class)
	public ResponseEntity<String> handleDuplicateUser(DuplicateUserException e) {
		return ResponseEntity.badRequest().body(e.getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleArgumentNotValid(MethodArgumentNotValidException e) {

		// 발생한 에러를 앞단으로 보내주기위해 Map으로 만든다.
		Map<String, String> errors = new HashMap();

		/*
		 * // 에러 난 목록을 list로 받을 수 있다. List list = e.getBindingResult().getFieldErrors();
		 * 
		 * 
		 * for(int i = 0; i < list.size(); i++) { log.info("예외발생필드명 : {}, 이유 : {}",
		 * ((FieldError)list.get(i)).getField(),
		 * ((FieldError)list.get(i)).getDefaultMessage()); errors.put(
		 * ((FieldError)list.get(i)).getField(),
		 * ((FieldError)list.get(i)).getDefaultMessage() ); }
		 */

		// 위 코드를 줄여서 아래처럼 만들 수 있다.
		e.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

		// badReqeust가 400 에러 들어왔을때 발생하는 에러를 잡아줌
		return ResponseEntity.badRequest().body(errors);

	}
	


}
