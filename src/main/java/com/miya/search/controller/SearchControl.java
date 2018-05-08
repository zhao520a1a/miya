package com.miya.search.controller;


import com.golden.pojo.ResponseModal;
import com.golden.util.StringUtil;
import com.golden.util.Util;
import com.miya.search.pojo.SearchResult;
import com.miya.search.service.SearchItemService;
import com.miya.search.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
public class SearchControl {
    @Autowired
    public SearchService searchService;
    @Autowired
    public SearchItemService searchItemService;

    /*添加索引*/
    @RequestMapping("/addItemIndex")
    public ResponseModal addItemIndex(@RequestParam(required = false) String itemIds, @RequestParam(required = false, defaultValue="false") boolean allFlag) {
        try {
            if (allFlag) {
                searchItemService.deleteItemsIndex();
                searchItemService.importItemsToIndex();
            } else {
                if (!StringUtil.isEmpty(itemIds)) {
                    List<String> itemIdList = Util.str2list(itemIds);
                    for (String itemId : itemIdList) {
                        searchItemService.importItemToIndexByItemId(Long.parseLong(itemId));
                    }
                }
            }
        } catch (Exception e) {
            log.error("统一添加索引{}", StringUtil.getTrace(e));
            return ResponseModal.errorMsg("添加索引失败");
        }
        return ResponseModal.successMsg("添加索引成功");
    }


    /*查询*/
    @RequestMapping("/search")
    public SearchResult search(@RequestParam String queryString, @RequestParam(defaultValue = "12") int limit, @RequestParam(defaultValue = "1") int currPage) {
        SearchResult searchResult;
        try {
            searchResult = searchService.search(queryString, limit, currPage);
        } catch (Exception e) {
            log.error("查询数据失败{}", StringUtil.getTrace(e));
            return null;
        }
        return searchResult;
    }


    /*删除索引*/
    @RequestMapping("/deleteItemIndex")
    public ResponseModal deleteItemIndex(@RequestParam(required = false) String itemIds, @RequestParam(required = false, defaultValue="false") boolean allFlag) {
        log.info("itemIds: {}  allCat:{}", itemIds, allFlag);
        try {
            if (allFlag) {
                searchItemService.deleteItemsIndex();
            } else {
                if (!StringUtil.isEmpty(itemIds)) {
                    List<String> itemIdList = Util.str2list(itemIds);
                    for (String itemId : itemIdList) {
                        searchItemService.deleteItemsIndex(Long.parseLong(itemId));
                    }
                }
            }
        } catch (Exception e) {
            log.error("getItemCat():{}", com.golden.util.StringUtil.getTrace(e));
            return ResponseModal.successMsg("删除索引异常");
        }
        return ResponseModal.successMsg("删除索引成功");
    }

}
