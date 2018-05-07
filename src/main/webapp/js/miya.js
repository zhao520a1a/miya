var MIYA = Miya = {
    checkLogin: function () {
        var _ticket = $.cookie("MIYA_TOKEN");
        // alert("获得Token:" + _ticket);

        if (!_ticket) {
            return;
        }
        $.ajax({
            url: "http://passport.miya.com:8084/user/token/" + _ticket,
            dataType: "jsonp",
            type: "GET",
            success: function (data) {
                if (data.responseModal.code == 1) {
                    var username = data.data.username;
                    var html = username + "，欢迎来到Miya！<a href=\"http://www.miya.com:8082/user/logout\" class=\"link-logout\">[退出]</a>";
                    $("#loginbar").html(html);
                }
            }
        });


    }
};


$(function(){
	// 查看是否已经登录，如果已经登录查询登录信息
    MIYA.checkLogin();
});