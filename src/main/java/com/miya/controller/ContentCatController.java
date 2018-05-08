package com.miya.controller;


import com.golden.pojo.RespList;
import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.golden.util.StringUtil;
import com.google.common.collect.Lists;
import com.miya.dto.ContentCatDto;
import com.miya.entity.ContentCat;
import com.miya.service.impl.ContentCatServiceImpl;
import com.miya.service.impl.ContentServiceImpl;
import com.springboot.ping.mybatis.enums.DbType;
import com.springboot.ping.mybatis.enums.Operator;
import com.springboot.ping.mybatis.vo.Condition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/contentCats")
public class ContentCatController {

    @Autowired
    ContentCatServiceImpl contentCatService;

    @Autowired
    ContentServiceImpl contentService;

    @GetMapping(value = "/count")
    public RespObject<Integer> getContentCount() {
        int sumNum = 0;
        try {
            Condition condition = null;
            sumNum = (int) contentCatService.count(condition);
            log.info("contentCount: {}", sumNum);
        } catch (Exception e) {
            log.error(StringUtil.getTrace(e));
            return new RespObject<Integer>(ResponseModal.successMsg("获取用户数量失败"),-1 ) ;
        }
        return new RespObject<Integer>(ResponseModal.successMsg("获取用户数量成功"),sumNum ) ;
    }


    @RequestMapping(value = "/info")
    public RespList<ContentCat> getContentCat(@RequestParam String contentCatIds, @RequestParam boolean allCat) {
        log.info("contentCatIds: {}  allCat:{}",contentCatIds,allCat);
        List<ContentCat> contentCatList = Lists.newArrayList();
        try {
            if(allCat) {
                Condition condition = new Condition("is_parent", DbType.INT, Operator.EQ,0);
                contentCatList =  contentCatService.find(condition);
                for(ContentCat contentCat : contentCatList){
                    Long parentId = contentCat.getParent_id();

                    ContentCat cat = ContentCat.builder().id(parentId).build();
                    cat = contentCatService.findByPk(cat);
                    if(cat != null) {
                        contentCat.setName(cat.getName() + "--" + contentCat.getName());
                    }
                }

            } else {
                if (!StringUtil.isEmpty(contentCatIds)) {
                    contentCatList = contentCatService.getContentCatByIds(contentCatIds);
                }
            }
        } catch (Exception e) {
            log.error(StringUtil.getTrace(e));
            return new RespList<>(ResponseModal.successMsg("获取内容类别异常"),null ) ;
        }
        return new RespList<>(ResponseModal.successMsg("获取内容类别成功"),contentCatList ) ;
    }


    @RequestMapping(value = "/list/parentId/{parentId}")
    RespList<ContentCatDto> getContentCatList(@PathVariable(value = "parentId") Long parentId){
        List<ContentCatDto> contentCatDtoList = Lists.newArrayList();
        try {
            List<ContentCat> contentCatList = contentCatService.getContentCatByParentId(parentId);
            for(ContentCat contentCat: contentCatList) {
                ContentCatDto contentCatDto = new ContentCatDto().converFor(contentCat);
                contentCatDtoList.add(contentCatDto);
            }
        } catch (Exception e) {
            log.error(StringUtil.getTrace(e));
            return new RespList<>(ResponseModal.successMsg("获取内容类别异常"),null ) ;
        }
        return new RespList<>(ResponseModal.successMsg("获取内容类别成功"),contentCatDtoList ) ;
    }









}
