package com.miya.service;

import com.miya.common.pojo.EUTreeNode;
import com.miya.dto.RespObject;
import com.miya.entity.ContentCategory;

import java.util.List;

public interface ContentCategoryService {

	List<EUTreeNode> getContentCategoryList(long parentId);
	RespObject<ContentCategory> addContentCategory(long parentId, String name);

}

