package com.miya.sso.controller;


import com.golden.pojo.RespCode;
import com.golden.pojo.RespObject;
import com.golden.pojo.ResponseModal;
import com.golden.util.CookieUtils;
import com.miya.sso.dto.LoginUserDto;
import com.miya.sso.dto.UserInputDto;
import com.miya.sso.dto.UserOutputDto;
import com.miya.sso.feign.SsoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户处理Controller
 */
@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {

    @Value("${TOKEN_KEY}")
    private String TOKEN_KEY;

    @Autowired
    private SsoService ssoService;

    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public ResponseModal checkUserData(@PathVariable String param, @PathVariable Integer type) {
        ResponseModal result = ssoService.checkData(param, type);
        return result;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseModal regitster( UserInputDto user) {
        ResponseModal result = ssoService.register(user);
        return result;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public RespObject<String> login( LoginUserDto loginUserDto,
                                    HttpServletResponse response, HttpServletRequest request) {
        RespObject<String> result = ssoService.login(loginUserDto);
        //登录成功后写cookie
        if (result.getResponseModal().getCode() == RespCode.OK.getKey()) {
            //把token写入cookie
            CookieUtils.setCookie(request, response, TOKEN_KEY, result.getData());
        }
        return result;
    }

	/*@RequestMapping(value="/user/token/{token}", method=RequestMethod.GET,
			//指定返回响应数据的content-type
			produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	public String getUserByToken(@PathVariable String token, String callback) {
		ResponseModal result = userService.getUserByToken(token);
		//判断是否为jsonp请求
		if (StringUtils.isNotBlank(callback)) {
			return callback + "(" + JsonUtils.objectToJson(result) + ");";
		}
		return JsonUtils.objectToJson(result);
	}*/


    //jsonp的第二种方法，spring4.1以上版本使用
    @RequestMapping(value = "/token/{token}", method = RequestMethod.GET)
    @ResponseBody
    public Object getUserByToken(@PathVariable(value = "token") String token, String callback) {
        RespObject<UserOutputDto> result = ssoService.getUserByToken(token);
        //判断是否为jsonp请求, 是否要进行跨域处理（响应结果，拼接一个js语句）
        if (!StringUtils.isEmpty(callback)) {
            MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(result);
            //设置回调方法
            mappingJacksonValue.setJsonpFunction(callback);
            return mappingJacksonValue;
        }
        return result;
    }


    @RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseModal logout(HttpServletRequest request) {
        String token  = CookieUtils.getCookieValue(request,TOKEN_KEY);
        return ssoService.logout(token);
    }


    @RequestMapping("/showRegister")
    public String showRegister() {
        return "register";
    }


    @RequestMapping("/showLogin")
    public String showLogin(String url, Model model) {
        model.addAttribute("redirect", url);
        return "login";
    }

}
