package com.miya.item.controller;

import com.golden.pojo.RespCode;
import com.golden.pojo.RespList;
import com.golden.util.JsonUtils;
import com.golden.util.StringUtil;
import com.google.common.collect.Maps;
import com.miya.item.dto.*;
import com.miya.item.feign.ContentService;
import com.miya.item.feign.ItemService;
import com.miya.item.pojo.Ad1Node;
import com.miya.item.pojo.ItemParamNode;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Log4j2
@Controller
public class ItemWebController {


    @Value("${AD1_CATEGORY_ID}")
    private Long AD1_CATEGORY_ID;
    @Value("${AD1_WIDTH}")
    private Integer AD1_WIDTH;
    @Value("${AD1_HEIGHT}")
    private Integer AD1_HEIGHT;

    @Value("${ITEMLIST_RESULT_ROWS}")
    private Integer ITEMLIST_RESULT_ROWS;

    @Autowired
    ItemService itemService;

    @Autowired
    ContentService contentService;


    @RequestMapping("/item/{itemId}.html")
    public String showItem(@PathVariable String itemId, Model model) {
        try {
            if (!StringUtil.isEmpty(itemId)) {
                Long id = Long.parseLong(itemId);
                //取商品基本信息
                ItemDto itemDto = itemService.getItemById(id).getData();
                if (itemDto == null) {
                    model.addAttribute("message", "商品请假回家了，请稍后！");
                    return "error/exception";
                }
                //把数据传递给页面
                model.addAttribute("item", itemDto);
            }
        } catch (Exception e) {
            log.info("{}", StringUtil.getTrace(e));
            model.addAttribute("message", "Oh my God,出错了。请稍后重试！");
            return "error/exception";
        }
        return "item";
    }


    @RequestMapping("/item/ad")
    public String showItemInfoAd(Model model) {
        try {
            List<Ad1Node> ad1List = this.getAdContent(AD1_CATEGORY_ID, AD1_WIDTH, AD1_HEIGHT);
            model.addAttribute("ad1List", ad1List);
        } catch (Exception e) {
            log.info("showItemAd():{}", StringUtil.getTrace(e));
            return "";
        }
        return "itemInfoAd";
    }


    @RequestMapping("/item/param/{itemId}.html")
    public String showItemParam(@PathVariable String itemId, Model model) {
        try {
            if (!StringUtil.isEmpty(itemId)) {
                Long id = Long.parseLong(itemId);
                //商品规格
                ItemParamDto itemParamDto = itemService.getItemParam(id).getData();
                //把数据传递给页面

                String jsonData = itemParamDto.getParam_data();

                List<ItemParamNode> itemParamNodes = JsonUtils.jsonToList(jsonData, ItemParamNode.class);

                Map<String, Map<String, String>> itemParamMap = Maps.newHashMap();

                for (ItemParamNode node1 : itemParamNodes) {
                    Map<String, String> itemParam = Maps.newHashMap();
                    if (!itemParamMap.containsKey(node1.getGroup())) {
                        for (ItemParamNode node2 : itemParamNodes) {
                            if (node1.getGroup().equals(node2.getGroup())) {
                                itemParam.put(node2.getKey(), node2.getValue());
                            }
                        }
                        itemParamMap.put(node1.getGroup(), itemParam);
                    }
                }

                model.addAttribute("itemParamMap", itemParamMap);

            }
        } catch (Exception e) {
            log.info("showItemParam:{}", StringUtil.getTrace(e));
            return " ";
        }
        return "itemParam";
    }


    @RequestMapping("/item/desc/{itemId}.html")
    @ResponseBody
    public String showItemDesc(@PathVariable String itemId, Model model) {
        try {
            if (!StringUtil.isEmpty(itemId)) {
                Long id = Long.parseLong(itemId);
                //取商品详情
                ItemDescDto itemDescDto = itemService.getItemDesc(id).getData();
                //把数据传递给页面
                model.addAttribute("itemDesc", itemDescDto);
                return itemDescDto.getItem_desc();
            }
        } catch (Exception e) {
            log.info("showItemDesc():{}", StringUtil.getTrace(e));
            return "";
        }
        return " ";
    }


    @RequestMapping("/products/{itemCatId}.html")
    public String showItemsByCatId(@PathVariable(value = "itemCatId") Long itemCatId, Model model, @RequestParam(defaultValue = "1") int currPage) throws Exception {
        try {
            //调用服务执行查询
            Page page = itemService.getItemsByCat(itemCatId, ITEMLIST_RESULT_ROWS, currPage).getData();

            if (page != null) {
                List<Map> itemList = page.getRows();
                model.addAttribute("totalPages", page.getTotalPages());
                model.addAttribute("itemList", itemList);
                model.addAttribute("page", page);
            } else {
                model.addAttribute("totalPages", 0);
                model.addAttribute("itemList", Lists.newArrayList());
                model.addAttribute("page", new Page());
            }
            model.addAttribute("itemCatId", itemCatId);
        } catch (Exception e) {
            log.error("查询数据{}", StringUtil.getTrace(e));
            model.addAttribute("message", "Oh my God,出错了。请稍后重试！");
            return "error/exception";
        }
        //返回逻辑视图
        return "itemlist";
    }

    @RequestMapping("/exception")
    public String test() {
        return "error/exception";
    }


    private List<Ad1Node> getAdContent(Long adCatId, Integer width, Integer height) {
        RespList<ContentDto> respList;
        List<Ad1Node> linkNodes = new ArrayList<>();
        try {
            respList = contentService.getContentByCid(adCatId);
            Integer rtnKey = respList.getResponseModal().getCode();
            if (rtnKey.equals(RespCode.OK.getKey())) {
                List<ContentDto> contentDtos = respList.getData();
                for (int i = 0; i < contentDtos.size(); i++) {
                    ContentDto contentDto = contentDtos.get(i);
                    Ad1Node node = new Ad1Node();
                    node.setTitle(contentDto.getTitle());
                    node.setSrc(contentDto.getPic1_path());
                    node.setHref(contentDto.getUrl());
                    node.setWidth(width);
                    node.setHeight(height);
                    linkNodes.add(node);
                }
            }
        } catch (Exception e) {
            log.error("广告位异常{}", StringUtil.getTrace(e));
        }
        return linkNodes;
    }

}
