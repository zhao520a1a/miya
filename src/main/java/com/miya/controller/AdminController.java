package com.miya.controller;

import com.miya.dto.AdminDto;
import com.miya.dto.LoginUserDto;
import com.miya.dto.ResponseModal;
import com.miya.entity.User;
import com.miya.service.impl.UserServiceImpl;
import com.miya.utils.StringUtil;
import com.springboot.ping.mybatis.enums.DbType;
import com.springboot.ping.mybatis.enums.Operator;
import com.springboot.ping.mybatis.vo.Condition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理员模块
 * @author 赵金鑫
 * @date 2017年10月25日
 */

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

//    String img_url = "http://10.106.204.12/group1/M00/00/00/CmrMDFoAs0yAMfD8AAUbNfBOdMg715.jpg";
    String img_url = "http://120.78.222.191/group1/M00/00/00/wKgBMFqfpXWAOjMlAANajfAuqWs627.jpg";
    AdminDto adminDto = AdminDto.builder().admin("管理员").id(11).user_name("Godlen").create_time("2017-11-11").avatar(img_url).build();

    private UserServiceImpl userService;

    // 登陆
    @RequestMapping(value = "/login", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseModal login(@RequestBody LoginUserDto loginUserDto) {
        log.info(loginUserDto.toString());
        try {
           String username = loginUserDto.getUser_name();
           String pwd = loginUserDto.getPassword();
            log.info("username:{} pwd:{}", loginUserDto.getUser_name(),loginUserDto.getPassword());
            Condition condition = new Condition("username", DbType.STRING, Operator.EQ,username);

            List<User> userList = userService.find(condition);
            for(User user: userList) {
                if(user.getPassword().equals(pwd)) {
                    return ResponseModal.successMsg("亲，Welcome you!");
                }
            }
            return ResponseModal.failMsg("用户登陆失败");
            //获得管理员的详细信息
        } catch (Exception e) {
            log.error("管理员登录:{}", StringUtil.getTrace(e));
            return ResponseModal.errorMsg("用户登陆异常");
        }
    }

    //退出
    @RequestMapping(value = "/singout", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseModal singout() {
        return ResponseModal.successMsg("用户退出成功");
    }

    //详细信息
    @RequestMapping(value = "/info" )
    public ResponseModal info() {
        log.info("获得管理员的详细信息");
        return ResponseModal.successMsg("获得管理员信息成功");
    }


    @RequestMapping(value = "/all", method = {RequestMethod.POST, RequestMethod.GET})
    public Map getAdminList(@RequestBody LoginUserDto loginUserDto) {
        log.info(loginUserDto.toString());

        Map map = new HashMap();
        map.put("status", 1);
        return map;
    }


    @RequestMapping(value = "/count", method = {RequestMethod.POST, RequestMethod.GET})
    public Map getAdminsCount(@RequestBody LoginUserDto loginUserDto) {
        log.info(loginUserDto.toString());

        Map map = new HashMap();
        map.put("status", 1);
        return map;
    }
}
