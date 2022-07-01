package com.springbook.biz.user.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.springbook.biz.user.UserVO;

//비즈니스 로직을 처리하는 서비스 클래스 : @Service

@Service("userService")
public class UserService1impl implements UserService1 {

	@Autowired		//유지 보수를 쉽게 처리하도록 설정
	private UserDAOMybatis userDAO;
	
	@Override
	public void insertUser(UserVO vo) {
		userDAO.insertUser(vo);
		
	}

	@Override
	public void updateUser(UserVO vo) {
		userDAO.updateUser(vo);
		
	}

	@Override
	public void deleteUser(UserVO vo) {
		userDAO.deleteUser(vo);
		
	}

	@Override
	public UserVO getUser(UserVO vo) {
		return userDAO.getUser(vo);
	}

	@Override
	public List<UserVO> getUserList(UserVO vo) {
		return userDAO.getUserList(vo);
	}

}
