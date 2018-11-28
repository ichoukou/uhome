/**
 * Created with JetBrains PhpStorm.
 * User: admin
 * Date: 13-3-7
 * Time: 下午1:34
 * To change this template use File | Settings | File Templates.
 */
/*默认文本*/
$(function(){
    $('.input-tip').each(function(){
        $(this).Merlin({
            // 文本域 设置默认值
            "inputFocus":{"setDefault":true}
        });
    })
})