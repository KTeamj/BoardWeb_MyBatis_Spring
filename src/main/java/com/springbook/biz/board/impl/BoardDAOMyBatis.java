package com.springbook.biz.board.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.springbook.biz.board.BoardVO;

@Repository
public class BoardDAOMyBatis {

	@Autowired
	private SqlSessionTemplate mybatis;
	
	
	public void insertBoard(BoardVO vo) {
		System.err.println("MyBatis를 사용해서 insertboard()수행");
		mybatis.insert("BoardDAO.insertBoard",vo);
		//mybatis.commit();
	}
	
	public void updateBoard(BoardVO vo) {
		System.err.println("MyBatis를 사용해서 updateboard()수행");
		mybatis.update("BoardDAO.updateBoard",vo);
		//mybatis.commit();
	}
	
	public void deleteBoard(BoardVO vo) {
		System.err.println("MyBatis를 사용해서 deleteboard()수행");
		mybatis.delete("BoardDAO.deleteBoard",vo);
		//mybatis.commit();
	}
	
	public BoardVO getBoard(BoardVO vo) {
		System.err.println("MyBatis를 사용해서 getboard()수행");
		return (BoardVO)mybatis.selectOne("BoardDAO.getBoard",vo);
	}
	
	public List<BoardVO> getBoardList(BoardVO vo){
		System.err.println("MyBatis를 사용해서 getboardList()수행");
		return mybatis.selectList("BoardDAO.getBoardList",vo);
	}
}
