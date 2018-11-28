/**
 * User: Vincent
 * Date: 13-5-3 下午3:36
 * Detail: linkage_sel地址联动
 */

var linkageSelect = function () {
    var $parent = this; // 父级对象

    // 配置
    $parent.init = function (options) {
        // 初始值
        var settings = {
            'oneSelUrl':_ctxPath+'/linkagesel/get-province.htm',
            'twoSelUrl':_ctxPath+'/linkagesel/get-city.htm?pid={oneId}',
            'threeSelUrl':_ctxPath+'/linkagesel/get-area.htm?pid={oneId}&cid={twoId}',
            'oneCallback':null,
            'twoCallback':null,
            'threeCallback':null
        };

        var opts = $.extend({}, settings, options);

        if (opts.oneSel[0]) {
            $parent.foundOne(opts);
            $parent.foundTwo(opts);
            $parent.foundThree(opts);
            $parent.setData(opts);
            // 绑定选择oneSel事件
            $(opts.oneSel[0]).off().on('change keyup', function () {
                $parent.twoSelChange($(this), opts);
            });
            // 绑定选择twoSel事件
            $(opts.twoSel[0]).off().on('change keyup', function () {
                $parent.threeSelChange($(this), opts);
            });
            // threeSel 的回调方法
            if (typeof opts.threeCallback === 'function') {
                $(opts.threeSel[0]).off().on('change keyup', function () {
                    opts.threeCallback(this);
                });
            }
        } else {
            alert('未设置联动的ID或url地址，请注意调用规则');
        }
    };

    // 创建省
    $parent.foundOne = function (opts) {
        var $oneSel = $(opts.oneSel[0]);
        if (opts.oneSel[1]) {
            $oneSel[0].options.add(new Option(opts.oneSel[1], 0));
        } else {
            $oneSel[0].options.add(new Option('--省份--', 0));
        }
    };

    // 创建市
    $parent.foundTwo = function (opts) {
        var $twoSel = $(opts.twoSel[0]);
        if (opts.twoSel[1]) {
            $twoSel[0].options.add(new Option(opts.twoSel[1], 0));
        } else {
            $twoSel[0].options.add(new Option('--城市--', 0));
        }
    };

    // 创建区
    $parent.foundThree = function (opts) {
        var $threeSel = $(opts.threeSel[0]);
        if (opts.threeSel[1]) {
            $threeSel[0].options.add(new Option(opts.threeSel[1], 0));
        } else {
            $threeSel[0].options.add(new Option('--区域--', 0));
        }
    };

    // 设置页面加载默认值
    $parent.setData = function (opts) {
        var $oneSel = $(opts.oneSel[0]),
            $twoSel = $(opts.twoSel[0]),
            $threeSel = $(opts.threeSel[0]);

        $parent.ajaxSet(opts.oneSelUrl, $oneSel, opts.oneSel[2]); // 设置省

        if (opts.oneSel[2]) {
            var url = opts.twoSelUrl.replace('{oneId}', opts.oneSel[2]);
            $parent.ajaxSet(url, $twoSel, opts.twoSel[2]); // 设置市

            if (opts.twoSel[2]) {
                var url = opts.threeSelUrl.replace('{oneId}', opts.oneSel[2]).replace('{twoId}', opts.twoSel[2]);
                $parent.ajaxSet(url, $threeSel, opts.threeSel[2]); // 设置区
            }
        }
    };

    // 请求ajax地址
    $parent.ajaxSet = function (url, id, defaults) {
        $.ajax({
            "url": url,
            "type": 'get',
            "success": function (result) {
                // 数据是否存在
                if (result) {
                    var jsonData = $.parseJSON(result.info), $sel = id[0];
                    for (var i = 0; i < jsonData.length; i ++) {
                        $sel.options.add(new Option(jsonData[i].name, jsonData[i].id));
                        if (defaults == jsonData[i].id) {
                            $sel[i + 1].selected = true; // 有默认值
                        }
                    }
                }
            },
            "error": function () {
                alert('请求错误，请稍后尝试:'+url);
            }
        });
    };

    // 变更市
    $parent.twoSelChange = function (o, opts) {
        var oid = parseInt(o.val()),
            $twoSel = $(opts.twoSel[0]),
            $threeSel = $(opts.threeSel[0]);

        // 清空twoSel/threeSel 的 select
        $twoSel.empty();
        $threeSel.empty();
        $parent.foundTwo(opts);
        $parent.foundThree(opts);

        // 不等于0
        if (oid !== 0) {
            var url = opts.twoSelUrl.replace('{oneId}', oid);
            $parent.ajaxSet(url, $twoSel);
            // oneSel 的回调方法
            if (typeof opts.oneCallback === 'function') {
                opts.oneCallback(this);
            }
        }
    };

    // 变更区
    $parent.threeSelChange = function (o, opts) {
        var tid = parseInt(o.val()),
            $oneSel = $(opts.oneSel[0]),
            $threeSel = $(opts.threeSel[0]);

        // 清空 threeSel 的 select
        $threeSel.empty();
        $parent.foundThree(opts);

        // 不等于0
        if (tid !== 0) {
            var url = opts.threeSelUrl.replace('{oneId}', $oneSel.val()).replace('{twoId}', tid)
            $parent.ajaxSet(url, $threeSel);
            // twoSel 的回调方法
            if (typeof opts.twoCallback === 'function') {
                opts.twoCallback(this);
            }
        }
    };
};