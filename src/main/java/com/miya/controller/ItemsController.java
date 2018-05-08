package com.miya.controller;

import com.alibaba.fastjson.JSONObject;
import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.golden.util.StringUtil;
import com.google.common.collect.Lists;
import com.miya.dto.ItemOutputDto;
import com.miya.entity.Item;
import com.miya.service.impl.ItemCatServiceImpl;
import com.miya.service.impl.ItemServiceImpl;
import com.springboot.ping.mybatis.vo.Condition;
import com.springboot.ping.mybatis.vo.Page;
import com.springboot.ping.mybatis.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemsController {

    @Autowired
    ItemServiceImpl itemService;
    @Autowired
    ItemCatServiceImpl itemCatService;


    /* 获得商品的总数量 */
    @RequestMapping(value = "/count", method = {RequestMethod.POST, RequestMethod.GET})
    public RespObject<Integer> getItemCount() {
        int sumNum = 0;
        try {
            Condition condition = null;
            sumNum = (int) itemService.count(condition);
            log.info("itemCount: {}", sumNum);
        } catch (Exception e) {
            log.info("{}", StringUtil.getTrace(e));
            return new RespObject<Integer>(ResponseModal.errorMsg("获取商品数量异常"), -1);
        }
        return new RespObject<Integer>(ResponseModal.successMsg("获取商品数量成功"), sumNum);
    }


    /* 分页获得商品列表 */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public RespObject<JSONObject> getItemsList(@RequestParam int limit, @RequestParam int currPage, @RequestParam int count, HttpSession httpSession) {
        log.info("分页limit:{}, currPage:{}, count:{}, httpSession:{}", limit, currPage, count, httpSession);
        JSONObject itemData = new JSONObject();
        Pagination pagination = null;
        Page page = null;
        try {

            page = itemService.getItemsByPage(limit, currPage, count);

            List<Item> itemList = page.getRows();
            List<ItemOutputDto> itemDtoList = Lists.newArrayList();
            for (Item item : itemList) {
                ItemOutputDto itemDto = ItemOutputDto.builder().build();
                itemDto = itemDto.converFor(item);
                //类别名称
                String cname = itemCatService.getItemCatNameById(item.getCid());
                itemDto.setCname(cname);
                itemDtoList.add(itemDto);
            }
            page.setRows(itemDtoList);
            itemData.put("page", page);


            List<String> columnList = Arrays.asList("商品编号", "类别名称", "商品名称", "商品卖点", "库存数量", "月销量",
                    "商品价格", "好评率", "商品状态", "修改时间", "商品图片");
            itemData.put("columns", columnList); //表头标题

        } catch (Exception e) {
            log.info("{}", StringUtil.getTrace(e));
            return new RespObject<>(ResponseModal.errorMsg("获取商品列表异常"), null);
        }
        return new RespObject<>(ResponseModal.successMsg("获取商品列表成功"), itemData);
    }


    /* 分页获得商品列表 */
    @GetMapping(value = "/listByCatId")
    RespObject<Page> getItemsByCat(@RequestParam(value = "catId") Long catId, @RequestParam(value = "limit") int limit, @RequestParam(value = "currPage") int currPage) {
        Pagination pagination = null;
        Page page = null;
        try {
            page = itemService.getItemsByCatId(catId, limit, currPage);

            List<Item> itemList = page.getRows();
            List<ItemOutputDto> itemDtoList = new LinkedList<>();
            for (Item item : itemList) {
                ItemOutputDto itemDto = ItemOutputDto.builder().build();
                itemDto = itemDto.converFor(item);

                //图片只取一张
                itemDto.setImage(item.getImage().split(",")[0]);
                itemDtoList.add(itemDto);
            }
            page.setRows(itemDtoList);
        } catch (Exception e) {
            log.info("{}", StringUtil.getTrace(e));
            return new RespObject<>(ResponseModal.errorMsg("获取特定类别下的商品列表异常"), null);
        }
        return new RespObject<>(ResponseModal.successMsg("获取特定类别下的商品列表成功"), page);
    }


}
