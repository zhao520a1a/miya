package com.miya.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.miya.search.pojo.SearchItem;
import com.miya.search.pojo.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


/**
 * 查询索引库商品dao
 */
@Repository
public class SearchDao {

	@Autowired
	private SolrClient solrClient;

	public SearchResult search(SolrQuery query) throws Exception{
		//根据query对象进行查询
		QueryResponse response = solrClient.query(query);
		//取查询结果
		SolrDocumentList solrDocumentList = response.getResults();

		SearchResult result = new SearchResult();
		List<SearchItem> itemList = new ArrayList<>();
		//取查询结果总记录数
		long numFound = solrDocumentList.getNumFound();
		result.setRecordCount(numFound);
		//把查询结果封装到SearchItem对象中
		for (SolrDocument solrDocument : solrDocumentList) {
			SearchItem item = new SearchItem();
			item.setCategory_name((String) solrDocument.get("item_category_name"));
			item.setId((String) solrDocument.get("id"));
			//取一张图片
			String image = (String) solrDocument.get("item_image");
			if (StringUtils.isNotBlank(image)) {
				image = image.split(",")[0];
			}
			item.setImage(image);
			item.setPrice((long) solrDocument.get("item_price"));
			item.setSell_point((String) solrDocument.get("item_sell_point"));
			//取高亮显示
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String title = "";
			if (list != null && list.size() > 0) {
				title = list.get(0);
			} else {
				title = (String) solrDocument.get("item_title");
			}
			item.setTitle(title);
			//添加到商品列表
			itemList.add(item);
		}
//		result.setRecordCount(itemList.size());

		//把结果添加到SearchResult中
		result.setItemList(itemList);
		//返回
		return result;
	}



}
