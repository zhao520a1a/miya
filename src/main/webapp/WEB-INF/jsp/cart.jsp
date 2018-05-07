<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="format-detection" content="telephone=no">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta name="format-detection" content="telephone=no">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

    <link rel="stylesheet" href="/css/base.css">
    <link rel="stylesheet" type="text/css" href="/css/purchase.base.2012.css"/>
    <link href="/css/purchase.2012.css?v=201410141639" rel="stylesheet" type="text/css">
    <title>我的购物车 - Miya商城</title>
    <script>
        var pageConfig = {};
    </script>
<body class="root61">
<!--shortcut start-->
<jsp:include page="commons/shortcut.jsp"/>
<!--shortcut end-->
<%--<div class="w w1 header clearfix">--%>
<div class="w">

    <div class="w w1 header clearfix " id="headers">
        <div id="logo"><a href="javascript:index()">
            <%--<img clstag="clickcart|keycount|xincart|logo"   src="/images/miya-logo.gif"     title="返回Miya商城首页" alt="返回Miya商城首页"></a>--%>
            返回Miya商城首页
        </a>
        </div>

        <ul class="step" id="step1">
            <li class="fore1">1.我的购物车<b></b></li>
            <li class="fore2">2.填写核对订单信息<b></b></li>
            <li class="fore3">3.成功提交订单</li>
        </ul>
    </div>
    <div class="w cart">
        <div class="cart-hd group">
            <h2>我的购物车</h2>
        </div>
        <div id="show">

            <div class="cart-frame">
                <div class="tl"></div>
                <div class="tr"></div>
            </div>
            <div class="cart-inner">
                <div class="cart-thead clearfix">
                    <div class="column t-checkbox form">
                        <%--<input data-cart="toggle-cb" name="toggle-checkboxes" id="toggle-checkboxes_up" type="checkbox" checked="" value=""><label for="toggle-checkboxes_up">全选</label>--%>
                        <input data-cart="toggle-cb" name="checkAll" id="checkAll" type="checkbox">
                        <label for="checkAll">全选
                        </label>
                    </div>
                    <div class="column t-goods">商品</div>
                    <div class="column t-price">Miya价</div>
                    <div class="column t-promotion">优惠</div>
                    <div class="column t-inventory">库存</div>
                    <div class="column t-quantity">数量</div>
                    <div class="column t-action">操作</div>
                </div>
                <div id="product-list" class="cart-tbody">
                    <!-- ************************商品开始********************* -->
                    <c:set var="totalPrice" value="0"></c:set>
                    <c:forEach items="${cartList}" var="cart">
                        <%--<c:set var="totalPrice" value="${ totalPrice + (cart.price * cart.num)}"/>--%>
                        <c:set var="totalPrice"  />
                        <div  data-bind="rowid:1" class="item item_selected ">
                            <div class="item_form clearfix">
                                <div class="cell p-checkbox">
                                        <%--<input data-bind="cbid:1" class="checkbox" type="checkbox" name="checkItem" checked="" value="11345721-1">--%>
                                    <input class="checkbox" type="checkbox" name="checkItem"  value="${cart.id}">
                                </div>
                                <div class="cell p-goods">
                                    <div class="p-img">
                                        <a href="javascript:showItem(${cart.id})" target="_blank">
                                            <img clstag="clickcart|keycount|xincart|p-imglistcart" src="${cart.image}"
                                                 alt="${cart.title}" width="52" height="52">
                                        </a>
                                    </div>
                                    <div class="p-name">
                                        <a href="javascript:showItem(${cart.id})"
                                           clstag="clickcart|keycount|xincart|productnamelink"
                                           target="_blank">${cart.title}</a>
                                        <span class="promise411 promise411_11345721" id="promise411_11345721"></span>
                                    </div>
                                </div>
                                <div class="cell p-price">
                                    <span class="price">¥<fmt:formatNumber groupingUsed="false"
                                                                           value="${cart.price / 100}"
                                                                           maxFractionDigits="2"
                                                                           minFractionDigits="2"/>
                                    </span>
                                </div>
                                <div class="cell p-promotion">
                                </div>
                                <div class="cell p-inventory stock-11345721">有货</div>
                                <div class="cell p-quantity" for-stock="for-stock-11345721">
                                    <div class="quantity-form" data-bind="">
                                        <a href="javascript:void(0);" class="decrement"
                                           clstag="clickcart|keycount|xincart|diminish1" id="decrement">-</a>
                                        <input type="text" class="quantity-text" itemPrice="${cart.price}"
                                               itemId="${cart.id}" value="${cart.num}"
                                               id="changeQuantity">
                                        <a href="javascript:void(0);" class="increment"
                                           clstag="clickcart|keycount|xincart|add1" id="increment">+</a>
                                    </div>
                                </div>
                                <div class="cell p-remove"><a id="remove-11345721-1" data-more="removed-87.20-1"
                                                              clstag="clickcart|keycount|xincart|btndel318558"
                                                              class="cart-remove"
                                                              href="javascript:deleteCart(${cart.id})">删除</a>
                                </div>
                            </div>
                        </div>
                    </c:forEach>

                </div><!-- product-list结束 -->
                <div class="cart-toolbar clearfix">
                    <div class="total fr">
                        <p><span class="totalSkuPrice">¥<fmt:formatNumber value="${totalPrice / 100}"
                                                                          maxFractionDigits="2"
                                                                          minFractionDigits="2"
                                                                          groupingUsed="true"/></span>总计：
                        </p>
                        <p><span id="totalRePrice">- ¥0.00</span>优惠：</p>
                    </div>
                    <%--<div class="amout fr"><span id="selectedCount">0</span> 件商品</div>--%>
                </div>
                <div class="ui-ceilinglamp-1" style="width: 1208px; height: 49px;">
                    <div class="cart-dibu ui-ceilinglamp-current" style="width: 1208px; height: 49px;">
                        <div class="control fdibu fdibucurrent">
                     <span class="column t-checkbox form">
                     <%--<input data-cart="toggle-cb" name="toggle-checkboxes" id="toggle-checkboxes_down" type="checkbox"--%>
                         <%--checked="" value="" class="jdcheckbox">--%>
                     <%--<label for="toggle-checkboxes_down">--%>
                          <%--全选--%>
                     <%--</label>--%>
                     </span>
                            <%--<span class="delete">--%>
                            <%--<b>--%>
                            <%--</b>--%>
                            <%--<a href="javascript:void(0);" clstag="clickcart|keycount|xincart|clearcartlink" id="remove-batch">--%>
                            <%--删除选中的商品--%>
                            <%--</a>--%>
                            <%--</span>--%>

                            <span class="shopping">
                          <b></b>
                            <a class="ftx-05" id="continue" clstag="clickcart|keycount|xincart|coudanlink"
                               href="javascript:index()">继续购物</a>
                          </span>
                        </div>
                        <div class="cart-total-2014">
                            <div class="cart-button">
                  <span class="check-comm-btns" id="checkout-jd">
                      <a class="checkout" href="javascript:goToOrder();"
                         clstag="clickcart|keycount|xincart|gotoOrderInfo" id="toSettlement">去结算<b></b></a>
                  </span>
                                <span class="combine-btns" style="display:none">
                        <span class="fore1" style="display: none;">
                          <a href="" class="combine-btn">不支持合并付款</a>
                      </span>
                      <span class="fore2 hide" style="display: inline;">
                          <a href="javascript:goToOverseaOrder();" class="checkout-jdInt">去Miya国际结算<b></b></a>
                          <a href="javascript:goToOrder();" class="checkout-jd">去Miya结算<b></b></a>
                      </span>
                  </span>
                            </div>
                            <div class="total fr">
                                总计（不含运费）：
                                <span class="totalSkuPrice">¥<fmt:formatNumber value="${totalPrice / 100}"
                                                                               maxFractionDigits="2"
                                                                               minFractionDigits="2"
                                                                               groupingUsed="true"/></span>
                            </div>
                        </div>
                    </div>
                </div>
            </div><!-- cart-inner结束 -->
        </div>
    </div>

    <!--推荐位html修改处-->


</div>


<!-- footer start -->
<jsp:include page="commons/footer.jsp"/>
<!-- footer end -->

<!-- 购物车相关业务 -->
<script type="text/javascript" src="/js/cart.js"></script>
<script type="text/javascript" src="/js/jquery.price_format.2.0.min.js"></script>


<link href="/css/miya.css" rel="stylesheet"/>
<script type="text/javascript">
    window.pageConfig = {
        compatible: true,
        navId: "home",
        enableArea: true
    };
</script>
<script type="text/javascript" src="/js/home.js" charset="utf-8"></script>


</html>