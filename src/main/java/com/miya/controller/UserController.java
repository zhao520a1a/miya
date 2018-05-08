package com.miya.controller;

import com.golden.pojo.RespCode;
import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.golden.util.StringUtil;
import com.miya.dto.LoginUserDto;
import com.miya.dto.UserInputDto;
import com.miya.dto.UserOutputDto;
import com.miya.entity.User;
import com.miya.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;


    @RequestMapping("/check/{param}/{type}")
    public ResponseModal checkUserData(@PathVariable String param, @PathVariable Integer type) {
        ResponseModal result = userService.checkData(param, type);
        return result;
    }


    @RequestMapping(value = "/register", method = RequestMethod.POST)
    ResponseModal register(@RequestBody UserInputDto user) {
        //检查数据的有效性
        if (StringUtils.isBlank(user.getUsername())) {
            return ResponseModal.failMsg("用户名不能为空");
        }
        //判断密码是否为空
        if (StringUtils.isBlank(user.getPassword())) {
            return ResponseModal.failMsg("密码不能为空");
        }
        //判断用户名是否重复
        ResponseModal result = userService.checkData(user.getUsername(), 1);
        if (result.getCode().equals(RespCode.FAILURE.getKey())) {
            return ResponseModal.failMsg("用户名重复");
        }
        if (StringUtils.isNotBlank(user.getPhone())) {
            //是否重复校验
            result = userService.checkData(user.getPhone(), 2);
            if (result.getCode().equals(RespCode.FAILURE.getKey())) {
                return ResponseModal.failMsg("电话号码重复");
            }
        }
        //如果email不为空的话进行是否重复校验
        if (StringUtils.isNotBlank(user.getEmail())) {
            //是否重复校验
            result = userService.checkData(user.getEmail(), 3);
            if (result.getCode().equals(RespCode.FAILURE.getKey())) {
                return ResponseModal.failMsg("email重复");
            }
        }
        return userService.register(user);
    }


    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public RespObject<String> login(@RequestBody LoginUserDto loginUserDto) {
        log.info("username:{} pwd:{}", loginUserDto.getUsername(), loginUserDto.getPassword());
        try {
            return userService.login(loginUserDto);
        } catch (Exception e) {
            log.error("用户登录异常:{}", StringUtil.getTrace(e));
            return new RespObject<>(ResponseModal.failMsg("用户登陆异常" + e.getMessage()), null);
        }
    }


    @RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
    RespObject<UserOutputDto> getUserByToken(@PathVariable(value = "token") String token) {
        User user = userService.getUserByToken(token);
        if (user == null) {
            return new RespObject<>(ResponseModal.failMsg("密码不正确"), null);
        } else {
            UserOutputDto outputDto = UserOutputDto.builder().build();
            outputDto = outputDto.converFor(user);
            return new RespObject<>(ResponseModal.successMsg("获得Token成功"), outputDto);
        }
    }


    @RequestMapping(value = "/logout/{token}")
    ResponseModal logout(@PathVariable(value = "token") String token) {
        try {
            userService.logout(token);
        } catch (Exception e) {
            log.error("logout:{}", StringUtil.getTrace(e));
            return ResponseModal.failMsg("用户退出异常" + e.getMessage());
        }
        return ResponseModal.failMsg("用户退出成功");
    }

}
