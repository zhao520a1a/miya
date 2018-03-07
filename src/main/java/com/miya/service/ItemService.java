package com.miya.service;


import com.miya.dto.ResponseModal;
import com.miya.entity.Item;
import com.miya.entity.ItemDesc;

public interface ItemService {

	Item getItemById(long itemId);
    ResponseModal addItem(Item item, String desc, String itemParam) throws Exception;

    ItemDesc getItemDesc(Long id);
    String getItemCat(Long id);
}
