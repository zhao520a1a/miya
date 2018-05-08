package com.miya.service.impl;


import com.golden.pojo.ResponseModal;
import com.golden.util.JsonUtils;
import com.miya.entity.Content;
import com.miya.entity.dao.ContentCatDao;
import com.miya.entity.dao.ContentDao;
import com.miya.redis.RedisService;
import com.miya.service.ContentService;
import com.springboot.ping.mybatis.enums.DbType;
import com.springboot.ping.mybatis.enums.Operator;
import com.springboot.ping.mybatis.extend.service.BaseCURDService;
import com.springboot.ping.mybatis.vo.Condition;
import com.springboot.ping.mybatis.vo.Page;
import com.springboot.ping.mybatis.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 内容管理
 */
@Slf4j
@Service
public class ContentServiceImpl extends BaseCURDService<Content, ContentDao> implements ContentService {

    @Autowired
    private ContentDao contentDao;

    @Autowired
    private ContentCatDao contentCatDao;

    @Autowired
    private RedisService redisService;

    @Value("${INDEX_CONTENT}")
    private String INDEX_CONTENT;


    @Override
    public ResponseModal addContent(Content content) {
        //----补全pojo的属性
        content.setCreate_time(new Date());
        content.setUpdate_time(new Date());
        contentDao.insert(content);
        redisService.delete(INDEX_CONTENT);
        return ResponseModal.successMsg("添加内容成功");
    }

    @Override
    public ResponseModal updateContent(Content content) {
        content.setUpdate_time(new Date());
        contentDao.updateByPk(content);
        redisService.delete(INDEX_CONTENT);
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
        List<Content> list = contentDao.find(null, conditions, null);
        try {
            //把结果添加到缓存
            redisService.set(cid + "", JsonUtils.objectToJson(list),0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //返回结果
        return list;
    }

    @Override
    public Page getContentsByPage(int limit, int currPage, int count) {
        Pagination pagination = new Pagination();
        pagination.setTotalCount(count);
        pagination.setPageSize(limit);
        pagination.setCurrentPage(currPage);
        Page page = this.find(pagination);
        return page;
    }


}
