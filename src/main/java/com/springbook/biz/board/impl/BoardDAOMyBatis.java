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
		System.err.println("MyBatis�� ����ؼ� insertboard()����");
		mybatis.insert("BoardDAO.insertBoard",vo);
		//mybatis.commit();
	}
	
	public void updateBoard(BoardVO vo) {
		System.err.println("MyBatis�� ����ؼ� updateboard()����");
		mybatis.update("BoardDAO.updateBoard",vo);
		//mybatis.commit();
	}
	
	public void deleteBoard(BoardVO vo) {
		System.err.println("MyBatis�� ����ؼ� deleteboard()����");
		mybatis.delete("BoardDAO.deleteBoard",vo);
		//mybatis.commit();
	}
	
	public BoardVO getBoard(BoardVO vo) {
		System.err.println("MyBatis�� ����ؼ� getboard()����");
		return (BoardVO)mybatis.selectOne("BoardDAO.getBoard",vo);
	}
	
	public List<BoardVO> getBoardList(BoardVO vo){
		System.err.println("MyBatis�� ����ؼ� getboardList()����");
		return mybatis.selectList("BoardDAO.getBoardList",vo);
	}
}
