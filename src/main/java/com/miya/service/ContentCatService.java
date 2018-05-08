package com.miya.service;

import com.golden.pojo.RespObject;
import com.miya.common.pojo.EUTreeNode;
import com.miya.entity.ContentCat;

import java.util.List;

public interface ContentCatService {

	List<EUTreeNode> getContentCatList(long parentId);
	RespObject<ContentCat> addContentCat(long parentId, String name);

	List<ContentCat> getContentCatByIds(String contentCatIds);

	List<ContentCat> getContentCatByParentId(Long parentId);
}

