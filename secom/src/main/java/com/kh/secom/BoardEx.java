package com.kh.secom;

public class BoardEx {

	// 테이블, 시퀀스 생성
	/*
	 * CREATE TABLE TB_BOARD(
			BOARD_NO NUMBER PRIMARY KEY,
			BOARD_TITLE VARCHAR2(300) NOT NULL,
			BOARD_CONTENT VARCHAR2(4000) NOT NULL,
			BOARD_WRITER NUMBER REFERENCES TB_MEMBER,
			BOARD_FILE_URL VARCHAR2(400)
		);
		
		CREATE SEQUENCE SEQ_BNO;
	 * 
	 */
	
	/*
	 * 테이블, 시퀀스 세팅 후 yml 파일에서 spring mvc, web 설정
     * 
     * 게시글 작성 (Insert), 새로운 데이터를 만들어내는것이기때문에 PostMapping
     * 
     * DTO 작성
     * VO 내부에서 애노테이션을 이용해 유효성검사를 진행할수있음
     * 
     * BoardDTO형태의 board객체를 받아와서 보낼것이고, file도 마찬가지임 매개변수자리에 애노테이션 잘 달아주기.
     * 
     * 값이 잘 넘어오는지 확인하기, postman에서 파일 업로드가 안된다면
     * Postman Agent를 찾아 이름을 Postman으로 바꿔주고 Postman 폴더 안의 file에 사진파일을 넣고 올려보기
     * 
     * FileService 만들고 파일 위치지정, 이름변경, 저장 등 메서드 작성
     * 
     * 페이징처리 방법 Busan 프로젝트 할 때처럼 더보기를 누르면 세개씩 더 보이게
     * 게시글이 더이상 없으면 더보기 버튼 비활성화
     * 
     * 집에서 STATUS, 조회수, 작성일 등 추가해서 수정해보기
     * 
     * 컨트롤러에서 유효성 검사를 하고싶다면 @Validated 애노테이션을 추가해줘야한다.
     * @Validated를 추가하면 매개변수자리에서 유효성검사 가능, 서비스로 넘기기 전에 걸려서 못들어감
     * 
     * 
     * 게시글이 있나 없나 확인하는 예외처리 메서드 만들기, 예외처리 메서드 활용하기.
     * 
     * SecurityContextHolder에서 Authentication을 얻어내는 메서드(getAuthenticatedUser),
     * 게시글 작성자를 검증하는 메서드(validBoardWriter) 작성하고 활용하기.
     * 
     * 
     * 
     * 댓글패키지만들고 DTO에서 LocalDateTime 형태 사용해보기
     * 
     * 실습 겸 숙제
     * 표 만들기 (테이블사용하면될듯)
     * 
     * 현빈 서비스 ㅣ  해야되는 작업 흐름에 맞게(순서대로)       ㅣ
     * ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
     * 회원가입    ㅣ 사용자에게 이름, 나이, 아이디 등등 입력받기 ㅣ Id가 DB에 존재하는지 확인(security 이용해서 loadUserByUsername 반환 값 UserDetails로 확인 ㅣ 그 이후
     * 로그인     ㅣ 
     * 비밀번호변경 ㅣ
     * 회원탈퇴    ㅣ
	 */
	
	/*
	 * DBeaver
	 * 
	CREATE TABLE TB_MEMBER(
	USER_NO NUMBER PRIMARY KEY,
	USER_ID VARCHAR2(30) NOT NULL UNIQUE,
	USER_PWD VARCHAR2(100) NOT NULL,
	ROLE VARCHAR2(30) NOT NULL
);

DROP TABLE TB_MEMBER ;

CREATE SEQUENCE SEQ_MNO;

DROP SEQUENCE SEQ_MNO;

SELECT * FROM TB_MEMBER;

DELETE 
FROM TB_MEMBER tm 
WHERE USER_ID = 'dlgusqls1';

DROP TABLE TB_REFRESH_TOKEN;

CREATE TABLE TB_REFRESH_TOKEN(
	USER_NO NUMBER REFERENCES TB_MEMBER,
	TOKEN VARCHAR2(500) NOT NULL,
	EXPIRED_AT NUMBER NOT NULL
);

SELECT * FROM TB_REFRESH_TOKEN;


CREATE TABLE TB_BOARD(
	BOARD_NO NUMBER PRIMARY KEY,
	BOARD_TITLE VARCHAR2(300) NOT NULL,
	BOARD_CONTENT VARCHAR2(4000) NOT NULL,
	BOARD_WRITER NUMBER REFERENCES TB_MEMBER,
	BOARD_FILE_URL VARCHAR2(400)
);

CREATE SEQUENCE SEQ_BNO;

SELECT * FROM TB_BOARD;

CREATE TABLE TB_COMMENT (
	COMMENT_NO NUMBER PRIMARY KEY,
	REF_BOARD_NO NUMBER REFERENCES TB_BOARD,
	REF_USER_NO NUMBER REFERENCES TB_MEMBER,
	CONTENT VARCHAR2(4000) NOT NULL,
	CREATE_DATE DATE DEFAULT SYSDATE
)
	

CREATE SEQUENCE SEQ_CNO;

SELECT * FROM TB_COMMENT;

		SELECT
				COMMENT_NO commentNo
			,	REF_BOARD_NO refBoardNo
			,	USER_ID commentWriter
			,	CONTENT content
			,	CREATE_DATE createDate
		  FROM
		  		TB_COMMENT
		  JOIN
		  		TB_MEMBER ON (REF_USER_NO = USER_NO)
		 WHERE
		 		REF_BOARD_NO = 5
		 ORDER
		 	BY
		 		CREATE_DATE DESC;
		




	 */
	
}
