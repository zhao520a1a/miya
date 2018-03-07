package com.miya.service.impl;

import com.miya.dto.RespObject;
import com.miya.entity.ContentCategory;
import com.miya.entity.dao.ContentCategoryDao;
import com.miya.pojo.EasyUITreeNode;
import com.miya.service.ContentCategoryService;
import com.springboot.ping.mybatis.extend.service.BaseCURDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 内容分类管理
 */
@Service
public class ContentCategoryServiceImpl extends BaseCURDService<ContentCategory,ContentCategoryDao> implements ContentCategoryService {

	@Autowired
	private ContentCategoryDao contentCategoryDao;
	@Override
	public List<EasyUITreeNode> getCategoryList(long parentId) {
		//根据parentid查询节点列表
		//执行查询
		return null;
	}
	@Override
	public RespObject<ContentCategory> insertContentCategory(long parentId, String name) {
		
		//创建一个pojo
		//'状态。可选值:1(正常),2(删除)',
		//添加记录
		//查看父节点的isParent列是否为true，如果不是true改成true
		//判断是否为true
			//更新父节点
		//返回结果
		return  null;
	}

}
