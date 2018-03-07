package com.miya.service.impl;


import com.miya.entity.User;
import com.miya.entity.dao.UserDao;
import com.miya.service.UserService;
import com.springboot.ping.mybatis.extend.service.BaseCURDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 商品规格参数模板管理
 */
@Service
public class UserServiceImpl extends BaseCURDService<User,UserDao> implements UserService {

	@Autowired
	private UserDao userDao;



}
