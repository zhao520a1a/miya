package com.miya.service;


import com.miya.dto.RespObject;
import com.miya.entity.ContentCategory;
import com.miya.pojo.EasyUITreeNode;

import java.util.List;

public interface ContentCategoryService {

	List<EasyUITreeNode> getCategoryList(long parentId);
	RespObject<ContentCategory> insertContentCategory(long parentId, String name);
}
