package com.miya.service;



import com.miya.common.pojo.EUTreeNode;

import java.util.List;

public interface ItemCatService {

	List<EUTreeNode> getCatList(long parentId);
}
