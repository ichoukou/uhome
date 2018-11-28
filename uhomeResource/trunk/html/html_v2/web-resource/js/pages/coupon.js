/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-5-17
 * Time: 上午10:48
 * Description: 优惠券
 */
$(function () {
    $(".coupon-sort-wp").Merlin({
        "tabSwitcher":{
            tabCls:".panel li",
            itemCls:".list",
            trigger:"click",
            effect: true
        }
    });
})