package com.springbook.biz.view.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.springbook.biz.user.UserVO;
import com.springbook.biz.user.impl.UserDAO;
import com.springbook.biz.user.impl.UserService1;

@Controller
public class UserController {

	@Autowired
	private UserService1 userService1;
		//userService1�� �������̽� �̹Ƿ�, �̸� ������ ��ü�� ���Եȴ�.
	
	//1-1. �α� �� : login Controller ���� (GET ��� ó�� �޼ҵ�)
		@RequestMapping(value="/login.do",method = RequestMethod.GET)
		//public String loginViewGet(UserVO vo) {
		public String loginViewGet(@ModelAttribute("user") UserVO vo) {
			System.out.println("�α��� ó�� - Spring_MVC ������̼� ȣ�� Controller ����");
			vo.setId("admin");
			vo.setPass("1234");
			return "login.jsp";	//forward�� ���۽� vo�� ������ ���� �� ���޵�
			//return "redirect:login.jsp";
			
			//Command ��ü�� ������ ���� ��Ƽ� View �������� ���� �� �� �ִ�.
			//VO ��ü�� Ŭ���� �̸��� UserVO, ${userCO.id} ������������ ����� �� �� �ִ�.
		/*
		 * @ModelAtrrtibute : Command ��ü�� �������� ��ü �̸��� �ٸ� �̸����� ����
		 * (UserVO => user)		
		 * view ���������� ��½� : ${user.id},${user.pass}
		 * 	
		 * */	
			
			
		}
		//1-2. �α� �� : login Controller ���� (POST ��� ó�� �޼ҵ�)
		@RequestMapping(value="/login.do",method = RequestMethod.POST)
		public String loginViewPost(UserVO vo, UserDAO userDAO,HttpSession session) {
			System.out.println("�α��� ó�� - Spring_MVC ������̼� ȣ�� Controller ����");
			
			if (vo.getId() == null || vo.getId().equals("")) {
				throw new IllegalArgumentException("���̵�� �ݵ�� �Է�");
			}	//vo�� id ������ ���� �Ѿ���� ������ ������ ���ܸ� �߻���Ŵ .....
			
			UserVO user = userDAO.getUser(vo);
			
			if(user != null) {
				session.setAttribute("userName", user.getName());
				return "getBoardList.do";
			}else {
				return "login.jsp";
			}
			
			
		}
		/*
		//1-3. �α� �� : login Controller ���� 
		@RequestMapping(value="/login.do")
		public String login( UserVO vo, UserDAO userDAO) {
			System.out.println("�α��� ó�� - Spring_MVC ������̼� ȣ�� Controller ����");
			
			if (userDAO.getUser(vo) != null) {
				return "redirect:getBoardList.do";
			} else {
				return "redirect:login.jsp"; 
			}
			
		}
		*/
		//2. �α� �ƿ� : logout Controller ����
		@RequestMapping(value="/logout.do")
		public String logout(HttpSession session) {
			System.out.println("�α׾ƿ� ó�� Spring MVC ������̼�  Controller ����");
			
			session.invalidate();
			return "redirect:login.jsp";
		}
		
		//3. ȸ�� ��� (insert)
		@RequestMapping(value= "/insertUser.do", method = RequestMethod.GET)
		public String insertView (UserVO vo) {
			return "insertUser.jsp";
		}
		
		//ȸ����� ������ ���� �ְ� ���� : DB������ <==POST
		@RequestMapping(value = "/insertUser.do", method =RequestMethod.POST)
		public String insertUser(UserVO vo) {
			
			System.out.println("ȸ�� ���� ���� : - insertUser()�޼ҵ� ȣ�� - Mybatis");
			userService1.insertUser(vo);
			return "login.do";
		}
		
		//4. ȸ�� ���� (update)
			//ȸ�� ���� ��ũ�� Ŭ�� ������
		@RequestMapping(value="/updateUser.do",method = RequestMethod.GET)
		public String updateView(UserVO vo) {
			return "updateUser.jsp";
		}
		
		//ȸ�� ���� ���������� ���� �ְ� ���� ��ư�� �������� : POST
		
		@RequestMapping(value="/updateUser.do",method = RequestMethod.POST)
		public String updateUser(UserVO vo) {
			userService1.updateUser(vo);
			
			return "getUserList.do";
		}
		
		//5. ȸ�� Ż��
		
			//ȸ�� Ż�� Ŭ�� ������ : get ���
		@RequestMapping(value = "/deleteUser.do", method = RequestMethod.GET)
		public String deleteView(UserVO vo) {
			return "deleteUser.jsp";
		}
		
		//POST
		@RequestMapping(value = "/deleteUser.do", method = RequestMethod.POST)
		public String deleteUser(UserVO vo) {
			userService1.deleteUser(vo);
			return "getUserList.do";
		}
		
		
		//6. ȸ�� ��� ���
		
		@RequestMapping(value = "/getUserList.do")
		public String getUserList(UserVO vo, Model model) {
			model.addAttribute("userList",userService1.getUserList(vo));
			return "getUserList.jsp";
		}
		
		
		
}
