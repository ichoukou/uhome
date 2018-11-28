<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>品牌特卖_${_webSiteName}${_domainName}，品牌折扣,限时抢购，品牌打折网。</title>
    <meta name="keywords" content="${_webSiteName}，${_domainName}，品牌特卖，限时抢购，品牌折扣，打折网">
    <meta name="description" content="${_webSiteName}-品牌特卖商城，提供${_metaHot}的特卖，全场限时抢购，品牌折扣，尽在${_domainName}打折网。">
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/brand-spe-sale.css?d=${_resVerion}" media="all">
</head>
<body>
    <!--头部-->
    <%@include file="/WEB-INF/pages/include/head.jsp"%>
    <!--头部 end-->
    <!--商品区块-->
    <div class="goods-block cf">
        <!--首页品牌导航-->
        <div class="rel">
            <div class="bread-wrap" id="proPanel" name="proPanel">
                <div class="bread-crumbs">
                    <div class="data-frame"><input type="text" value="" data-default="输入邮箱订阅最新优惠活动" /><a href="javascript:" id="postSubscriptionEmail">订 阅</a></div>
                    <div class="brands-sort">
                    <a href="javascript:;" class="current">全部</a>
                    <c:forEach items="${productCategorys }" var="pcs">
                    <a id="productCategoryId_${pcs.productCategoryId }" href="javascript:;">${pcs.name }</a>
                    </c:forEach>
                    <a href="${_ctxPath}/import.htm" target="_blank">进口商品</a>
                    </div>
                </div>
            </div>
        </div>
        <!--首页品牌导航end-->
        <!--新品发布区块-->
        <div class="new-goods-issue">
            <div class="bd multi-product">
                <div class="pro-panel">
                    <div class="inner-nav">
                        <a href="javascript:;" class="first current">最受欢迎</a>
                        <a href="javascript:;">最新上线</a>
                        <a href="javascript:;">最低折扣</a>
                        <a href="javascript:;" class="last" id="upcomLink">即将上线</a>
                    </div>
                </div>
                <!--全部品牌-->
                <div class="sort-item" id="allpro" name="allpro" style="display: block;">
                    <ul class="tab-item" style="display: block;">
                        <!-- 页面默认最受欢迎的品牌 start-->
                        <c:forEach items="${mostPopularBrands }" var="special">
                        <li>
                            <div class="pic">
                                <span class="discount">
                                    <c:forEach items="${special.products }" var="product" begin="0" end="0">
                                        <span><var>${product.rebate }</var>折起</span>
                                        <span><fmt:formatDate value="${special.startTime }" pattern="MM月dd日"/>-<fmt:formatDate value="${special.endTime }" pattern="MM月dd日"/></span>
                                    </c:forEach>
                                </span>
                                <span class="brand-logo">
                                    <a target="_blank" href="${_ctxPath }/brand-${special.brand.brandId}.htm">
                                    <img src="<zx:thumbImage originalPath='${special.brand.logoImageUrl}' imageType='t1'></zx:thumbImage>" alt="${special.brand.name }">
                                    </a>
                                </span>
                                <span class="brand-img">
                                    <a target="_blank" href="${_ctxPath }/plan-${special.planId }.htm">
                                    <img src="<zx:thumbImage originalPath='${special.imageUrl}' imageType='t13'></zx:thumbImage>" alt="${special.name }">
                                    </a>
                                </span>
                            </div>
                            <div class="bm-info">
                                <i class="info-name">${special.name }</i>
                                <i class="info-time">剩余 <span class="countdown" data-config="{
                                'endTime':'<fmt:formatDate value="${special.endTime }" type="both"/>',
                                'nowTime':'<%=System.currentTimeMillis()/1000 %>',
                                'stopTips':'<span>活动结束谢谢参与！</span>'
                              }"></span></i>
                                <i class="info-story"><a target="_blank" href="${_ctxPath }/brand-${special.brand.brandId}.htm">品牌介绍</a></i>
                            </div>
                        </li>
                        </c:forEach>
                        <!-- 页面默认最受欢迎的品牌 end -->
                    </ul>
                    
                    <ul class="tab-item">
                    	<!-- 最新上线品牌 start -->
                    	<c:forEach items="${lastOnLineBrands }" var="special">
                        <li>
                            <div class="pic">
                                <span class="discount">
                                    <c:forEach items="${special.products }" var="product" begin="0" end="0">
                                        <span><var>${product.rebate }</var>折起</span>
                                        <span><fmt:formatDate value="${special.startTime }" pattern="MM月dd日"/>-<fmt:formatDate value="${special.endTime }" pattern="MM月dd日"/></span>
                                    </c:forEach>
                                </span>
                                <span class="brand-logo">
                                    <a target="_blank" href="${_ctxPath }/brand-${special.brand.brandId}.htm">
                                    <img src="<zx:thumbImage originalPath='${special.brand.logoImageUrl}' imageType='t1'></zx:thumbImage>" alt="${special.brand.name }">
                                    </a>
                                </span>
                                <span class="brand-img">
                                    <a target="_blank" href="${_ctxPath }/plan-${special.planId }.htm">
                                    <img src="<zx:thumbImage originalPath='${special.imageUrl}' imageType='t13'></zx:thumbImage>" alt="${special.name }">
                                    </a>
                                </span>
                            </div>
                            <div class="bm-info">
                                <i class="info-name">${special.name }</i>
                                <i class="info-time">剩余 <span class="countdown" data-config="{
                                'endTime':'<fmt:formatDate value="${special.endTime }" type="both"/>',
                                'nowTime':'<%=System.currentTimeMillis()/1000 %>',
                                'stopTips':'<span>活动结束谢谢参与！</span>'
                              }"></span></i>
                                <i class="info-story"><a target="_blank" href="${_ctxPath }/brand-${special.brand.brandId}.htm">品牌介绍</a></i>
                            </div>
                        </li>
                        </c:forEach>
                    	<!-- 最新上线品牌  end-->
                    </ul>
                    <ul class="tab-item">
                    	<!-- 最低折扣品牌 start-->
                    	<c:forEach items="${lowestDiscountBrands }" var="special">
                        <li>
                            <div class="pic">
                                <span class="discount">
                                    <c:forEach items="${special.products }" var="product" begin="0" end="0">
                                        <span><var>${product.rebate }</var>折起</span>
                                        <span><fmt:formatDate value="${special.startTime }" pattern="MM月dd日"/>-<fmt:formatDate value="${special.endTime }" pattern="MM月dd日"/></span>
                                    </c:forEach>
                                </span>
                                <span class="brand-logo">
                                    <a target="_blank" href="${_ctxPath }/brand-${special.brand.brandId}.htm">
                                    <img src="<zx:thumbImage originalPath='${special.brand.logoImageUrl}' imageType='t1'></zx:thumbImage>" alt="${special.brand.name }">
                                    </a>
                                </span>
                                <span class="brand-img">
                                    <a target="_blank" href="${_ctxPath }/plan-${special.planId }.htm">
                                    <img src="<zx:thumbImage originalPath='${special.imageUrl}' imageType='t13'></zx:thumbImage>" alt="${special.name }">
                                    </a>
                                </span>
                            </div>
                            <div class="bm-info">
                                <i class="info-name">${special.name }</i>
                                <i class="info-time">剩余 <span class="countdown" data-config="{
                                'endTime':'<fmt:formatDate value="${special.endTime }" type="both"/>',
                                'nowTime':'<%=System.currentTimeMillis()/1000 %>',
                                'stopTips':'<span>活动结束谢谢参与！</span>'
                              }"></span></i>
                                <i class="info-story"><a target="_blank" href="${_ctxPath }/brand-${special.brand.brandId}.htm">品牌介绍</a></i>
                            </div>
                        </li>
                        </c:forEach>
                    	<!-- 最低折扣品牌 end-->
                    </ul>
                </div>
                <!--全部品牌end-->
                
				<c:forEach items="${planCategorys}" var="planCategory">
	                <!--按照分类-->
	                <div class="sort-item">
	                	<%-- ${planCategory.key} --%>
	                	<c:forEach items="${planCategory.value}" var="planC" varStatus="s">
	                		<%-- ${planC.key} --%>
	                		<ul class="tab-item" style="<c:if test='${s.index == 0}'>display: block;</c:if>">
	                			<c:forEach items="${planC.value}" var="plan">
									<li>
			                            <div class="pic">
			                                <span class="discount">
				                                <c:forEach items="${plan.products }" var="product" begin="0" end="0">
					                                <span><var>${product.rebate }</var>折起</span>
					                                <span><fmt:formatDate value="${plan.startTime }" pattern="MM月dd日"/>-<fmt:formatDate value="${plan.endTime }" pattern="MM月dd日"/></span>
				                                </c:forEach>
			                                </span>
			                                <span class="brand-logo">
				                                <a target="_blank" href="${_ctxPath }/brand-${plan.brand.brandId}.htm">
				                                	<img src="<zx:thumbImage originalPath='${plan.brand.logoImageUrl}' imageType='t1'></zx:thumbImage>" alt="${plan.brand.name}">
				                                </a>
			                                </span>
			                                <span class="brand-img">
				                                <a target="_blank" href="${_ctxPath }/plan-${plan.planId }.htm">
				                                	<img src="<zx:thumbImage originalPath='${plan.imageUrl}' imageType='t13'></zx:thumbImage>" alt="${plan.name }">
				                                </a>
			                                </span>
			                            </div>
			                            <div class="bm-info">
			                                <i class="info-name">${plan.name }</i>
			                                <i class="info-time">剩余 <span class="countdown" data-config="{
			                                'endTime':'<fmt:formatDate value="${plan.endTime }" type="both"/>',
			                                'nowTime':'${currentTime }',
			                                'stopTips':'<span>活动结束谢谢参与！</span>'
			                                }"></span></i>
			                                <i class="info-story"><a target="_blank" href="${_ctxPath }/brand-${plan.brand.brandId}.htm">品牌介绍</a></i>
			                            </div>
			                        </li>
	                			</c:forEach>
	                		</ul>
	                	</c:forEach>
	                </div> 
	                <!--按照分类 end-->
              </c:forEach>

            </div>
            <div class="bd upcom-product grayscale">
            	<!-- 如果没有即将上线的数据 则不显示 -->
            	<c:if test="${fn:length(soonBrands)!=0}">
            	<div class="grayscale-t">即将上线</div>
                <ul>
                	<!-- 即将上线start -->
                	<c:forEach items="${soonBrands }" var="soon">
                    <li>
                        <div class="pic">
                            <span class="discount">
                                <c:forEach items="${soon.products }" var="product" begin="0" end="0">
				                   <span><var>${product.rebate }</var>折起</span>
				                   <span>${soon.name }</span>
				                </c:forEach>
                            </span>
                            <span class="brand-logo">
                            	<a target="_blank" href="${_ctxPath }/brand-${soon.brand.brandId}.htm">
                            		<img src="<zx:thumbImage originalPath='${soon.brand.logoImageUrl}' imageType='t1'></zx:thumbImage>" alt="${soon.brand.name }">
                            	</a>
                           	</span>
                            <span class="brand-img">
                            	<img src="<zx:thumbImage originalPath='${soon.imageUrl}' imageType='t13'></zx:thumbImage>" alt="${soon.name }">
                           	</span>
                        </div>
                        <div class="bm-info">
                            <i class="info-name">
                            	<fmt:formatDate value="${soon.startTime }" pattern="MM月dd日"/>
	                            (<fmt:formatDate value="${soon.startTime }" pattern="EEEE"/>)
	                            -
	                            <fmt:formatDate value="${soon.endTime }" pattern="MM月dd日"/>
	                            (<fmt:formatDate value="${soon.endTime }" pattern="EEEE"/>)
                            </i>
                            <%--<i class="info-time">还有  <span class="countdown" data-config="{--%>
                            <%--'endTime':'<fmt:formatDate value="${soon.endTime }" type="both"/>',--%>
                            <%--'nowTime':'<%=System.currentTimeMillis()/1000 %>',--%>
                            <%--'stopTips':'<span>活动已经开始,请刷新页面！</span>'--%>
                          <%--}"></span> 上线</i>--%>
                            <%--<i class="info-story"><a href="${_ctxPath }/brand-${soon.brand.brandId}.htm">品牌介绍</a></i>--%>
                            <div class="data-frame"><input type="text" value="" data-default="请输入您的邮箱" class="input-default" id="postSubSoonEmail${soon.planId}"><a href="javascript:postSubSoonEmail(${soon.brand.brandId},${soon.planId});">开售提醒</a></div>
                        </div>
                    </li>
                    </c:forEach>
                   <!-- 即将上线end -->
                </ul>
                </c:if>
            </div>
        </div>
        <!--新品发布区块 end-->
        <!--通告+广告区块-->
        <div class="notice">
        <cache:cache time="${_cacheSec}" refresh="${_needRefresh}">
            <div class="brand-appointment">
                <h2><span>每日<em>10:00</em>点新品上线</span>热卖品牌</h2>
                <div class="main cf">
                    <ul>
                    	<c:forEach items="${hotBrands }" var="hotBrand">
                        <li><a target="_blank" href="${_ctxPath }/brand-${hotBrand.brandId}.htm"><img src="<zx:thumbImage originalPath='${hotBrand.logoImageUrl}' imageType='t2'></zx:thumbImage>" alt="${hotBrand.name }"></a></li>
                        </c:forEach>
                    </ul>
                </div>
            </div>
       	 	<div class="sale-notice">
                <h2>人气商品</h2>
                <div class="tab">
                    <div class="content-wrap">
                        <div class="tab-item" style="display: block;">
                            <table>
                                <tbody>
                                	<c:forEach items="${mostHits }" var="mostHit" begin="0" end="2" varStatus="status">
                                    <tr>
                                        <td class="num-rank" width="30"><span>${status.count }</span></td>
                                        <td width="160">
                                            <div class="pic">
                                            <a target="_blank" href="${_ctxPath }/item-${mostHit.productId}.htm">
                                            	<img src="<zx:thumbImage originalPath='${mostHit.defaultImage }' imageType='t6'/>" alt="${mostHit.name }" width="50" height="50">
                                           	</a>
                                           	</div>
                                            <div class="main">
                                                <p class="link">
	                                                <a target="_blank" href="${_ctxPath }/item-${mostHit.productId}.htm">
	                                                <c:choose>
							                          <c:when test="${fn:length(mostHit.name) > 13}">  
										    			<c:out value="${fn:substring(mostHit.name, 0, 13)}" />  
												   	  </c:when>  
											  	      <c:otherwise>${mostHit.name }</c:otherwise>  
												    </c:choose>
	                                                </a>
                                                </p>
                                                <p class="org-price">原价：${mostHit.marketPrice }</p>
                                            </div>
                                        </td>
                                        <td><span class="price">¥${mostHit.salePrice }</span></td>
                                    </tr>
                                    </c:forEach>
                                    <c:forEach items="${mostHits }" var="mostHit" begin="3" varStatus="status">
                                    <tr class="s-link">
                                        <td class="num-rank"><span>${status.index +1}</span></td>
                                        <td>
                                            <a target="_blank" href="${_ctxPath }/item-${mostHit.productId}.htm">
                                         	<c:choose>
					                          <c:when test="${fn:length(mostHit.name) > 13}">  
								    			<c:out value="${fn:substring(mostHit.name, 0, 13)}" />  
										   	  </c:when>  
									  	      <c:otherwise>${mostHit.name }</c:otherwise>  
										    </c:choose>
                                            </a>
                                        </td>
                                        <td></td>
                                    </tr>
                                    <tr class="s-detail">
                                        <td class="num-rank"><span>${status.index +1}</span></td>
                                        <td>
                                            <div class="pic">
                                           	<a target="_blank" href="${_ctxPath }/item-${mostHit.productId}.htm">
                                            	<img src="<zx:thumbImage originalPath='${mostHit.defaultImage }' imageType='t6'/>" alt="${mostHit.name }" width="50" height="50">
                                            </a>
                                            </div>
                                            <div class="main">
                                                <p class="link">
                                                <a target="_blank" href="${_ctxPath }/item-${mostHit.productId}.htm">
                                                	<c:choose>
							                          <c:when test="${fn:length(mostHit.name) > 13}">  
										    			<c:out value="${fn:substring(mostHit.name, 0, 13)}" />  
												   	  </c:when>  
											  	      <c:otherwise>${mostHit.name }</c:otherwise>  
												    </c:choose>
                                                </a>
                                                </p>
                                                <p class="org-price">原价：${mostHit.marketPrice }</p>
                                            </div>
                                        </td>
                                        <td><span class="price">¥${mostHit.salePrice }</span></td>
                                    </tr>
                                   </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="sale-notice">
                <h2>优惠多多</h2>
                <div class="tab">
                    <div class="content-wrap">
                        <div class="tab-item" style="display: block;">
                            <table>
                                <tbody>
                                <c:forEach items="${mostCheaps }" var="mostCheap" begin="0" end="2" varStatus="status">
                                <tr>
                                    <td class="num-rank" width="30"><span>${status.count }</span></td>
                                    <td width="160">
                                        <div class="pic">
                                        <a target="_blank" href="${_ctxPath }/item-${mostCheap.productId}.htm">
                                        	<img src="<zx:thumbImage originalPath='${mostCheap.defaultImage }' imageType='t6'/>" alt="${mostCheap.name }" width="50" height="50">
                                        </a>
                                        </div>
                                        <div class="main">
                                            <p class="link">
                                            	<a target="_blank" href="${_ctxPath }/item-${mostCheap.productId}.htm">
                                            	<c:choose>
						                          <c:when test="${fn:length(mostCheap.name) > 13}">  
									    			<c:out value="${fn:substring(mostCheap.name, 0, 13)}" />  
											   	  </c:when>  
										  	      <c:otherwise>${mostCheap.name }</c:otherwise>  
										   		</c:choose>
                                            	</a>
                                           	</p>
                                            <p class="org-price">原价：${mostCheap.marketPrice }</p>
                                        </div>
                                    </td>
                                    <td><span class="price">¥${mostCheap.salePrice }</span></td>
                                </tr>
                                </c:forEach>
                                <c:forEach items="${mostCheaps }" var="mostCheap" begin="3" varStatus="status">
                                <tr class="s-link">
                                    <td class="num-rank"><span>${status.index +1}</span></td>
                                    <td>
                                        <a target="_blank" href="${_ctxPath }/item-${mostCheap.productId}.htm">
                                        	<c:choose>
					                          <c:when test="${fn:length(mostCheap.name) > 13}">  
								    			<c:out value="${fn:substring(mostCheap.name, 0, 13)}" />  
										   	  </c:when>  
									  	      <c:otherwise>${mostCheap.name }</c:otherwise>  
										    </c:choose>
                                        </a>
                                    </td>
                                    <td></td>
                                </tr>
                                <tr class="s-detail">
                                    <td class="num-rank"><span>${status.index +1 }</span></td>
                                    <td>
                                        <div class="pic">
                                        <a target="_blank" href="${_ctxPath }/item-${mostCheap.productId}.htm">
                                        	<img src="<zx:thumbImage originalPath='${mostCheap.defaultImage }' imageType='t6'/>" alt="${mostCheap.name }" width="50" height="50">
                                        </a>
                                        </div>
                                        <div class="main">
                                            <p class="link">
                                            <a href="${_ctxPath }/item-${mostCheap.productId}.htm">
                                         	 	<c:choose>
						                          <c:when test="${fn:length(mostCheap.name) > 13}">  
									    			<c:out value="${fn:substring(mostCheap.name, 0, 13)}" />  
											   	  </c:when>  
										  	      <c:otherwise>${mostCheap.name }</c:otherwise>  
											    </c:choose>
                                            </a>
                                            </p>
                                            <p class="org-price">原价：${mostCheap.marketPrice }</p>
                                        </div>
                                    </td>
                                    <td><span class="price">¥${mostCheap.salePrice }</span></td>
                                </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>	
        </cache:cache>
        </div>
        <!--通告+广告区块 end-->
    </div>
    <!--商品区块 end-->
    <!--返回顶部-->
    <div class="rt-fixed-wrap">
        <div class="go-top"><a href="javascript:" title="返回顶部">返回顶部</a></div>
    </div>
    <!--返回顶部 end-->
    <!--底部区块-->
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
    <!--底部区块 end-->
    <script type="text/javascript" src="${_jsPath }/pages/brand_spe_sale.js?d=${_resVerion}"></script>
    <script type="text/javascript">
 	 //邮件订阅
    var subEmail = $("#postSubscriptionEmail");
    subEmail.click(function(){
    	//获取用户的邮箱
    	//$(".hd.cf input[type='text']")
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
    			'mailSubscribe.type':<%=MailSubscribe.TYPE_SPECIAL_SELLER%>,
    			'mailSubscribe.status':<%=MailSubscribe.STATUS_SUBSCRIBE%>,
    			'mailSubscribe.email':txt
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
    //即将上线订阅
    function postSubSoonEmail(brandId,obj){
    	var email = $("#postSubSoonEmail"+obj).val();
    	if(email == $("#postSubSoonEmail"+obj).attr("data-default")){
    		return;
    	}
    	//邮箱格式不正确
    	if(!validtorEmail(email)){
    		return;
    	}
    	$.ajax({
    		type: 'POST',
			url: '${_ctxPath}/subscriptionEmail.htm',
			async: true,
			data: {
				'mailSubscribe.type':<%=MailSubscribe.TYPE_BRAND_SUBSCRIBE%>,
				'mailSubscribe.status':<%=MailSubscribe.STATUS_SUBSCRIBE%>,
				'mailSubscribe.email':email,
				'mailSubscribe.brandId':brandId
			},
			dataType:'json',
			success: function(data){
				popupDialog(data.info);
				$("#postSubSoonEmail"+obj).attr("value","");
		        },
	        error: function(data){
	        	popupDialog("error:"+data.responseText);
	        }
    	});
    }
    
    //  验证邮箱TODO
    function validtorEmail(email){
    	var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    	if(reg.test(email)){
    		return true;
    	}
    	popupDialog("邮箱格式不正确!","1002");
    	return false;
    }
    
    //3个子目录选中加样式
    var url = location.href;
    if(url.indexOf("-") > 0){
    	var pcid = url.split("-")[1].split(".")[0];
    	//console.log("pcid:"+pcid);
    	var sid = "#productCategoryId_"+pcid;
       	$(sid).addClass("on");
    }
    </script>
</body>
</html>