package com.springbook.biz.board;

import java.util.List;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.springbook.biz.board.BoardService;
import com.springbook.biz.board.BoardVO;


public class BoardServiceClient {
	public static void main(String[] args) {
		// 1. Spring 컨테이너를 구동한다.
		AbstractApplicationContext container = 
				new GenericXmlApplicationContext("applicationContext.xml");

		// 2. Spring 컨테이너로부터 BoardServiceImpl 객체를 Lookup한다.
		BoardService boardService = 
				(BoardService) container.getBean("boardService");

		// 3. 글 등록 기능 테스트
		BoardVO vo = new BoardVO();
		//vo.setSeq(200);             // 임시로 예외 발생 시키기 위해서 만듬(AfterThrowing 설정)
		vo.setTitle("모래 제목300");
		vo.setWriter("홍길동300");
		vo.setContent("모래 내용300..............");
		boardService.insertBoard(vo);
		

		// 4. 글 목록 검색 기능 테스트
		
		vo.setSearchCondition("TITLE");  //SQL의 TITLE 컬럼을 검색
		vo.setSearchKeyword("모래");		// TITLE 컬럼의 값 "임시" 검색 
	
		List<BoardVO> boardList = boardService.getBoardList(vo);
		for (BoardVO board : boardList) {
			System.out.println("---> " + board.toString());
		}
		

		
		// 5. Spring 컨테이너 종료
		container.close();
		
		
	}
}