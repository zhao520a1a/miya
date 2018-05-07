<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta name="format-detection" content="telephone=no">

    <link href="/css/initcart20150123.css" type="text/css" rel="stylesheet">

    <title>商品已成功加入购物车</title>
    <style id="style-1-cropbar-clipper">
        .en-markup-crop-options {
            top: 18px !important;
            left: 50% !important;
            margin-left: -100px !important;
            width: 200px !important;
            border: 2px rgba(255, 255, 255, .38) solid !important;
            border-radius: 4px !important;
        }

        .en-markup-crop-options div div:first-of-type {
            margin-left: 0px !important;
        }
    </style>
</head>
<body class="root61">

<!-- header start -->
<jsp:include page="commons/header.jsp"/>
<!-- header end -->


<!--main start-->
<div class="w  ">
    <div class="left">
        <div class="m" id="succeed">

            <div class="corner tl"></div>
            <div class="corner tr"></div>
            <div class="corner bl"></div>
            <div class="corner br"></div>
            <div class="success">
                <div class="success-b">
                    <h3>商品已成功加入购物车！</h3>
                    <span id="flashBuy"  >商品数量有限，请您尽快下单并付款！</span>
                </div>
                <span id="initCart_next_go"> <a class="btn-1"
                                                href="javascript:showCart()"
                                                id="GotoShoppingCart">去购物车结算</a> <span class="ml10">您还可以 <a
                        class="ftx-05" href="javascript:history.back();">继续购物</a></span>
					</span>
            </div>
        </div>
        <!--succeed end-->

    </div>
</div>

<!-- footer start -->
<jsp:include page="commons/footer.jsp"/>
<!-- footer end -->


<link href="/css/miya.css" rel="stylesheet"/>
<script type="text/javascript">
window.pageConfig = {
compatible: true,
navId: "home",
enableArea: true
};
</script>
<script type="text/javascript" src="/js/home.js" charset="utf-8"></script>

</body>
</html>