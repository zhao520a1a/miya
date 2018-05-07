var MIYACart = {
    load: function () { // 加载购物车数据
        MIYACart.refreshTotalPrice();
    },
    itemNumChange: function () {
        $(".increment").click(function () {//＋
            var _thisInput = $(this).siblings("input");
            _thisInput.val(eval(_thisInput.val()) + 1);
            $.post("/update/num/" + _thisInput.attr("itemId") + "/" + _thisInput.val(), function (data) {
                MIYACart.refreshTotalPrice();
            });
        });
        $(".decrement").click(function () {//-
            var _thisInput = $(this).siblings("input");
            if (eval(_thisInput.val()) == 1) {
                return;
            }
            _thisInput.val(eval(_thisInput.val()) - 1);
            $.post("/update/num/" + _thisInput.attr("itemId") + "/" + _thisInput.val(), function (data) {
                MIYACart.refreshTotalPrice();
            });
        });
        $(".quantity-form .quantity-text").rnumber(1);//限制只能输入数字
        $(".quantity-form .quantity-text").change(function () {
            var _thisInput = $(this);
            $.post("/update/num/" + _thisInput.attr("itemId") + "/" + _thisInput.val(), function (data) {
                MIYACart.refreshTotalPrice();
            });
        });
    },
    refreshTotalPrice: function () { //重新计算总价
        var total = 0;

        // 购物车中商品的总价格（无论商品是否被勾选）
        // $(".quantity-form .quantity-text").each(function (i, e) {
        //         var _this =  $(".quantity-form .quantity-text");
        //         alert(_this.val());
        //         total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
        // });
        //
        // $(".totalSkuPrice").html(new Number(total / 100).toFixed(2)).priceFormat({ //价格格式化插件
        //     prefix: '￥',
        //     thousandsSeparator: ',',
        //     centsLimit: 2
        // });

        // $("input[name='checkItem']:checked").each(function () {


        $("#product-list").find(".item_form").each(function() {
            if ($(this).find(":checkbox").prop("checked") == true) {
                 var _this = $(this).find(":text");
                total += (eval(_this.attr("itemPrice")) * 10000 * eval(_this.val())) / 10000;
            }
        });


        $(".totalSkuPrice").html(new Number(total / 100).toFixed(2)).priceFormat({ //价格格式化插件
            prefix: '￥',
            thousandsSeparator: ',',
            centsLimit: 2
        });
    }
};

$(function () {
    MIYACart.load();
    MIYACart.itemNumChange();
});


$("#checkAll").click(function () {
    if (this.checked) {
        $("input[name='checkItem']:checkbox").each(function () {
            $(this).attr("checked", true);
            // $(this).prop("checked", "checked");
        });
    } else {
        $("input[name='checkItem']:checkbox").each(function () {
            $(this).attr("checked", false);
            // $(this).prop("checked", "");
        });
    }
    MIYACart.refreshTotalPrice();
});



$("input[name='checkItem']:checkbox").click(function () {
    MIYACart.refreshTotalPrice();

    var selectAllFlag = true;
    $("input[name='checkItem']:checkbox").each(function(){
        if(this.checked == false ){
            selectAllFlag = false;
            $("#checkAll").attr("checked", false);
        }
    });
    if(selectAllFlag) {
        $("#checkAll").attr("checked", true);
    }

});

