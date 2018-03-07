package com.miya.service.impl;


import com.miya.dto.RespObject;
import com.miya.dto.ResponseModal;
import com.miya.utils.HttpClientUtil;
import com.miya.entity.Content;
import com.miya.entity.dao.ContentDao;
import com.miya.service.ContentService;
import com.springboot.ping.mybatis.extend.service.BaseCURDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 内容管理
 */
@Service
public class ContentServiceImpl  extends BaseCURDService<Content,ContentDao>  implements ContentService {

	@Autowired
	private ContentDao contentDao;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_SYNC_URL}")
	private String REST_CONTENT_SYNC_URL;

	@Override
	public RespObject<Content> insertContent(Content content) {
		//补全pojo内容
		content.setCreate_time(new Date());
		content.setUpdate_time(new Date());
		contentDao.insert(content);

		//添加缓存同步逻辑
		try {
			HttpClientUtil.doGet(REST_BASE_URL + REST_CONTENT_SYNC_URL + content.getCategory_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new  RespObject<Content>(ResponseModal.success(),null);
	}




}
