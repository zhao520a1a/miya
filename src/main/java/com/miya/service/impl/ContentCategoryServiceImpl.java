package com.miya.service.impl;

import com.miya.common.pojo.EUTreeNode;
import com.miya.dto.RespObject;
import com.miya.dto.ResponseModal;
import com.miya.entity.ContentCategory;
import com.miya.entity.dao.ContentCategoryDao;
import com.miya.service.ContentCategoryService;
import com.springboot.ping.mybatis.extend.service.BaseCURDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 内容分类管理
 */
@Service
public class ContentCategoryServiceImpl extends BaseCURDService<ContentCategory,ContentCategoryDao> implements ContentCategoryService {


	@Autowired
	private ContentCategoryDao contentCategoryDao;


	@Override
	public List<EUTreeNode> getContentCategoryList(long parentId) {
		//根据parentId查询子节点列表
		ContentCategory contentCategory = new ContentCategory();
		//补全对象的属性
		contentCategory.setParent_id(parentId);
		//执行查询
		List<ContentCategory> list = (List<ContentCategory>) contentCategoryDao.findByPk(contentCategory);
		List<EUTreeNode> resultList = new ArrayList<>();
		for (ContentCategory tbContentCategory : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIs_parent()?"closed":"open");
			//添加到结果列表
			resultList.add(node);
		}
		return resultList;
	}

	@Override
	public RespObject<ContentCategory> addContentCategory(long parentId, String name) {
		//创建一个pojo对象
		ContentCategory contentCategory = new ContentCategory();
		//补全对象的属性
		contentCategory.setParent_id(parentId);
		contentCategory.setName(name);
		//状态。可选值:1(正常),2(删除)
		contentCategory.setStatus(1);
		//排序，默认为1
		contentCategory.setSort_order(1);
		contentCategory.setIs_parent(false);
		contentCategory.setCreate_time(new Date());
		contentCategory.setUpdate_time(new Date());
		//插入到数据库
		contentCategoryDao.insert(contentCategory);
		//判断父节点的状态
		ContentCategory parent = new ContentCategory();
		//补全对象的属性
		parent.setParent_id(parentId);
		  parent = contentCategoryDao.findByPk(parent);
		if (!parent.getIs_parent()) {
			//如果父节点为叶子节点应该改为父节点
			parent.setIs_parent(true);
			//更新父节点
			contentCategoryDao.updateByPk(parent);
		}

		//返回结果
		return new RespObject<ContentCategory>(ResponseModal.success() ,contentCategory);
	}

}


