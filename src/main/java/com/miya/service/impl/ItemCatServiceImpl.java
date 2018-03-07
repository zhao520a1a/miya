package com.miya.service.impl;


import com.miya.entity.dao.ItemCatDao;
import com.miya.entity.ItemCat;
import com.miya.pojo.EasyUITreeNode;
import com.miya.service.ItemCatService;
import com.springboot.ping.mybatis.extend.service.BaseCURDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品分类管理
 */
@Service
public class ItemCatServiceImpl extends BaseCURDService<ItemCat,ItemCatDao> implements ItemCatService {

	@Autowired
	private ItemCatDao itemCatDao;
	@Override
	public List<EasyUITreeNode> getCatList(long parentId) {
		//创建查询条件
		//根据条件查询
		//把列表转换成treeNodelist
		//返回结果
		return null;
	}

}
