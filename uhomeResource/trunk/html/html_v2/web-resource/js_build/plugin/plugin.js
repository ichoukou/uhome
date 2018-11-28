/**
 * Creator: Merlin
 * Date created: 2013/1/24 19:03
 * Contact: QQ 77642304
 * Describe: 自定义全能插件库
 */
!function ($) {
    $.fn.Merlin = function (options) {
        // 替代eval的方法
        function jsonDecode(json, method) {
            var func = method || function (key, val) {
                    return val;
                },
                each = function(data) {
                    for (i in data) {
                        data[i] = func(i, data[i]);
                        switch (typeof data[i]) {
                            case 'undefined' : delete data[i]; break;
                            case 'object' : data[i] = each(data[i]); break;
                        }
                    }
                    return data;
                };
            return each(eval('(' + json + ')'));
        }

        // 绑定事件类型
        function bindEvents(elem, eType, events) {
            // 防止 elem 不是 jq 对象
            elem = $(elem);
            $.each(eType, function (index, val) {
                $.each(events, function (eventName, fn) {
                    elem[val].call(elem, eventName, fn);
                });
            });
        }

        // 判断IE6
        function isIe6() {
            return $.browser.msie && $.browser.version == '6.0';
        }

        // 表格hover换色
        this.trHover = function (options) {
            var settings = {
                    "eType":'on', // 事件类型
                    "trList":'.list-tr',
                    "trMCVal":false, // hover颜色值
                    "trMCName":'tr-hover' // hover变色class名
                },
                opts = $.extend({}, settings, options),
                $tr = this.find(opts.trList),
                eType = opts.eType === 'on' ? ['on'] : ['die', 'live'];

            bindEvents($tr, eType, {
                'mouseenter':function () {
                    !opts.trMCVal ? $(this).addClass(opts.trMCName) : $(this).css('backgroundColor', opts.trMCVal);
                },
                'mouseleave':function () {
                    !opts.trMCVal ? $(this).removeClass(opts.trMCName) : $(this).css('backgroundColor', '');
                }
            });

            return this;
        };

        // 表格逐行换色
        this.interlaced = function (options) {
            var settings = {
                    "trList":'.list-tr',
                    "iColorVal":false, // 隔行颜色值
                    "iClassName":'tr-blue' // 隔行颜色class名
                },
                opts = $.extend({}, settings, options),
                $tr = this.find(opts.trList);

            $tr.each(function (i) {
                if (i % 2 === 0) {
                    opts.iColorVal ? $(this).css('backgroundColor', opts.iColorVal) : $(this).addClass(opts.iClassName);
                }
            });

            return this;
        };

        // 表格下拉
        this.tablePullDown = function (options) {
            var settings = {
                    "eType":'on',
                    "derail":'.pull-down .arrow',
                    "select":'.table-select',
                    "item":'li',
                    "itemCls":'table-li-hover'
                },
                opts = $.extend({}, settings, options),
                $derail = $(opts.derail), // 获取开关按钮
                eType = opts.eType === 'on' ? ['on'] : ['die', 'live'];

            bindEvents($derail, eType, {
                "click":function () {
                    var $this = $(this),
                        $parent = $this.parent(),
                        $select = $parent.find(opts.select), // 获取需要打开的对象
                        $item = $select.find(opts.item);  // 滑动变色对象

                    // 展开
                    if ($this.data('index')) {
                        $parent.removeClass('table-up');
                        $select.slideUp();
                        $this.data('index', 0);
                    } else {
                        $parent.addClass('table-up');
                        $select.slideDown();
                        $this.data('index', 1);
                    }

                    // 离开收缩
                    $parent.on('mouseleave', function () {
                        $parent.removeClass('table-up');
                        $select.hide();
                        $this.data('index', 0);
                    });

                    // select 下拉列表的变色
                    $item.hover(function () {
                        $(this).addClass(opts.itemCls);
                    }, function () {
                        $(this).removeClass(opts.itemCls);
                    });
                }
            });
            return this;
        };

        // 全选/反选
        this.checkAll = function (options) {
            // 初始化
            var settings = {
                    "eType":'on', // 事件类型
                    "checkAll":'input[name="checkAll"]', // 全选按钮
                    "checkSub":'input[name="checkSub"]' // 子选择按钮
                },
                opts = $.extend({}, settings, options),
                $all = $(opts.checkAll), // 获取当前控制的所有同名的全选按钮
                $checkSub = $(opts.checkSub), // 获取所有子检查框
                eType = opts.eType === 'on' ? ['on'] : ['die', 'live'];

            // 绑定全选按钮事件
            bindEvents($all, eType, {
                'click':function () {
                    $checkSub.prop('checked', this.checked); // 给当前一起绑定的子选择添加效果
                    $(opts.checkAll).prop('checked', this.checked); // 给所有同名的按钮添加效果
                }
            });
            // 绑定子选按钮事件
            bindEvents($checkSub, eType, {
                'change':function () {
                    var $this = $(this);
                    setTimeout(function () {
                        var $check = $this.closest('tbody').find('input[type="checkbox"]'),
                            minLen = $check.filter('input:checked').length,
                            maxLen = $check.length;
                        // 判断被选中的个数是否等于最大数，如果等于就把全选按钮加上勾，否则去掉勾
                        minLen === maxLen ? $(opts.checkAll).prop('checked', true) : $(opts.checkAll).prop('checked', false);
                    }, 0);
                }
            });
            return this;
        };

        // 固定表头
        this.fixedTableHead = function (options) {
            var settings = {
                    'id':'gaugeOutfit',
                    'className':'table-data'
                },
                opts = $.extend({}, settings, options),
                $document = $(window, document),
                $table = this,
                $head = $table.find('thead'),
                topHeight = $table.offset().top,
                $fixed = $('#' + opts.id) || ''; // 如果对象存在

            // 判断创建的表头是否存在 不存在就创建
            if (parseInt($fixed.length) === 0) {
                // 创建新的html  // 赋值id                       // 赋值class                                  // 赋值高度
                var html = '<table id="' + opts.id + '" class="' + opts.className + '" style="display:none; height:' + $head.height() + 'px; left:' + $table.offset().left + 'px; top:0; width:' + $table.outerWidth() + 'px;">';
                html += '<thead>' + $head.html() + '</thead>'; // 将表头代码copy一份进去
                html += '</table>';
                // 将创建好的html添加到body
                $('body').append(html);
                $fixed = $('#' + opts.id); // 重新获取对象
                isIe6() ? $fixed.css('position', 'absolute') : $fixed.css('position', 'fixed');
            }

            // 绑定滚动事件
            $document.on({
                'scroll':function () {
                    scrollT(parseFloat($(this).scrollTop()));
                },
                'resize':function () {
                    if ($fixed.length) {
                        $fixed.css('left', $table.offset().left);
                    }
                }
            });
            // 动态获取滚动条滚动高度
            function scrollT(num) {
                // 当滚动条高度 大于 表头距离当前顶部的高度后，显示浮动表头。

                if (num >= topHeight) {
                    // 给 创建出来的表头动态定位
                    $fixed.show();
                    if (isIe6()) {
                        $fixed.css('top', num);
                    }
                } else {
                    // 否则删除 或者 隐藏
                    $fixed.hide();
                }
            }

            return this;
        };

        // 表格行 展开收缩
        this.foldingTable = function (options) {
            var settings = {
                    "derail":'.item-list',
                    "content":'.detail-list'
                },
                opts = $.extend({}, settings, options);

            var $derail = $(opts.derail);
            $derail.on("click", function () {
                var $self = $(this),
                    $next = $self.next(),
                    $content = $next.find(opts.content);
                if ($content.is(":hidden")) {
                    $content.slideDown(400);
                } else {
                    $content.slideUp(400);
                }
            });

            return this;
        };

        // input框操作状态改变
        this.inputFocus = function (options) {
            var settings = {
                    "textCls":'input-text',
                    "focusCls":'input-focus', // 焦点
                    "errorCls":'input-error', // 错误
                    "defaultCls":'input-default', // 默认
                    "setDefault":false,
                    "dataAttribute":'data-default'
                },
                opts = $.extend({}, settings, options);

            // 设置默认value值
            if (opts.setDefault) {
                this.val(this.attr(opts.dataAttribute)).addClass(opts.defaultCls);
            }

            this.on({
                // 获取焦点
                "focus":function () {
                    var $self = $(this);
                    $self.addClass(opts.focusCls);
                    if (opts.setDefault && $self.val() === $self.attr(opts.dataAttribute)) {
                        $self.val('').removeClass(opts.defaultCls);
                    }
                },
                // 失去焦点
                "blur":function () {
                    var $self = $(this);
                    $self.removeClass(opts.focusCls);
                    if (opts.setDefault && $.trim($self.val()) === '') {
                        $self.val($self.attr(opts.dataAttribute)).addClass(opts.defaultCls);
                    }
                }
            });

            return this;
        };

        // 输入文字文本框里的默认值消失
        this.importInput = function (options) {
            var settings = {
                    "default_box":'.key'
                },
                opts = $.extend({}, settings, options);

            var $this = $(this),
                $default_box = $(opts.default_box);

            $this.on("keyup", function () {
                if (parseInt($.trim($(this).val()).length) > 0) {
                    $default_box.hide();
                } else {
                    $default_box.show();
                }
            });

            return this;
        };

        // 选项卡
        this.tabSwitcher = function (options) {
            var settings = {
                    startIndex:0, //开始的数字
                    activeCls:"cur", //选中的样式
                    tabCls:".tab", //切换栏列表cls
                    itemCls:".tab-item", //切换块cls
                    delay:0, //延时
                    duration:5000, //自动切换时间
                    autoPlay:false, //是否执行自动
                    trigger:"click", //事件类型
                    effect:false, //效果
                    evt:null//事件
                },
                opts = $.extend({}, settings, options);

            opts.trigger = (opts.trigger == 'hover') ? 'mouseenter' : opts.trigger;

            this.each(function () {
                var $this = $(this),
                    tabs = $this.find(opts.tabCls),
                    items = $this.find(opts.itemCls),
                    current = -1,
                    timer, autoRunning;

                items.hide();

                show(opts.startIndex);

                // 是否开启自动
                if (opts.autoPlay) {
                    startPlay();
                    $this.hover(function () {
                        if (autoRunning) {
                            clearTimeout(autoRunning);
                        }
                    }, function () {
                        startPlay();
                    });
                }

                //切换延迟
                tabs.each(function (index, el) {
                    if (opts.delay > 0) {
                        $(el).hover(function () {
                            timer = setTimeout(function () {
                                show(index);
                            }, opts.delay);
                        }, function () {
                            if (timer) {
                                clearTimeout(timer);
                            }
                        });
                    } else {
                        $(el).on(opts.trigger, function () {
                            show(index);
                        });
                    }
                });

                // 显示切换内容
                function show(index) {
                    if (index != current) {
                        tabs.eq(current).removeClass(opts.activeCls);
                        items.eq(current).hide();
                        // 显示效果
                        if (opts.effect) {
                            if (opts.effect == 'slide') {
                                items.eq(index).fadeIn({
                                    queue:false,
                                    duration:300
                                });
                            } else {
                                items.eq(index).fadeIn({
                                    queue:false,
                                    duration:300
                                });
                            }
                        } else {
                            items.eq(index).show();
                        }
                        tabs.eq(index).addClass(opts.activeCls);
                        current = index;
                        if (opts.onShow) {
                            opts.onShow.apply(this, [current, tabs, items]);
                        }
                        // 是否有回调
                        if (typeof opts.evt == 'function') {
                            opts.evt.call(this);
                        }
                    }

                }

                // 显示下一个
                function showNext() {
                    var next = (current >= tabs.length - 1) ? 0 : current + 1;
                    show(next);
                }

                // 开始自动运行
                function startPlay() {
                    autoRunning = setInterval(function () {
                        showNext();
                    }, opts.duration);
                }
            });
            return this;
        };

        // 设置倒计时关闭
        this.timing = function (options) {
            // 默认属性
            var settings = {
                    "time":3,
                    "tag":'',
                    "closed":'hide',
                    "url":'',
                    "callback":null
                },
                opts = $.extend({}, settings, options),
                $timer = null,
                $this = $(this);

            if (opts.tag != '') {
                var $tag = $this.find(opts.tag);
                $tag.text(opts.time);
            }

            $timer = setInterval(function () {
                if (opts.time > 1) {
                    if (opts.tag != '') {
                        $tag.text(--opts.time);
                    } else {
                        --opts.time;
                    }
                } else {
                    clearInterval($timer);
                    // 关闭的各种效果
                    switch (opts.closed) {
                        case "hide" : {
                            $this.hide();
                        }
                            break;
                        case "remove" : {
                            $this.remove();
                        }
                            break;
                        case "refresh" : {
                            window.location.href = opts.url ? opts.url : window.location.href;
                        }
                            break;
                        case "custom" : {
                            opts.callback.call(this);
                        }
                            break;
                    }
                }
            }, 1000);

            return this;
        };

        // 倒计时
        this.countDown = function (options) {
            // 设置默认属性
            var settings = {
                    "startTime":this.attr('data-start') || 0,
                    "endTime":this.attr('data-end') || 0,
                    "nowTime":this.attr('data-now') || 0,
                    "interval":1000,
                    "minDigit":true,
                    "showDay":true,
                    "timeUnitCls":{"day":'cmp-d', "hour":'cmp-h', "min":'cmp-m', "sec":'cmp-s'},
                    "startTips":'',
                    "endTips":'',
                    "stopTips":'',
                    "timeStamp":true,
                    "timeStampTow":false, // 文字分离
                    "callback":null
                },
                opts = $.extend({}, settings, options);

            return this.each(function () {
                var $timer = null,
                    $this = $(this),
                    $block = $('<span></span>'),
                    nowTime,
                    // 匹配时间
                    startTime = isNaN(opts.startTime) ? (Date.parse(opts.startTime.replace(/-/g, '/')) / 1000) : Math.round(opts.startTime),
                    endTime = isNaN(opts.endTime) ? (Date.parse(opts.endTime.replace(/-/g, '/')) / 1000) : Math.round(opts.endTime);

                // 判断当前时间
                nowTime = opts.nowTime == 0 ? Math.round(new Date().getTime() / 1000) : Math.round(opts.nowTime);

                // 补零方法
                function addZero(isAdd, time) {
                    if (!isAdd) {
                        return time;
                    } else {
                        return time < 10 ? time = '0' + time : time;
                    }
                }

                // 天、时、分、秒
                var timeStamp = {"day" : '', "hour" : '', "min" : '', "sec" : ''};
                if (opts.timeStamp) {
                    timeStamp = {"day" : '天', "hour" : '时', "min" : '分', "sec" : '秒'};
                }

                (function remainTime() {
                    var time = {},
                        seconds,
                        word = '';

                    // 获取需要计时的毫秒数
                    seconds = nowTime < startTime ? startTime - nowTime : endTime - nowTime;
                    $this.html('');

                    // 是否显示天数
                    if (opts.showDay) {
                        time.day = addZero(opts.minDigit, Math.floor(seconds / 3600 / 24));
                        time.hour = addZero(opts.minDigit, Math.floor(seconds / 3600 % 24));
                    } else {
                        time.hour = addZero(opts.minDigit, Math.floor(seconds / 3600));
                    }
                    time.min = addZero(opts.minDigit, Math.floor(seconds / 60 % 60));
                    time.sec = addZero(opts.minDigit, Math.floor(seconds % 60));

                    // 活动开始倒计时
                    if (nowTime < startTime) {
                        if (opts.startTips) {
                            word = opts.startTips;
                        }
                    } else {
                        // 活动结束倒计时
                        if (endTime > nowTime) {
                            if (opts.endTips) {
                                word = opts.endTips;
                            }
                        }// 倒计时停止
                        else {
                            if (opts.stopTips) {
                                word = opts.stopTips;
                                $this.html(word);

                                if (typeof opts.callback === 'function') {
                                    opts.callback.call(this);
                                }
                            } else {
                                for (var i in time) {
                                    if (i == 'day' && !opts.showDay) {
                                        continue;
                                    }
                                    time[i] = 0;  // 时间置0
                                    $block.clone().addClass(opts.timeUnitCls[i]).text(opts.timeStampTow ? '<i>' + (time[i]) +'</i>' + timeStamp[i] : time[i] + timeStamp[i]).appendTo($this);
                                }
                            }
                            clearTimeout($timer);
                            return false;
                        }
                    }

                    // 写入
                    $this.html(word);
                    for (var i in time) {
                        if (i == 'day' && !opts.showDay) continue;
                        $block.clone().addClass(opts.timeUnitCls[i]).html(opts.timeStampTow ? '<i>' + (time[i]) +'</i>' + timeStamp[i] : time[i] + timeStamp[i]).appendTo($this);
                    }

                    // 累加时间
                    nowTime = nowTime + opts.interval / 1000;

                    // 循环调用
                    $timer = setTimeout(function () {
                        remainTime();
                    }, opts.interval);

                })();
            });
        };

        // 幻灯片效果
        this.slider = function (options) {
            var settings = {
                    "effect":'x', //效果  水平 - x | 垂直 - y | 渐隐 - fade | 隐藏 - none
                    "speed":600, //动画速度
                    "space":5000, //时间间隔
                    "auto":true, //自动滚动
                    "trigger":'mouseenter', //触发事件
                    "content_box":'.contents', //内容容器id或class
                    "content_tag":'li', //内容标签 默认为<li>
                    "switcher_box":'.switchers', //内容标签 默认为<li>
                    "switcher_tag":'li', //切换器标签 默认为<li>
                    "active_class":'active', //当前切换器样式名称 不含"."
                    "prev":'prev', //上一个幅箭头样式名称
                    "next":'next', //下一个幅箭头样式名称
                    "rand":false, //是否随机指定默认幻灯页
                    "callback":null // 回调函数
                    /*
                     *TODO  不倒序滚动
                     *recycle:true,
                     */
                },
                opts = $.extend({}, settings, options);

            var index = 1,
                last_index = 0;
            var $content_box = $(this).find(opts.content_box),
                $content_tag = $content_box.find(opts.content_tag);
            var $switcher = $(this).find(opts.switcher_box),
                $switcher_tag = $switcher.find(opts.switcher_tag);

            // 随机
            if(opts.rand) {
                index = Math.floor(Math.random() * $content_tag.length);
                slide();
            }
            // 判断是否渐隐渐显效果
            if(opts.effect == 'fade') {
                $.each($content_tag, function(k, v) {
                    (k === 0) ? $(this).css({
                        'position':'absolute',
                        'z-index':9
                    }) : $(this).css({
                        'position':'absolute',
                        'z-index':1,
                        'opacity':0
                    });
                });
            }
            // 开始执行
            function slide() {
                if (index >= $content_tag.length) {
                    index = 0;
                }

                // 按钮样式
                $switcher_tag.removeClass(opts.active_class).eq(index).addClass(opts.active_class);

                // 选择效果
                switch (opts.effect) {
                    // 水平
                    case 'x': {
                            $content_box.width($content_tag.length * $content_tag.width());
                            $content_box.stop().animate({
                                left:-parseFloat($content_tag.width()) * index
                            }, opts.speed);
                        }
                        break;
                    // 垂直
                    case 'y': {
                            $content_tag.css("display", 'block');
                            $content_box.stop().animate({
                                "top":-parseFloat($content_tag.height()) * index
                            }, opts.speed);
                        }
                        break;
                    // 渐隐
                    case 'fade': {
                            $content_tag.eq(last_index).stop().animate({
                                'opacity':0
                            }, opts.speed / 2).css('z-index', 1)
                                .end()
                                .eq(index).css('z-index', 9).stop().animate({
                                    'opacity':1
                                }, opts.speed / 2);
                        }
                        break;
                    // 隐藏
                    case 'none': {
                            $content_tag.hide().eq(index).show();
                        }
                        break;
                }
                last_index = index;
                index++;
                // 是否有回调函数
                if (typeof opts.callback === 'function') {
                    opts.callback.call(this);
                }
            }

            opts.trigger = opts.trigger === 'mouseover' || opts.trigger === 'hover' ? "mouseenter" : opts.trigger;

            // 标签切换按钮
            $switcher_tag.on(opts.trigger, function () {
                pause();
                index = $(this).index();
                slide();
                _continue();
            });
            // 左右箭头
            $(this).find('.arrow').on("click", function (evt) {
                pause();
                if (!evt) {
                    evt = window.event;
                }
                // 上一个
                if (evt.target.className == opts.prev) {
                    if (!last_index) {
                        return false;
                    }
                    index = --last_index;
                    slide();
                }
                // 下一个
                if (evt.target.className == opts.next) {
                    slide();
                }
                _continue();
            });
            // 是否自动
            if (opts.auto) {
                var timer = setInterval(slide, opts.space);
            }
            // 触发悬停事件
            $content_box.hover(pause, _continue);
            // 暂停
            function pause() {
                clearInterval(timer);
            }
            // 继续
            function _continue() {
                if (opts.auto) {
                    timer = setInterval(slide, opts.space);
                }
            }

            return this;
        };

        var $parent = this; // 获取当前调用Merlin的对象

        // 遍历需要开启的功能插件
        $.each(options, function (name, opts) {
            $parent[name](opts);
        });

        return this;
    }
}(jQuery);