package com.miya.controller;

import com.alibaba.fastjson.JSONObject;
import com.miya.dto.RespObject;
import com.miya.utils.StringUtil;
import com.miya.dto.ItemCatDto;
import com.miya.dto.ItemDto;
import com.miya.dto.ResponseModal;
import com.miya.entity.Item;
import com.miya.entity.ItemCat;
import com.miya.service.impl.ItemCatServiceImpl;
import com.springboot.ping.mybatis.vo.Condition;
import com.springboot.ping.mybatis.vo.Page;
import com.springboot.ping.mybatis.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/itemCats")
public class ItemCatController {
    private int sumNum;

    @Autowired
    ItemCatServiceImpl itemCatService;

    /* 获得商品的总数量 */
    @RequestMapping(value = "/count", method = {RequestMethod.POST, RequestMethod.GET})
    public RespObject<Integer> getItemCount() {
        try {
            Condition condition = null;
            sumNum = (int) itemCatService.count(condition);
            log.info("itemCount: {}", sumNum);
        } catch (Exception e) {
            log.error(StringUtil.getTrace(e));
            return new RespObject<Integer>(ResponseModal.successMsg("获取用户数量失败"),-1 ) ;
        }
        return new RespObject<Integer>(ResponseModal.successMsg("获取用户数量成功"),sumNum ) ;
    }

    /* 分页获得商品列表 */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseModal getItemList(@RequestParam(value = "limit") int limit, @RequestParam(value = "offset") int offset, HttpSession httpSession) {
        log.info("分页limit:{}, offset:{}, httpSession:{}", limit, offset, httpSession);

        JSONObject usersData = new JSONObject();
        Pagination pagination = null;
        Page page = null;
        try {
            pagination = new Pagination();
            int curPage = offset / limit + 1;
            pagination.setTotalCount(sumNum);
            pagination.setPageSize(limit);
            pagination.setCurrentPage(curPage);
            page = itemCatService.find(pagination);

            List<Item> itemList = page.getRows();


            List<ItemDto> itemDtoList = new LinkedList<>();
            for (Item item : itemList) {

                ItemDto itemDto = ItemDto.builder().build();
                itemDto = itemDto.converFor(item);
                itemDtoList.add(itemDto);
            }
            page.setRows(itemDtoList);


            List<String> columnList = Arrays.asList("商品编号", "商品状态", "类别名称", "商品卖点", "库存数量",
                    "商品价格", "月销量", "商品创建时间", "商品修改时间", "商品名称", "商品图片", "商品介绍");
            usersData.put("page", page);
            usersData.put("columns", columnList); //表头标题

            /*
             * 将数据存在Session中，主要为了前端下载时使用
             *  rowlist + firstRow
             */
            JSONObject data = new JSONObject();

            List<List<String>> dataList = new ArrayList<List<String>>();  //二维矩阵结构
            List<String> rowsList = new ArrayList<String>();
            for (ItemDto itemDto : itemDtoList) {
                List<String> innerDataList = new ArrayList<String>();
//                Todo 下载的列表数据***代填
//                innerDataList.add(itemDto.getCreate_time());
//                innerDataList.add(itemDto.getUsername());
//                innerDataList.add(itemDto.getPhone());
                dataList.add(innerDataList);
            }
            data.put("rows", rowsList);
            data.put("data", dataList);
            data.put("columns", columnList);
            httpSession.setAttribute("itemList", data);  //以备下载数据时使用

        } catch (Exception e) {
            log.info(StringUtil.getTrace(e));
            return ResponseModal.failMsg("获取用户列表失败");
        }
        return ResponseModal.successMsg("获取用户列表成功");
    }

    @RequestMapping(value = "/info", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseModal getItemList(Long cid) {
        log.info("CID：" + cid);
        ItemCat itemCat = ItemCat.builder().id(cid).build();

        ItemCatDto itemCatDto = ItemCatDto.builder().build();
        try {
            itemCat = itemCatService.findByPk(itemCat);
            itemCatDto = itemCatDto.converFor(itemCat);
        } catch (Exception e) {
            log.error("XXXXX{}",  StringUtil.getTrace(e));
            return ResponseModal.failMsg("获取商品分类信息失败");
        }
        return ResponseModal.successMsg("获取商品分类信息成功");
    }
}
