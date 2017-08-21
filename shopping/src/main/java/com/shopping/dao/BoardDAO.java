package com.shopping.dao;

import java.util.List;

import com.shopping.main.MainController;
import com.shopping.vo.BoardVO;
import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.shopping.vo.BoardVO;

@Repository
public class BoardDAO {	
	
	
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionFactory;
	
	private static final Logger logger = LoggerFactory.getLogger(BoardDAO.class);
	
	public List<BoardVO> selectBoardList() {
		logger.info("boardDAO.selecBoardList");
		return sqlSessionFactory.selectList("boardDAO.selectBoardList",null);
	}
}
