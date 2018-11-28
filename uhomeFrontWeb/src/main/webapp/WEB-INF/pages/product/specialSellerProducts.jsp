<%@page import="com.opensymphony.xwork2.ActionContext"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <cache:cache refresh="${_needRefresh}" key="seo-plan-${productPage.params.planId}" cron="${_seoRefreshCron}">
    <c:forEach items="${productPage.result}" var="product" begin="0" end="0">
    <c:if test="${empty seoTitle }">
	    <title>${product.brand.name}_${_webSiteName }商城_特卖抢购专场</title>
	</c:if>
	<c:if test="${!empty seoTitle }">
		<title>${seoTitle}</title>
	</c:if>
	
	<c:if test="${empty seoKeyWords }">
	    <meta name="keywords" content="${product.brand.name}抢购，${product.brand.name}折扣，${product.brand.name}特卖，${_webSiteName }">
	</c:if>
	<c:if test="${!empty seoKeyWords }">
		<meta name="keywords" content="${seoKeyWords}">
	</c:if>
	
	<c:if test="${empty seoDescription }">
	    <meta name="description" content="${_webSiteName }商城${product.brand.name}专场提供各种男女时尚品牌行货介绍，提供${product.brand.name}最新款式的各类型正品行货商品的购买、报价、参数、评价等信息，${product.brand.name}特卖，限时抢购，100%正品保障。">
	</c:if>
	<c:if test="${!empty seoDescription}">
		<meta name="description" content="${seoDescription}">
	</c:if>
    </c:forEach>
    </cache:cache>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/brand-goods.css?d=${_resVerion}" media="all">
</head>
<body>
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
    <div class="m-w960p">
        <!--面包屑-->
        <div class="crumbs">
        <a href="${_domain}">${_webSiteName} ></a><c:if test="${not empty linkArr}"><a href="${_domain}${linkArr[0]}/">${linkArr[1]} ></a></c:if>${productPage.result[0].plans[0].name}
        </div>
        <!--面包屑 end-->
        <div class="brand-sale m-mt5p">
        	<c:forEach items="${productPage.result}" var="product" begin="0" end="0">
            <div class="inner cf">
            	<c:forEach items="${product.plans }" var="plan" begin="0" end="0">
            	<c:set value="${plan.planId}" var="planId"/>
                <div class="pic">
                    <img src="<zx:thumbImage originalPath='${plan.planBannerImageUrl}' imageType='t14'></zx:thumbImage>" width="680" height="210" alt="${plan.name }">
                </div>
                </c:forEach>
                <div class="main">
                    <div class="sale-tip">
                        <dl>
                            <dt>
	                            <a href="${_ctxPath }/p/${product.brand.brandId}/" target="_blank">
	                            <img src="<zx:thumbImage originalPath='${product.brand.logoImageUrl}' imageType='t22'></zx:thumbImage>" width="120" height="60" alt="${product.brand.name}">
	                            </a>
                            </dt>
                            <c:forEach items="${product.plans}" var="plan">
                            <a href="${_ctxPath }/plan-${plan.planId}.htm">
                            	<dd class="special-saller">${plan.name}</dd>
                            </a>
                            </c:forEach>
                            <dd><span class="spe-val">${minRebateProduct.rebate }</span>折起</dd>
                            <dd class="btn"><%-- <a target="_blank" href="${_ctxPath }/brand-${product.brand.brandId}.htm">品牌介绍</a> --%></dd>
                            <c:forEach items="${product.plans}" var="plan">
                            <dd><fmt:formatDate value="${plan.startTime}" pattern="MM月dd日"/>-<fmt:formatDate value="${plan.endTime}" pattern="MM月dd日"/></dd>
                            <dd class="count">
	                            <span class="countdown" data-config="{
	                            'endTime':'<fmt:formatDate value="${plan.endTime }" type="both"/>',
	                            'nowTime':'<%=System.currentTimeMillis()/1000 %>',
	                            'stopTips':'<span>活动结束谢谢参与！</span>',
	                            'timeStampTow':true
	                            }"></span>
                            </dd>
                            </c:forEach>
                        </dl>
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
            </div>
        	</c:forEach>
        </div>
        <div class="brand-goods m-mt30p">
            <div class="rank-wrap cf">
                <div class="rank">
                    <div class="m-fl">排序：</div>
                    <div class="action">
                        <span id="s-sales">销量
                        	<!-- plan-planId-sort-dir -->
                        	<a href="${_ctxPath }/plan-${productPage.params.planId}-${url_sort_100}-${url_dir_100001}.htm" class="down"></a>
                        </span>
                        <span id="s-rebate">折扣
                        	<a href="${_ctxPath }/plan-${productPage.params.planId}-${url_sort_200}-${url_dir_100000}.htm" class="down"></a>
                        </span>
                        <span id="s-price">价格
                      		<a href="${_ctxPath }/plan-${productPage.params.planId}-${url_sort_300}-${url_dir_100000}.htm" class="up"></a>
                      		<a href="${_ctxPath }/plan-${productPage.params.planId}-${url_sort_300}-${url_dir_100001}.htm" class="down"></a>
                     	</span>
                     	<%-- 
                        <span id="s-time">上架时间
                    		<a href="${_ctxPath }/plan-${productPage.params.planId}-${url_sort_400}-${url_dir_100000}.htm" class="up"></a>
                    		<a href="${_ctxPath }/plan-${productPage.params.planId}-${url_sort_400}-${url_dir_100001}.htm" class="down"></a>
                    	</span>
                    	 --%>
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
                                <a target="_blank" href="${_ctxPath }/item-${product.productId}.htm">
                                    <img src="<zx:thumbImage originalPath='${product.defaultImage }' imageType='t7'></zx:thumbImage>" alt="${product.name }" title="${product.name }" width="220" height="220">
                                    <!-- 库存为  0 或者卖家被禁用 -->
                                    <c:if test="${product.saleInventoryCount == 0 or product.isSellerOff}">
                                    <!--售罄--><span class="sale-result"></span><!--售罄 end-->
                                    </c:if>
                                    <c:if test="${product.saleInventoryCount > 0 && product.saleInventoryCount <= _minInventory}">
                                    <!--库存紧张--><span class="nervous"></span><!--库存紧张 end-->
                                    </c:if>
                                </a>
                            </dt>
                            <dd class="details">
	                            <a target="_blank" href="${_ctxPath }/item-${product.productId}.htm">
							    ${uhome:cutString(product.name,13)}
	                            </a>
                            </dd>
                            <dd class="value-box "><i class="now-value"><i class="coin">&yen;</i>${product.salePrice }</i><i class="rebate">(${product.rebate }折)</i><i class="orig-value"><i class="coin">&yen;</i>${product.marketPrice }</i></dd>
                        </dl>
                    </li>
                    </c:forEach>
                </ul>
            </div>
            <div class="navigation-wp">
            	<c:if test="${not empty productPage.result}">
           			<wms:uhomeFrontPage urlEnd=".htm" urlStart="plan-${productPage.params.planId}-${productPage.sort}-${productPage.dir}-${productPage.total}-" page="${productPage}"></wms:uhomeFrontPage>
           		</c:if>
            </div>
        </div>
    </div>
    <!--footer.jsp放置在这-->
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
    <script type="text/javascript" src="${_jsPath}/pages/brand_goods.js?d=${_resVerion}"></script>
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
    </script>
    <script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=0" ></script>
	<script type="text/javascript" id="bdshell_js"></script>
	<script type="text/javascript">
		/**
		 * 在这里定义bds_config
		 */
		var bds_config = {
			'bdDes':'${product.brand.name}-品牌特卖-${_webSiteName}-${_domain}plan-${planId}.htm',
			'bdText':'${product.brand.name}-品牌特卖-${_webSiteName}-${_domain}plan-${planId}.htm',
			'bdPic':'${_fileThumbPath}${product.brand.logoImageUrl}_t19.${fn:split(product.brand.logoImageUrl,'.')[1]}',
			'searchPic':'1'
		}
		document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
	</script>
</body>
</html>