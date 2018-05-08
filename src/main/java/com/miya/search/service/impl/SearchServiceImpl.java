package com.miya.search.service.impl;


import com.miya.search.pojo.SearchResult;
import com.miya.search.dao.SearchDao;
import com.miya.search.service.SearchService;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 搜索服务功能实现
 */
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private SearchDao searchDao;

	@Override
	public SearchResult search(String queryString, int limit,int currPage ) throws Exception {
		//根据查询条件拼装查询对象
		//创建一个SolrQuery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(queryString);
		//设置分页条件
		if (currPage < 1) currPage =1;
		query.setStart((currPage - 1) * limit);
		if (limit < 1) limit = 10;
		query.setRows(limit);
		//设置默认搜索域
		query.set("df", "item_title");
		//设置高亮显示
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<font color='red'>");
		query.setHighlightSimplePost("</font>");
		//调用dao执行查询
		SearchResult searchResult = searchDao.search(query);
		//计算查询结果的总页数
		long recordCount = searchResult.getRecordCount();
		long pages =  recordCount / limit;
		if (recordCount % limit > 0) {
			pages++;
		}
		searchResult.setTotalPages(pages);
		//返回结果
		return searchResult;
	}

}
