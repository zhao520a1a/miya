package com.miya.service;


import com.miya.entity.ItemCat;

import java.util.List;

public interface ItemCatService {

    List<ItemCat> getItemCatByParentId(Long parentId);

    String getItemCatNameById(Long cid);

    List<ItemCat> getItemCatByIds(String ids);

    ItemCat getItemCatById(Long id);

}
