<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Product"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.service.ProductService"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>505-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath}/pages/not-find.css?d=${_resVerion}" media="all">
</head>
<body>
    <%@include file="/WEB-INF/pages/include/head.jsp"%>
    <%
    ProductService productService = (ProductService)(SpringContextUtils.getContext().getBean("productServiceImpl"));
   	List<Product> mostHits = productService.getProductListByHits(4);
   	request.setAttribute("mostHits",mostHits);
    %>
    <div class="m-w960p">
		<div class="inner-wrap">
		   <div class="contxt"></div>
		   <div class="go-back">
		   	<p>抱歉，出错啦：服务器可能病了！但已经有医生在治疗啦！F5 刷新试试！</p>
		   	<a href="${_domain}">返回首页&gt;&gt;</a>
		   </div>
		</div>
		<div class="static-mod">
            <div class="hd">热卖商品</div>
            <div class="bd cf">
            <c:forEach items="${mostHits}" var="product">
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
    <!--footer.jsp放置在这-->
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
</body>
</html>