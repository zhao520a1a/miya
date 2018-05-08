package com.miya.service.impl;


import com.golden.pojo.ResponseModal;
import com.golden.util.*;
import com.google.common.collect.Lists;
import com.miya.entity.Item;
import com.miya.entity.ItemCat;
import com.miya.entity.ItemDesc;
import com.miya.entity.ItemParam;
import com.miya.entity.dao.ItemCatDao;
import com.miya.entity.dao.ItemDao;
import com.miya.entity.dao.ItemDescDao;
import com.miya.entity.dao.ItemParamDao;
import com.miya.mq.KafkaSender;
import com.miya.redis.RedisService;
import com.miya.service.ItemService;
import com.springboot.ping.mybatis.enums.DbType;
import com.springboot.ping.mybatis.enums.Operator;
import com.springboot.ping.mybatis.extend.service.BaseCURDService;
import com.springboot.ping.mybatis.vo.Column;
import com.springboot.ping.mybatis.vo.Condition;
import com.springboot.ping.mybatis.vo.Page;
import com.springboot.ping.mybatis.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 商品管理Service
 */
@Slf4j
@Service
public class ItemServiceImpl extends BaseCURDService<Item, ItemDao> implements ItemService {

    @Autowired
    private ItemDao itemDao;

    @Autowired
    private ItemDescDao itemDescDao;

    @Autowired
    private ItemCatDao itemCatDao;

    @Autowired
    private ItemParamDao itemParamDao;

    @Autowired
    private RedisService redisService;

    @Value("${ITEM_INFO}")
    private String ITEM_INFO;

    @Value("${TIEM_EXPIRE}")
    private Integer TIEM_EXPIRE;


    @Autowired
    private KafkaSender kafkaSender;


    /*
     * 添加查询缓存
     */
    @Override
    public Item getItemById(long itemId) {
        Item item = null;
        String json = (String) redisService.get(ITEM_INFO + ":" + itemId + ":BASE");
        if (!StringUtil.isEmpty(json)) {
            System.out.println("从Redis");
            item = JsonUtils.jsonToPojo(json, Item.class);
            return item;
        }
        System.out.println("DB");
        //缓存中没有查询数据库
        item = Item.builder().id(itemId).build();
        item = itemDao.findByPk(item);
//        ItemCat itemCat = new ItemCat().builder().id(item.getCid()).build();
//        itemCat = itemCatDao.findByPk(itemCat);
        if (item != null) {
            //把查询结果添加到缓存,设置过期时间，提高缓存的利用率
            redisService.set(ITEM_INFO + ":" + itemId + ":BASE", JsonUtils.objectToJson(item), TIEM_EXPIRE);
        }
        return item;
    }


    @Override
    public ResponseModal addItem(Item item, String desc, String specs) {
        //生成商品id
        final Long itemId = IDUtils.genItemId();
        log.info("itemId:{} ", itemId);
        item.setId(itemId);
        //商品状态，1-正常，2-下架，3-删除
        item.setStatus((byte) 1);
        //保存商品
        this.save(item);
        //保存商品描述
        this.saveDesc(itemId, desc);
        //保存商品规格
        this.saveSpecs(itemId, specs);
        //清空缓存
        redisService.delete(ITEM_INFO + ":" + itemId + ":BASE");
        //发送通知消息
        kafkaSender.sendMessage("add:" + itemId.toString());
        return ResponseModal.successMsg("添加商品成功");
    }

    @Override
    public ResponseModal updateItem(Item item, String desc, String specs) {
        this.update(item);
        Long itemId = item.getId();
        this.updateDesc(itemId, desc);
        this.updateSpecs(itemId, specs);
        redisService.delete(ITEM_INFO + ":" + itemId + ":BASE");
        //发送通知消息
        kafkaSender.sendMessage("update:" + itemId.toString());
        return ResponseModal.successMsg("修改商品成功");
    }


    @Override
    public ResponseModal deleteItem(Long id) {
        Item item = Item.builder().id(id).build();
        super.deleteByPk(item);
        //发送通知消息
        kafkaSender.sendMessage("delete:" + id);
        return ResponseModal.successMsg("删除商品成功");
    }


    @Override
    public ItemDesc getItemDesc(Long itemId) {
        ItemDesc itemDesc = null;
        String json = redisService.get(ITEM_INFO + ":" + itemId + ":DESC");
        if (!StringUtil.isEmpty(json)) {
            itemDesc = JsonUtils.jsonToPojo(json, ItemDesc.class);
            return itemDesc;
        }
        //缓存中没有查询数据库
        itemDesc = ItemDesc.builder().item_id(itemId).build();
        itemDesc = itemDescDao.findByPk(itemDesc);
        if (itemDesc != null) {
            //把查询结果添加到缓存,设置过期时间，提高缓存的利用率
            redisService.set(ITEM_INFO + ":" + itemId + ":DESC", JsonUtils.objectToJson(itemDesc), TIEM_EXPIRE);
        }
        return itemDesc;
    }


    @Override
    public ItemParam getItemParam(Long itemId) {
        ItemParam itemParam = null;
        String json = redisService.get(ITEM_INFO + ":" + itemId + ":Param");
        if (!StringUtil.isEmpty(json)) {
            itemParam = JsonUtils.jsonToPojo(json, ItemParam.class);
            return itemParam;
        }
        Condition condition = Condition.getCondition("item_id|int|eq", itemId);
        List<Condition> conditions = Lists.newArrayList();
        conditions.add(condition);
        List<ItemParam> itemParams= itemParamDao.find(null, conditions, null);
        if(!Collects.isEmpty(itemParams)) {
            itemParam = itemParams.get(0);
        }
        return itemParam;
    }

    @Override
    public Page getItemsByCatId(Long catId, int limit, int currPage) {
        //查找其子类
        Page<Item> page;

        List<ItemCat> itemCats = Lists.newArrayList();
        this.getChildItemCat(catId, itemCats);


        StringBuilder sb = new StringBuilder();
        sb.append(catId + ",");
        if (!ListUtil.isEmpty(itemCats)) {
            for (ItemCat itemCat : itemCats) {
                sb.append(itemCat.getId() + ",");
            }
        }
        String itemCatIds = sb.substring(0, sb.length() - 1);

        Condition condition = new Condition("cid", DbType.INT, Operator.IN, itemCatIds);

        Pagination pagination = new Pagination();
        Long count = super.count(condition);
        pagination.setTotalCount(count.intValue());
        pagination.setPageSize(limit);
        pagination.setCurrentPage(currPage);
        page = super.find(pagination, condition);
        return page;
}

    private void getChildItemCat(Long catId, List<ItemCat> itemCats) {

        ItemCat itemCat = itemCatDao.findByPk(ItemCat.builder().id(catId).build());
        itemCats.add(itemCat);
        if (itemCat.getIs_parent() == true) {
            List<Condition> conditions = Lists.newArrayList();

            Condition condition = new Condition("parent_id", DbType.INT, Operator.EQ, catId);
            conditions.add(condition);
            List<ItemCat> itemCatList = itemCatDao.find(null, conditions, null);
            for (ItemCat ic : itemCatList) {
                getChildItemCat(ic.getId(), itemCats);
            }
        }
    }

    @Override
    public Page getItemsByPage(int limit, int currPage, int count) {
        Pagination pagination = new Pagination();
        pagination.setTotalCount(count);
        pagination.setPageSize(limit);
        pagination.setCurrentPage(currPage);
        Page page = this.find(pagination);
        return page;
    }



    /**
     * 添加商品描述
     */
    public void saveDesc(Long itemId, String desc) {
        ItemDesc itemDesc = ItemDesc.builder().build();
        itemDesc.setItem_id(itemId);
        itemDesc.setItem_desc(desc);
        itemDesc.setCreate_time(new Date());
        itemDesc.setUpdate_time(new Date());
        itemDescDao.insert(itemDesc);
        redisService.delete(ITEM_INFO + ":" + itemId + ":DESC");
    }


    /**
     * 修改商品描述
     */
    public void updateDesc(Long itemId, String desc) {
        ItemDesc itemDesc = ItemDesc.builder().build();
        itemDesc.setItem_id(itemId);
        itemDesc.setItem_desc(desc);
        itemDesc.setUpdate_time(new Date());
        itemDescDao.updateByPk(itemDesc);
        redisService.delete(ITEM_INFO + ":" + itemId + ":DESC");
    }


    /**
     * 添加规格参数
     */
    public void saveSpecs(Long itemId, String itemParam) {
        //创建一个pojo
        ItemParam itemParamItem = ItemParam.builder().build();
        itemParamItem.setItem_id(itemId);
        itemParamItem.setParamData(itemParam);
        itemParamItem.setCreate_time(new Date());
        itemParamItem.setUpdate_time(new Date());
        //向表中插入数据
        itemParamDao.insert(itemParamItem);
        redisService.delete(ITEM_INFO + ":" + itemId + ":Param");
    }


    /**
     * 修改规格参数
     */
    public void updateSpecs(Long itemId, String itemParam) {
        List<Column> columns = Lists.newArrayList();
        Column column1 = new Column("param_data", itemParam);
        columns.add(column1);
        Column column2 = new Column("update_time", new Date());
        columns.add(column2);

        List<Condition> conditions = Lists.newArrayList();
        Condition condition = new Condition("item_id", DbType.INT, Operator.EQ, itemId);
        conditions.add(condition);
        itemParamDao.updateBatchColumns(columns, conditions);
        redisService.delete(ITEM_INFO + ":" + itemId + ":Param");
    }


}
