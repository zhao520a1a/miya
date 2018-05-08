package com.miya.controller;

import com.golden.pojo.RespList;
import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.golden.util.StringUtil;
import com.google.common.collect.Lists;
import com.miya.dto.ItemCatDto;
import com.miya.entity.ItemCat;
import com.miya.pojo.CatNode;
import com.miya.pojo.CatResult;
import com.miya.service.impl.ItemCatServiceImpl;
import com.miya.service.impl.ItemServiceImpl;
import com.springboot.ping.mybatis.enums.DbType;
import com.springboot.ping.mybatis.enums.Operator;
import com.springboot.ping.mybatis.vo.Condition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/itemCats")
public class ItemCatController {

    @Value("${ITEM_MIYA_URL}")
    private String ITEM_MIYA_URL;

    @Autowired
    ItemCatServiceImpl itemCatService;

    @Autowired
    ItemServiceImpl itemService;

    @GetMapping(value = "/count")
    public RespObject<Integer> getItemCount() {
        int sumNum = 0;
        try {
            Condition condition = null;
            sumNum = (int) itemCatService.count(condition);
        } catch (Exception e) {
            log.error("getItemCount():{}", StringUtil.getTrace(e));
            return new RespObject<Integer>(ResponseModal.successMsg("获取商品类别数量失败"), -1);
        }
        return new RespObject<Integer>(ResponseModal.successMsg("获取商品类别数量成功"), sumNum);
    }


    @RequestMapping(value = "/info")
    public RespList<ItemCat> getItemCat(@RequestParam(required = false) String itemCatIds, @RequestParam(required = false, defaultValue="false") boolean allCat) {
        log.info("itemCatIds: {}  allCat:{}", itemCatIds, allCat);
        List<ItemCat> itemCatList = Lists.newArrayList();
        try {
            if (allCat) {
                Condition condition = new Condition("is_parent", DbType.INT, Operator.EQ, 0);
                itemCatList = itemCatService.find(condition);
                for (ItemCat itemCat : itemCatList) {
                    Long parentId = itemCat.getParent_id();

                    ItemCat cat = ItemCat.builder().id(parentId).build();
                    cat = itemCatService.findByPk(cat);
                    if (cat != null) {
                        itemCat.setName(cat.getName() + "--" + itemCat.getName());
                    }
                }

            } else {
                if (!StringUtil.isEmpty(itemCatIds)) {
                    itemCatList = itemCatService.getItemCatByIds(itemCatIds);
                }
            }
        } catch (Exception e) {
            log.error("getItemCat():{}", StringUtil.getTrace(e));
            return new RespList<>(ResponseModal.successMsg("获取商品类别异常"), null);
        }
        return new RespList<>(ResponseModal.successMsg("获取商品类别成功"), itemCatList);
    }


    @RequestMapping(value = "/list/parentId/{parentId}")
    RespList<ItemCatDto> getItemCatList(@PathVariable(value = "parentId") Long parentId) {
        List<ItemCatDto> itemCatDtoList = Lists.newArrayList();
        try {
            List<ItemCat> itemCatList = itemCatService.getItemCatByParentId(parentId);
            for (ItemCat itemCat : itemCatList) {
                ItemCatDto itemCatDto = new ItemCatDto().converFor(itemCat);
                itemCatDtoList.add(itemCatDto);
            }
        } catch (Exception e) {
            log.error("getItemCatList():{}", StringUtil.getTrace(e));
            return new RespList<>(ResponseModal.successMsg("获取商品类别异常" + e.getMessage()), null);
        }
        return new RespList<>(ResponseModal.successMsg("获取商品类别成功"), itemCatDtoList);
    }


    /* 获取商品分类信息 */
    @RequestMapping("/all")
    @ResponseBody
    public Object getItemCatList(@RequestParam String callback) {

        CatResult catResult = new CatResult();
        //查询分类列表
        catResult.setData(getCatList(0));

        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(catResult);
        mappingJacksonValue.setJsonpFunction(callback);

        System.out.println(" mappingJacksonValue:" + mappingJacksonValue.getValue().toString());
        return mappingJacksonValue;
    }


    private List<?> getCatList(long parentId) {
        List<ItemCatDto> catList = this.getItemCatList(parentId).getData();

        //返回值list
        List resultList = Lists.newArrayList();
        //向list中添加节点
        int count = 0;
        for (ItemCatDto itemCat : catList) {
            //判断是否为父节点
            if (itemCat.getIs_parent()) {
                CatNode catNode = new CatNode();
                if (parentId == 0) {
                    catNode.setName("<a href='"+ ITEM_MIYA_URL +"/products/" + itemCat.getId() + ".html'>" + itemCat.getName() + "</a>");
                } else {
                    catNode.setName(itemCat.getName());
                }
                catNode.setUrl(ITEM_MIYA_URL + "/products/" + itemCat.getId() + ".html");
                catNode.setItem(getCatList(itemCat.getId()));

                resultList.add(catNode);
                count++;
                //第一层只取14条记录
                if (parentId == 0 && count >= 14) {
                    break;
                }
                //如果是叶子节点, 格式  URL | NAME
            } else {
                resultList.add(ITEM_MIYA_URL + "/products/" + itemCat.getId() + ".html|" + itemCat.getName());
            }
        }
        return resultList;
    }





}
