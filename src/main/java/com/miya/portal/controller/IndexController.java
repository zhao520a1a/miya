package com.miya.portal.controller;


import com.golden.pojo.RespCode;
import com.golden.pojo.RespList;
import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.golden.util.CookieUtils;
import com.golden.util.JsonUtils;
import com.golden.util.StringUtil;
import com.google.common.collect.Maps;
import com.miya.portal.dto.ContentDto;
import com.miya.portal.dto.Page;
import com.miya.portal.feign.ContentService;
import com.miya.portal.feign.ItemService;
import com.miya.portal.feign.SsoService;
import com.miya.portal.pojo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 首页展示Controller
 */
@Slf4j
@Controller
public class IndexController {
    @Value("${AD1_CATEGORY_ID}")
    private Long AD1_CATEGORY_ID;  //大广告
    @Value("${AD1_WIDTH1}")
    private Integer AD1_WIDTH1;
    @Value("${AD1_WIDTH2}")
    private Integer AD1_WIDTH2;
    @Value("${AD1_HEIGHT1}")
    private Integer AD1_HEIGHT1;
    @Value("${AD1_HEIGHT2}")
    private Integer AD1_HEIGHT2;

    @Value("${AD2_CATEGORY_ID}")
    private Long AD2_CATEGORY_ID;  //中广告
    @Value("${AD2_WIDTH1}")
    private Integer AD2_WIDTH1;
    @Value("${AD2_WIDTH2}")
    private Integer AD2_WIDTH2;
    @Value("${AD2_HEIGHT1}")
    private Integer AD2_HEIGHT1;
    @Value("${AD2_HEIGHT2}")
    private Integer AD2_HEIGHT2;

    @Value("${AD3_CATEGORY_ID}")
    private Long AD3_CATEGORY_ID; //小广告
    @Value("${AD3_WIDTH1}")
    private Integer AD3_WIDTH1;
    @Value("${AD3_WIDTH2}")
    private Integer AD3_WIDTH2;
    @Value("${AD3_HEIGHT1}")
    private Integer AD3_HEIGHT1;
    @Value("${AD3_HEIGHT2}")
    private Integer AD3_HEIGHT2;

    @Value("${AD4_CATEGORY_ID}")
    private Long AD4_CATEGORY_ID;  //商品轮播图

    @Value("${AD5_CATEGORY_ID}")
    private Long AD5_CATEGORY_ID;  //快报

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @Autowired
    private ContentService contentService;

    @Autowired
    private SsoService ssoService;

    @Autowired
    private ItemService itemService;


    @RequestMapping("/index")
    public String showIndex(Model model) {
        //大广告位
        String ad1Json = getIndexContent(AD1_CATEGORY_ID, AD1_WIDTH1, AD1_WIDTH2, AD1_HEIGHT1, AD1_HEIGHT2);
        //中广告位
        String ad2Json = getIndexContent(AD2_CATEGORY_ID, AD2_WIDTH1, AD2_WIDTH1, AD2_HEIGHT1, AD2_HEIGHT2);
        //小广告：快报上方广告
        String ad3Json = getIndexContent(AD3_CATEGORY_ID, AD3_WIDTH1, AD3_WIDTH2, AD3_HEIGHT1, AD3_HEIGHT2);
        //商品滚动条
        String ad4Json = getScrollContent(AD4_CATEGORY_ID);
        //链接
        List<LinkNode> linkList = getLinkContent(AD5_CATEGORY_ID);
        //商品热卖品
        String dataTabsJson = getIndexItems();

        System.out.println("dataTabsJson: " + dataTabsJson);
        //把json数据传递给页面
        model.addAttribute("ad1", ad1Json);
        model.addAttribute("ad2", ad2Json);
        model.addAttribute("ad3", ad3Json);
        model.addAttribute("ad4", ad4Json);
        model.addAttribute("linkList", linkList);
        model.addAttribute("dataTabs", dataTabsJson);
        return "index";
    }

    private String getIndexContent(Long adCatId, int width1, int width2, int height1, int height2) {
        //根据cid查询大广告位轮播图内容列表
        RespList<ContentDto> respList;
        //把列表转换为Ad1Node列表
        List<AD1Node> ad1Nodes = new ArrayList<>();
        String adJson = new String();
        try {
            //根据cid查询大广告位轮播图内容列表
            respList = contentService.getContentByCid(adCatId);
            Integer rtnKey = respList.getResponseModal().getCode();
            if (rtnKey.equals(RespCode.OK.getKey())) {
                List<ContentDto> contentDtos = respList.getData();

                for (ContentDto contentDto : contentDtos) {
                    AD1Node node = new AD1Node();
                    node.setAlt(contentDto.getTitle());
                    node.setHeight(height1);
                    node.setHeightB(height2);
                    node.setWidth(width1);
                    node.setWidthB(width2);
                    node.setSrc(contentDto.getPic1_path());
                    node.setSrcB(contentDto.getPic2_path());
                    node.setHref(contentDto.getUrl());
                    //添加到节点列表
                    ad1Nodes.add(node);
                }

                //把列表转换成json数据
                adJson = JsonUtils.objectToJson(ad1Nodes);
            }

        } catch (Exception e) {
            log.error("广告位异常{}", StringUtil.getTrace(e));
        }
        return adJson;
    }

    private String getScrollContent(Long adCatId) {
        RespList<ContentDto> respList;
        List<ScrollNode> scrollNodes = new ArrayList<>();
        String adJson = new String();
        try {
            respList = contentService.getContentByCid(adCatId);
            Integer rtnKey = respList.getResponseModal().getCode();
            if (rtnKey.equals(RespCode.OK.getKey())) {
                List<ContentDto> contentDtos = respList.getData();
                for (int i = 0; i < contentDtos.size(); i++) {
                    ContentDto contentDto = contentDtos.get(i);
                    ScrollNode node = new ScrollNode();
                    node.setAlt(contentDto.getTitle());
                    node.setSrc(contentDto.getPic1_path());
                    node.setHref(contentDto.getUrl());
                    node.setIndex(i);
                    scrollNodes.add(node);
                }
                adJson = JsonUtils.objectToJson(scrollNodes);
            }
        } catch (Exception e) {
            log.error("广告位异常{}", StringUtil.getTrace(e));
        }
        return adJson;
    }


    private List<LinkNode> getLinkContent(Long adCatId) {
        RespList<ContentDto> respList;
        List<LinkNode> linkNodes = new ArrayList<>();
        try {
            respList = contentService.getContentByCid(adCatId);
            Integer rtnKey = respList.getResponseModal().getCode();
            if (rtnKey.equals(RespCode.OK.getKey())) {
                List<ContentDto> contentDtos = respList.getData();
                for (int i = 0; i < contentDtos.size(); i++) {
                    ContentDto contentDto = contentDtos.get(i);
                    LinkNode node = new LinkNode();
                    node.setTitle(contentDto.getTitle());
                    node.setSrc(contentDto.getPic1_path());
                    node.setHref(contentDto.getUrl());
                    linkNodes.add(node);
                }
            }
        } catch (Exception e) {
            log.error("广告位异常{}", StringUtil.getTrace(e));
        }
        return linkNodes;
    }



    private String getIndexItems() {
        DataTip dataTip = DataTip.builder().build();
        String dataTibJson = new String();
        try {
            dataTip.setWomen_dress(getIndexItemNodes(750L));
            dataTip.setMen_wear(getIndexItemNodes(784L));
            dataTip.setBeauty(getIndexItemNodes(249L));
            dataTip.setLiving(getIndexItemNodes(633L));
            dataTip.setBooks(getIndexItemNodes(3L));
            //把列表转换成json数据
            dataTibJson = JsonUtils.objectToJson(dataTip);
        } catch (Exception e) {
            log.error("首页商品滑动展示信息异常{}", StringUtil.getTrace(e));
        }
        return dataTibJson;
    }


    /*只取10数据，组装成前端所需的数据格式*/
    public IndexItemNodes getIndexItemNodes(Long itemCatId) {
        RespObject<Page> respObject;
        IndexItemNodes itemNodes = IndexItemNodes.builder().build();
        List<Map> items;

        respObject = itemService.getItemsByCat(itemCatId, 10, 1);
        Integer rtnKey = respObject.getResponseModal().getCode();
        if (rtnKey.equals(RespCode.OK.getKey())) {
            Page page = respObject.getData();
            items = page.getRows();

            Map<Integer, IndexItemNode> itemNodeMap = Maps.newHashMap();
            for (int i = 0; i < items.size(); i++) {
                Map itemMap = items.get(i);

                IndexItemNode node = new IndexItemNode();
                if (!StringUtil.isEmpty(itemMap.get("image").toString())) {
                    node.setImage(((String) itemMap.get("image")).split(",")[0]);
                }
                node.setItemId((String) itemMap.get("id"));
                node.setTitle((String) itemMap.get("title"));

                Double d = (Integer) itemMap.get("price") / 100.0;
                DecimalFormat df = new DecimalFormat(".00");
                String price = df.format(d);
                node.setPrice(price);


                itemNodeMap.put(i, node);
            }
            itemNodes.setA(itemNodeMap.get(0));
            itemNodes.setB(itemNodeMap.get(1));
            itemNodes.setC(itemNodeMap.get(2));
            itemNodes.setD(itemNodeMap.get(3));
            itemNodes.setE(itemNodeMap.get(4));
            itemNodes.setF(itemNodeMap.get(5));
            itemNodes.setG(itemNodeMap.get(6));
            itemNodes.setH(itemNodeMap.get(7));
            itemNodes.setI(itemNodeMap.get(8));
            itemNodes.setJ(itemNodeMap.get(9));
        }
        return itemNodes;
    }

    @RequestMapping("/")
    public String show(Model model) {
        return showIndex(model);
    }


    @RequestMapping(value = "/user/logout", method = {RequestMethod.POST, RequestMethod.GET})
    public String logout(HttpServletRequest request) {
        try {
            String token = CookieUtils.getCookieValue(request, TOKEN_KEY);
            System.out.println(token);
            ResponseModal result = ssoService.logout(token);
        } catch (Exception e) {
            log.error("logout():{}" + StringUtil.getTrace(e));
        }
        return "index";
    }

    @RequestMapping("/exception")
    public String test () {
        return "error/exception";
    }



}
