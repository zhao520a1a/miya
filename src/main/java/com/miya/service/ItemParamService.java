package com.miya.service;


import com.miya.dto.ResponseModal;
import com.miya.entity.ItemParam;

public interface ItemParamService {

	ResponseModal getItemParamByCid(long cid);
	ResponseModal insertItemParam(ItemParam itemParam);
}
