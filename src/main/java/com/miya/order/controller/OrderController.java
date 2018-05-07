package com.miya.order.controller;

import com.golden.pojo.RespCode;
import com.golden.pojo.RespList;
import com.golden.pojo.RespObject;
import com.golden.util.CookieUtils;
import com.golden.util.DateTimeUtil;
import com.golden.util.JsonUtils;
import com.golden.util.StringUtil;
import com.miya.order.dto.*;
import com.miya.order.feign.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * 订单确认页面处理Controller
 */
@Slf4j
@Controller
public class OrderController {


    @Value("${ORDERLIST_RESULT_ROWS}")
    private Integer ORDERLIST_RESULT_ROWS;

    @Value("${CART_KEY}")
    private String CART_KEY;
    @Value("${CART_EXPIER}")
    private Integer CART_EXPIER;

    @Autowired
    private OrderService orderService;


    /**
     * 展示订单确认页面
     */
    @RequestMapping("/order-cart")
    public String showOrderCart(HttpServletRequest request) {



        //用户必须是登录状态
        //取用户id
        UserOutputDto user = (UserOutputDto) request.getAttribute("user");
        //根据用户信息取收货地址列表，使用静态数据。
        //把收货地址列表取出传递给页面
        //从cookie中取购物车商品列表展示到页面
        List<ItemOutputDto> cartList = getCartItemList(request);

        List<ItemOutputDto> checkItemList = Lists.newArrayList();
        String itemIds = request.getParameter("itemIds");
        if(!StringUtil.isEmpty(itemIds) ) {
            String[] itemIdArr = itemIds.split(",");
            for (String itemId : itemIdArr) {
                for (ItemOutputDto itemOutputDto : cartList) {
                    if (itemId.equals(itemOutputDto.getId())) {
                        checkItemList.add(itemOutputDto);
                    }
                }
            }
        }

        request.setAttribute("cartList", checkItemList);
        //返回逻辑视图
        return "order-cart";
    }


    private List<ItemOutputDto> getCartItemList(HttpServletRequest request) {
        //从cookie中取购物车商品列表
        String json = CookieUtils.getCookieValue(request, CART_KEY, true);
        if (StringUtils.isEmpty(json)) {
            //如果没有内容，返回一个空的列表
            return new ArrayList<>();
        }
        List<ItemOutputDto> list = JsonUtils.jsonToList(json, ItemOutputDto.class);
        return list;
    }

    /**
     * 生成订单处理
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createOrder(OrderInfo orderInfo, Model model,HttpServletRequest request,HttpServletResponse response) {

        UserOutputDto user = (UserOutputDto) request.getAttribute("userInfo");
        orderInfo.setUser_id(user.getId());

        try {
            //生成订单
            RespObject<String> result = orderService.createOrder(orderInfo);
            //返回逻辑视图
            model.addAttribute("orderId", result.getData());
            model.addAttribute("payment", orderInfo.getPayment());
            //预计送达时间，预计三天后送达
            model.addAttribute("date", DateTimeUtil.getAddDate("yyyy-MM-dd", 3));


            //删除购物车对应的商品
            //从cookie中取购物车列表
            List<ItemOutputDto> cartItemList = getCartItemList(request);
            for(OrderItemDto  orderItemDto : orderInfo.getOrderItems()) {
                //找到对应的商品
                for (ItemOutputDto item : cartItemList) {
                    if (item.getId().equals(orderItemDto.getItem_id())) {
                        //删除商品
                        cartItemList.remove(item);
                        break;
                    }
                }
            }
            //把购车列表写入cookie
            CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList),
                    CART_EXPIER, true);

            return "success";
        } catch (Exception e) {
            log.info("创建订单出错：{}", StringUtil.getTrace(e));
            model.addAttribute("message", "创建订单出错。请稍后重试！");
            return "error/exception";
        }
    }


    /**
     * 方法描述：我的订单
     */
    @RequestMapping("/my-orders")
    public String showOrderList(@RequestParam(defaultValue = "1")int currPage, Model model, HttpServletRequest request) {
        try {
            //从Request中取用户信息
            UserOutputDto user = (UserOutputDto) request.getAttribute("userInfo");
            //调用服务
            RespObject<Page> result = orderService.showOrderList(user.getId(),ORDERLIST_RESULT_ROWS,currPage);
            if (result.getResponseModal().getCode().equals(RespCode.OK.getKey())) {
                Page<OrderInfo> page = result.getData();
                model.addAttribute("totalPages", page.getTotalPages());
                model.addAttribute("totalCount", page.getTotalCount());
                model.addAttribute("currPage", page.getPagination().getCurrentPage());
                model.addAttribute("orderInfo", page.getRows());
                return "my-orders";
            } else {
                model.addAttribute("message", "查看订单出错。请稍后重试！");
                return "error/exception";
            }
        } catch (Exception e) {
            log.info("showOrderList：{}", StringUtil.getTrace(e));
            model.addAttribute("message", "查看订单出错。请稍后重试！");
            return "error/exception";
        }
    }


    @RequestMapping("/my-order-comment")
    public String showOrderList1(Model model, HttpServletRequest request) {
        return "my-order-comment";
    }

    /**
     * 方法描述：展示订单详情
     */
    @RequestMapping("/order-items/{orderId}")
    public String showItemList(@PathVariable String orderId, Model model) {
        try {
            //调用服务
            RespList<OrderItemDto> result = orderService.showItemList(orderId);
            if (result.getResponseModal().getCode().equals(RespCode.OK.getKey())) {
                List<OrderItemDto> items = result.getData();
                model.addAttribute("itemsInfo", items);
                return "my-order-comment";
            } else {
                model.addAttribute("message", "查看订单详情出错。请稍后重试！");
                return "error/exception";
            }
        } catch (Exception e) {
            log.info("showItemList：{}", StringUtil.getTrace(e));
            model.addAttribute("message", "查看订单详情出错。请稍后重试！");
            return "error/exception";
        }
    }





}
