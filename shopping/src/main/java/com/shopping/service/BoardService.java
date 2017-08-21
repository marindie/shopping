package com.shopping.service;

import java.util.List;
import com.shopping.vo.BoardVO;

public interface BoardService {
	List<BoardVO> selectBoardList() throws Exception;
}
