<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <cache:cache refresh="${_needRefresh}" key="seo-specialTopic-${templetId}" cron="${_seoRefreshCron }">
	  	<c:if test="${empty seoTitle }">
	    	<title>专题</title>
		</c:if>
		<c:if test="${!empty seoTitle }">
			<title>${seoTitle}</title>
		</c:if>
		<c:if test="${empty seoKeyWords }">
	    	<meta name="keywords" content="专题" >
		</c:if>
		<c:if test="${!empty seoKeyWords }">
			<meta name="keywords" content="${seoKeyWords}">
		</c:if>
		
		<c:if test="${empty seoDescription }">
	    	<meta name="description" content="专题" >
		</c:if>
		<c:if test="${!empty seoDescription}">
			<meta name="description" content="${seoDescription}">
		</c:if>
  	</cache:cache>
  	
	<style type="text/css">
	*{margin:0;padding:0;}
	li{list-style:none;}
	.wrap{width:960px;margin:0 auto;}
	.wrap .ad{margin-bottom:10px;}
	.wrap .big{height:390px;margin-top:10px;}
	.wrap .small{height:100px;}
	.event-item{overflow:hidden;}
	.event-item ul{width:984px;overflow:hidden;*zoom:1;}
	.event-item li{float:left;display:inline;width:228px;margin-right:16px;margin-bottom:25px;font-size:12px;}
	.event-item li a{float:left;width:226px;height:283px;border:1px solid #fff;text-decoration:none;color:#333;}
	.event-item li a span{display:block;margin:0 10px 5px;text-overflow:ellipsis;overflow:hidden;white-space: nowrap;}
	.event-item li a .tit-con{width:206px;}
	.event-item li a .pic{text-align:center;margin:0 0 8px;height:220px;}
	.event-item li a i{font-style:normal;}
	.event-item li a .now-value{font-size:18px;color:#f96619;}
	.event-item li a .rebate{color:#f96619;}
	.event-item li a .coin{font-family:arial;}
	.event-item li a .orig-value{text-decoration:line-through;margin-left:35px;}
	.event-item li a:hover{border-color:#cdcdcd;box-shadow:2px 2px 2px #ccc;}
	</style>
	<link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
	<link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
	<link rel="stylesheet" type="text/css" href="${_cssPath }/pages/brand-spe-sale.css?d=${_resVerion}" media="all">
</head>
<body>
	<!-- 引入头部 -->
	<!-- 头部标签 -->
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
	<!-- 引入头部 end-->
	<div class="wrap">
		<div class="ad big">
			<c:if
				test="${map['B_1'].imageUrl != null && map['B_1'].imageUrl != ''}">
				<div class="pic">
					<c:if
						test="${map['B_1'].turnUrl != null && map['B_1'].turnUrl != ''}">
						<a href="${map['B_1'].turnUrl}" target="_blank">
					</c:if>
					<img
						src="<zx:thumbImage originalPath="${map['B_1'].imageUrl}" imageType='t29'></zx:thumbImage>"
						width="960" height="390">
					<c:if
						test="${map['B_1'].turnUrl != null && map['B_1'].turnUrl != ''}">
						</a>
					</c:if>
				</div>
			</c:if>
		</div>
		<div class="ad small">
			<c:if
				test="${map['M_1'].imageUrl != null && map['M_1'].imageUrl != ''}">
				<div class="pic">
					<c:if
						test="${map['M_1'].turnUrl != null && map['M_1'].turnUrl != ''}">
						<a href="${map['M_1'].turnUrl}" target="_blank">
					</c:if>
					<img
						src="<zx:thumbImage originalPath="${map['M_1'].imageUrl}" imageType='t30'></zx:thumbImage>"
						width="960" height="100">
					<c:if
						test="${map['M_1'].turnUrl != null && map['M_1'].turnUrl != ''}">
						</a>
					</c:if>
				</div>
			</c:if>
		</div>
		<!-- 商品成列组 -->
		<div class="event-item">
			<ul>
				<c:if test="${map['M_1'].productImageUrls != null && map['M_1'].productImageUrls != ''}">
					<c:set value="${fn:split(map['M_1'].productImageUrls, ',')}"
						var="productImageUrls" />
					<c:set value="${fn:split(map['M_1'].productLinkUrls, ',')}"
						var="productLinkUrls" />
					<c:set value="${fn:split(map['M_1'].productNames, ',')}"
						var="productNames" />
					<c:set value="${fn:split(map['M_1'].productSalePrices, ',')}"
						var="productSalePrices" />
					<c:set value="${fn:split(map['M_1'].productRebates, ',')}"
						var="productRebates" />
					<c:set value="${fn:split(map['M_1'].productMarketPrices, ',')}"
						var="productMarketPrices" />				
					<c:forEach items="${productImageUrls}" var="piu" varStatus="status">
						<c:set value="${status.index}" var="si" />
						<li>		
							<a href="${_ctxPath }${productLinkUrls[si]}" target="_blank">
								<span class="pic">
									<img src="<zx:thumbImage originalPath="${piu}" imageType='t7'></zx:thumbImage>" alt="${productNames[si]}" title="${productNames[si]}" width="220" height="220">
								</span>
								<span class="tit-con">${productNames[si]}</span>
								<span><i class="now-value"><i class="coin">¥</i>${productSalePrices[si]}</i><i class="rebate">(${productRebates[si]}折)</i><i class="orig-value"><i class="coin">¥</i>${productMarketPrices[si]}</i></span>
							</a>		
						</li>
					</c:forEach>
				</c:if>	
			</ul>
		</div>
		<!-- 商品成列组 end-->
		<div class="ad small">
			<c:if
				test="${map['M_2'].imageUrl != null && map['M_2'].imageUrl != ''}">
				<div class="pic">
					<c:if
						test="${map['M_2'].turnUrl != null && map['M_2'].turnUrl != ''}">
						<a href="${map['M_2'].turnUrl}" target="_blank">
					</c:if>
					<img
						src="<zx:thumbImage originalPath="${map['M_2'].imageUrl}" imageType='t30'></zx:thumbImage>"
						width="960" height="100">
					<c:if
						test="${map['M_2'].turnUrl != null && map['M_2'].turnUrl != ''}">
						</a>
					</c:if>
				</div>
			</c:if>
		</div>
		<!-- 商品成列组 -->
		<div class="event-item">
			<ul>
				<c:if test="${map['M_2'].productImageUrls != null && map['M_2'].productImageUrls != ''}">
					<c:set value="${fn:split(map['M_2'].productImageUrls, ',')}"
						var="productImageUrls" />
					<c:set value="${fn:split(map['M_2'].productLinkUrls, ',')}"
						var="productLinkUrls" />
					<c:set value="${fn:split(map['M_2'].productNames, ',')}"
						var="productNames" />
					<c:set value="${fn:split(map['M_2'].productSalePrices, ',')}"
						var="productSalePrices" />
					<c:set value="${fn:split(map['M_2'].productRebates, ',')}"
						var="productRebates" />
					<c:set value="${fn:split(map['M_2'].productMarketPrices, ',')}"
						var="productMarketPrices" />				
					<c:forEach items="${productImageUrls}" var="piu" varStatus="status">
						<c:set value="${status.index}" var="si" />
						<li>		
							<a href="${_ctxPath }${productLinkUrls[si]}" target="_blank">
								<span class="pic">
									<img src="<zx:thumbImage originalPath="${piu}" imageType='t7'></zx:thumbImage>" alt="${productNames[si]}" title="${productNames[si]}" width="220" height="220">
								</span>
								<span class="tit-con">${productNames[si]}</span>
								<span><i class="now-value"><i class="coin">¥</i>${productSalePrices[si]}</i><i class="rebate">(${productRebates[si]}折)</i><i class="orig-value"><i class="coin">¥</i>${productMarketPrices[si]}</i></span>
							</a>		
						</li>
					</c:forEach>
				</c:if>	
			</ul>
		</div>
		<!-- 商品成列组 end-->
		<div class="ad small">
			<c:if
				test="${map['M_3'].imageUrl != null && map['M_3'].imageUrl != ''}">
				<div class="pic">
					<c:if
						test="${map['M_3'].turnUrl != null && map['M_3'].turnUrl != ''}">
						<a href="${map['M_3'].turnUrl}" target="_blank">
					</c:if>
					<img
						src="<zx:thumbImage originalPath="${map['M_3'].imageUrl}" imageType='t30'></zx:thumbImage>"
						width="960" height="100">
					<c:if
						test="${map['M_3'].turnUrl != null && map['M_3'].turnUrl != ''}">
						</a>
					</c:if>
				</div>
			</c:if>
		</div>
		<!-- 商品成列组 -->
		<div class="event-item">
			<ul>
				<c:if test="${map['M_3'].productImageUrls != null && map['M_3'].productImageUrls != ''}">
					<c:set value="${fn:split(map['M_3'].productImageUrls, ',')}"
						var="productImageUrls" />
					<c:set value="${fn:split(map['M_3'].productLinkUrls, ',')}"
						var="productLinkUrls" />
					<c:set value="${fn:split(map['M_3'].productNames, ',')}"
						var="productNames" />
					<c:set value="${fn:split(map['M_3'].productSalePrices, ',')}"
						var="productSalePrices" />
					<c:set value="${fn:split(map['M_3'].productRebates, ',')}"
						var="productRebates" />
					<c:set value="${fn:split(map['M_3'].productMarketPrices, ',')}"
						var="productMarketPrices" />				
					<c:forEach items="${productImageUrls}" var="piu" varStatus="status">
						<c:set value="${status.index}" var="si" />
						<li>		
							<a href="${_ctxPath }${productLinkUrls[si]}" target="_blank">
								<span class="pic">
									<img src="<zx:thumbImage originalPath="${piu}" imageType='t7'></zx:thumbImage>" alt="${productNames[si]}" title="${productNames[si]}" width="220" height="220">
								</span>
								<span class="tit-con">${productNames[si]}</span>
								<span><i class="now-value"><i class="coin">¥</i>${productSalePrices[si]}</i><i class="rebate">(${productRebates[si]}折)</i><i class="orig-value"><i class="coin">¥</i>${productMarketPrices[si]}</i></span>
							</a>		
						</li>
					</c:forEach>
				</c:if>	
			</ul>
		</div>
		<!-- 商品成列组 end-->
		<div class="ad small">
			<c:if
				test="${map['M_4'].imageUrl != null && map['M_4'].imageUrl != ''}">
				<div class="pic">
					<c:if
						test="${map['M_4'].turnUrl != null && map['M_4'].turnUrl != ''}">
						<a href="${map['M_4'].turnUrl}" target="_blank">
					</c:if>
					<img
						src="<zx:thumbImage originalPath="${map['M_4'].imageUrl}" imageType='t30'></zx:thumbImage>"
						width="960" height="100">
					<c:if
						test="${map['M_4'].turnUrl != null && map['M_4'].turnUrl != ''}">
						</a>
					</c:if>
				</div>
			</c:if>
		</div>
		<!-- 商品成列组 -->
		<div class="event-item">
			<ul>
				<c:if test="${map['M_4'].productImageUrls != null && map['M_4'].productImageUrls != ''}">
					<c:set value="${fn:split(map['M_4'].productImageUrls, ',')}"
						var="productImageUrls" />
					<c:set value="${fn:split(map['M_4'].productLinkUrls, ',')}"
						var="productLinkUrls" />
					<c:set value="${fn:split(map['M_4'].productNames, ',')}"
						var="productNames" />
					<c:set value="${fn:split(map['M_4'].productSalePrices, ',')}"
						var="productSalePrices" />
					<c:set value="${fn:split(map['M_4'].productRebates, ',')}"
						var="productRebates" />
					<c:set value="${fn:split(map['M_4'].productMarketPrices, ',')}"
						var="productMarketPrices" />				
					<c:forEach items="${productImageUrls}" var="piu" varStatus="status">
						<c:set value="${status.index}" var="si" />
						<li>		
							<a href="${_ctxPath }${productLinkUrls[si]}" target="_blank">
								<span class="pic">
									<img src="<zx:thumbImage originalPath="${piu}" imageType='t7'></zx:thumbImage>" alt="${productNames[si]}" title="${productNames[si]}" width="220" height="220">
								</span>
								<span class="tit-con">${productNames[si]}</span>
								<span><i class="now-value"><i class="coin">¥</i>${productSalePrices[si]}</i><i class="rebate">(${productRebates[si]}折)</i><i class="orig-value"><i class="coin">¥</i>${productMarketPrices[si]}</i></span>
							</a>		
						</li>
					</c:forEach>
				</c:if>	
			</ul>
		</div>
		<!-- 商品成列组 end-->
		<div class="ad small">
			<c:if
				test="${map['M_5'].imageUrl != null && map['M_5'].imageUrl != ''}">
				<div class="pic">
					<c:if
						test="${map['M_5'].turnUrl != null && map['M_5'].turnUrl != ''}">
						<a href="${map['M_5'].turnUrl}" target="_blank">
					</c:if>
					<img
						src="<zx:thumbImage originalPath="${map['M_5'].imageUrl}" imageType='t30'></zx:thumbImage>"
						width="960" height="100">
					<c:if
						test="${map['M_5'].turnUrl != null && map['M_5'].turnUrl != ''}">
						</a>
					</c:if>
				</div>
			</c:if>
		</div>
		<!-- 商品成列组 -->
		<div class="event-item">
			<ul>
				<c:if test="${map['M_5'].productImageUrls != null && map['M_5'].productImageUrls != ''}">
					<c:set value="${fn:split(map['M_5'].productImageUrls, ',')}"
						var="productImageUrls" />
					<c:set value="${fn:split(map['M_5'].productLinkUrls, ',')}"
						var="productLinkUrls" />
					<c:set value="${fn:split(map['M_5'].productNames, ',')}"
						var="productNames" />
					<c:set value="${fn:split(map['M_5'].productSalePrices, ',')}"
						var="productSalePrices" />
					<c:set value="${fn:split(map['M_5'].productRebates, ',')}"
						var="productRebates" />
					<c:set value="${fn:split(map['M_5'].productMarketPrices, ',')}"
						var="productMarketPrices" />				
					<c:forEach items="${productImageUrls}" var="piu" varStatus="status">
						<c:set value="${status.index}" var="si" />
						<li>		
							<a href="${_ctxPath }${productLinkUrls[si]}" target="_blank">
								<span class="pic">
									<img src="<zx:thumbImage originalPath="${piu}" imageType='t7'></zx:thumbImage>" alt="${productNames[si]}" title="${productNames[si]}" width="220" height="220">
								</span>
								<span class="tit-con">${productNames[si]}</span>
								<span><i class="now-value"><i class="coin">¥</i>${productSalePrices[si]}</i><i class="rebate">(${productRebates[si]}折)</i><i class="orig-value"><i class="coin">¥</i>${productMarketPrices[si]}</i></span>
							</a>		
						</li>
					</c:forEach>
				</c:if>	
			</ul>
		</div>
		<!-- 商品成列组 end-->
	</div>
	<!-- 引入底部 -->
	<!-- 底部标签 -->
	<%@include file="/WEB-INF/pages/include/foot.jsp"%>
	<!-- 引入底部 end-->
</body>
</html>