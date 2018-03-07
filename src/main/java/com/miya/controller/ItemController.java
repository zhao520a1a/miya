package com.miya.controller;

import com.miya.dto.ItemDescDto;
import com.miya.dto.RespObject;
import com.miya.dto.ResponseModal;
import com.miya.entity.ItemDesc;
import com.miya.service.impl.ItemServiceImpl;
import com.miya.utils.StringUtil;
import com.springboot.ping.mybatis.vo.Condition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {
    private int sumNum;

    @Autowired
    ItemServiceImpl itemService;

    /* 获得商品的总数量 */
    @RequestMapping(value = "/count", method = {RequestMethod.POST, RequestMethod.GET})
    public RespObject<Integer> getItemCount() {
        try {
            Condition condition = null;
            sumNum = (int) itemService.count(condition);
            log.info("itemCount: {}", sumNum);
        } catch (Exception e) {
            log.info("{}", StringUtil.getTrace(e));
            return new RespObject<Integer>(ResponseModal.errorMsg("获取用户数量失败"), -1);
        }
        return new RespObject<Integer>(ResponseModal.successMsg("获取用户数量成功"), sumNum);
    }


    /* 分页获得商品列表
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public RespList<User> getItemList(@RequestParam(value = "limit") int limit, @RequestParam(value = "offset") int offset, HttpSession httpSession) {
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
            page = itemService.find(pagination);

            List<Item> itemList = page.getRows();

            List<ItemDto> itemDtoList = new LinkedList<>();
            for (Item item : itemList) {
                String cName = itemService.getItemCat(item.getCid());

                ItemDto itemDto = ItemDto.builder().build();
                itemDto = itemDto.converFor(item);

                itemDtoList.add(itemDto);
            }
            page.setRows(itemDtoList);


            List<String> columnList = Arrays.asList("商品编号", "商品状态", "类别名称", "商品卖点", "库存数量",
                    "商品价格", "月销量", "商品创建时间", "商品修改时间", "商品名称", "商品图片", "商品介绍");
            usersData.put("page", page);
            usersData.put("columns", columnList); //表头标题
//             * 将数据存在Session中，主要为了前端下载时使用
//             *  rowlist + firstRow
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
           log.info("{}",StringUtil.getTrace(e));
            return new RespList<User>(ResponseModal.errorMsg("获取用户列表失败"),"");
        return new RespList<User>(ResponseModal.successMsg("获取用户列表成功"),usersData);
        }

        */

    @RequestMapping(value = "/descInfo", method = {RequestMethod.POST, RequestMethod.GET})
    public RespObject<ItemDescDto> getItemList(Long cid) {
        log.info("CID：" + cid);
        ItemDesc itemDesc;
        ItemDescDto itemDescDto = ItemDescDto.builder().build();

        try {
            itemDesc = itemService.getItemDesc(cid);
            itemDescDto = itemDescDto.converFor(itemDesc);
        } catch (Exception e) {

            log.info("{}", StringUtil.getTrace(e));
            return new RespObject<ItemDescDto>(ResponseModal.errorMsg("获取商品描述信息失败"), null);
        }
        return new RespObject<ItemDescDto>(ResponseModal.successMsg("获取商品描述信息成功"), itemDescDto);
    }

}
