package com.shopping.dao;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.shopping.vo.MainVO;

@Repository("testDao")
public class TestDAO {

	private static final Logger logger = LoggerFactory.getLogger(TestDAO.class);
	
	@Resource(name = "sqlSessionTemplate")
	private SqlSessionTemplate sqlSessionFactory;
	
//	public List<MainVO> selectCustomer(MainVO mainVO){
//		logger.info("TestDao.selectCustomer");
//		return sqlSessionFactory.selectList("test.selectCustomer",mainVO);
//	}

	public List<Map<String, Object>> returnByMapSample(){
		logger.info("TestDao.returnByMapSample");
		return sqlSessionFactory.selectList("test.returnByMapSample");
	}
}
