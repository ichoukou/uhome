/**
 * Created by JetBrains PhpStorm.
 * User: jVan
 * Date: 13-3-5
 * Time: 下午6:07
 * Description:
 */
$(function () {
    //倒计时
    $(".countdown").each(function () {
        var $this = $(this),
            data = $this.attr('data-config');
        $this.Merlin({
            "countDown": eval('(' + data + ')')
        });
    });
})