package com.shopping.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.shopping.vo.MainVO;

@Repository("mainDao")
public class MainDAO {

	private static final Logger logger = LoggerFactory.getLogger(MainDAO.class);
	
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionFactory;
	
	public List<MainVO> selectCustomer(MainVO mainVO){
		logger.info("MainDao.selectCustomer");
		return sqlSessionFactory.selectList("mainDao.selectCustomer",mainVO);
	}

	public List<Map<String, Object>> selectAllCust(){
		logger.info("MainDao.selectAllCust");
		return sqlSessionFactory.selectList("mainDao.selectAllCust");
	}
}
