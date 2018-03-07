package com.miya.service;


import com.miya.dto.RespObject;
import com.miya.entity.ItemParam;

public interface ItemParamService {

	RespObject<ItemParam> getItemParamByCid(long cid);
	RespObject<ItemParam> insertItemParam(ItemParam itemParam);
}
