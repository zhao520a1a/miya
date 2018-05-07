<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Cache-Control" content="max-age=300"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title> 商品列表 </title>

    <meta name="Keywords" content="java"/>
    <meta name="description" content=""/>
    <link rel="stylesheet" type="text/css" href="/css/base.css" media="all"/>
    <link rel="stylesheet" type="text/css" href="/css/psearch20131008.css" media="all"/>
    <link rel="stylesheet" type="text/css" href="/css/psearch.onebox.css" media="all"/>
    <link rel="stylesheet" type="text/css" href="/css/pop_compare.css" media="all"/>
    <script type="text/javascript" src="/js/jquery-1.6.4.js"></script>
</head>
<body>
<script type="text/javascript" src="/js/base-2011.js" charset="utf-8"></script>
<!-- header start -->
<jsp:include page="commons/header.jsp"/>
<!-- header end -->

<div class="w main">
    <div class="crumb">全部结果&nbsp;&gt;&nbsp;</div>
    <div class="clr"></div>
    <div class="m clearfix" id="bottom_pager">
        <div id="pagin-btm" class="pagin fr" clstag="search|keycount|search|pre-page2">

            <c:if test="${totalPages>1}">
                <span class="prev-disabled">上一页<b></b></span>
                <c:set var="isDoing" value="0"/>
                <%--<c:set var="num" value="${totalPages}" />--%>
                <c:forEach var="index" begin="1" end="${totalPages}" step="1">
                    <c:if test="${isDoing==0}">
                        <a href="search?page=${index}">${index}</a>
                        <c:if test="${index==6}">
                            <span class="text">…</span>
                            <c:set var="isDoing" value="1"/>
                        </c:if>
                    </c:if>
                </c:forEach>
                <a href="search?keyword=java&enc=utf-8&qr=&qrst=UNEXPAND&rt=1&page=2" class="next">下一页<b></b></a>
                <span class="page-skip"><em>&nbsp;&nbsp;共${totalPages}页&nbsp;&nbsp;&nbsp;&nbsp;</em></span>
            </c:if>

        </div>
    </div>
    <div class="m psearch " id="plist">
        <ul class="list-h clearfix" tpl="2">
            <c:forEach items="${itemList}" var="item">
                <li class="item-book" bookid="11078102">
                    <div class="p-img">
                        <a target="_blank" href="http://www.miya.com:8086/item/${item.id}.html">
                            <img width="160" height="160" data-img="1" data-lazyload="${item.image}"/>
                        </a>
                    </div>
                    <div class="p-name">
                        <a target="_blank" href="http://www.miya.com:8086/item/${item.id}.html">
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
<script type="text/javascript" src="/js/jquery.hashchange.js"></script>
<script type="text/javascript" src="/js/search_main.js"></script>
<script type="text/javascript">
    //${paginator.totalPages}
    SEARCH.bottom_page_html(${page}, ${totalPages}, '');
</script>
</body>
</html>