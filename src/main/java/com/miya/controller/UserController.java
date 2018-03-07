package com.miya.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.miya.dto.RespObject;
import com.miya.dto.ResponseModal;
import com.miya.dto.UserOutputDto;
import com.miya.entity.User;
import com.miya.service.impl.UserServiceImpl;
import com.miya.utils.DateTimeUtil;
import com.miya.utils.StringUtil;
import com.springboot.ping.mybatis.vo.Condition;
import com.springboot.ping.mybatis.vo.Page;
import com.springboot.ping.mybatis.vo.Pagination;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 赵金鑫
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserServiceImpl userServiceImpl;

    /* 获得用户的总数量 */
    @RequestMapping(value = "/count", method = {RequestMethod.POST, RequestMethod.GET})
    public String getUsersCount() {
//    public RespObject<Integer> getUsersCount() {
        Integer sumNum;
        try {
            Condition condition = null;
            sumNum = (int) userServiceImpl.count(condition);
            log.info("UserCount: {}", sumNum);
        } catch (Exception e) {
            log.info(StringUtil.getTrace(e));
//            return new RespObject<Integer>().failMsgAndData("获取用户数量失败", -1);
            return JSON.toJSONString(new RespObject<Integer>().failMsgAndData("获取用户数量失败", -1));
        }
        return JSON.toJSONString((new RespObject<Integer>().successData(sumNum)).toString());
    }

    /* 分页获得用户列表 */
    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public ResponseModal getUsersList(@RequestParam(value = "limit") int limit, @RequestParam(value = "offset") int offset, HttpSession httpSession) {
        log.info("分页limit:{}, offset:{}, httpSession:{}", limit, offset, httpSession);

        JSONObject usersData = new JSONObject();
        Pagination pagination = null;
        Page page = null;
        try {
            pagination = new Pagination();
            int curPage = offset / limit + 1;
            Condition condition = null;
            pagination.setTotalCount((int) userServiceImpl.count(condition));
            pagination.setPageSize(limit);
            pagination.setCurrentPage(curPage);
            page = userServiceImpl.find(pagination);

            List<User> userList = page.getRows();
            List<UserOutputDto> userOutputDtoList = new LinkedList<>();
            for (User user : userList) {
                UserOutputDto userOutputDto = UserOutputDto.builder().username(user.getUsername()).phone(user.getPhone()).email(user.getEmail()).build();
                userOutputDto.setCreate_time(DateTimeUtil.formatDate(user.getCreate_time(), "yyyy-MM-dd HH:mm:ss"));
                userOutputDto.setUpdate_time(DateTimeUtil.formatDate(user.getUpdate_time(), "yyyy-MM-dd HH:mm:ss"));
                userOutputDtoList.add(userOutputDto);
            }
            page.setRows(userOutputDtoList);
            List<String> columnList = Arrays.asList("注册日期", "用户姓名", "手机号");
            usersData.put("page", page);
            usersData.put("columns", columnList); //表头标题

            /*
             * 将数据存在Session中，主要为了前端下载时使用
             *  rowlist + firstRow
             */
            JSONObject data = new JSONObject();

            List<List<String>> dataList = new ArrayList<List<String>>();  //二维矩阵结构
            List<String> rowsList = new ArrayList<String>();
            for (UserOutputDto userOutputDto : userOutputDtoList) {
                List<String> innerDataList = new ArrayList<String>();
                innerDataList.add(userOutputDto.getCreate_time());
                innerDataList.add(userOutputDto.getUsername());
                innerDataList.add(userOutputDto.getPhone());
                dataList.add(innerDataList);
            }
            data.put("rows", rowsList);
            data.put("data", dataList);
            data.put("columns", columnList);
            httpSession.setAttribute("userList", data);  //以备下载数据时使用

        } catch (Exception e) {
            log.info(StringUtil.getTrace(e));
            return ResponseModal.failMsg("获取用户列表失败");
        }
        return ResponseModal.successMsg("获取用户列表成功");
    }


}
