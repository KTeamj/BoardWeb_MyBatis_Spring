package com.springbook.biz.user.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springbook.biz.user.UserVO;

@Repository
public class UserDAOMybatis {

	@Autowired
	private SqlSessionTemplate mybatis; //mybatis : 1.db,  2.mapper

	public void insertUser(UserVO vo) {
		System.out.println("mybatis insertuser");
		mybatis.insert("UserDAO.insertUser",vo);
		
		System.out.println(vo.getId());
	}
	
	public void updateUser(UserVO vo) {
		System.out.println("mybatis updateUser");
		mybatis.update("UserDAO.updateUser",vo);
	}
	
	public void deleteUser(UserVO vo) {
		System.out.println("mybatis deleteUser");
		mybatis.delete("UserDAO.deleteUser",vo);
	}
	
	public UserVO getUser(UserVO vo) {
		System.out.println("mybatis getUser");
		return (UserVO)mybatis.selectOne("UserDAO.getUser",vo);
	}

	public List<UserVO> getUserList(UserVO vo){
		System.out.println("mybatis getUserList");
		return mybatis.selectList("UserDAO.getUserList",vo);
	}
	
}
