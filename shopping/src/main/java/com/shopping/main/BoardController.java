package com.shopping.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import javax.annotation.Resource;
import java.util.List;
import com.shopping.service.BoardService;
import com.shopping.vo.BoardVO;

@Controller
public class BoardController {
	
	
	@Resource(name = "BoardService") 
	private BoardService boardService;
	
	@RequestMapping(value = "/boardList")
	public String boardList(Model model) throws Exception {
		
	    List<BoardVO> list = boardService.selectBoardList();

//	    logger.info(list.toString());

	    model.addAttribute("list", list);

	    return "boardList";
	}
}
