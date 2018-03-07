package com.miya.service.impl;

import com.miya.entity.dao.ItemParamItemDao;
import com.miya.entity.ItemParamItem;
import com.miya.service.ItemParamItemService;
import com.springboot.ping.mybatis.extend.service.BaseCURDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 展示商品规格参数
 */
@Service
public class ItemParamItemServiceImpl extends BaseCURDService<ItemParamItem,ItemParamItemDao> implements ItemParamItemService {

	@Autowired
	private ItemParamItemDao itemParamItemDao;
	
	@Override
	public String getItemParamByItemId(Long itemId) {
		//根据商品id查询规格参数
		//执行查询
		//取规格参数信息
		//生成html
		// 把规格参数json数据转换成java对象
		return null;
	}

}
