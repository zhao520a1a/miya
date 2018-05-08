package com.miya.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;


/**
 * 订单确认页面处理Controller
 */
@Slf4j
@Controller
public class UserController {


    @RequestMapping("/showMyInfo")
    public String showOrderList1(Model model, HttpServletRequest request) {
        return "my-info";
    }



}
