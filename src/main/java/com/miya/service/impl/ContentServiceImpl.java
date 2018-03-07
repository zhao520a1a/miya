package com.miya.service.impl;


import com.miya.dto.ResponseModal;
import com.miya.entity.Content;
import com.miya.entity.dao.ContentDao;
import com.miya.jedis.JedisClient;
import com.miya.redis.RedisService;
import com.miya.service.ContentService;
import com.miya.utils.JsonUtils;
import com.springboot.ping.mybatis.enums.DbType;
import com.springboot.ping.mybatis.enums.Operator;
import com.springboot.ping.mybatis.vo.Condition;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容管理
 */
@Service
public class ContentServiceImpl  implements ContentService {

    @Autowired
    private ContentDao contentDao;

    @Autowired
    @Qualifier("JedisClientPool")
    private JedisClient jedisClient;

//    @Autowired
//    private RedisService<Content> redisService;

    @Autowired
    private RedisService<String> redisService;

    @Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT;


    @Override
    public ResponseModal addContent(Content content) {
        //补全pojo的属性
        content.setCreate_time(new Date());
        content.setUpdate_time(new Date());
        //插入到内容表
//        contentDao.insert(content);
        return ResponseModal.success();
    }


    @Override
    public List<Content> getContentByCid(long cid) {
        //先查询缓存
        //添加缓存不能影响正常业务逻辑
        try {
            //查询缓存
            String json = redisService.get(cid + "");
            //查询到结果，把json转换成List返回
            if (StringUtils.isNotBlank(json)) {
                List<Content> list = JsonUtils.jsonToList(json, Content.class);
                return list;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //缓存中没有命中，需要查询数据库
        Content content = new Content();
        //设置查询条件
        content.setCategory_id(cid);
        List<Condition> conditions = new ArrayList<>();
        Condition condition = new Condition("category_id", DbType.INT, Operator.EQ, cid);
        conditions.add(condition);

        //执行查询
        List<Content> list = contentDao.find(null,conditions,null);
        try {
            //把结果添加到缓存
            redisService.put(cid + "", JsonUtils.objectToJson(list),-1);
//            jedisClient.hset(INDEX_CONTENT, cid + "", JsonUtils.objectToJson(list));
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return list;
    }


}
