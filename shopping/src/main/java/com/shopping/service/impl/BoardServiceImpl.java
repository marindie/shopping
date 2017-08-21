package com.shopping.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 
import org.springframework.transaction.annotation.Transactional;
import com.shopping.service.BoardService;
import com.shopping.dao.BoardDAO;
import com.shopping.vo.BoardVO;

@Service("BoardService")
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardDAO boardDAO;
	
	@Override
	@Transactional
	public List<BoardVO> selectBoardList() throws Exception {
		// TODO Auto-generated method stub
		return boardDAO.selectBoardList();
	}

}
