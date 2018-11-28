<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${productPage.params.keyWord }-搜索页面-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/search-result.css?d=${_resVerion}" media="all">
    <link rel="stylesheet" type="text/css" href="${_jsPath }/plugin/Xslider/css/Xslider.css?d=${_resVerion}" media="all">
</head>
<body>
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
    <div class="m-w960p">
        <!--面包屑-->
        <div class="crumbs"><a href="${_ctxPath }/default.htm">${_webSiteName } ></a>搜索结果</div>
        <!--面包屑 end-->
        <c:if test="${not empty productPage.result }">
        <div class="search-result m-mt30p">
        	<div class="brand-cat">
        		<div class="brand-cat-bg">
                <div class="tit">品牌1：</div>
                <div class="link-arry">
                	<a id="allBrand" href="${_ctxPath}/search.htm?keyWord=${productPage.params.keyWord}">全部</a>
                	<c:forEach items="${brandCategorys}" var="bcs">
                    	<a id="brand${bcs.brandId}" href="${_ctxPath}/search-${bcs.brandId}.htm?keyWord=${productPage.params.keyWord}">${bcs.name}</a>
                    </c:forEach>
                </div>
            </div>
          </div>
            <div class="rank-wrap cf">
                <div class="rank">
                    <div class="m-fl">排序：</div>
                    <div class="action">
                        <span id="s-sales">销量
                        	<!--sort-dir  -->
                        	<a href="${_ctxPath}/search-${productPage.params.brandId}-${url_sort_100}-${url_dir_100001}.htm?keyWord=${productPage.params.keyWord}" class="down"></a>
                        	</span>
                        <span id="s-rebate">折扣
                        	<a href="${_ctxPath}/search-${productPage.params.brandId}-${url_sort_200}-${url_dir_100000}.htm?keyWord=${productPage.params.keyWord}" class="down"></a>
                       	</span>
                        <span id="s-price">价格
                        	<a href="${_ctxPath}/search-${productPage.params.brandId}-${url_sort_300}-${url_dir_100000}.htm?keyWord=${productPage.params.keyWord}" class="up"></a>
                        	<a href="${_ctxPath}/search-${productPage.params.brandId}-${url_sort_300}-${url_dir_100001}.htm?keyWord=${productPage.params.keyWord}" class="down"></a>
                       	</span>
                        <span id="s-time">上架时间
                        	<a href="${_ctxPath}/search-${productPage.params.brandId}-${url_sort_400}-${url_dir_100000}.htm?keyWord=${productPage.params.keyWord}" class="up"></a>
                            <a href="${_ctxPath}/search-${productPage.params.brandId}-${url_sort_400}-${url_dir_100001}.htm?keyWord=${productPage.params.keyWord}" class="down"></a>
                        </span>
                    </div>
                </div>
                <div class="result">找到相关产品<span>${productPage.total }</span>件</div>
            </div>
            <div class="item-wrap">
                <ul>
                	<c:forEach items="${productPage.result}" var="product">
                    <li>
                        <dl>
                            <dt>
                                <a href="${_ctxPath }/item-${product.productId}.htm">
                                    <img src="<zx:thumbImage originalPath='${product.defaultImage }' imageType='t7'></zx:thumbImage>" alt="${product.name }" width="220" height="220">
                                    <c:if test="${product.saleInventoryCount == 0}">
                                    <!--售罄--><span class="sale-result"></span><!--售罄 end-->
                                    </c:if>
                                    <span class="count-wrap">
                                   	<c:forEach items="${product.plans }" var="plan" begin="0" end="0">
                                        <span class="countdown" data-config="{
                                    'endTime':'<fmt:formatDate value="${plan.endTime }" type="both"/>',
                                    'nowTime':'<%=System.currentTimeMillis()/1000 %>',
                                    'stopTips':'<span>活动结束谢谢参与！</span>',
                                    'timeStampTow':true
                                    }"></span>
                                    </c:forEach>
                                    </span>
                                </a>
                            </dt>
                            <dd class="details"><a href="${_ctxPath }/item-${product.productId}.htm">${product.brand.name }&nbsp;&nbsp;${product.name }</a></dd>
                            <dd class="value-box"><i class="now-value">¥${product.salePrice }</i><i class="rebate">(${product.rebate }折)</i><i class="orig-value">¥${product.marketPrice }</i></dd>
                        </dl>
                    </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="navigation-wp">
            	<c:if test="${not empty productPage.result}">
                   <wms:uhomeFrontPage urlEnd=".htm?keyWord=${productPage.params.keyWord}" urlStart="search-${productPage.params.brandId}-${productPage.sort}-${productPage.dir}-${productPage.total}-" page="${productPage}"></wms:uhomeFrontPage>
           		</c:if>
            </div>
        </div>
        </c:if>
     	<c:if test="${empty productPage.result }">
     		<div class="no-search">
                <div class="no-num">找到相关产品<var>0</var>件</div>
                <div class="no-search-tip">
                    <h3>很抱歉，没有找到与“
                    <b>
                    	<c:choose>
                         <c:when test="${fn:length(productPage.params.keyWord) > search_char_length}">  
		    			   <c:out value="${fn:substring(productPage.params.keyWord, 0, search_char_length)}" />  
				   	     </c:when>  
				  	      <c:otherwise>${productPage.params.keyWord }</c:otherwise>  
					    </c:choose>
                    </b>”相关的商品!</h3>
                    <p>建议您：</p>
                    <ul>
                        <li>1.看看输入文字是否有误</li>
                        <li>2.去掉不需要的字词，如“的”、“什么”等</li>
                        <li>3.调整关键字，如“nikeaf1运动鞋”改成“nike af1运动鞋”或“nike af1”</li>
                    </ul>
                </div>
            </div>
     	</c:if>
        <div class="hot-sale m-mt30p cf">
            <div class="lt">热卖品牌</div>
            <div class="productshow">
                <div class="scrollcontainer">
                    <div class="scrollwraper">
                        <ul>
                        	<c:forEach items="${hotBrands }" var="hotBrand">
                            <li>
                                <a href="${_ctxPath }/brand-${hotBrand.brandId}.htm">
                                    <img src="<zx:thumbImage originalPath='${hotBrand.logoImageUrl}' imageType='t21'></zx:thumbImage>" alt="${hotBrand.name }" width="92" height="48">
                                    <span><%--不要 2013-03-08 14:39:51 1.5折起-${hotBrand.name } --%></span>
                                </a>
                            </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
                <a class="abtn aleft" href="#left">左移</a>
                <a class="abtn aright" href="#right">右移</a>
            </div>
        </div>
    </div>
    <!--footer.jsp放置在这-->
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
    <script type="text/javascript" src="${_jsPath }/plugin/Xslider/Xslider.js"></script>
    <script type="text/javascript" src="${_jsPath }/pages/search_result.js?d=${_resVerion}"></script>
    <script type="text/javascript">
	 	 //选择排序选中的效果
		var url = location.href;
		if(url.indexOf("-100-") > 0){
			$("#s-sales").addClass("cur-down")
		}else if(url.indexOf("-200-") > 0){
			$("#s-rebate").addClass("cur-down")
		}else if(url.indexOf("-300-") > 0 && url.indexOf("-100000") > 0){
			$("#s-price").addClass("cur-up")
		}else if(url.indexOf("-300-") > 0 && url.indexOf("-100001") > 0){
			$("#s-price").addClass("cur-down")
		}else if(url.indexOf("-400-") > 0 && url.indexOf("-100000") > 0){
			$("#s-time").addClass("cur-up");
		}else if(url.indexOf("-400-") > 0 && url.indexOf("-100001") > 0){
			$("#s-time").addClass("cur-down");
		}
		var urlArgs = url.split("-");
		if(urlArgs.length == 1){
			$("#allBrand").addClass("active");
		}else if(urlArgs.length > 1){
			//如果brandId不是数字说明url出错
			if($.isNumeric(urlArgs[1])){
				$("#brand"+urlArgs[1]).addClass("active");
			}else{
				$("#brand"+urlArgs[1].split(".")[0]).addClass("active");
			}
		}
		$(function(){
			//如果搜索框的值  是不是默认值
			var searchValue = $("#search").val();
			if($.trim(searchValue)!=''&& searchValue !="搜索商品/品牌"){
				$("#search").addClass("input-focus");
				$("#search").next().addClass("highlight");
			}
		});
    </script>
</body>
</html>