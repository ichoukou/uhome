<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${_webSiteName}（${_domainName}）_${_webSiteName}是一家天天特价的网上特卖商城，品牌折扣，正品保障，限时抢购。</title>
    <meta name="keywords" content="${_webSiteName}，${_domainName}，天天特价，正品，特卖，限时抢购，品牌折扣" >
    <meta name="description" content="${_webSiteName}${_domainName}_是一个以天天特价、品牌折扣、限时抢购为特色的网上特卖商城，全球各大品牌官方授权入驻，全国配送，100%正品保障，超值购物，首选${_webSiteName}。" >
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath}/pages/shopping-cart.css?d=${_resVerion}" media="all">
</head>
<body>
    <!--头部-->
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
    <!--头部 end-->
    <!--主体 start-->
	<div class="m-w960p cf">
		<div class="cart-wrap m-mt5p">
      <div class="cf">
          <div class="step-status"><span class="step">我的购物车</span><span>确认订单信息</span><span>成功提交订单</span></div>
      </div>
      <div class="add-pro-success cf">
      	<i></i>
      	<span class="add-pro-success-tip">商品已成功加入购物车！</span>
      	<a href="${_ctxPath}/default.htm" class="btn-c"><span>继续购物</span></a>
      	<a href="${_ctxPath}/order/order-shoppingCart.htm" class="btn-b"><span>去购物车结算</span></a>
      </div>
		<div class="static-mod">
			<div class="hd">购买该商品的用户还购买了</div>
		</div>
		<div class="item-wrap">
			<ul>
				<c:forEach items="${products}" var="product" >
		        <li>
		          <dl>
		            <dt>
		              <a target="_blank" href="${_ctxPath}/item-${product.productId}.htm">
		                <img src="<zx:thumbImage originalPath='${product.defaultImage }' imageType='t7'></zx:thumbImage>" alt="${product.name}" title="${product.name}">
		              </a>
		            </dt>
		            <dd class="details">
		              <a target="_blank" href="${_ctxPath}/item-${product.productId}.htm">${uhome:cutString(product.name,13)}</a>
		            </dd>
		            <dd class="value-box"><i class="now-value"><i class="coin">&yen;</i>${product.salePrice}</i><i class="rebate">(${product.rebate}折)</i><a href="javascript:;" class="add-cart">加入购物车</a></dd>
		          </dl>
		        </li>
		        </c:forEach>
			</ul>
		</div>
	</div>
	</div>
	<!--主体 end-->
    <!--底部区块-->
   	<%@include file="/WEB-INF/pages/include/foot.jsp"%>
    <!--底部区块 end-->
    <script>
    $(function () {
	    //鼠标划过效果
	    $(".item-wrap li").on("hover",function(){
	    	$(this).toggleClass("hover");
	    });
	    //倒计时
	   /*  $(".countdown").each(function () {
	        var $this = $(this),
	            data = $this.attr('data-config');
	        $this.Merlin({
	            "countDown": eval('(' + data + ')')
	        });
	    }); */
		})
    </script>
</body>
</html>