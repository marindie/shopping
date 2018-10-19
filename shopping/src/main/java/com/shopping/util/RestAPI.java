package com.shopping.util;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;

public class RestAPI {
	private static final Logger logger = LoggerFactory.getLogger(RestAPI.class);
 
//	HttpPost httpPost = new HttpPost(SERVER_URL);
//	HttpEntity entity = null;
// 
//	try{
//		entity = new StringEntity(data, "UTF-8");
//	} catch (UnsupportedEncodingException e) {
//		logger.info("UnsupportedEncodingException"+e);
//		throw new ApplicationException("io.EncodingError", e);
//	}
//	
//	httpPost.setEntity(entity);
//	HttpClient httpClient = new DefaultHttpClient();
//	HttpParams httpParams = httpClient.getParams();
//	
//	HttpConnectionParams.setConnectionTimeout(httpParams, 10000);
//	HttpConnectionParams.setSoTimeout(httpParams, 10000);
// 
//	HttpResponse httpResponse = null;
//	try {
//		httpResponse = httpClient.execute(httpPost);
//		logger.info("@@@@@@@@@@@@sw9:"+soTimeout);
//	} catch (ClientProtocolException e) {
//		logger.info("@@@@@@@@@@@@sw9-1:"+e);
//		throw new ApplicationException("protocol.ProtocolError", e);
//	} catch (IOException e) {
//		logger.info("@@@@@@@@@@@@sw9-2:"+e);
//		throw new ApplicationException("io.IOError", e);
//	}
//	
//	HttpEntity responseEntity = httpResponse.getEntity();
//	
//	if (responseEntity != null) {
//		ByteArrayOutputStream bos = new ByteArrayOutputStream();
//		try {
//			//ByteArray 로 받아서 to String 으로 변환
//			responseEntity.writeTo(bos);
//			responseXml = bos.toString("UTF-8");
//			logger.info("@@@@@@@@@@@@sw12:responseXml:\n"+responseXml);
//		} catch (IOException e) {
//			throw new ApplicationException("io.IOError", e);
//		} finally {
//			try {
//				if (bos != null) {
//					bos.close();
//				}
//			} catch (IOException e) {
//				throw new ApplicationException("io.IOError", e);
//			}
//		}
//	}
}
