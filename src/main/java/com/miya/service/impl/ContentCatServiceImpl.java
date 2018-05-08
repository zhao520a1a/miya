package com.miya.service.impl;

import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.golden.util.Util;
import com.google.common.collect.Lists;
import com.miya.common.pojo.EUTreeNode;
import com.miya.entity.ContentCat;
import com.miya.entity.ItemCat;
import com.miya.entity.dao.ContentCatDao;
import com.miya.service.ContentCatService;
import com.springboot.ping.mybatis.enums.DbType;
import com.springboot.ping.mybatis.enums.Operator;
import com.springboot.ping.mybatis.extend.service.BaseCURDService;
import com.springboot.ping.mybatis.vo.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 内容分类管理
 */
@Service
public class ContentCatServiceImpl extends BaseCURDService<ContentCat,ContentCatDao> implements ContentCatService {


	@Autowired
	private ContentCatDao contentCatDao;


	@Override
	public List<EUTreeNode> getContentCatList(long parentId) {
		//根据parentId查询子节点列表
		ContentCat contentCat = new ContentCat();
		//补全对象的属性
		contentCat.setParent_id(parentId);
		//执行查询
		List<ContentCat> list = (List<ContentCat>) contentCatDao.findByPk(contentCat);
		List<EUTreeNode> resultList = new ArrayList<>();
		for (ContentCat tbContentCat : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCat.getId());
			node.setText(tbContentCat.getName());
			node.setState(tbContentCat.getIs_parent()?"closed":"open");
			//添加到结果列表
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public RespObject<ContentCat> addContentCat(long parentId, String name) {
		//创建一个pojo对象
		ContentCat contentCat = new ContentCat();
		//补全对象的属性
		contentCat.setParent_id(parentId);
		contentCat.setName(name);
		//状态。可选值:1(正常),2(删除)
		contentCat.setStatus(1);
		//排序，默认为1
		contentCat.setSort_order(1);
		contentCat.setIs_parent(false);
		contentCat.setCreate_time(new Date());
		contentCat.setUpdate_time(new Date());
		//插入到数据库
		contentCatDao.insert(contentCat);
		//判断父节点的状态
		ContentCat parent = new ContentCat();
		//补全对象的属性
		parent.setParent_id(parentId);
		  parent = contentCatDao.findByPk(parent);
		if (!parent.getIs_parent()) {
			//如果父节点为叶子节点应该改为父节点
			parent.setIs_parent(true);
			//更新父节点
			contentCatDao.updateByPk(parent);
		}

		//返回结果
		return new RespObject<ContentCat>(ResponseModal.success() ,contentCat);
	}

	
	
	@Override
	public List<ContentCat> getContentCatByIds(String ids) {

		List<ContentCat> contentCatList = Lists.newArrayList();
		List<Long> idList = Util.str2LongList(ids);
		for(Long id : idList) {
			ContentCat contentCat = ContentCat.builder().id(id).build();
			contentCatList.add(super.findByPk(contentCat));
		}
		return contentCatList;
	}

	@Override
	public List<ContentCat> getContentCatByParentId(Long parentId) {
		ContentCat contentCat = ContentCat.builder().parent_id(parentId).build();

		List<Condition> conditions = Lists.newArrayList();
		Condition condition = new Condition("parent_id", DbType.INT, Operator.EQ,parentId);
		conditions.add(condition);
		List<ContentCat> contentCatList = contentCatDao.find(null, conditions, null);
		return contentCatList;
	}

}


