/**
 * Created with JetBrains WebStorm.
 * User: jVan
 * Date: 13-3-2
 * Time: 下午2:59
 * Description:
 */
$(function(){
// 购物车全反选
    var oneLevel = $(".order-list-hd :checkbox"), //购物车头部复选框
    twoLevel = $(".order-brand-top :checkbox"), //购物车内品牌头部复选框
    allCkb = $("#show :checkbox"), //购物车内所有复选框个数(含品牌头部复选框)
    cartEle = $("#show"), //购物车商品展示盒子
    cartCkb = cartEle.find(".ckb").length; //购物车内所有复选框个数(不含品牌头部复选框)
    oneLevel.click(function(){
        var $this = $(this),
        brandBox = cartEle.find(".order-brand-main tr");
        if($this.get(0).checked){
            allCkb.attr("checked",true);
            brandBox.addClass("selected");
        }
        else{
            allCkb.attr("checked",false);
            brandBox.removeClass("selected");
        }
    });
    twoLevel.click(function(){
        var $this = $(this),
            topCkbLen = $this.closest("#show").find(".order-brand-top :checkbox").length,
            brandBox = $this.closest(".order-brand-top").next(),
            itemTr = brandBox.find("tr"),
            subEle = brandBox.find(":checkbox");
        $this.prop("checked", this.checked);
        if($this.get(0).checked){
            subEle.attr("checked",true);
            itemTr.addClass("selected");
        }
        else{
            subEle.attr("checked",false);
            itemTr.removeClass("selected");
        }
        setTimeout(function(){
            var len = $this.closest("#show").find(".order-brand-top :checked").length;
            if(len === topCkbLen){
                oneLevel.attr("checked",true);
            }
            else{
                oneLevel.attr("checked",false);
            }
        },0)
    }); 
    $(".order-brand .ckb").on("change", function(){
        var $this = $(this),
        parEle = $this.closest(".order-brand"), //当前品牌父级盒子
        topCkb =  parEle.find(".order-brand-top :checkbox"), //当前品牌头部复选框
        subCkb = parEle.find(".order-brand-main :checkbox").length, //当前品牌内所有复选框个数
        brandBox = $this.closest("tr");
        $this.prop("checked", this.checked);
        if($this.get(0).checked){
            brandBox.addClass("selected");
        }
        else{
            brandBox.removeClass("selected");
        }
        setTimeout(function () {
            var len =  parEle.find(".ckb:checked").length, //当前品牌内被激活的复选框个数
                catrLen = cartEle.find(".ckb:checked").length; //购物车中所有被激活的复选框个数
            if(len === subCkb){
                topCkb.attr("checked",true);
            }
            else{
                topCkb.attr("checked",false);
            }
            if(catrLen === cartCkb){
                oneLevel.attr("checked",true);
            }
            else{
                oneLevel.attr("checked",false);
            }
        },0);
    });
//购物车单项商品删除
    $('.delete').on("click", function(){
        var $this = $(this),
            skuTd = $this.attr("data-sku"), //取点击当前删除按钮的skuid
            itemTr = $("#"+skuTd), //当前商品行
            itemId = itemTr.attr("data-item"),
            brandDiv = $("#"+itemId); //当前品牌
        var tipDialog = $.dialog({
            title: false,
            padding: "15px 20px 8px",
            content: "<span style='color:#C00;'>确定从购物车中删除此商品？</span>",
            follow: $this.get(0),
            okValue: "确定",
            ok: function(){
                if(brandDiv.find("tr").length == 1){
                    brandDiv.delay(400).fadeOut("400",function(){
                        $(this).remove();
                    });
                    if(cartEle.find(".order-brand").length == 1){
                        cartEle.delay(400).fadeOut("400",function(){
                            $(this).remove();
                            var str = "购物车已经清空了";
                            $(".order-show").append(str)
                        });
                    }
                }
                else{
                    itemTr.delay(400).fadeOut("400",function(){
                        $(this).remove();
                    });
                }
            },
            cancelValue: "取消",
            cancel: true
        });
        $(".d-close").hide();
    });
//购物车整理清空
//    $(".delete-all").on("click",function(){
//        var $this = $(this);
//        $.dialog({
//            title: false,
//            padding: "15px 20px 8px",
//            content: "<span style='color:#C00;'>确定从购物车中删除所有商品？</span>",
//            follow: $this.get(0),
//            okValue: "确定",
//            ok: function(){
//                cartEle.delay(400).fadeOut("400",function(){
//                    $(this).remove();
//                });
//            },
//            cancelValue: "取消",
//            cancel: true
//        });
//        $(".d-close").hide();
//    })
})
