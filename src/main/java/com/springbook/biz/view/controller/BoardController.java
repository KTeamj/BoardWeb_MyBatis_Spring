package com.springbook.biz.view.controller;



import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.swing.plaf.multi.MultiMenuItemUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import com.springbook.biz.board.BoardService;
import com.springbook.biz.board.BoardVO;

@Controller
@SessionAttributes("board")

	/*
	 	@SessionAttribute("board")
	 	
	 	board = BoardVO vo /
	 	BoardVO에서 이전에 설정값을 Session에다가 저장해 두고 새롭게 변경된 항목으로 수정
	 	기존의 저장된 값들은 그대로 유지
	 
	 
	 */
public class BoardController {

	//기능별의 Controller를 통합:
	
	//유지 보수를 쉽게 하기 위해서 DAO 객체를 직접 호출하면 안된다.
	/*
	 * DAO 를 직접 호출하면 DAO를 새로 변경하려고할때(새로운클래스를 생성해서 연결)
	 *  아래의 코드를 다 
	 * 뜯어 고쳐야 한다. (유지 보수 어려움)
	 * BoardDAO로 BoardService라는 인터페이스를 만듬.
	 * ALT shift t => extract interface
	 * Controller에서는 ServiceImpl을 호출
	 * 인터페이스를 객체 주입 해서 코드를 구현을 해놓아야 유지 보수를 쉽게 할 수 있다.
	 * 
	 * 
	 * BoardDAO 생성, 이걸로 BoardService 인터페이스 생성
	 * BoardServiceImpl 클래스 만들어서 impl BoardService
	 * BoardServiceImpl은 @Service("boardService")로 객체 주입
	 * @Autowired private BoardDAO boardDAO; 로 객체주입?
	 * <== DAO가 바뀌면 이부분만 바꿔주면됨.
	 * 내부엔 boardDAO.crud(BoardVO vo);
	 * 
	 * 
	 */
	
	@Autowired
	private BoardService boardService; //인터페이스로 DAO를 호출
	
	/*json으로 데이터 변환*/
	//데이터 변환 처리 (DB에서 가져온 값을 JSON포멧으로 값을 변환시킴
	@RequestMapping("/dataTransform.do")
	@ResponseBody	
	//mvc:annotation-driven>에서 @ResponseBody를 게더링하고
	//
	public List<BoardVO> dataTransform(BoardVO vo) {
		// NULL 체크
		vo.setSearchCondition("TITLE");
		vo.setSearchKeyword("");
		List<BoardVO> boardList = boardService.getBoardList(vo);
		return boardList;
	}
	
 
	 
	
	
	//1. 글 등록 : insertBoard Controller 통합
	@RequestMapping(value="/insertBoard.do") //클라이언트 요청
	public String insertBoard(BoardVO vo) throws IOException{
		System.out.println("글 등록 처리- Spring MVC 어노테이션 작동  Controller 통합");
		System.out.println(vo.getUploadFile());
		System.out.println(vo.getContent());
		System.out.println(vo.getSeq());
		System.out.println(vo.getTitle());
		System.out.println(vo.getWriter());
		//파일 업로드 처리
		
		MultipartFile uploadFile = vo.getUploadFile();
		if (!uploadFile.isEmpty()) {
			String fileName; 
			fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File("C:/upload/" + fileName));
		}

		boardService.insertBoard(vo);
		return "getBoardList.do";
	}
	
	//2. 글 수정 : updateBoard Controller 통합
	@RequestMapping(value ="/updateBoard.do")
	public String updateBoadr(@ModelAttribute("board")BoardVO vo){
		System.out.println("글 수정 처리 Spring MVC 어노테이션  Controller 통합");
		
		
		System.out.println("번호 : "+ vo.getSeq());
		System.out.println("제목 : "+ vo.getTitle());
		System.out.println("작성자 : "+vo.getWriter()); //update에서 넘기는 변수가 설정안됬다.
		System.out.println("내용 : "+vo.getContent());
		System.out.println("등록일 : " + vo.getReg_date());
		System.out.println("조회수 : " + vo.getCnt());
		System.out.println("==============================");
		
		boardService.updateBoard(vo);
		
		return "redirect:getBoardList.do";
		

	}
	
	//3. 글 삭제 : deleteBoard Controller 통합
	@RequestMapping(value="/deleteBoard.do")
	public String deleteBoard(BoardVO vo){
		System.out.println("글 삭제 처리 Spring MVC 어노테이션  Controller 통합");
		
		boardService.deleteBoard(vo);
		
		return "redirect:getBoardList.do"; 
	}
	//검색 조건 목록 성정(Model 객체에 값을 더 추가합니다.)
		//제일먼저 작동, Model객체를 호출하기 전에 먼저 작동되어서 Model객체에 값을 할당한다.
		//
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap(){
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("제목", "TITLE");
		conditionMap.put("내용", "CONTENT");
		conditionMap.put("이름", "NAME");
		conditionMap.put("날짜", "REGDATE");
		
		return conditionMap;
	}
	
		//searchConditionMap() 메소드는 @ModelAttribute 어노테이션이 있기
			//getBoardList메소드보다 먼저 실행이된다.
	
	//4. 글 목록 검색 : getBoardList Controller 통합
	@RequestMapping(value="/getBoardList.do")
	public String getBoardList(BoardVO vo, Model model) {
		System.out.println("글 목록 검색 처리 -- Spring MVC 호출 annotation  Controller 통합");
		
		//NUll 체크 (기본 처리)
		if (vo.getSearchCondition() == null) { //검색을 선택하지 않으면 기본값 TITLE처리
			vo.setSearchCondition("TITLE");
		}
		if (vo.getSearchKeyword() == null) {
			vo.setSearchKeyword("");
		}
		
		model.addAttribute("boardList",boardService.getBoardList(vo));
		
		return "getBoardList.jsp";
		
	}
	
	//5. 글 상세 검색 : getBoard Conteroller 통합
	@RequestMapping(value="/getBoard.do")
	public String getBoard(BoardVO vo, Model model) {
		System.out.println("글 상세 조회 처리 --  스프링 어노테이션 처리  Controller 통합");
		
		System.out.println("VO로 자동으로 넘어오는 값"+vo.getSeq());
		
		model.addAttribute("board",boardService.getBoard(vo));

		return "getBoard.jsp";
		
		//ModelAndView : Model (뷰페이지로 변수의 값을 넘길떄) + view (뷰페이지를 저장)
		//Model 	   : Model (뷰페이지로 변수의 값을 넘길떄)
	}
	
	/*@ModelAttribute :
		1. Command 객체로 전송되는 객체이름을 다른이름으로 변경해서 사용 할때
		2. View에서 사용할 데이터를 설정하는 용도로 사용됨.
			@ModelAttribute가 설정된 메소드는 @RequestMapping 어노테이션이
			성정된 메소드 보다 먼저 호출됨.
			@ModelAttribute 메소드 실행 결과로 리턴되는 객체는 자동으로 Model에 저장이된다.
			@ModelAttribute 메소드 실행 결과로 리턴되는 객체를 View 페이지에서
			사용 할 수 없다.
			
	
	*/
}
