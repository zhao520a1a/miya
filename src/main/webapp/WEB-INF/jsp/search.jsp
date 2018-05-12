<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Cache-Control" content="max-age=300"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>${queryStr} 商品搜索 </title>
    <meta name="Keywords" content="java"/>
    <meta name="description" content="在Miya中找到了29910件java的类似商品，其中包含了“图书”，“电子书”，“教育音像”，“骑行运动”等类型的java的商品。"/>
    <%--<link rel="stylesheet" type="text/css" href="/css/base.css" media="all"/>--%>
    <link rel="stylesheet" type="text/css" href="/css/psearch20131008.css" media="all"/>
    <%--<link rel="stylesheet" type="text/css" href="/css/psearch.onebox.css" media="all"/>--%>
    <%--<link rel="stylesheet" type="text/css" href="/css/pop_compare.css" media="all"/>--%>
    <link href="/css/miya.css" rel="stylesheet"/>

    <%--<script type="text/javascript" src="/js/jquery-1.6.4.js"></script>--%>
</head>
<body>
<!-- header start -->
<jsp:include page="commons/header.jsp"/>
<!-- header end -->
<div class="w main">
    <div class="crumb">全部结果&nbsp;&gt;&nbsp;<strong>"${queryStr}"</strong></div>
    <div class="clr"></div>
    <div class="m clearfix" id="bottom_pager">
        <div id="pagin-btm" class="pagin fr" clstag="search|keycount|search|pre-page2">

            <%--<span class="prev-disabled">上一页<b></b></span>--%>
            <%--<a href="javascript:void(0)" class="current">1</a>--%>
            <%--<a href="search?keyword=java&enc=utf-8&qr=&qrst=UNEXPAND&rt=1&page=2">2</a>--%>
            <%--<a href="search?keyword=java&enc=utf-8&qr=&qrst=UNEXPAND&rt=1&page=3">3</a>--%>
            <%--<a href="search?keyword=java&enc=utf-8&qr=&qrst=UNEXPAND&rt=1&page=4">4</a>--%>
            <%--<a href="search?keyword=java&enc=utf-8&qr=&qrst=UNEXPAND&rt=1&page=5">5</a>--%>
            <%--<a href="search?keyword=java&enc=utf-8&qr=&qrst=UNEXPAND&rt=1&page=6">6</a>--%>
            <%--<span class="text">…</span>--%>
            <%--<a href="search?keyword=java&enc=utf-8&qr=&qrst=UNEXPAND&rt=1&page=2" class="next">下一页<b></b></a>--%>
            <%--<span class="page-skip"><em>&nbsp;&nbsp;共${totalPages}页&nbsp;&nbsp;&nbsp;&nbsp;到第</em></span>--%>

                <c:if test="${totalPages>1}">
                    <c:if test="${currPage>1}">
                        <a href="http://search.miya.com:8810/search?q=${queryStr}&currPage=${currPage-1}"
                           class="prev">上一页 <b></b></a>
                    </c:if>


                    <c:set var="startIndex" value="1"/>
                    <c:set var="endIndex" value="${totalPages}"/>
                    <c:if test="${(currPage-3) > 1}">
                        <c:set var="startIndex" value="${currPage-3}"/>
                    </c:if>
                    <c:if test="${currPage+3 < totalPages}">
                        <c:set var="endIndex" value="${currPage+3}"/>
                    </c:if>

                    <c:forEach var="index" begin="${startIndex}" end="${endIndex}" step="1">
                        <c:if test="${index==currPage}">
                            <a href="http://search.miya.com:8810/search?q=${queryStr}&currPage=${index}"
                               style="color: #C38700">${index}</a>
                        </c:if>
                        <c:if test="${index!=currPage}">
                            <a href="http://search.miya.com:8810/search?q=${queryStr}&currPage=${index}">${index}</a>
                        </c:if>
                    </c:forEach>


                    <c:if test="${currPage != totalPages}">
                        <a href="http://search.miya.com:8810/search?q=${queryStr}&currPage=${currPage+1}"
                           class="next">下一页 <b></b></a>
                    </c:if>
                    <span class="page-skip"><em>&nbsp;&nbsp;共${totalPages}页&nbsp;&nbsp;&nbsp;&nbsp;</em></span>
                </c:if>

        </div>
    </div>


    <div class="m psearch " id="plist">
        <ul class="list-h clearfix" tpl="2">
            <c:forEach items="${itemList}" var="item">
                <li class="item-book" bookid="11078102">
                    <div class="p-img">
                        <a  href="javascript:showItem(${item.id})">
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
                        <strong>￥<fmt:formatNumber groupingUsed="false" maxFractionDigits="2" minFractionDigits="2"
                                                   value="${item.price / 100 }"/></strong>
                    </div>
                    <div class="service">由 Miya 发货</div>
                    <div class="extra">
                        <span class="star"><span class="star-white"><span
                                class="star-yellow h5">&nbsp;</span></span></span>
                    </div>
                </li>
            </c:forEach>
        </ul>
    </div>
</div>
<!-- footer start -->
<jsp:include page="commons/footer.jsp"/>
<!-- footer end -->
<%--<script type="text/javascript" src="/js/jquery.hashchange.js"></script>--%>
<%--<script type="text/javascript" src="/js/search_main.js"></script>--%>
<script type="text/javascript">
    <%--//${paginator.totalPages}--%>
    <%--SEARCH.query = "${queryStr}";--%>
    <%--SEARCH.bottom_page_html(${currPage}, ${totalPages}, '');--%>
</script>

<script type="text/javascript">
    window.pageConfig = {
        compatible: true,
        navId: "home",
        enableArea: true
    };
</script>
<script type="text/javascript" src="/js/home.js" charset="utf-8"></script>
<script type="text/javascript" src="/js/base-2011.js" charset="utf-8"></script>


</body>
</html>