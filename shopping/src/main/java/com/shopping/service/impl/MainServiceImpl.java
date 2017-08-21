package com.shopping.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.dao.MainDAO;
import com.shopping.service.MainService;
import com.shopping.vo.MainVO;

@Service("mainService")
public class MainServiceImpl implements MainService {
	
	@Autowired
	private MainDAO mainDao;
	
	@Override
	public List<MainVO> selectCustomer(MainVO mainVO){
		return mainDao.selectCustomer(mainVO);
	}
	
	@Override
	public List<Map<String, Object>> selectAllCust(){
		return mainDao.selectAllCust();
	}
}
