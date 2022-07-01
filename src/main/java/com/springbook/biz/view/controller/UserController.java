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
		//userService1은 인터페이스 이므로, 이를 구현한 객체가 주입된다.
	
	//1-1. 로그 인 : login Controller 통합 (GET 방식 처리 메소드)
		@RequestMapping(value="/login.do",method = RequestMethod.GET)
		//public String loginViewGet(UserVO vo) {
		public String loginViewGet(@ModelAttribute("user") UserVO vo) {
			System.out.println("로그인 처리 - Spring_MVC 어노테이션 호출 Controller 통합");
			vo.setId("admin");
			vo.setPass("1234");
			return "login.jsp";	//forward로 전송시 vo의 변수의 값이 잘 전달됨
			//return "redirect:login.jsp";
			
			//Command 객체에 변수의 값을 담아서 View 페이지로 전송 할 수 있다.
			//VO 객체의 클래스 이름은 UserVO, ${userCO.id} 뷰페이지에서 출력을 할 수 있다.
		/*
		 * @ModelAtrrtibute : Command 객체로 던져지는 객체 이름을 다른 이름으로 변경
		 * (UserVO => user)		
		 * view 페이지에서 출력시 : ${user.id},${user.pass}
		 * 	
		 * */	
			
			
		}
		//1-2. 로그 인 : login Controller 통합 (POST 방식 처리 메소드)
		@RequestMapping(value="/login.do",method = RequestMethod.POST)
		public String loginViewPost(UserVO vo, UserDAO userDAO,HttpSession session) {
			System.out.println("로그인 처리 - Spring_MVC 어노테이션 호출 Controller 통합");
			
			if (vo.getId() == null || vo.getId().equals("")) {
				throw new IllegalArgumentException("아이디는 반드시 입력");
			}	//vo에 id 변수의 값이 넘어오지 않으면 강제로 예외를 발생시킴 .....
			
			UserVO user = userDAO.getUser(vo);
			
			if(user != null) {
				session.setAttribute("userName", user.getName());
				return "getBoardList.do";
			}else {
				return "login.jsp";
			}
			
			
		}
		/*
		//1-3. 로그 인 : login Controller 통합 
		@RequestMapping(value="/login.do")
		public String login( UserVO vo, UserDAO userDAO) {
			System.out.println("로그인 처리 - Spring_MVC 어노테이션 호출 Controller 통합");
			
			if (userDAO.getUser(vo) != null) {
				return "redirect:getBoardList.do";
			} else {
				return "redirect:login.jsp"; 
			}
			
		}
		*/
		//2. 로그 아웃 : logout Controller 통합
		@RequestMapping(value="/logout.do")
		public String logout(HttpSession session) {
			System.out.println("로그아웃 처리 Spring MVC 어노테이션  Controller 통합");
			
			session.invalidate();
			return "redirect:login.jsp";
		}
		
		//3. 회원 등록 (insert)
		@RequestMapping(value= "/insertUser.do", method = RequestMethod.GET)
		public String insertView (UserVO vo) {
			return "insertUser.jsp";
		}
		
		//회원등록 폼에서 값을 넣고 전송 : DB에저장 <==POST
		@RequestMapping(value = "/insertUser.do", method =RequestMethod.POST)
		public String insertUser(UserVO vo) {
			
			System.out.println("회원 가입 성공 : - insertUser()메소드 호출 - Mybatis");
			userService1.insertUser(vo);
			return "login.do";
		}
		
		//4. 회원 수정 (update)
			//회원 수정 링크를 클릭 했을때
		@RequestMapping(value="/updateUser.do",method = RequestMethod.GET)
		public String updateView(UserVO vo) {
			return "updateUser.jsp";
		}
		
		//회원 수정 페이지에서 값을 넣고 전송 버튼을 눌럿을떄 : POST
		
		@RequestMapping(value="/updateUser.do",method = RequestMethod.POST)
		public String updateUser(UserVO vo) {
			userService1.updateUser(vo);
			
			return "getUserList.do";
		}
		
		//5. 회원 탈퇴
		
			//회원 탈퇴를 클릭 했을때 : get 방식
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
		
		
		//6. 회원 목록 출력
		
		@RequestMapping(value = "/getUserList.do")
		public String getUserList(UserVO vo, Model model) {
			model.addAttribute("userList",userService1.getUserList(vo));
			return "getUserList.jsp";
		}
		
		
		
}
