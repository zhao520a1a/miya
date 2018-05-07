package com.miya.cart.controller;

import com.golden.pojo.ResponseModal;
import com.golden.util.CookieUtils;
import com.golden.util.JsonUtils;
import com.miya.cart.dto.ItemOutputDto;
import com.miya.cart.feign.ItemService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


/**
 * 购物车管理Controller
 */
@Slf4j
@Controller
public class ItemCartController {

    @Value("${CART_KEY}")
    private String CART_KEY;
    @Value("${CART_EXPIER}")
    private Integer CART_EXPIER;

    @Autowired
    private ItemService itemCartService;


    @RequestMapping("/add/{itemId}")
    public String addItemCart(@PathVariable(value = "itemId") String itemId,
                              @RequestParam(defaultValue = "1") Integer num, HttpServletRequest request,
                              HttpServletResponse response) {
        //取购物车商品列表
        List<ItemOutputDto> cartItemList = getCartItemList(request);
        //判断商品在购物车中是否存在
        boolean flag = false;
        for (ItemOutputDto item : cartItemList) {
            if (item.getId().equals(itemId)) {
                //如果存在数量相加
                item.setNum(item.getNum() + num);
                flag = true;
                break;
            }
        }
        //如果不存在，添加一个新的商品
        if (!flag) {
            //需要调用服务取商品信息
            ItemOutputDto item = itemCartService.getItemById(Long.parseLong(itemId)).getData();
            //设置购买的商品数量
            item.setNum(num);
            //取一张图片
            String image = item.getImage();
            if (StringUtils.isNotBlank(image)) {
                String[] images = image.split(",");
                item.setImage(images[0]);
            }
            //把商品添加到购物车
            cartItemList.add(item);
        }
        //把购物车列表写入cookie
        CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList),
                CART_EXPIER, true);
        //返回添加成功页面
        return "cartSuccess";
    }

    private List<ItemOutputDto> getCartItemList(HttpServletRequest request) {
        //从cookie中取购物车商品列表
		String json = CookieUtils.getCookieValue(request, CART_KEY, true);
        if (StringUtils.isBlank(json)) {
            //如果没有内容，返回一个空的列表
            return new ArrayList<>();
        }
        List<ItemOutputDto> list = JsonUtils.jsonToList(json, ItemOutputDto.class);
        return list;
    }

    @RequestMapping("/cart")
    public String showCartList(HttpServletRequest request) {
        //从cookie中取购物车列表
        List<ItemOutputDto> cartItemList = getCartItemList(request);
        //把购物车列表传递给jsp
        request.setAttribute("cartList", cartItemList);
        //返回逻辑视图
        return "cart";
    }

    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public ResponseModal updateItemNum(@PathVariable String itemId, @PathVariable Integer num,
                                       HttpServletRequest request, HttpServletResponse response) {
        //从cookie中取购物车列表
        List<ItemOutputDto> cartList = getCartItemList(request);
        //查询到对应的商品
        for (ItemOutputDto item : cartList) {
            if (item.getId().equals(itemId)) {
                //更新商品数量
                item.setNum(num);
                break;
            }
        }
        //把购车列表写入 cookie
        CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartList),
                CART_EXPIER, true);
        //返回成功
        return ResponseModal.success();
    }

    @RequestMapping("/delete/{itemId}")
    public String deleteCartItem(@PathVariable String itemId, HttpServletRequest request,
                                 HttpServletResponse response) {
        //从cookie中取购物车列表
        List<ItemOutputDto> cartItemList = getCartItemList(request);
        //找到对应的商品
        for (ItemOutputDto item : cartItemList) {
            if (item.getId().equals(itemId)) {
                //删除商品
                cartItemList.remove(item);
                break;
            }
        }
        //把购车列表写入cookie
        CookieUtils.setCookie(request, response, CART_KEY, JsonUtils.objectToJson(cartItemList),
                CART_EXPIER, true);
        //重定向到购物车列表页面
        return "redirect:/cart";
    }
}
