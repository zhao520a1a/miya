package com.miya.service.impl;

import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.golden.util.JsonUtils;
import com.golden.util.ListUtil;
import com.miya.dto.LoginUserDto;
import com.miya.dto.UserInputDto;
import com.miya.entity.User;
import com.miya.entity.dao.UserDao;
import com.miya.redis.RedisService;
import com.miya.service.UserService;
import com.springboot.ping.mybatis.enums.DbType;
import com.springboot.ping.mybatis.enums.Operator;
import com.springboot.ping.mybatis.vo.Condition;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;
import java.util.UUID;


/**
 * 用户处理Service
 */
@Service
public class UserServiceImpl  implements UserService{

	@Autowired
	private UserDao userDao;
	@Autowired
	private RedisService redisService;


	@Value("${USER_SESSION}")
	private String USER_SESSION;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;

	@Override
	public ResponseModal checkData(String data, int type) {
        Condition condition;
		//设置查询条件
		//1.判断用户名是否可用
		if (type == 1) {
            condition = new Condition("username", DbType.STRING, Operator.EQ, data);
		//2判断手机号是否可以使用
		} else if (type == 2) {
            condition = new Condition("phone", DbType.STRING, Operator.EQ, data);
		//3判断邮箱是否可以使用
		} else if (type == 3) {
            condition = new Condition("email", DbType.STRING, Operator.EQ, data);
		} else {
			return ResponseModal.errorMsg("参数中包含非法数据");
		}
		//执行查询
        List<Condition> conditions = Lists.newArrayList();
        conditions.add(condition);
        List<User> list = userDao.find(null,conditions,null);
		if (list !=null && list.size() > 0) {
			return ResponseModal.failMsg("数据已存在");
		}
		//数据可以使用
		return ResponseModal.success();
	}

	@Override
	public ResponseModal register(UserInputDto userDto) {
		User user = userDto.converToUser();
		userDao.insert(user);
		return ResponseModal.success();
	}

	@Override
	public RespObject<String> login(LoginUserDto loginUserDto) {
		//判断用户名和密码是否正确
		String username = loginUserDto.getUsername();
		String password = loginUserDto.getPassword();
		String pwd = loginUserDto.getPassword();
		Condition condition = new Condition("username", DbType.STRING, Operator.EQ, username);
		List<Condition> conditions = Lists.newArrayList();
		conditions.add(condition);

		List<User> list = userDao.find(null,conditions,null);
		if (ListUtil.isEmpty(list)) {
			//返回登录失败
			return new RespObject<>(ResponseModal.failMsg("该用户不存在"),null);
		}
		User user = list.get(0);
		//密码要进行md5加密然后再校验
		if (!DigestUtils.md5DigestAsHex(password.getBytes())
				.equals(user.getPassword())) {
			//返回登录失败
			return new RespObject<>(ResponseModal.failMsg("密码不正确"),null);
		}
		//生成token，使用uuid
		String token = UUID.randomUUID().toString();
		//清空密码
		user.setPassword(null);
		//把用户信息保存到redis，key就是token，value就是用户信息;并设置key的过期时间
		redisService.set(USER_SESSION + ":" + token, JsonUtils.objectToJson(user),SESSION_EXPIRE);
		//返回登录成功，其中要把token返回。
		return new RespObject<>(ResponseModal.successMsg("登录成功"),token);
	}

	@Override
	public User getUserByToken(String token) {
		String json = redisService.get(USER_SESSION + ":" + token);
		if (StringUtils.isBlank(json)) {
			return  null;
		}
		//重置Session的过期时间
		redisService.expire(USER_SESSION + ":" + token, SESSION_EXPIRE);
		//把json转换成User对象
		User user = JsonUtils.jsonToPojo(json, User.class);
		return  user;
	}

	public void logout(String token) {
		redisService.delete(USER_SESSION + ":" + token);
	}
}