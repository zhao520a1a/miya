package com.miya.service;


import com.miya.pojo.EasyUITreeNode;

import java.util.List;

public interface ItemCatService {

	List<EasyUITreeNode> getCatList(long parentId);
}
