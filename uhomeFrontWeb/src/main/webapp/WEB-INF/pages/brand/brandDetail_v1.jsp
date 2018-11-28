<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>${brand.name }_${_webSiteName}${_domainName}商城</title>
	<meta name="keywords" content="${_webSiteName}，${_domainName},天天特价，正品，特卖，限时抢购，品牌折扣">
	<meta name="description" content="${_webSiteName}商城${brand.name }专场提供各种男女时尚品牌行货特卖，（${_domainName}）${brand.name }折扣，天天特价，属于公司正规品牌销售，限时抢购，100%正品保障。">
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/brand-intro.css?d=${_resVerion}" media="all">
</head>
<body>
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
    <div class="m-w960p">
        <!--面包屑-->
        <div class="crumbs"><a href="${_ctxPath }/default.htm">${_webSiteName } ></a>${brand.name }</div>
        <!--面包屑 end-->
        <div class="cf">
        	<cache:cache time="${_cacheSec}" refresh="${_needRefresh}" key="brand-${brand.brandId}">
	        <div class="intro-l">
	            <div class="box">
	               <div class="b1">
	                   <img src="<zx:thumbImage originalPath='${brand.logoImageUrl }' imageType='t19'></zx:thumbImage>" alt="${brand.name }" width="200" height="100">
	               </div>
	               <div class="b2">
	                   <h3>品牌介绍:</h3>
	                   <p><span>英文名：</span>${brand.englishName }</p>
	                   <p><span>中文名：</span>${brand.name }</p>
	                   <p><span>品牌创始人：</span>${brand.founder }</p>
	                   <p><span>所属国家：</span>${brand.country }</p>
	                   <p><span>创始时间：</span><fmt:formatDate value="${brand.foundationTime }" pattern="yyyy年MM月"/></p>
	                   <p><span>产品类别：</span>${brand.feature }</p>
	               </div>
	               <div class="b3 cf">
	                   <p>预定${brand.name }下次特卖</p>
	                   <div class="cf"><div class="data-frame"><input type="text" value="" data-default="输入邮箱订阅最新优惠" class="input-default"><a href="javascript:" id="postSubscriptionEmail">订阅</a></div></div>
	                   <div class="cf">
	                   <!-- Baidu Button BEGIN -->
	                   <div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare" 
								data="'desc':'${brand.name}-品牌介绍-${_webSiteName}',
								'text':'${brand.name}-品牌介绍-${_webSiteName}',
								'url':'',
								'pic':'<zx:thumbImage originalPath='${brand.logoImageUrl }' imageType='t19'></zx:thumbImage>'">
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
	            <div class="box m-mt30p">
	                <div class="hd"><h2>热销品牌</h2></div>
	                <div class="bd cf">
	                    <ul>
	                    	<c:forEach items="${hotBrands }" var="hotBrand">
	                        	<li><a href="${_ctxPath }/brand-${hotBrand.brandId}.htm"><img src="<zx:thumbImage originalPath='${hotBrand.logoImageUrl }' imageType='t2'></zx:thumbImage>" alt="${hotBrand.name }" width="68" height="34"></a></li>
	                        </c:forEach>
	                    </ul>
	                </div>
	            </div>
	        </div>
	    	</cache:cache>    
	        <div class="intro-r">
	    	<cache:cache time="${_cacheSec}" refresh="${_needRefresh}" key="describe-${brand.brandId}">
	            <div class="brand-sale">
	                <div class="inner cf">
	                    <div class="pic">
	                        <img src="<zx:thumbImage originalPath='${brand.brandImageUrl }' imageType='t3'></zx:thumbImage>" width="710" height="210" alt="${brand.name }">
	                    </div>
	                    <div class="main">
						    ${brand.describe }
	                    </div>
	                </div>
	            </div>
	    	</cache:cache>
	            <div class="brand-goods m-mt30p">
	                <div class="rank-wrap cf">
	                    <h2>
	                    ${brand.name }
	                    <c:forEach items="${productPage.result }" var="product" begin="0" end="0">
	                    	<c:if test="${product.brand.isHistoryRecord}">历史</c:if>
	                    </c:forEach>
	                    	热销商品
	                    </h2>
	                </div>
	                <div class="item-wrap">
	                    <ul>
                    		<c:forEach items="${productPage.result }" var="product">
	                        <li>
	                            <dl>
	                                <dt>
                                	<c:if test="${!product.brand.isHistoryRecord}">
	                                    <a href="${_ctxPath }/item-${product.productId}.htm">
	                                        <img src="<zx:thumbImage originalPath='${product.defaultImage }' imageType='t7'></zx:thumbImage>" alt="${product.name }" title="${product.name }" width="220" height="220">
	                                        <c:if test="${product.saleInventoryCount == 0 or product.isSellerOff}">
		                                    <!--售罄--><span class="sale-result"></span><!--售罄 end-->
		                                    </c:if>
	                                    </a>
                                    </c:if>
                                    <!-- 历史排期商品 -->
                                    <c:if test="${product.brand.isHistoryRecord}">
	                                    <a href="javascript:;">
	                                        <img src="<zx:thumbImage originalPath='${product.defaultImage }' imageType='t7'></zx:thumbImage>" alt="${product.name }" title="${product.name }" width="220" height="220">
	                                    </a>
                                    </c:if>
	                                </dt>
	                                <!-- 历史排期商品 -->
                                    <c:if test="${product.brand.isHistoryRecord}">
                                    	<dd class="details"><a href="javascript:;">${product.name }</a></dd>
                                    </c:if>
	                                <c:if test="${!product.brand.isHistoryRecord}">
	                                	<dd class="details"><a href="${_ctxPath }/item-${product.productId}.htm">${product.name }</a></dd>
	                                </c:if>
	                                <dd class="value-box"><i class="now-value">¥${product.salePrice }</i><i class="rebate">(${product.rebate }折)</i><i class="orig-value">¥${product.marketPrice }</i></dd>
	                            </dl>
	                        </li>
	                        </c:forEach>
	                    </ul>
	                </div>
	                <div class="navigation-wp">
	                	<c:if test="${not empty productPage.result}">
		                    <wms:uhomeFrontPage urlEnd=".htm" urlStart="brand-${brand.brandId}-${productPage.total}-" page="${productPage}"></wms:uhomeFrontPage>
		           		</c:if>
	                </div>
	            </div>
	        </div>
    	</div>
    </div>
    <!--footer.jsp放置在这-->
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
    <script type="text/javascript" src="${_jsPath }/pages/brand_intro.js?d=${_resVerion}"></script>
    <script type="text/javascript">
    var subEmail = $("#postSubscriptionEmail");
    subEmail.click(function(){
    	//获取用户的邮箱
    	var txt = subEmail.prev().val();
    	if(txt == subEmail.prev().attr("data-default")){
    		return;
    	}
    	if(!validtorEmail(txt)){
    		return;
    	}
    	$.ajax({
    		type: 'POST',
			url: '${_ctxPath}/subscriptionEmail.htm',
			async: true,
			data: {
				'mailSubscribe.type':<%=MailSubscribe.TYPE_BRAND_SUBSCRIBE%>,
				'mailSubscribe.status':<%=MailSubscribe.STATUS_SUBSCRIBE%>,
				'mailSubscribe.email':txt,
				'mailSubscribe.brandId':${brand.brandId}
			},
			dataType:'json',
			success: function(data){
				popupDialog(data.info,"10000");
				subEmail.prev().attr("value","");
		        },
	        error: function(data){
	        	popupDialog("error:"+data.responseText,"10001");
	        }
    	});
    });
	//  验证邮箱TODO
    function validtorEmail(email){
    	var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    	if(reg.test(email)){
    		return true;
    	}
    	popupDialog("邮箱格式不正确!","1006");
    	return false;
    }
    </script>
</body>
</html>