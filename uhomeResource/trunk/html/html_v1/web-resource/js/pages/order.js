/**
 * Created with JetBrains PhpStorm.
 * User: admin
 * Date: 13-3-11
 * Time: 上午11:19
 * To change this template use File | Settings | File Templates.
 */
$(function(){
    /*订单收起*/
    $('.onshow').toggle(function(){
        $('.show-td').hide();
        $('.sp1').addClass('hide');
        $('.sp2').removeClass('hide');
    },function(){
        $('.show-td').show();
        $('.sp1').removeClass('hide');
        $('.sp2').addClass('hide');
    });
})