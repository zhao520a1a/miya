package com.miya.search.service.impl;


import com.miya.search.pojo.SearchItem;
import com.miya.search.dao.SearchItemDao;
import com.miya.search.service.SearchItemService;
import com.springboot.ping.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.util.NamedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 商品数据导入索引库
 */
@Log4j2
@Service
public class SearchItemServiceImpl implements SearchItemService {

    @Autowired
    private SearchItemDao searchItemDao;

    // 定义http的solr服务
    @Autowired
    private SolrClient solrClient;

    @Override
    public void importItemsToIndex() throws IOException, SolrServerException {
        //1、先查询所有商品数据
        List<SearchItem> itemList = searchItemDao.getItemList();
        //2、遍历商品数据添加到索引库
        for (SearchItem searchItem : itemList) {
            //创建文档对象
            SolrInputDocument document = new SolrInputDocument();
            //向文档中添加域
            document.addField("id", searchItem.getId());
            document.addField("item_title", searchItem.getTitle());
            document.addField("item_sell_point", searchItem.getSell_point());
            document.addField("item_price", searchItem.getPrice());
            document.addField("item_image", searchItem.getImage());
            document.addField("item_category_name", searchItem.getCategory_name());
            document.addField("item_desc", searchItem.getItem_desc());
            //把文档写入索引库
            solrClient.add(document);
        }
        //3、提交
        solrClient.commit();
    }


    @Override
    public void importItemToIndexByItemId(Long itemId) throws IOException, SolrServerException {
        //先查询商品数据
        SearchItem searchItem = searchItemDao.getItemById(itemId);
        //创建文档对象
        SolrInputDocument document = new SolrInputDocument();
        //向文档中添加域
        document.addField("id", searchItem.getId());
        document.addField("item_title", StringUtil.isEmpty(searchItem.getTitle(),"WW"));
        document.addField("item_sell_point", StringUtil.isEmpty(searchItem.getSell_point(),"WW"));
        document.addField("item_price", StringUtil.isEmpty(searchItem.getPrice().toString(),"WW"));
        document.addField("item_image", StringUtil.isEmpty(searchItem.getImage(),"WW"));
        document.addField("item_category_name", StringUtil.isEmpty(searchItem.getCategory_name(), "WW"));
        document.addField("item_desc", StringUtil.isEmpty(searchItem.getItem_desc(), "WW"));
        //把文档写入索引库
        solrClient.add(document);
        //提交
        UpdateResponse updateResponse = solrClient.commit();
        log.info("{}",updateResponse);


        // 测试验证 如下
//        SolrQuery query = new SolrQuery();
//        //设置查询条件
//        query.setQuery( searchItem.getTitle());
//        //设置分页条件
////        if (page < 1) page =1;
////        query.setStart((page - 1) * rows);
////        if (rows < 1) rows = 10;
////        query.setRows(rows);
//        query.setStart(1);
//        query.setRows(10);
//        //设置默认搜索域
//        query.set("df", "item_title");
//        //设置高亮显示
//        query.setHighlight(true);
//        query.addHighlightField("item_title");
//        query.setHighlightSimplePre("<font color='red'>");
//        query.setHighlightSimplePost("</font>");
//        QueryResponse response = solrClient.query(query);
//        //取查询结果
//        SolrDocumentList solrDocumentList = response.getResults();
    }

    @Override
    public void deleteItemsIndex(Long itemId) throws IOException, SolrServerException {
        solrClient.deleteById(itemId.toString());
        solrClient.commit();
    }
    @Override
    public void deleteItemsIndex() throws IOException, SolrServerException {
        solrClient.deleteByQuery("*:*");
        solrClient.commit();
    }





}
