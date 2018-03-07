package com.miya.redis;


import com.miya.dto.ResponseModal;
import com.miya.entity.User;
import com.miya.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class ExampleController {

    @Autowired
    private RedisServiceImpl redisServiceImpl;


    @RequestMapping(value = "/redis/setObject", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseModal setObject() {
        try {
            User user = User.builder().username("文曰小强").password("123").build();
            redisServiceImpl.put("user", user, -1);
            return   ResponseModal.success();
        } catch (Exception e) {
            log.info("调用异常:{}",e);
        }
        return  ResponseModal.failMsg("调用异常");

    }



    @RequestMapping("/redis/set")
    public ResponseModal redisSet(@RequestParam("value") String value) {
        try {
            redisServiceImpl.put("name", value, -1);
            log.info("{}","调用redis/set成功");
            return   ResponseModal.success();
        } catch (  Exception e) {
            log.info("调用异常:{}",e);
        }
        return  ResponseModal.failMsg("调用异常");
    }
    @RequestMapping("/redis/get")
    public ResponseModal redisSet() {
        try {
            Map map = (Map) new HashMap<>().put("value", "ewq");
            HttpClientUtil.doGet("http://10.106.204.5:8080/redis/set", map);
            //HttpClientUtil.doPostSSL("http://localhost:8080/redis/set",)
            return ResponseModal.success();
        } catch (Exception e) {
            log.info("调用异常:{}", e);
        }
        return ResponseModal.failMsg("调用异常");
    }

}
