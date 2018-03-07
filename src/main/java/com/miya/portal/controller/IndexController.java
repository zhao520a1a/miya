package com.miya.portal.controller;


import com.miya.portal.dto.RespList;
import com.miya.portal.dto.ResponseModal;
import com.miya.portal.feign.ContentService;
import com.miya.portal.pojo.AD1Node;
import com.miya.portal.dto.ContentDto;
import com.miya.portal.utils.JsonUtils;
import com.miya.portal.utils.StringUtil;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页展示Controller
 */
@Log4j2
@Controller
public class IndexController {

    @Value("${AD1_CATEGORY_ID}")
    private Long AD1_CATEGORY_ID;
    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;
    @Value("${AD1_WIDTH_B}")
    private Integer AD1_WIDTH_B;
    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;
    @Value("${AD1_HEIGHT_B}")
    private Integer AD1_HEIGHT_B;

    @Autowired
    private ContentService contentService;

    @RequestMapping("/index")
    public String showIndex(Model model) {

        //根据cid查询轮播图内容列表
        RespList<ContentDto> respList;
        //把列表转换为Ad1Node列表
        List<AD1Node> ad1Nodes = new ArrayList<>();
        String ad1Json = new String();
        try {
            //根据cid查询轮播图内容列表
            respList = contentService.getContentByCid(AD1_CATEGORY_ID);
            Integer rtnKey = respList.getResponseModal().getCode();
            if ( rtnKey.equals(ResponseModal.RespCode.OK.getKey()))  {
                List<ContentDto> contentDtos = respList.getData();

                for (ContentDto contentDto : contentDtos) {
                    AD1Node node = new AD1Node();
                    node.setAlt(contentDto.getTitle());
                    node.setHeight(AD1_HEIGHT);
                    node.setHeightB(AD1_HEIGHT_B);
                    node.setWidth(AD1_WIDTH);
                    node.setWidthB(AD1_WIDTH_B);
                    node.setSrc(contentDto.getPic1_path());
                    node.setSrcB(contentDto.getPic2_path());
                    node.setHref(contentDto.getUrl());
                    //添加到节点列表
                    ad1Nodes.add(node);
                }

                //把列表转换成json数据
                ad1Json = JsonUtils.objectToJson(ad1Nodes);
                log.info("广告图片数据：{}" , ad1Json);
            }

        } catch (Exception e) {
            log.error("统一添加索引{}", StringUtil.getTrace(e));
        }
        //把json数据传递给页面
        model.addAttribute("ad1", ad1Json);
        return "index";
    }
}
