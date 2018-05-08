package com.miya.search.dao;


import com.miya.search.pojo.SearchItem;
import com.springboot.ping.mybatis.extend.dao.BaseCURDDao;

import java.util.List;

public interface SearchItemDao  extends BaseCURDDao<SearchItem> {

	List<SearchItem> getItemList();
	SearchItem getItemById(long itemId);
}
