<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>品牌特卖，品牌折扣，男装、女装品牌大全—${_webSiteName}。</title>
	<meta name="keywords" content="品牌特卖，品牌折扣，男装、女装品牌大全">
	<meta name="description" content="${_webSiteName}网品牌特卖频道提供男装、女装、母婴用品、美容护肤、进口商品等品牌折扣、特卖、折扣店信息，低至1-7折。了解品牌折扣信息请登陆xlbuy365.com
">
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/brand-reserve.css?d=${_resVerion}" media="all">
</head>
<body>
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
    <div class="m-w960p">
        <!--面包屑-->
        <div class="crumbs"><a href="${_ctxPath }">${_webSiteName } ></a>品牌集中营</div>
        <!--面包屑 end-->
        <div class="reserve-wrap m-mt20p">
        <cache:cache time="${_cacheSec}" refresh="${_needRefresh}">
            <div class="hd cf">
                <h2><a href="${_ctxPath }/brand/brands.htm">品牌集中营</a></h2>
                <span>
                	<c:forEach items="${brandMap}" var="brandMap">
                		<c:if test="${fn:length(brandMap.value) != 0}">
                				<a href="#${brandMap.key }">${brandMap.key }</a>
                		</c:if>
                	</c:forEach>
               	</span>
            </div>
            
            <div class="bd cf">
	            <c:forEach items="${brandMap}" var="brandMap">
	            	<c:if test="${fn:length(brandMap.value) != 0}">
		            	<dl>
		                    <dt>
		                    	<c:choose>
		                    		<c:when test="${brandMap.key == '其它'}">
		                    			<i style="font-size:30px;" id="${brandMap.key }" name="${brandMap.key }">${brandMap.key }</i><span></span>
		                    		</c:when>
		                    		<c:otherwise>
		                    			<i id="${brandMap.key }" name="${brandMap.key }">${brandMap.key }</i><span></span>
		                    		</c:otherwise>
		                    	</c:choose>
		                    </dt>
		                    <dd>
		                    	<ul>
				                	<c:forEach items="${brandMap.value }" var="brand">
				                    <li>
				                    	<a href="${_ctxPath }/p/${brand.brandId}/" target="_blank">
				                        	<img src="<zx:thumbImage originalPath='${brand.logoImageUrl }' imageType='t21'></zx:thumbImage>" alt="${brand.name }" title="${brand.name }" width="94" height="48">
				                        	<c:if test="${brand.name != ''}"><div>${brand.name }</div></c:if>
				                        	<c:if test="${brand.englishName != '' }"><div>${brand.englishName }</div></c:if>
				                   		</a>
				                   		
				                    </li>
				                    </c:forEach>
		                		</ul>
		                    </dd>
		                </dl>
	                </c:if>
	            </c:forEach>
            </div>
            </cache:cache>
        </div>
    </div>
    <!--footer.jsp放置在这-->
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
</body>
</html>