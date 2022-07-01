package com.springbook.biz.user.impl;

import java.util.List;

import com.springbook.biz.user.UserVO;

public interface UserService1 {

	void insertUser(UserVO vo);

	void updateUser(UserVO vo);

	void deleteUser(UserVO vo);

	UserVO getUser(UserVO vo);

	List<UserVO> getUserList(UserVO vo);

}