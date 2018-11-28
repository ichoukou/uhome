<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
<meta charset="utf-8" />
<title>网站地图</title>
  <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
  <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
	<link rel="stylesheet" type="text/css" href="${_cssPath}/pages/sitemap.css?d=${_resVerion}" media="all">
</head>
<body>
<jsp:include page="../include/head.jsp" flush="true" />
<%-- <cache:cache time="${_siteMapCacheSec}" refresh="${_needRefresh}">  --%>
	<div class="m-w960p">
		<div class="crumbs"><a href="${_domain }">${_webSiteName} ></a>网站地图</div>
		<div class="setmap-t">
			<b>网站地图</b>
			<span>新龙直销网主打品牌折扣，限时打折、特卖、低至1-7折。商城数万种品牌折扣商品，每天限时打折、限时抢购，所有商品100%正品。</span>
		</div>
		<div class="setmap">
			<h3><i></i>品牌特卖</h3>
						<a id="fsxb" href="${_ctxPath }/fsxb/">服饰箱包</a>
						<a id="myyp" href="${_ctxPath }/myyp/">母婴用品</a>
						<a id="jjyp" href="${_ctxPath }/jjyp/">家居用品</a>
						<a id="mrhf" href="${_ctxPath }/mrhf/">美容护肤</a>
		</div>
		<div class="setmap">
      <h3><i class="ico-2"></i>品牌集中营</h3>
    </div>
            <ul class="setmap-c cf">
        		<c:forEach items="${brands}" var="brand" varStatus="status">
        		<li>
        			<i></i>
        			<a href="${_ctxPath }/p/${brand.brandId}/" target="_blank">
        			<c:choose>
	        			<c:when test="${brand.name==null||brand.name==''}">
	        				${uhome:cutString(brand.englishName,11)}
	        			</c:when>
	        			<c:otherwise>
	        				${uhome:cutString(brand.name,11)}
	        			</c:otherwise>    
        			</c:choose>    			
            	</a>
        		</li>
        		</c:forEach>
        	</ul>
		</div>
		 <%-- </cache:cache>  --%>
	<!-- foot -->
	 <%@include file="/WEB-INF/pages/include/foot.jsp"%> 
</body>
</html>