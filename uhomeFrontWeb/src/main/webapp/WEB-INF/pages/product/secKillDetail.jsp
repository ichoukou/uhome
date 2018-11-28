<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Product"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.common.utils.DateUtil"%>
<%@page import="java.util.Date" %>
<%@page import="net.sf.json.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${product.name }-秒杀商品详情-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/produce-detail.css?d=${_resVerion}" media="all">
</head>
<body>
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
    <div class="m-w960p">
        <!--面包屑-->
        <div class="crumbs"><a href="${_ctxPath }/default.htm">${_webSiteName} ></a><a href="${_ctxPath }/seckills.htm">秒杀商品> </a><a href="javascript:void(0);">${product.brand.name} ></a>${product.name}</div>
        <!--面包屑 end-->
        <div class="produce-top cf">
            <div class="multizoom thumbs">
           	<c:forEach items="${product.imageList }" var="image" begin="0" end="0">
                <a href="<zx:thumbImage originalPath='${image }' imageType='t10'></zx:thumbImage>" data-magsize="488,400" data-large="${_filePath}${image }" data-title="" class="cur">
                    <img height="85" width="85" src="<zx:thumbImage originalPath='${image }' imageType='t9'></zx:thumbImage>"/>
                    <i></i>
                </a>
            </c:forEach>
            <c:forEach items="${product.imageList }" var="image" begin="1">
                <a href="<zx:thumbImage originalPath='${image }' imageType='t10'></zx:thumbImage>" data-magsize="488,400" data-large="${_filePath}${image }" data-title="">
                    <img height="85" width="85" src="<zx:thumbImage originalPath='${image }' imageType='t9'></zx:thumbImage>"/>
                    <i></i>
                </a>
            </c:forEach>
            </div>
            <div class="property">
                <div class="targetarea">
                    <img id="multizoom" alt="zoomable" title="" src=""/>
                    <c:if test="${product.saleInventoryCount == 0 or product.isSellerOff}">
                    		<div class="sale-over"><!-- 售罄 --></div>
                    </c:if>
                    <c:forEach items="${product.plans }" var="plan" begin="0" end="0">
                    <div class="surplus">
                       	 剩余：<span class="countdown" data-config="{
                            'endTime':'<fmt:formatDate value="${plan.endTime }" type="both"/>',
                            'nowTime':'<%=System.currentTimeMillis()/1000 %>',
                            'stopTips':'<span>开始秒杀</span>'
                          }"></span>
                    </div>
                    </c:forEach>
                </div>
                <div class="pro-details">
                    <div class="goods-name">【${product.brand.name}】 ${product.name }</div>
                    <div class="goods-price"><span class="g-val">¥${product.secKillPrice }</span></div>
                    <div class="goods-org-item">
                        <table width="100%">
                            <thead>
                            <tr>
                                <td width="80" align="right">原价</td>
                                <td width="170" align="center">折扣</td>
                                <td>运费</td>
                            </tr>
                            </thead>
                            <tr>
                                <td align="right"><span class="coin">¥</span> ${product.marketPrice }</td>
                                <td align="center">
                                <span class="g-val">
                                <c:set value="${(product.secKillPrice / product.marketPrice)*10 }" var="secKillPrice"></c:set>
                                <c:if test="${secKillPrice < 0.1}">0.1</c:if>
                                <c:if test="${secKillPrice > 0.1}">
                                	<!-- 保留1位小数 -->
                                	<fmt:formatNumber value="${secKillPrice }" pattern="#.#" minFractionDigits="1"></fmt:formatNumber>
                                </c:if>
                                	折
                                </span>
                                </td>
                                <td><span class="coin">¥</span> 0.00（全场免邮）</td>
                            </tr>
                        </table>
                    </div>
                    <div class="goods-sort-list cf">
                        <ul>
                        <c:if test="${product.isSkuOptionValue}">
                       	<c:forEach items="${product.skuOptionValueMap }" var="entry" varStatus="status">
                            <li>
                                <div class="tit">
                                <!-- 如果有选项为空  不显示 -->
                                <c:if test="${fn:length(entry.value) > 0}">
                                	${entry.key}:
                                </c:if>
                                </div>
                                <c:if test="${fn:length(entry.value) > 0}">
                                <div class="info" id="skuOptionValueMap_${status.index }">
                               	<c:forEach items="${entry.value}" var="entryValue">
                               		<c:if test="${status.index == 0}">
                               			<span onclick="javascript:chooseColor(${entryValue.skuOptionValueId });" data-color="${entryValue.skuOptionValueId }">${entryValue.skuOptionValue }</span>
                               		</c:if>
                               		<c:if test="${status.index == 1}">
                               			<span onclick="javascript:chooseGauge(${entryValue.skuOptionValueId });" data-Gauge="${entryValue.skuOptionValueId }">${entryValue.skuOptionValue }</span>
                               		</c:if>
                                </c:forEach>
                                </div>
                                </c:if>
                            </li>
                         </c:forEach>
                         </c:if>
                            <li>
                                <div class="tit">数量:</div>
                                <div class="info">
                                    <div class="change-sum">
                                        <input id="product_num" type="text" value="1" readonly="readonly">
                                        <!-- 
                                        <a href="#" class="add"></a>
                                        <a href="#" class="reduce"></a> 
                                        -->
                                    </div>
                                    <div class="change-txt">
                                    	件（库存<i id="inventory">${product.secKillInventoryCount }</i>件）
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <c:forEach items="${product.plans }" var="plan" begin="0" end="0">
                    <c:set var="planEndTime" value="${plan.endTime }"></c:set>
                    <div class="surplus" id="killCountDown">
                       	<%--  
                       	剩余：<span class="countdown" data-config="{
                            'endTime':'<fmt:formatDate value="${plan.endTime }" type="both"/>',
                            'nowTime':'<%=System.currentTimeMillis()/1000 %>',
                            'stopTips':'<span>开始秒杀</span>'
                          }"></span> 
                          --%>
                    </div>
                    <%-- <fmt:formatDate value="${plan.endTime}" type="both" var="tmp"/> --%> 
                    </c:forEach>
                    <div class="goods-btn cf">
					<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set> 
                    <%-- 
                    <c:if test="${planEndTime.time-nowDate > 0 }">
                  		<a href="javascript:;" class="btn-c"><span>10点开秒</span></a>
                  	</c:if>
                  	<c:if test="${planEndTime.time-nowDate < 0 and nowDate - planEndTime.time <= 60000 and product.secKillInventoryCount > 0}">
                  		<a href="javascript:;" class="btn-b" id="secKill"><span>开始秒杀</span></a>
                  	</c:if>
                  	<c:if test="${nowDate - planEndTime.time > 60000 or product.secKillInventoryCount == 0 }">
                       <a href="javascript:;" class="btn-e"><span>秒杀结束</span></a>
                  	</c:if> 
                  	--%>
                  	<a href="javascript:;" class="btn-c"><span>10点开秒</span></a>
                  	<script type="text/javascript">
                  		$(function(){
                  			var nowTime = 0;
                     		var interval1 = null;
                     		var interval2 = null;
                     		var sellerOff = null;
                     		//如果为空说明没有被禁用
                     		if($.isEmptyObject(${product.isSellerOff})){
                     			sellerOff = false;
                     		}
                      		$("#killCountDown").Merlin({
                                "countDown": {
                                    "endTime": '<fmt:formatDate value="${planEndTime}" type="both"/>',
                                    "nowTime": '<%=System.currentTimeMillis()/1000 %>',
                                    "stopTips": '<span id="stopTips">活动结束谢谢参与！</span>',
                                    "callback": function(){
                                    	if(${nowDate} - ${planEndTime.time} > ${_secKillTime}){
                                    		$("#inventory").empty().append("0"); //将显示的库存变为0
                                    		var $this = $(".btn-c");
                              				$this.removeClass().addClass("btn-e");
                              				$this.attr("id",""); //将秒杀的id改为空
                              				$this.find("span").empty().append("秒杀结束");
                              				$("#stopTips").empty().append("活动结束谢谢参与!");
                              				$("#stopTips1").empty().append("活动结束谢谢参与!");
                                    		return;
                                    	}
                                    	//如果卖家被禁用  立即结束秒杀
                                    	if(sellerOff){
    	                            		var $this = $(".btn-b");
    	                      				$this.removeClass().addClass("btn-e");
    	                      				$this.attr("id",""); //将秒杀的id改为空
    	                      				$this.find("span").empty().append("秒杀结束");
    	                      				$("#stopTips").empty().append("活动结束谢谢参与!");
    	                            		return;
                                		}
                                    	var $this = $(".btn-c");
                                    	$this.removeClass().addClass("btn-b");
                                    	$this.attr("id","secKill");
                          				$this.find("span").empty().append("开始秒杀");
                          				$("#stopTips").empty().append("开始秒杀");
                          				
                                    	nowTime = ${planEndTime.time};
                                    	interval1 = setInterval(reduceTime,1000);
                              			
                              			changeSecKillBtn();
                              			interval2 = setInterval(changeSecKillBtn,1000);
                                    }
                                }
                             });
                      		/* 秒杀按钮 文字的显示 */
                      		function changeSecKillBtn(){
                      			if(nowTime - ${planEndTime.time} > ${_secKillTime} || ${product.secKillInventoryCount == 0}){
                      				$("#inventory").empty().append("0"); //将显示的库存变为0
                      				var $this = $(".btn-b");
                      				$this.removeClass().addClass("btn-e");
                      				$this.attr("id",""); //将秒杀的id改为空
                      				$this.find("span").empty().append("秒杀结束");
                      				$("#stopTips").empty().append("活动结束谢谢参与!");
                      				$("#stopTips1").empty().append("活动结束谢谢参与!");
                      				clearInterval(interval1);
                      				clearInterval(interval2);
                      			}
                      		}
                      		//时间自动减1000
                      		function reduceTime(){
                      			nowTime = nowTime + 1000;
                      		}
                  		});
                  	</script>
                  	<p class="random-see">
                      	  您可以查看<a target="_blank" href="${_ctxPath }/seckills.htm#tomorrowSecKill">明日秒杀</a><br/>
                       	 或者去<a target="_blank" href="${_ctxPath }/specials.htm">品牌特卖</a>查看特卖商品
                    </p>
                    </div>
                    <div class="share-wrap cf">
                        <span class="m-fl">分享到：</span>
                        <!-- Baidu Button BEGIN -->
						<div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare">
							<a class="bds_tsina" href="#" title="新浪微博"></a>
							<a class="bds_tqq" href="#" title="腾讯微博"></a>
							<a class="bds_qzone" href="#" title="QQ空间"></a>
							<a class="bds_mogujie" href="#" title="蘑菇街"></a>
							<a class="bds_meilishuo" href="#" title="美丽说"></a>
							<a class="bds_douban" href="#" title="豆瓣网"></a>
							<a class="bds_kaixin001" href="#" title="开心网"></a>
							<a class="bds_renren" href="#" title="人人网"></a>
							<a class="bds_taobao" href="#" title="淘江湖"></a>
						</div>
						<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=0" ></script>
						<script type="text/javascript" id="bdshell_js"></script>
						<script type="text/javascript">
							document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
						</script>
						<!-- Baidu Button END -->
                    </div>
                </div>
            </div>
        </div>
        <div class="produce-info cf">
            <div class="static-mod">
                <div class="hd">最近浏览</div>
                <div class="bd">
               	<c:forEach items="${products }" var="product">
                    <dl>
                        <dt><a href="${_ctxPath }/item-${product.productId}.htm"><img src="<zx:thumbImage originalPath='${product.defaultImage }' imageType='t11'></zx:thumbImage>" alt="${product.name }" width="75" height="75"></a></dt>
                        <dd class="link"><a href="${_ctxPath }/item-${product.productId}.htm">${product.name }</a></dd>
                        <dd class="price">¥${product.salePrice }</dd>
                    </dl>
                </c:forEach>
                </div>
            </div>
            <div class="pro-main">
                <div class="tab-gn">
                    <ul class="panel">
                        <li>商品信息<b></b></li>
                        <li>服务保障<b></b></li>
                        <li>支付方式<b></b></li>
                    </ul>
                    <div class="protab-item" style="display: block;">
                        ${product.describe }
                    </div>
                    <div class="protab-item">
                       	<div class="mod-detail">
                            <div class="hd">新龙直销服务保障</div>
                            <div class="bd">
                                <table width="100%">
                                    <tbody>
                                        <tr>
                                            <td width="54"></td>
                                            <td class="bg-td">
                                                <table width="100%">
                                                    <tbody>
                                                        <tr>
                                                            <td class="top-td"><img src="${_ctxPath }/web-resource/images/produce_true.gif">正品保障</td>
                                                        </tr>
                                                        <tr>
                                                            <td>新龙直销所售商品均为正规品牌，供应商类 型包括品牌生产商、各代理商、各分公司、 国际品牌驻中国办事处。</td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </td>
                                            <td width="130" align="center"><img src="${_ctxPath }/web-resource/images/produce_arrow.gif"></td>
                                            <td class="bg-td">
                                                <table width="100%">
                                                    <tbody>
                                                        <tr>
                                                            <td class="top-td"><img src="${_ctxPath }/web-resource/images/produce_cars.gif">全场免邮</td>
                                                        </tr>
                                                        <tr>
                                                            <td>买家在新龙直销上所购买的商品全部免邮费，买家只需要付商品费用即可。</td>
                                                        </tr>
                                                    </tbody>
                                                </table>
                                            </td>
                                            <td width="50"></td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="mod-detail">
                            <div class="hd">温馨提示</div>
                            <div class="bd">
                                <p>1.请您确保订单中的收货地址、邮编、电话、Email地址等各项信息的准确性，以便商品及时、准确地发出。</p>
                                <p>2.如果长时间未收到货，可能是由于您填写的地址或电话号码有误，您可联系新龙客服为您处理。</p>
                                <p>3.配送过程中如果我们联络您的时间超过7天未得到回复，此订单将被默认为您已经放弃订购。</p>
                            </div>
                        </div>
                    </div>
                    <div class="protab-item">
                      	  <div class="mod-detail">
                            <div class="hd">支付方式</div>
                            <div class="bd">
                                <p>目前新龙直销仅提供支付宝支付模式.</p>
                            </div>
                        </div>
                        <div class="mod-detail">
                            <div class="hd">关于发票</div>
                            <div class="bd">
                                <p>1.个人及不具有一般纳税人资格的企业客户，均开具普通发票。</p>
								<p>2.同一个入驻卖家售出的商品对应一张发票。</p>
								<p>3.发票金额不能高于订单金额，使用代金券支付的金额不开具发票。</p>
								<p>4.促销直减金额不开具发票。</p>
								<p>5.积分换购商品金额不开具发票。</p>
								<p>6.收到发票后，如发现发票抬头、内容或金额等不符，请先与商家客服中心联系确认。</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--footer.jsp放置在这-->
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
    <script type="text/javascript" src="${_jsPath }/plugin/multizoom/multizoom.js"></script>
    <script type="text/javascript" src="${_jsPath }/pages/produce_detail.js?d=${_resVerion}"></script>
    <%
	   	Product p = (Product)request.getAttribute("product");
	    String ss = p.getProductSkuMapJson();
    %>
    <script type="text/javascript">
    	var skuOptionJson = <%=ss%>;
    	var color = "";
    	var gauge = "";
    	//选择颜色
    	function chooseColor(obj){
    		color = obj;
    	}
    	//选择尺码
    	function chooseGauge(obj){
    		gauge = obj;
    	}
    	//选定当前商品属性 
    	jQuery(".goods-sort-list .info").find("span").on("click",function(){
    		var $this = jQuery(this);
    		$this.addClass("selected").siblings().removeClass("selected");
    	});
    	//秒杀
    	jQuery("#secKill").live("click",function(event){
    		var $this = $(this);
    		//选项为空
		 	 if($.isEmptyObject(skuOptionJson)){
				//如果没有规格直接获取sku
				productSkuId = ${product.productSkus[0].productSkuId}
			 //选项不为空
		 	 }else{
		 		 //根据SkuOptionValue选择skuId
			 	 productSkuId = skuOptionJson[color+"-"+gauge];
				 //有选项且用户没有选择选项
				 if(productSkuId == undefined){
					 productSkuId = skuOptionJson[color];
					 if(productSkuId == undefined && color == undefined){
						 popupDialog("请选择商品的颜色");
						 return;
					 }
					 //如果颜色没有 在验证 规格
					 if(productSkuId == undefined){
						 productSkuId = skuOptionJson[gauge];
						 if(productSkuId == undefined && gauge == undefined){
							 popupDialog("请选择商品的规格/尺码");
							 return;
						 }
					 }
					 if(productSkuId == undefined){
						 popupDialog("请选择商品的颜色或者规格/尺码");
						 return; 
					 }
				 } 
			 }
			 var type = <%=com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku.TYPE_SEC_KILL %>;
			 //通过查询库存提示是否被秒杀完了 TODO
			 //location.href = "${_ctxPath}/order/order-checkOrder.htm?type="+type+"&item.productSkuId="+productSkuId+"&item.num="+1;
			 //js 给表单赋值
			 $("#pskuId").attr("value",productSkuId);
			 //$("#num").attr("value",1);
			 var unlogin = '<%=com.ytoxl.module.uhome.uhomebase.common.CodeConstants.UNLOGIN_ERROR %>';
	         var timeout = '<%=com.ytoxl.module.uhome.uhomebase.common.CodeConstants.LOGIN_TIMEOUT_ERROR %>';
	         var forbid = '<%=com.ytoxl.module.uhome.uhomebase.common.CodeConstants.MANAGE_SELLER_FORBID %>';
	         $.ajax({
                 type:'POST',
                 url: _ctxPath + '/user/user-checkIsLogin.htm',
                 success:function(html){
                     if(html.code == "false"){
                         if(html.info == unlogin || html.info ==timeout || html.info == forbid){
                             //hack the common login
                             $this.addClass("login");
                             popupLogin($this);
                         }
                     } else {
                    	 //提交表单
                    	 $("#secDataForm").submit();
                     }

                 }
            });
		});
    	
    	//sku只有一个默认选中  1.只有颜色且只有一个颜色 2.只有规格且只有一个规格 3.2个都有且2个都只有一个选项
    	$(function(){
    		var skuOptionColor = $("#skuOptionValueMap_0");
    		var skuOptionGauge = $("#skuOptionValueMap_1");
    		//只有颜色
    		if(skuOptionColor.length > 0 && skuOptionGauge.length == 0){
    			//console.log("只有颜色");
    			var spanColor = skuOptionColor.find("span");
    			var spanColorLength = spanColor.length;
    			if(spanColorLength == 1){
    				spanColor[0].click();
    				//console.log(spanColor[0]);
    			}
    			return;
    		}
    		//只有规格
    		if(skuOptionColor.length == 0 && skuOptionGauge.length > 0){
    			//console.log("只有规格");
    			var spanGauge = skuOptionGauge.find("span");
    			var spanGaugeLength = spanGauge.length;
    			if(spanGaugeLength == 1){
    				spanGauge[0].click();
    				//console.log(spanGauge[0]);
    			}
    			return;
    		}
    		//规格颜色都有
    		if(skuOptionColor.length > 0 && skuOptionGauge.length > 0){
    			//console.log("规格颜色都有");
    			var spanColor = skuOptionColor.find("span");
    			var spanColorLength = spanColor.length;
    			var spanGauge = skuOptionGauge.find("span");
    			var spanGaugeLength = spanGauge.length;
    			if(spanColorLength == 1 && spanGaugeLength == 1){
    				spanColor[0].click();
    				spanGauge[0].click();
    				//console.log(spanColor[0]+","+spanGauge[0]);
    			}
    			return;
    		}
    	});
    </script>
   <form id="secDataForm" action="${_ctxPath}/order/order-checkOrder.htm" method="post" style="display: none;">
   		<input type="hidden" name="type" value="<%=com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku.TYPE_SEC_KILL %>" id="secType"/>
   		<input type="hidden" name="item.productSkuId" value="" id="pskuId"/>
   		<input type="hidden" name="item.num" value="1" id="num"/>
   </form>
</body>
</html>