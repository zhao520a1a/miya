package com.miya.entity.dao;

import com.miya.entity.User;
import com.springboot.ping.mybatis.extend.dao.BaseCURDDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface UserDao extends BaseCURDDao<User> {

    List<UserDao> selectLastDates(@Param("num")int num, @Param("is_holiday")String is_holiday);

}