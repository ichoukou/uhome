<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>进口商品_${_webSiteName}${_domainName}特卖商城，进口品牌、100%正品保障</title>
    <meta name="keywords" content="${_webSiteName}，${_domainName}，进口商品，进口品牌，进口品牌特卖">
    <meta name="description" content="${_webSiteName}进口商品频道，主要经营进口服装、鞋帽等进口品牌特卖，绝对正品，100%保障。">
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/search-result.css?d=${_resVerion}" media="all">
</head>
<body>
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
    <div class="m-w960p">
        <!--面包屑-->
        <div class="crumbs"><a href="${_ctxPath }/default.htm">${_webSiteName}></a>进口商品</div>
        <!--面包屑 end-->
        <c:if test="${not empty productPage.result }">
        <div class="search-result m-mt20p">
            <div class="brand-cat">
            	<div class="brand-cat-bg">
                <div class="tit">品牌：</div>
                <div class="link-arry">
                	<a id="allBrand" href="${_ctxPath}/import.htm">全部</a>
                	<c:forEach items="${brandCategorys}" var="bcs">
                    	<a id="brand${bcs.brandId}" href="${_ctxPath}/import-${bcs.brandId}.htm">${bcs.name}</a>
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
                        	<a href="${_ctxPath}/import-${productPage.params.brandId}-${url_sort_100}-${url_dir_100001}.htm" class="down"></a>
                        	</span>
                        <span id="s-rebate">折扣
                        	<a href="${_ctxPath}/import-${productPage.params.brandId}-${url_sort_200}-${url_dir_100000}.htm" class="down"></a>
                       	</span>
                        <span id="s-price">价格
                        	<a href="${_ctxPath}/import-${productPage.params.brandId}-${url_sort_300}-${url_dir_100000}.htm" class="up"></a>
                        	<a href="${_ctxPath}/import-${productPage.params.brandId}-${url_sort_300}-${url_dir_100001}.htm" class="down"></a>
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
                                    <img src="<zx:thumbImage originalPath='${product.defaultImage }' imageType='t7'></zx:thumbImage>" alt="${product.name }" alt="${product.name}" title="${product.name}" width="220" height="220">
                                    <c:if test="${product.saleInventoryCount == 0}">
                                    <!--售罄--><span class="sale-result"></span><!--售罄 end-->
                                    </c:if>
                                </a>
                            </dt>
                            <dd class="goods-brand">${product.brand.name}</dd>
                            <dd class="details"><a href="${_ctxPath }/item-${product.productId}.htm">${uhome:cutString(product.name,13)}</a></dd>
                            <dd class="value-box"><i class="now-value"><i class="coin">&yen;</i>${product.salePrice }</i><i class="rebate">(${product.rebate }折)</i><i class="orig-value"><i class="coin">&yen;</i>${product.marketPrice }</i></dd>
                        </dl>
                    </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="navigation-wp">
            	<c:if test="${not empty productPage.result}">
                   <wms:uhomeFrontPage urlEnd=".htm" urlStart="import-${productPage.params.brandId}-${productPage.sort}-${productPage.dir}-${productPage.total}-" page="${productPage}"></wms:uhomeFrontPage>
           		</c:if>
            </div>
        </div>
        </c:if>
     	<c:if test="${empty productPage.result }">
     		<div class="no-search">
                <div class="no-num">找到相关产品<var>0</var>件</div>
                <div class="no-search-tip">
                    <h3>很抱歉，暂时还没有进口商品</h3>
                </div>
            </div>
     	</c:if>
        </div>
    </div>
    <!--footer.jsp放置在这-->
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
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
    </script>
</body>
</html>