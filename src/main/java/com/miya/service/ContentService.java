package com.miya.service;


import com.golden.pojo.ResponseModal;
import com.miya.entity.Content;
import com.springboot.ping.mybatis.vo.Page;

import java.util.List;

public interface ContentService {

	ResponseModal addContent(Content content);

	ResponseModal updateContent(Content content);

	List<Content> getContentByCid(long cid);

	Page getContentsByPage(int limit, int currPage, int count);
}
