package com.miya.controller;


import com.alibaba.fastjson.JSONObject;
import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.golden.util.StringUtil;
import com.miya.dto.ContentDto;
import com.miya.entity.Content;
import com.miya.entity.ContentCat;
import com.miya.service.impl.ContentCatServiceImpl;
import com.miya.service.impl.ContentServiceImpl;
import com.springboot.ping.mybatis.vo.Condition;
import com.springboot.ping.mybatis.vo.Page;
import com.springboot.ping.mybatis.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/contents")
public class ContentsController {

    @Autowired
    ContentServiceImpl contentService;

    @Autowired
    ContentCatServiceImpl contentCatService;



    /* 获得内容的总数量 */
    @RequestMapping(value = "/count", method = {RequestMethod.POST, RequestMethod.GET})
    public RespObject<Integer> getContentCount() {
        int sumNum = 0;
        try {
            Condition condition = null;
            sumNum = (int) contentService.count(condition);
            log.info("contentCount: {}", sumNum);
        } catch (Exception e) {
            log.info("{}", StringUtil.getTrace(e));
            return new RespObject<Integer>(ResponseModal.errorMsg("获取内容数量异常"), -1);
        }
        return new RespObject<Integer>(ResponseModal.successMsg("获取内容数量成功"), sumNum);
    }


    /* 分页获得内容列表 */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public RespObject<JSONObject> getContentsList(@RequestParam int limit, @RequestParam int currPage, @RequestParam int count) {
        log.info("分页limit:{}, currPage:{}, count:{}, httpSession:{}", limit, currPage, count);
        JSONObject contentData = new JSONObject();
        Page page = null;
        try {
            page =  contentService.getContentsByPage(limit, currPage, count);

            List<Content> contentList = page.getRows();
            List<ContentDto> contentDtos = Lists.newArrayList();
            ContentDto contentDto = ContentDto.builder().build();
            for (Content content : contentList) {
                contentDto = contentDto.converFor(content);
                Long catId = content.getCategory_id();
                ContentCat contentCat = new ContentCat();
                contentCat.setId(catId);
                contentCat = contentCatService.findByPk(contentCat);
                contentDto.setCategory_name(contentCat.getName());
                contentDtos.add(contentDto);
            }
            page.setRows(contentDtos);
            contentData.put("page", page);
        } catch (Exception e) {
            log.info("{}", StringUtil.getTrace(e));
            return new RespObject<>(ResponseModal.errorMsg("获取内容列表异常"), null);
        }
        return new RespObject<>(ResponseModal.successMsg("获取内容列表成功"), contentData);
    }


}
