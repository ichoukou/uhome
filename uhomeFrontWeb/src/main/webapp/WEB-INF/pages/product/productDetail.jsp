<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Product"%>
<%@page import="net.sf.json.JSONObject" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<cache:cache refresh="${_needRefresh}" key="seo-product-${product.productId}" cron="${_seoRefreshCron}">
	<c:if test="${empty seoTitle }">
		<title>【${product.rebate }折】，${product.brand.name}_${product.name }，${_webSiteName}特卖商城</title>
	</c:if>
	<c:if test="${!empty seoTitle  }">
		<title>${seoTitle}</title>
	</c:if>
	
	<c:if test="${empty seoKeyWords }">
		<meta name="keywords" content="${product.brand.name}，${product.name }，特卖，${_webSiteName}">
	</c:if>
	<c:if test="${!empty seoKeyWords }">
		<meta name="keywords" content="${seoKeyWords}">
	</c:if>
	
	<c:if test="${empty seoDescription }">
		<meta name="description" content="${_webSiteName}${_domainName}名牌时尚折扣网提供${product.brand.name} ${product.name }，${product.rebate }折的价格、图片、最新款式等信息，${product.brand.name}特卖，100%正品保障。">
	</c:if>
	<c:if test="${!empty seoDescription }">
		<meta name="description" content="${seoDescription}">
	</c:if>
</cache:cache>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/produce-detail.css?d=${_resVerion}" media="all">
</head>
<body>
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
    <div class="m-w960p">
        <!--面包屑-->
        <div class="crumbs"><a href="${_domain}">${_webSiteName} ></a><c:if test="${not empty linkArr}"><a href="${_domain}${linkArr[0]}/">${linkArr[1]} ></a></c:if><c:if test="${not empty product.plans}"><a href="${_domain}plan-${product.plans[0].planId}.htm">${product.plans[0].name} ></a></c:if>${product.name}</div>
        <!--面包屑 end-->
        <div class="produce-top cf">
        <cache:cache time="${_cacheSec}" refresh="${_needRefresh}" key="product-${product.productId}">
            <div class="multizoom thumbs">
            <c:forEach items="${product.imageList }" var="image" begin="0" end="0">
            	<%-- <c:set var="_defaultImage" value="<zx:thumbImage originalPath='${image}' imageType='t10'/>"></c:set> --%>
                <a href="<zx:thumbImage originalPath='${image }' imageType='t10'></zx:thumbImage>" data-magsize="468,400" data-large="${_filePath}${image }" data-title="${product.name }" class="cur">
                    <img height="85" width="85" src="<zx:thumbImage originalPath='${image }' imageType='t9'></zx:thumbImage>"/>
                    <i></i>
                </a>
            </c:forEach>
            <c:forEach items="${product.imageList }" var="image" begin="1">
                <a href="<zx:thumbImage originalPath='${image }' imageType='t10'></zx:thumbImage>" data-magsize="488,400" data-large="${_filePath}${image }" data-title="${product.name }">
                    <img height="85" width="85" src="<zx:thumbImage originalPath='${image }' imageType='t9'></zx:thumbImage>"/>
                    <i></i>
                </a>
            </c:forEach>
            </div>
         </cache:cache>
            <div class="property">
            <!-- 如果商品没有下架 -->
            <c:if test="${!product.isOutOfStock }">
                <div class="targetarea">
                    <img id="multizoom" alt="zoomable" title="" src="<zx:thumbImage originalPath='${product.defaultImage}' imageType='t10'/>"/> 
                    <c:if test="${product.saleInventoryCount == 0 or product.isSellerOff}">
                    		<div class="sale-over"><!-- 售罄 --></div>
                    </c:if>
                </div>
                <div class="pro-details">
                    <div class="goods-name">【${product.brand.name}】 ${product.name}</div>
                    <div class="goods-price"><span class="g-val">¥${product.salePrice}</span></div>
                    <div class="goods-org-item">
                        <table width="100%">
                            <thead>
                            <tr>
                                <td width="130">原价：<span class="coin">¥</span>${product.marketPrice }</td>
                                <td width="95">折扣：<span class="g-val">${product.rebate }折</span></td>
                                <td width="187"></td>
                            </tr>
                            <tr><td colspan="3">
                            	运费:
                                	<c:choose>
                                		<c:when test="${product.postage.postage > 0}">
                                		<span class="coin">¥</span>${product.postage.postage}元(每商家仅需支付${product.postage.postage}元)
                                		</c:when>
                                		<c:otherwise><span class="fare">(本商品免运费)</span></c:otherwise>
                                	</c:choose>
                                </td>
                            </tr>
                            </thead>
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
                                	${entry.key}：
                                </c:if>
                                </div>
                                <c:if test="${fn:length(entry.value) > 0}">
                                <div class="info skuOptionValueMap_${status.index }">
                               	<c:forEach items="${entry.value}" var="entryValue">
                               		<c:if test="${status.index == 0}">
                               			<span class="chooseColor" data-value="${entryValue.skuOptionValueId}">
                               				${entryValue.skuOptionValue}
                               			</span>
                               		</c:if>
                               		<c:if test="${status.index == 1}">
                               			<span class="chooseGauge" data-value="${entryValue.skuOptionValueId}">
                               				${entryValue.skuOptionValue}
                               			</span>
                               		</c:if>
                                </c:forEach>
                                </div>
                                </c:if>
                            </li>
                         </c:forEach>
                         </c:if>
                            <li>
                                <div class="tit">数量：</div>
                                <div class="info">
                                    <div class="change-sum">
                                        <input class="product_num" type="text" value="1">
                                        <a href="javascript:;" class="add"></a>
                                        <a href="javascript:;" class="reduce"></a>
                                    </div>
                                   	<!-- 卖家被禁用 -->
                                   	<c:if test="${product.isSellerOff }">
	                                   	<div class="change-txt">
	                                   		 件（库存<i>0</i>件）
	                                   	</div>                                  		 
                                   	</c:if>
                                   	<c:if test="${!product.isSellerOff }">
                                   		<div class="change-txt">
                                    		 件<%-- （库存<i id="inventory">${product.saleInventoryCount}</i>件） --%>
                                    	</div>
                                      	<div class="nervous m-hidden showInventory"><i></i><em>库存紧张</em></div>	 
                                    </c:if>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <c:forEach items="${product.plans}" var="plan" begin="0" end="0">
                    <div class="surplus">
                       	 剩余：<span class="countdown" data-config="{
                            'endTime':'<fmt:formatDate value="${plan.endTime }" type="both"/>',
                            'nowTime':'<%=System.currentTimeMillis()/1000 %>',
                            'stopTips':'<span>活动结束谢谢参与！</span>'
                          }"></span>结束
                    </div>
                    </c:forEach>
                    <div class="goods-btn cf">
                    	<c:if test="${product.saleInventoryCount == 0 or product.isSellerOff}">
                    		<a href="javascript:;" class="btn-e"><span>加入购物车</span></a><a href="javascript:;" class="btn-e"><span>立即购买</span></a>
                    	</c:if>
                    	<c:if test="${product.saleInventoryCount > 0 and !product.isSellerOff}">
                        	<a href="javascript:;" class="btn-c addCart"><span>加入购物车</span></a><a href="javascript:;" class="btn-b nowBuy"><span>立即购买</span></a>
                        	<%-- 
                        	<form id="recommendForm" action="${_ctxPath}/recommend.htm" method="get">
                        		<input type="hidden" name="product.productCategoryId" value="${product.productCategoryId}"/>
                        	</form> 
                        	--%>
                        </c:if>
                    </div>
                    <div class="share-wrap cf">
                        <span class="m-fl">分享到：</span>
                        <!-- Baidu Button BEGIN -->
						<div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare" data="{'url':'${_domain}'}">
								<a class="bds_tsina" href="#" title="新浪微博"></a>
								<a class="bds_tqq" href="#" title="腾讯微博"></a>
								<a class="bds_qzone" href="#" title="QQ空间"></a>
								<a class="bds_douban" href="#" title="豆瓣网"></a>
								<a class="bds_kaixin001" href="#" title="开心网"></a>
								<a class="bds_renren" href="#" title="人人网"></a>
								<a class="bds_taobao" href="#" title="淘江湖"></a>
						</div>
						<!-- Baidu Button END -->
                    </div>
                </div>
            </c:if>
            <!-- 如果商品下架了 -->
            <c:if test="${product.isOutOfStock }">
            	<div class="targetarea">
            		<img id="multizoom" alt="zoomable" title="" src=""/> 
            		<div class="shelf-over"><!-- 已经下架 --></div>
            	</div>
            	<div class="pro-details">
                   		 <div class="goods-name">${product.brand.name} ${product.name }</div>
                   		 <div class="goods-price">
                   		 	此商品已经下架,您可以返回<a href="${_ctxPath }">首页</a>或者继续查看<a href="${_ctxPath}/user/orders.htm">我的订单</a>
                   		 </div>
                   		 <div class="goods-org-item"></div>
                   		 <div class="goods-sort-list cf"></div>
                </div>
            </c:if>
            </div>
        </div>
            <div class="pro-main">
            <cache:cache time="${_cacheSec}" refresh="${_needRefresh}" key="productDescribe-${product.productId}">
                <div class="tab-gn">
                    <ul class="panel">
                        <li>商品信息</li>
                        <li>服务保障</li>
                        <li>支付方式</li>
                    </ul>
                    <div class="protab-item" style="display: block;">
                        ${product.describe }
                    </div>
                    <div class="protab-item">
                        <div class="mod-detail">
                            <div class="hd"><img src="${_ctxPath }/web-resource/images/titleImg_01.jpg" height="22" /></div>
                            <div class="bd">
                                <table>
                                    <tbody>
                                        <tr>
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
                                            <td width="130" align="center" style="display:none"><img src="${_ctxPath }/web-resource/images/produce_arrow.jpg"></td>
                                            <td class="bg-td" style="display:none">
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
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div class="mod-detail">
                            <div class="hd"><img src="${_ctxPath }/web-resource/images/titleImg_02.jpg" height="22" /></div>
                            <div class="bd">
                                <p>1.请您确保订单中的收货地址、邮编、电话、Email地址等各项信息的准确性，以便商品及时、准确地发出。</p>
                                <p>2.如果长时间未收到货，可能是由于您填写的地址或电话号码有误，您可联系新龙客服为您处理。</p>
                                <p>3.配送过程中如果我们联络您的时间超过7天未得到回复，此订单将被默认为您已经放弃订购。</p>
                            </div>
                        </div>
                    </div>
                    <div class="protab-item">
                        <div class="mod-detail">
                            <div class="hd"><img src="${_ctxPath }/web-resource/images/titleImg_03.jpg" height="22" /></div>
                            <div class="bd">
                                <p>目前新龙直销仅提供支付宝支付模式。</p>
                            </div>
                        </div>
                        <div class="mod-detail">
                            <div class="hd"><img src="${_ctxPath }/web-resource/images/titleImg_04.jpg" height="22" /></div>
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
            </cache:cache>
            </div>
            <div class="static-mod">
                <div class="hd">最近浏览</div>
                <div class="bd cf">
               	<c:forEach items="${products }" var="product">
                    <dl>
                        <dt>
                        	<a href="${_ctxPath }/item-${product.productId}.htm">
                        		<img src="<zx:thumbImage originalPath='${product.defaultImage }' imageType='t7'></zx:thumbImage>" alt="${product.name }" width="220" height="220" />
                        	</a>
                        </dt>
                        <dd class="link">
                        <a href="${_ctxPath }/item-${product.productId}.htm">
                        	${uhome:cutString(product.name,13)}
                        </a>
                        </dd>
                        <dd class="value-box"><i class="now-value">¥${product.salePrice }</i><i class="rebate">(${product.rebate}折)</i><i class="orig-value">¥${product.marketPrice}</i></dd>
                    </dl>
                </c:forEach>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
    <!--footer.jsp放置在这-->
    <!--右侧导航 start-->
    <div class="sidebar-block screen-default">
        <ul>
            <li class="sidebar-cart">
                <a href="${_ctxPath}/order/order-shoppingCart.htm"><div class="sidebar-cart-ico"><i></i></div>购物车(<span class="show-sum">${totalNum}</span>)</a>
            </li>
            <li class="sidebar-totop"><a href="javascript:;"><i></i></a></li>
        </ul>
    </div>
    <!--右侧导航 end-->
    <!--顶部浮层 start-->
    <div class="shop-top-bar">
        <div class="m-w960p cf">
            <div class="pro-bar-act">
            	<a href="javascript:quickChoose('go-addcart');" class="add-cart"><span>加入购物车</span></a>
                <a href="javascript:quickChoose('go-buy');" class="buy-it"><span>立即购买</span></a>
            </div>
            <div class="pro-bar-price">
                <span class="goods-price-dis">原价:<del><span class="coin">¥</span>${product.marketPrice}</del></span>
                <span class="goods-price-sale"><span class="coin">¥</span>${product.salePrice}</span>
            </div>
            <c:forEach items="${product.plans}" var="plan" begin="0" end="0">
            <div class="pro-time-info">
               	剩余：<span class="countdown" data-config="{
                    'endTime':'<fmt:formatDate value="${plan.endTime }" type="both"/>',
                    'nowTime':'<%=System.currentTimeMillis()/1000 %>',
                    'stopTips':'<span>活动结束谢谢参与！</span>',
                    'timeStampTow':true
               	}"></span>
            </div>
            </c:forEach>
        </div>
    </div>
    <!--顶部浮层 end-->
    <script type="text/javascript" src="${_jsPath }/plugin/multizoom/multizoom.js"></script>
    <script type="text/javascript" src="${_jsPath }/pages/produce_detail.js?d=${_resVerion}"></script>
    <c:if test="${!product.isOutOfStock }">
    <%
	   	Product p = (Product)request.getAttribute("product");
	    String ss = p.getProductSkuMapJson(); //SkuOptionValueId - productSkuId
	    String sss = p.getProductSkuInventoryMapJson(); // productSkuId - inventory
    %>
    <script type="text/javascript">
    	var type = <%=com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku.TYPE_SPECIAL_SELLER %>;
	  	//每个选项的skuId
		var skuOptionJson = <%=ss%>;
		//每个skuId的库存
		var productSkuInventoryJson = <%=sss%>;
    	//商品的总库存数量
    	var productInventory = ${product.saleInventoryCount};
    	//获取商品默认数量
    	var productNum = $(".product_num").val();
    	var color = "";
    	var gauge = "";
    	var skuInventory = 0; //skuId对应的库存
    	var productSkuId = 0; //skuId
    	
    	//选择颜色
    	function chooseColor(obj){
    		color = obj;
   		 	//根据SkuOptionValue选择skuId
 		 	productSkuId = skuOptionJson[color+"-"+gauge];
    		//如果规格选中了  显示对应的库存
    		if(gauge != ""){
    			skuInventory = productSkuInventoryJson[productSkuId];
    			if(parseInt(skuInventory == 0)){
    				skuInventory = 0;
    			}
    			showInventoryInfo(${_minInventory},skuInventory);
    		}else{
    			//只有一种规格
    			productSkuId = skuOptionJson[color];
    			if(productSkuId != undefined){
    				skuInventory = productSkuInventoryJson[productSkuId];
    				if(parseInt(skuInventory == 0)){
        				skuInventory = 0;
        			}
    				showInventoryInfo(${_minInventory},skuInventory);
    			}
    		}
    	}
    	//选择尺码
    	function chooseGauge(obj){
    		gauge = obj;
    		//根据SkuOptionValue选择skuId
 		 	productSkuId = skuOptionJson[color+"-"+gauge];
    		//如果颜色选中了 显示对应的库存
    		if(color != ""){
    			skuInventory = productSkuInventoryJson[productSkuId];
    			if(parseInt(skuInventory == 0)){
    				skuInventory = 0;
    			}
    			showInventoryInfo(${_minInventory},skuInventory);
    		}else{
    			//只有一种规格
    			productSkuId = skuOptionJson[gauge];
    			if(productSkuId != undefined){
    				skuInventory = productSkuInventoryJson[productSkuId];
    				if(parseInt(skuInventory == 0)){
        				skuInventory = 0;
        			}
    				showInventoryInfo(${_minInventory},skuInventory);
    			}
    		}
    	}
    	//选定当前商品属性 
    	var spanClick = function(){
    		var $this = $(this);
    		$this.addClass("selected").siblings().removeClass("selected");
    		var dataValue = $this.attr("data-value");
    		if($this.hasClass("chooseGauge")){
    			chooseGauge(dataValue);
    			//将选中规格的尺码复原
    			$(".chooseColor").removeAttr("style").on("click",spanClick);
    			//遍历每个选项 没有库存变灰 不可选
    	    	$.each(skuOptionJson,function(key,value){
    	    		var k = key.split("-");
    	    		var skuId = value;
    	    		if(productSkuInventoryJson[skuId] <= 0 && parseInt(k[1]) == parseInt(gauge)){
    	    			//找到那些规格没有库存的节点
    	    			var $dataValues = $(".chooseColor");
    	    			for(var i = 0; i < $dataValues.length; i++){
    	    				var $t = $($dataValues[i]);
    	    				if(parseInt($t.attr("data-value")) == parseInt(k[0])){
    	    					//如果先选中颜色再选择规格  且此规格对应的此颜色没有值
    	    					//color = "";
    	    					$t.removeClass("selected").css("backgroundColor","#CCCCCC").css("cursor","not-allowed").off("click",spanClick);
    	    				}
    	    			}
    	    		}
    	    	});
    		}
    		if($this.hasClass("chooseColor")){
    			chooseColor(dataValue);
    			//将选中颜色的尺码复原
    			$(".chooseGauge").removeAttr("style").on("click",spanClick);
    			//遍历每个选项 没有库存变灰 不可选
    	    	$.each(skuOptionJson,function(key,value){
    	    		var k = key.split("-");
    	    		var skuId = value;
    	    		if(productSkuInventoryJson[skuId] <= 0 && parseInt(k[0]) == parseInt(color)){
    	    			//找到那些规格没有库存的节点
    	    			var $dataValues = $(".chooseGauge");
    	    			for(var i = 0; i < $dataValues.length; i++){
    	    				var $t = $($dataValues[i]);
    	    				if(parseInt($t.attr("data-value")) == parseInt(k[1])){
    	    					//如果先选中规格再选择颜色  且此颜色对应的此规格没有值
    	    					//gauge = "";
    	    					$t.removeClass("selected").css("backgroundColor","#CCCCCC").css("cursor","not-allowed").off("click",spanClick);
    	    				}
    	    			}
    	    		}
    	    	});
    		}
    	};
    	//为每个规格和颜色的span添加click事件
    	$(".goods-sort-list .info").find("span").on("click",spanClick);
    	if($.isEmptyObject(skuOptionJson)){
    		//如果没有规格直接获取sku
    		productSkuId = ${product.productSkus[0].productSkuId};
     	}
    	//加入购物车
    	$(".addCart").on("click",function(event){
    		addCartAndNowBuy(skuOptionJson,productSkuInventoryJson,${_minInventory},type,1,productSkuId);
		});
    	//立即购买
    	$(".nowBuy").on("click",function(event){
    		addCartAndNowBuy(skuOptionJson,productSkuInventoryJson,${_minInventory},type,0,productSkuId);
    	});
        //购物弹窗确定按钮
        $('.popup-ft').find("a").live("click",function(event){
        	$this = $(this);
        	//点击立即购买
        	if($this.hasClass("go-buy")){
        		$(".nowBuy").click();
        	}
        	//点击加入购物车
        	if($this.hasClass("go-addcart")){
        		$(".addCart").click();
        	}
        });
    	//商品数量加减
    	$(".add").on("click",function(){
    		addProductNum(skuOptionJson,productSkuInventoryJson,productSkuId,productInventory);
    	});
    	$(".reduce").on("click",function(){
    		reduceProductNum(skuOptionJson,productSkuInventoryJson,productSkuId);
    	});
    	//用户自己改变商品数量
    	$(".product_num").blur(function(){
    		editProductNum(skuOptionJson,productSkuInventoryJson,productSkuId,productInventory);
    	});
    	//sku只有一个默认选中  1.只有颜色且只有一个颜色 2.只有规格且只有一个规格 3.2个都有且2个都只有一个选项
    	chooseDefaultSku(spanClick);
    	//如果只有颜色或者是有规格 默认把没有库存的禁用
    	$(function(){
    		var map0 = $(".skuOptionValueMap_0").length;
    		var map1 = $(".skuOptionValueMap_1").length;
    		//只有颜色
    		if(map0 == 1 && map1 == 0){
    			$(".chooseColor").each(function(){
    				var $this = $(this);
    				var skuId = skuOptionJson[$this.attr("data-value")];
    				var skuInventory = productSkuInventoryJson[skuId];
    				if(skuInventory <= 0){
    					$this.removeClass("selected").css("backgroundColor","#CCCCCC").css("cursor","not-allowed").off("click",spanClick);
    				}
    			});
    		}
    		//只有规格
    		if(map0 == 0 && map1 == 1){
    			$(".chooseGauge").each(function(){
    				var $this = $(this);
    				var skuId = skuOptionJson[$this.attr("data-value")];
    				var skuInventory = productSkuInventoryJson[skuId];
    				if(skuInventory <= 0){
    					$this.removeClass("selected").css("backgroundColor","#CCCCCC").css("cursor","not-allowed").off("click",spanClick);
    				}
    			});
    		}
    	});
    </script>
   </c:if>
   	<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=0" ></script>
	<script type="text/javascript">
	/**
	 * 在这里定义bds_config
	 */
	var bds_config = {
		'bdDes':'${product.name}-商品详情-${_webSiteName}-${_domain}item-${product.productId}.htm',
		'bdText':'${product.name}-商品详情-${_webSiteName}-${_domain}item-${product.productId}.htm',
		'bdPic':'${_fileThumbPath}${product.defaultImage}_t10.${fn:split(product.defaultImage,'.')[1]}',
		'searchPic':'1'
	};
	(function(){    
		function async_load(){
			var s = document.createElement('script');
			s.type = 'text/javascript';
			s.id = 'bdshell_js';
			s.async = true;
			s.src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000);
			var x = document.getElementsByTagName('script')[0];
			x.parentNode.insertBefore(s, x);
		}
		if(window.attachEvent){
			window.attachEvent('onload', async_load);
		}
		else{
			window.addEventListener('load', async_load, false);
		}
	})(); 
	</script>
</body>
</html>