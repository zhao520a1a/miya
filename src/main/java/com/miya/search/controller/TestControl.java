package com.miya.search.controller;

import com.golden.pojo.ResponseModal;
import com.miya.search.service.SearchItemService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
public class TestControl {


    @Autowired
    private SolrClient solrClient;

    @Autowired
    private SearchItemService searchItemService;

    @RequestMapping("/add")
    public ResponseModal addDocument() throws IOException, SolrServerException {
        try {
            //创建文档对象
            SolrInputDocument document = new SolrInputDocument();
            //向文档中添加域
            document.addField("id", "test111");
            document.addField("item_title", "测试商品111");
            document.addField("item_price", 1000);
            //把文档写入索引库
            solrClient.add(document);
            //3、提交
            solrClient.commit();
        } catch (Exception e) {
            return ResponseModal.errorMsg("数据导入失败");
        }
        return ResponseModal.success();

    }

    @RequestMapping("/delete")
    public ResponseModal deleteDocumentById(@RequestParam String id) throws IOException, SolrServerException {
        try {
            solrClient.deleteById(id);
            solrClient.commit();
        } catch (Exception e) {
            return ResponseModal.errorMsg("数据导入失败");
        }
        return ResponseModal.success();
    }

    @RequestMapping("/update")
    public ResponseModal deleteDocumentByQuery() throws IOException, SolrServerException {
        try {
            solrClient.deleteByQuery("*:*");
        } catch (Exception e) {
            return ResponseModal.errorMsg("数据导入失败");
        }
        return ResponseModal.success();
    }

    @RequestMapping("/importItem/{id}")
    public ResponseModal deleteDocumentByQuery(@PathVariable Long id) throws IOException, SolrServerException {
        try {
            searchItemService.importItemToIndexByItemId(id);
        } catch (Exception e) {
            return ResponseModal.errorMsg("数据导入失败");
        }
        return ResponseModal.success();
    }





}
