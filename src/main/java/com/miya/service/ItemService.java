package com.miya.service;


import com.golden.pojo.ResponseModal;
import com.miya.entity.Item;
import com.miya.entity.ItemDesc;
import com.miya.entity.ItemParam;
import com.springboot.ping.mybatis.vo.Page;

import java.util.List;

public interface ItemService {

    Item getItemById(long itemId);

    ResponseModal addItem(Item item, String desc, String itemParam);

    ResponseModal updateItem(Item item, String desc, String itemParam);

    ItemDesc getItemDesc(Long id);

    ItemParam getItemParam(Long itemId);

    Page<Item> getItemsByCatId(Long catId, int limit, int currPage);

    Page getItemsByPage(int limit, int currPage, int count);

    ResponseModal deleteItem(Long id);
}
