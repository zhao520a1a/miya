package com.miya.service.impl;


import com.golden.util.Util;
import com.google.common.collect.Lists;
import com.miya.entity.ItemCat;
import com.miya.entity.dao.ItemCatDao;
import com.miya.service.ItemCatService;
import com.springboot.ping.mybatis.enums.DbType;
import com.springboot.ping.mybatis.enums.Operator;
import com.springboot.ping.mybatis.extend.service.BaseCURDService;
import com.springboot.ping.mybatis.vo.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品分类管理
 */
@Service
public class ItemCatServiceImpl extends BaseCURDService<ItemCat,ItemCatDao>  implements ItemCatService {

	@Autowired
	private ItemCatDao itemCatDao;

	@Override
	public List<ItemCat> getItemCatByParentId(Long parentId) {
		ItemCat itemCat = ItemCat.builder().parent_id(parentId).build();

		List<Condition> conditions = Lists.newArrayList();
		Condition condition = new Condition("parent_id", DbType.INT, Operator.EQ,parentId);
		conditions.add(condition);
		List<ItemCat> itemCatList = itemCatDao.find(null, conditions, null);
		return itemCatList;
	}


	@Override
	public String getItemCatNameById(Long cid) {
		ItemCat itemCat = itemCatDao.findByPk(ItemCat.builder().id(cid).build());
		if(itemCat != null) {
			return itemCat.getName();
		}
		return "无";
	}

	@Override
	public List<ItemCat> getItemCatByIds(String ids) {
		List<ItemCat> itemCatList = Lists.newArrayList();
		List<Long> idList = Util.str2LongList(ids);
		for(Long id : idList) {
			itemCatList.add(getItemCatById(id));
		}
		return itemCatList;
	}

	@Override
	public ItemCat getItemCatById(Long id) {
			ItemCat itemCat = ItemCat.builder().id(id).build();
			return super.findByPk(itemCat);
	}

}
