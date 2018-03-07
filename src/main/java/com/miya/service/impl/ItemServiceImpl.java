package com.miya.service.impl;


import com.miya.dto.RespObject;
import com.miya.dto.ResponseModal;
import com.miya.utils.DateTimeUtil;
import com.miya.dto.ItemDto;
import com.miya.entity.Item;
import com.miya.entity.ItemCat;
import com.miya.entity.ItemDesc;
import com.miya.entity.ItemParamItem;
import com.miya.entity.dao.ItemCatDao;
import com.miya.entity.dao.ItemDao;
import com.miya.entity.dao.ItemDescDao;
import com.miya.service.ItemService;
import com.springboot.ping.mybatis.extend.service.BaseCURDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 商品管理Service
 */
@Service
public class ItemServiceImpl extends BaseCURDService<Item,ItemDao> implements ItemService {

	@Autowired
	private ItemDao itemDao;
	
	@Autowired
	private ItemDescDao itemDescDao;
	@Autowired
	private ItemCatDao itemCatDao;




	@Override
	public Item getItemById(long itemId) {
		Item item = Item.builder().id(itemId).build();
		return itemDao.findByPk(item);
	}




	@Override
	public RespObject<Item> addItem(Item item, String desc, String itemParam) throws Exception {
		//item补全
		//生成商品ID
		// '商品状态，1-正常，2-下架，3-删除',
		//插入到数据库
		//添加商品描述信息
		//添加规格参数
		return new RespObject(ResponseModal.success(),null);
	}

	@Override
	public ItemDesc getItemDesc(Long itemId) {
		ItemDesc itemDesc = ItemDesc.builder().item_id(itemId).build();
		return	itemDescDao.findByPk(itemDesc);
	}

	@Override
	public String getItemCat(Long catId) {

		ItemCat itemCat = ItemCat.builder().id(catId).build();
		itemCat = itemCatDao.findByPk(itemCat);
		return itemCat.getName();
	}

	/**
	 * 添加商品描述
	 */
	private  RespObject<Item> insertItemDesc(Long itemId, String desc) {
		ItemDesc itemDesc = ItemDesc.builder().build();
		itemDesc.setItem_desc("  你好   ");
		itemDesc.setItem_id(itemId);
		itemDesc.setItem_desc(desc);
		itemDesc.setCreate_time(new Date());
		itemDesc.setUpdate_time(new Date());
		itemDescDao.insert(itemDesc);
		return new RespObject(ResponseModal.success(),null);

	}

	/**
	 * 添加规格参数
	 */
	private  RespObject<Item> insertItemParamItem(Long itemId, String itemParam) {
		//创建一个pojo
		ItemParamItem itemParamItem = new ItemParamItem();
		itemParamItem.setItem_id(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreate_time(new Date());
		itemParamItem.setUpdate_time(new Date());
		//向表中插入数据
//		itemParamItemDao.insert(itemParamItem);
		return new RespObject(ResponseModal.success(),null);

	}

}
