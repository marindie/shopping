package com.shopping.service;

import java.util.List;
import java.util.Map;

import com.shopping.vo.MainVO;

public interface MainService {
	public List<MainVO> selectCustomer(MainVO mainVO);
	
	public List<Map<String, Object>> selectAllCust();
}
