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
	 	BoardVO���� ������ �������� Session���ٰ� ������ �ΰ� ���Ӱ� ����� �׸����� ����
	 	������ ����� ������ �״�� ����
	 
	 
	 */
public class BoardController {

	//��ɺ��� Controller�� ����:
	
	//���� ������ ���� �ϱ� ���ؼ� DAO ��ü�� ���� ȣ���ϸ� �ȵȴ�.
	/*
	 * DAO �� ���� ȣ���ϸ� DAO�� ���� �����Ϸ����Ҷ�(���ο�Ŭ������ �����ؼ� ����)
	 *  �Ʒ��� �ڵ带 �� 
	 * ��� ���ľ� �Ѵ�. (���� ���� �����)
	 * BoardDAO�� BoardService��� �������̽��� ����.
	 * ALT shift t => extract interface
	 * Controller������ ServiceImpl�� ȣ��
	 * �������̽��� ��ü ���� �ؼ� �ڵ带 ������ �س��ƾ� ���� ������ ���� �� �� �ִ�.
	 * 
	 * 
	 * BoardDAO ����, �̰ɷ� BoardService �������̽� ����
	 * BoardServiceImpl Ŭ���� ���� impl BoardService
	 * BoardServiceImpl�� @Service("boardService")�� ��ü ����
	 * @Autowired private BoardDAO boardDAO; �� ��ü����?
	 * <== DAO�� �ٲ�� �̺κи� �ٲ��ָ��.
	 * ���ο� boardDAO.crud(BoardVO vo);
	 * 
	 * 
	 */
	
	@Autowired
	private BoardService boardService; //�������̽��� DAO�� ȣ��
	
	/*json���� ������ ��ȯ*/
	//������ ��ȯ ó�� (DB���� ������ ���� JSON�������� ���� ��ȯ��Ŵ
	@RequestMapping("/dataTransform.do")
	@ResponseBody	
	//mvc:annotation-driven>���� @ResponseBody�� �Դ����ϰ�
	//
	public List<BoardVO> dataTransform(BoardVO vo) {
		// NULL üũ
		vo.setSearchCondition("TITLE");
		vo.setSearchKeyword("");
		List<BoardVO> boardList = boardService.getBoardList(vo);
		return boardList;
	}
	
 
	 
	
	
	//1. �� ��� : insertBoard Controller ����
	@RequestMapping(value="/insertBoard.do") //Ŭ���̾�Ʈ ��û
	public String insertBoard(BoardVO vo) throws IOException{
		System.out.println("�� ��� ó��- Spring MVC ������̼� �۵�  Controller ����");
		System.out.println(vo.getUploadFile());
		System.out.println(vo.getContent());
		System.out.println(vo.getSeq());
		System.out.println(vo.getTitle());
		System.out.println(vo.getWriter());
		//���� ���ε� ó��
		
		MultipartFile uploadFile = vo.getUploadFile();
		if (!uploadFile.isEmpty()) {
			String fileName; 
			fileName = uploadFile.getOriginalFilename();
			uploadFile.transferTo(new File("C:/upload/" + fileName));
		}

		boardService.insertBoard(vo);
		return "getBoardList.do";
	}
	
	//2. �� ���� : updateBoard Controller ����
	@RequestMapping(value ="/updateBoard.do")
	public String updateBoadr(@ModelAttribute("board")BoardVO vo){
		System.out.println("�� ���� ó�� Spring MVC ������̼�  Controller ����");
		
		
		System.out.println("��ȣ : "+ vo.getSeq());
		System.out.println("���� : "+ vo.getTitle());
		System.out.println("�ۼ��� : "+vo.getWriter()); //update���� �ѱ�� ������ �����ȉ��.
		System.out.println("���� : "+vo.getContent());
		System.out.println("����� : " + vo.getReg_date());
		System.out.println("��ȸ�� : " + vo.getCnt());
		System.out.println("==============================");
		
		boardService.updateBoard(vo);
		
		return "redirect:getBoardList.do";
		

	}
	
	//3. �� ���� : deleteBoard Controller ����
	@RequestMapping(value="/deleteBoard.do")
	public String deleteBoard(BoardVO vo){
		System.out.println("�� ���� ó�� Spring MVC ������̼�  Controller ����");
		
		boardService.deleteBoard(vo);
		
		return "redirect:getBoardList.do"; 
	}
	//�˻� ���� ��� ����(Model ��ü�� ���� �� �߰��մϴ�.)
		//���ϸ��� �۵�, Model��ü�� ȣ���ϱ� ���� ���� �۵��Ǿ Model��ü�� ���� �Ҵ��Ѵ�.
		//
	@ModelAttribute("conditionMap")
	public Map<String, String> searchConditionMap(){
		Map<String, String> conditionMap = new HashMap<String, String>();
		conditionMap.put("����", "TITLE");
		conditionMap.put("����", "CONTENT");
		conditionMap.put("�̸�", "NAME");
		conditionMap.put("��¥", "REGDATE");
		
		return conditionMap;
	}
	
		//searchConditionMap() �޼ҵ�� @ModelAttribute ������̼��� �ֱ�
			//getBoardList�޼ҵ庸�� ���� �����̵ȴ�.
	
	//4. �� ��� �˻� : getBoardList Controller ����
	@RequestMapping(value="/getBoardList.do")
	public String getBoardList(BoardVO vo, Model model) {
		System.out.println("�� ��� �˻� ó�� -- Spring MVC ȣ�� annotation  Controller ����");
		
		//NUll üũ (�⺻ ó��)
		if (vo.getSearchCondition() == null) { //�˻��� �������� ������ �⺻�� TITLEó��
			vo.setSearchCondition("TITLE");
		}
		if (vo.getSearchKeyword() == null) {
			vo.setSearchKeyword("");
		}
		
		model.addAttribute("boardList",boardService.getBoardList(vo));
		
		return "getBoardList.jsp";
		
	}
	
	//5. �� �� �˻� : getBoard Conteroller ����
	@RequestMapping(value="/getBoard.do")
	public String getBoard(BoardVO vo, Model model) {
		System.out.println("�� �� ��ȸ ó�� --  ������ ������̼� ó��  Controller ����");
		
		System.out.println("VO�� �ڵ����� �Ѿ���� ��"+vo.getSeq());
		
		model.addAttribute("board",boardService.getBoard(vo));

		return "getBoard.jsp";
		
		//ModelAndView : Model (���������� ������ ���� �ѱ拚) + view (���������� ����)
		//Model 	   : Model (���������� ������ ���� �ѱ拚)
	}
	
	/*@ModelAttribute :
		1. Command ��ü�� ���۵Ǵ� ��ü�̸��� �ٸ��̸����� �����ؼ� ��� �Ҷ�
		2. View���� ����� �����͸� �����ϴ� �뵵�� ����.
			@ModelAttribute�� ������ �޼ҵ�� @RequestMapping ������̼���
			������ �޼ҵ� ���� ���� ȣ���.
			@ModelAttribute �޼ҵ� ���� ����� ���ϵǴ� ��ü�� �ڵ����� Model�� �����̵ȴ�.
			@ModelAttribute �޼ҵ� ���� ����� ���ϵǴ� ��ü�� View ����������
			��� �� �� ����.
			
	
	*/
}
