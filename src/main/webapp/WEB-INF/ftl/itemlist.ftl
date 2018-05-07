<#assign base = request.contextPath />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <base id="base" href="${base}">

    <title> 商品列表 - Miya</title>

    <meta name="Keywords" content="java"/>
    <meta name="description" content=""/>
    <link rel="stylesheet" type="text/css" href="/css/base.css" media="all"/>
    <link rel="stylesheet" type="text/css" href="/css/psearch20131008.css" media="all"/>
    <link rel="stylesheet" type="text/css" href="/css/psearch.onebox.css" media="all"/>
    <link rel="stylesheet" type="text/css" href="/css/pop_compare.css" media="all"/>
    <script type="text/javascript" src="/js/jquery-1.6.4.js"></script>


    <script>var jdpts = new Object();
    jdpts._st = new Date().getTime();</script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <link rel="stylesheet" type="text/css" href="${base}/css/miya.css" media="all"/>
    <link rel="stylesheet" type="text/css" href="${base}/css/pshow.css" media="all"/>
    <script type="text/javascript">
        window.pageConfig = {
            compatible: true,
            product: {
                skuid: 1221882,
                name: '\u957f\u8679\uff08\u0043\u0048\u0041\u004e\u0047\u0048\u004f\u004e\u0047\uff09\u004c\u0045\u0044\u0034\u0032\u0035\u0033\u0038\u0045\u0053\u0020\u0034\u0032\u82f1\u5bf8\u0020\u7a84\u8fb9\u84dd\u5149\u004c\u0045\u0044\u6db2\u6676\u7535\u89c6\uff08\u9ed1\u8272\uff09',
                skuidkey: 'E804B1D153D29E36088A33A134D85EEA',
                href: 'http://item.jd.com/1221882.html',
                src: 'jfs/t304/157/750353441/93159/e4ee9876/54227256N20d4f5ec.jpg',
                cat: [737, 794, 798],
                brand: 20710,
                nBrand: 20710,
                tips: false,
                type: 1,
                venderId: 0,
                shopId: '0',
                TJ: '0',
                specialAttrs: ["HYKHSP-0", "isHaveYB", "isSelfService-0", "isWeChatStock-0", "isCanUseJQ", "isOverseaPurchase-0", "YuShou", "is7ToReturn-1", "isCanVAT"],
                videoPath: '',
                HM: '0'
            }
        };
    </script>
</head>
<body version="140120">
<script type="text/javascript">try {
    (function (flag) {
        if (!flag) {
            return;
        }
        if (window.location.hash == '#m') {
            var exp = new Date();
            exp.setTime(exp.getTime() + 30 * 24 * 60 * 60 * 1000);
            document.cookie = "pcm=1;expires=" + exp.toGMTString() + ";path=/;domain=jd.com";
            return;
        } else {
            var cook = document.cookie.match(new RegExp("(^| )pcm=([^;]*)(;|$)"));
            var flag = false;
            if (cook && cook.length > 2 && unescape(cook[2]) == "1") {
                flag = true;
            }
        }
        var userAgent = navigator.userAgent;
        if (userAgent) {
            userAgent = userAgent.toUpperCase();
            if (userAgent.indexOf("PAD") > -1) {
                return;
            }
            var mobilePhoneList = ["IOS", "IPHONE", "ANDROID", "WINDOWS PHONE"];
            for (var i = 0, len = mobilePhoneList.length; i < len; i++) {
                if (userAgent.indexOf(mobilePhoneList[i]) > -1) {
                    var url = "http://m.jd.com/product/" + pageConfig.product.skuid + ".html";
                    if (flag) {
                        pageConfig.product.showtouchurl = true;
                    } else {
                        window.location.href = url;
                    }
                    break;
                }
            }
        }
    })((function () {
        var json = {"6881": 3, "1195": 3, "10011": 3, "6980": 3, "12360": 3};
        if (json[pageConfig.product.cat[0] + ""] == 1 || json[pageConfig.product.cat[1] + ""] == 2 || json[pageConfig.product.cat[2] + ""] == 3) {
            return false;
        } else {
            return true;
        }
    })());
} catch (e) {
}</script>



<!-- header start -->
<#include "commons/header.ftl" />
<!-- header end -->


<div class="w main">
    <div class="crumb">全部结果&nbsp;&gt;&nbsp;</div>
    <div class="clr"></div>
    <div class="m clearfix" id="bottom_pager">
        <div id="pagin-btm" class="pagin fr" clstag="search|keycount|search|pre-page2">


	<#if totalPages gt 1>
        <#if page.pagination.currentPage != 1>
             <a href="http://item.miya.com:8086/products/${itemCatId}.html?currPage=${page.pagination.currentPage-1}"
                class="prev">上一页 <b></b></a>
              <span class="text">…</span>
        </#if>

         <#if page.pagination.currentPage-3 gt 1  >
             <#assign startIndex = page.pagination.currentPage-3>
         <#else >
             <#assign startIndex = 1>
         </#if>
         <#if page.pagination.currentPage+3 lt   totalPages >
             <#assign endIndex = page.pagination.currentPage+3 >
         <#else >
             <#assign endIndex = totalPages>
         </#if>

        <#list startIndex .. endIndex as index>
            <#if page.pagination.currentPage == index >
        <a href="http://item.miya.com:8086/products/${itemCatId}.html?currPage=${index}"
           style="color: darkorange">${index}</a>
            <#else>
        <a href="http://item.miya.com:8086/products/${itemCatId}.html?currPage=${index}">${index}</a>
            </#if>
        </#list>
        <span class="text">…</span>
        <#if page.pagination.currentPage  != totalPages>
            <a href="http://item.miya.com:8086/products/${itemCatId}.html?currPage=${page.pagination.currentPage+1}"
               class="next">下一页 <b></b></a>
        </#if>

        <span class="page-skip"><em>&nbsp;&nbsp;共${totalPages}页&nbsp;&nbsp;&nbsp;&nbsp;</em></span>
    </#if>
        </div>
    </div>
    <div class="m psearch " id="plist">
        <ul class="list-h clearfix" tpl="2">
<#list itemList as item>
    <li class="item-book" bookid="11078102">
        <div class="p-img">
            <a href="javascript:showItem(${item.id})">
                <img width="160" height="160" data-img="1" data-lazyload="${item.image}"/>
            </a>
        </div>
        <div class="p-name">
            <a href="javascript:showItem(${item.id})">
                ${item.title}
            </a>
        </div>
        <div class="p-price">
            <i>Miya价：</i>
            <strong>￥ ${item.price / 100.0} </strong>
        </div>
        <div class="service">由 Miya 发货</div>
        <div class="extra">
        <#--<span class="star"><span class="star-white"><span class="star-yellow h5">&nbsp;</span></span></span>-->
            <span class="star sa4"> </span>
        </div>
    </li>
</#list>
        </ul>
    </div>
</div>
<!-- footer start -->
<#include "commons/footer.ftl" />
<!-- footer end -->

<script type="text/javascript" src="/js/home.js" charset="utf-8"></script>

<script type="text/javascript" src="../../js/jquery-1.6.4.js"></script>
<script type="text/javascript" src="/js/lib-v1.js"></script>
<script type="text/javascript" src="/js/product.js"></script>
<script type="text/javascript" src="/js/iplocation_server.js"></script>

<script type="text/javascript" src="/js/jquery.hashchange.js"></script>
<script type="text/javascript" src="/js/search_main.js"></script>
<script type="text/javascript">
    <#--//${paginator.totalPages}-->
    SEARCH.bottom_page_html(${page},${totalPages}, '');
</script>

</body>
</html>