package com.miya.service;


import com.miya.dto.RespObject;
import com.miya.entity.Item;
import com.miya.entity.ItemDesc;

public interface ItemService {

	Item getItemById(long itemId);
    RespObject<Item> addItem(Item item, String desc, String itemParam) throws Exception;

    ItemDesc getItemDesc(Long id);
    String getItemCat(Long id);
}
