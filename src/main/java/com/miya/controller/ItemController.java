package com.miya.controller;

import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.golden.util.Collects;
import com.golden.util.StringUtil;
import com.miya.dto.ItemDescDto;
import com.miya.dto.ItemInputDto;
import com.miya.dto.ItemOutputDto;
import com.miya.dto.ItemParamDto;
import com.miya.entity.Item;
import com.miya.entity.ItemCat;
import com.miya.entity.ItemDesc;
import com.miya.entity.ItemParam;
import com.miya.service.impl.ItemCatServiceImpl;
import com.miya.service.impl.ItemServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    ItemServiceImpl itemService;
    @Autowired
    ItemCatServiceImpl itemCatService;

    @RequestMapping(value = "/descInfo")
    public RespObject<ItemDescDto> getItemList(@RequestParam long id) {
        ItemDesc itemDesc;
        ItemDescDto itemDescDto = ItemDescDto.builder().build();
        try {
            itemDesc = itemService.getItemDesc(id);
            itemDescDto = itemDescDto.converFor(itemDesc);
        } catch (Exception e) {
            log.info("{}", StringUtil.getTrace(e));
            return new RespObject<>(ResponseModal.errorMsg("获取商品描述信息异常"), null);
        }
        return new RespObject<>(ResponseModal.successMsg("获取商品描述信息成功"), itemDescDto);
    }

    @RequestMapping(value = "/itemParam")
    public RespObject<String> getItemParam(@RequestParam long id) {
        String paramData = "";
        try {
            ItemParam itemParam = itemService.getItemParam(id);
            if (itemParam != null) {
                paramData = itemParam.getParam_data();
            }
        } catch (Exception e) {
            log.info("{}", StringUtil.getTrace(e));
            return new RespObject<>(ResponseModal.errorMsg("获取商品规格信息异常"), "");
        }
        return new RespObject<>(ResponseModal.successMsg("获取商品规格信息成功"), paramData);
    }


    @RequestMapping(value = "/delete/{id}")
    public ResponseModal deleteItem(@PathVariable Long id) {
        log.info("删除ItemId：" + id);
        try {
            return itemService.deleteItem(id);
        } catch (Exception e) {
            log.info("deleteItem:{}", StringUtil.getTrace(e));
            return ResponseModal.errorMsg("删除商品记录异常");
        }
    }


    @RequestMapping(value = "/add", method = {RequestMethod.POST})
    public ResponseModal addItem(@RequestBody ItemInputDto itemInputDto) {
        log.info("ItemInputDto:{}", itemInputDto.toString());
        try {
            Item item = itemInputDto.converToItem();
            return itemService.addItem(item, itemInputDto.getDescription(), itemInputDto.getSpecsStr());
        } catch (Exception e) {
            log.error("添加商品异常:{}", StringUtil.getTrace(e));
            return ResponseModal.failMsg("添加商品异常");
        }
    }


    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    public ResponseModal updateItem(@RequestBody ItemInputDto itemInputDto) {
        log.info("ItemInputDto:{}", itemInputDto.toString());
        try {
            Item item = itemInputDto.converToItem();
            return itemService.updateItem(item, itemInputDto.getDescription(), itemInputDto.getSpecsStr());
        } catch (Exception e) {
            log.error("添加商品异常:{}", StringUtil.getTrace(e));
            return ResponseModal.failMsg("添加商品异常");
        }
    }



    /* 前台 -- */
    @RequestMapping(value = "/{id}")
    public RespObject<ItemOutputDto> getItem(@PathVariable Long id) {
        log.info("查询item, ItemId：{}", id);
        ItemOutputDto itemDto = ItemOutputDto.builder().build();
        try {
            Item item = itemService.getItemById(id);
            itemDto = itemDto.converFor(item);

            List<String> cnameList = Lists.newArrayList();
            ItemCat itemCat = itemCatService.getItemCatById(item.getCid());
            cnameList.add(itemCat.getName());
            if (itemCat.getParent_id() != 0) {
                ItemCat itemCat1 = itemCatService.getItemCatById(item.getCid());
                if (itemCat1.getParent_id() != 0) {
                    ItemCat itemCat2 = itemCatService.getItemCatById(item.getCid());
                    cnameList.add(itemCat2.getName());
                }
            }
            for (int i = cnameList.size() - 1; i > 0; i--) {
                itemDto.setCname(cnameList.get(i));
            }
        } catch (Exception e) {
            log.info("{}", StringUtil.getTrace(e));
            return new RespObject<>(ResponseModal.errorMsg("获得商品记录异常"), null);
        }
        return new RespObject<>(ResponseModal.successMsg("获得商品记录成功"), itemDto);
    }


    @RequestMapping(value = "/itemDesc/{itemId}")
    public RespObject<ItemDescDto> getItemDesc(@PathVariable Long itemId) {
        log.info("查询itemDesc, ItemId：{}", itemId);
        ItemDescDto itemDescDto = ItemDescDto.builder().build();
        try {
            ItemDesc itemDesc = itemService.getItemDesc(itemId);
            itemDescDto = itemDescDto.converFor(itemDesc);
        } catch (Exception e) {
            log.info("{}", StringUtil.getTrace(e));
            return new RespObject<>(ResponseModal.errorMsg("获得商品描述异常"), null);
        }
        return new RespObject<>(ResponseModal.successMsg("获得商品描述成功"), itemDescDto);
    }

    @RequestMapping(value = "/itemParam/{itemId}")
    public RespObject<ItemParamDto> getItemParam(@PathVariable Long itemId) {
        log.info("查询itemParam, ItemId：{}", itemId);
        ItemParamDto itemParamDto = ItemParamDto.builder().build();
        try {
            ItemParam itemParam = itemService.getItemParam(itemId);
            itemParamDto = itemParamDto.converFor(itemParam);
        } catch (Exception e) {
            log.info("{}", StringUtil.getTrace(e));
            return new RespObject(ResponseModal.errorMsg("获得商品规格参数异常"), null);
        }
        return new RespObject(ResponseModal.successMsg("获得商品规格参数成功"), itemParamDto);
    }


    /* 前台 -- */


}
