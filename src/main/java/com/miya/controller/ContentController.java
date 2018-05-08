package com.miya.controller;


import com.golden.pojo.RespList;
import com.golden.pojo.ResponseModal;
import com.golden.util.StringUtil;
import com.google.common.collect.Lists;
import com.miya.dto.ContentDto;
import com.miya.entity.Content;
import com.miya.service.impl.ContentServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/content")
public class ContentController {

    @Autowired
    ContentServiceImpl contentService;

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
            return new RespList<ContentDto>().failMsgAndData("获得内容异常" + StringUtil.getTrace(e), null);
        }
        log.info("获得数据：{}", contentDtos.toString());
        return new RespList<ContentDto>().successData(contentDtos);
    }


    @RequestMapping(value = "/delete/{id}")
    public ResponseModal deleteContent(@PathVariable(value = "id") String id) {
        log.info("删除ContentId：" + id);
        try {
            if (!StringUtil.isEmpty(id)) {
                Content content = Content.builder().id(Long.parseLong(id)).build();
                contentService.deleteByPk(content);
            } else {
                return ResponseModal.failMsg("无法获得内容信息");
            }
        } catch (Exception e) {
            log.info("{}", StringUtil.getTrace(e));
            return ResponseModal.errorMsg("删除内容记录异常");
        }
        return ResponseModal.successMsg("删除内容记录成功");
    }


    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ResponseModal updateContent(@RequestBody ContentDto contentInputDto) {
        log.info("ContentDto:{}", contentInputDto.toString());
        try {
            Content content = contentInputDto.converToContent();
            return contentService.updateContent(content);
        } catch (Exception e) {
            log.error("添加内容异常:{}", com.golden.util.StringUtil.getTrace(e));
            return ResponseModal.failMsg("添加内容异常");
        }
    }

    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public ResponseModal addContent(@RequestBody ContentDto contentInputDto) {
        log.info("ContentDto:{}", contentInputDto.toString());
        try {
            Content content = contentInputDto.converToContent();
            return contentService.addContent(content);
        } catch (Exception e) {
            log.error("添加内容异常:{}", com.golden.util.StringUtil.getTrace(e));
            return ResponseModal.failMsg("添加内容异常");
        }
    }


}
