package com.miya.controller;


import com.google.common.collect.Lists;
import com.miya.dto.ContentDto;
import com.miya.dto.RespList;
import com.miya.entity.Content;
import com.miya.service.ContentService;
import com.springboot.ping.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
public class ContentController {

    @Autowired
    ContentService contentService;

    @RequestMapping("/getContent")
    RespList<ContentDto> getContentByCid(@RequestParam("cid") long cid) {
        List<ContentDto> contentDtos = Lists.newArrayList();
        try {
            List<Content> contents = contentService.getContentByCid(cid);
            for (Content content : contents) {
                ContentDto contentDto = new ContentDto();
                contentDto = contentDto.converFor(content);
                contentDtos.add(contentDto);
            }
        } catch (Exception e) {
            log.error("获得内容异常：{}", StringUtil.getTrace(e));
            return  new RespList<ContentDto>().failMsgAndData("获得内容异常"+ StringUtil.getTrace(e) ,null);
        }
        log.info("获得数据：{}",contentDtos.toString());
        return new RespList<ContentDto>().successData(contentDtos);
    }


}
