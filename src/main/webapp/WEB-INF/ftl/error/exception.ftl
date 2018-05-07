<#assign base = request.contextPath />

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>出错了 -Miya商城</title>
    <link rel="stylesheet" type="text/css" href="/css/miya.css" media="all"/>

    <style type="text/css">
        #refresh {
            text-align: left;
            margin: 30px auto;
            width: exception . jsppx;
            height: 500px;
            background: url(http://z5.tuanimg.com/v1/global/img/p404.jpg) no-repeat 0 0;
            /*url(/images/errordog.jpg)*/
            /*0 0;*/
            position: relative
        }

        #refresh .txt {
            position: absolute;
            left: 236px;
            top: 16px;
            color: #fff;
            font-size: 14px;
            font-family: "microsoft yahei"
        }

        #refresh .m {
            position: absolute;
            left: 336px;
            top: 80px;
            line-height: 18px;
            font-size: 14px
        }

        #refresh .m li {
            padding-bottom: 8px
        }

        #refresh .m a {
            color: #005eab
        }

        #refresh .m .fore1, #refresh .m .fore2 {
            font-family: "microsoft yahei"
        }

        #refresh .m .fore1 a {
            color: #e4393c
        }

        #refresh .m .fore2 a {
            color: #e4393c;
            font-weight: bold;
            font-size: 18px;
            padding: 0 3px
        }

        #refresh .m .fore3 {
            font-weight: bold;
            font-size: 12px
        }

        #refresh .m .fore4 a {
            margin-right: 15px;
            font-size: 12px
        }

    </style>
</head>
<body id="mainframe">

<!-- header start -->
<#include "../commons/header.ftl" />
<!-- header end -->

<div class="w">
    <div id="refresh">
        <span class="txt">糟了...系统出错了...</span>
        <ul class="m">
            <li class="fore1">您可以：稍后再试或联系客服400-1111-2222。
            </li>
            <li class="fore2">返回<a href="javascript:index()">Miya首页</a></li>
            <li class="fore3">错误消息</li>
            <li class="fore4">${message!}</li>
        </ul>
    </div>
</div>


<!-- footer start -->
<#include "../commons/footer.ftl" />
<!-- footer end -->
</body>

<script type="text/javascript">
    window.pageConfig = {
        compatible: true,
        navId: "home",
        enableArea: true
    };
</script>
<script type="text/javascript" src="/js/home.js" charset="utf-8"></script>



</html>