package com.miya.entity.dao;

import com.miya.entity.Admin;
import com.miya.entity.User;
import com.springboot.ping.mybatis.extend.dao.BaseCURDDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface AdminDao extends BaseCURDDao<Admin> {

    List<Admin> selectLastDates(@Param("num") int num, @Param("is_holiday") String is_holiday);

}