package com.shopping.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.dao.MainDAO;
import com.shopping.dao.TestDAO;
import com.shopping.service.MainService;
import com.shopping.service.TestService;
import com.shopping.vo.MainVO;

@Service("testService")
public class TestServiceImpl implements TestService {
	
	@Autowired
	private TestDAO testDao;

	@Override
	public List<Map<String, Object>> returnbyMapSample() {
		// TODO Auto-generated method stub
		testDao.returnByMapSample();
		return null;
	}
	
//	@Override
//	public List<MainVO> selectCustomer(MainVO mainVO){
//		return mainDao.selectCustomer(mainVO);
//	}
}
