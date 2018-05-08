package com.miya.service;


import com.golden.pojo.RespObject;
import com.miya.entity.ItemParam;

public interface ItemParamService {

	RespObject<ItemParam> getItemParamByCid(long cid);
	RespObject<ItemParam> insertItemParam(ItemParam itemParam);
}
