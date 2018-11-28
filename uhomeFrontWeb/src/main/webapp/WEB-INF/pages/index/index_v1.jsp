<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.Suggest"%>
<%@page import="com.ytoxl.module.core.common.utils.SpringContextUtils"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.service.PlanService"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.service.BrandService"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.service.AdvService"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.service.HotWordService"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.service.ProductCategoryService"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Plan"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Brand"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.Advertisement"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.AdvPosition"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.HotWord"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.common.utils.DateUtil" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.Map" %>
<%@page import="java.util.Date" %>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window" xmlns:wb="http://open.weibo.com/wb">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${_webSiteName}（${_domainName}）_${_webSiteName}是一家天天特价的网上特卖商城，品牌折扣，正品保障，限时抢购。</title>
    <meta name="keywords" content="${_webSiteName}，${_domainName}，天天特价，正品，特卖，限时抢购，品牌折扣" >
    <meta name="description" content="${_webSiteName}${_domainName}_是一个以天天特价、品牌折扣、限时抢购为特色的网上特卖商城，全球各大品牌官方授权入驻，全国配送，100%正品保障，超值购物，首选${_webSiteName}。" >
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath}/pages/index.css?d=${_resVerion}" media="all">
</head>
<body>
    <!--头部-->
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
    <!--头部 end-->
    <cache:cache time="${_cacheSec}" refresh="${_needRefresh}">
    <%!
    PlanService planService = (PlanService)(SpringContextUtils.getContext().getBean("planServiceImpl"));
    BrandService brandService = (BrandService)(SpringContextUtils.getContext().getBean("brandServiceImpl"));
    AdvService advService = (AdvService)(SpringContextUtils.getContext().getBean("advServiceImpl"));
    ProductCategoryService productCategoryService = (ProductCategoryService)(SpringContextUtils.getContext().getBean("productCategoryServiceImpl"));
    %>
    </cache:cache>
    <%
  	//首页秒杀预告  包括今天   
    List<Plan> secKillNotices = planService.listPlanSecKillNotice();
    //如果首页秒杀 不够数量  7个  用广告填充
    List<Advertisement> advs = new ArrayList<Advertisement>();
    if(secKillNotices.size() < AdvPosition.ADV_NUM){
    	//查询广告
    	advs = advService.getHomeDefaultSecKillAdvList();
    	if(advs != null && advs.size() > 0){
    		//获取秒杀没有填充的位置个数
        	int advVildNum = AdvPosition.ADV_NUM - secKillNotices.size();
        	advs = advs.subList(0,advVildNum);
        	request.setAttribute("advs",advs);
    	}
    }
    
    //最新上线的品牌
	List<Plan> lastOnLineBrands = planService.listPlanLastOnlineBrands();
	//按品牌的折扣最低排序
	List<Plan> lowestDiscountBrands = planService.listPlanLowestDiscountBrands();
	//最受欢迎的品牌 排序
	List<Plan> mostPopularBrands = planService.listPlanMostPopularBrands();
	//即将上线的品牌
	List<Plan> soonBrands = planService.listPlanSoonBrands();
	//排期按照类别存储
	Map<Integer, Map<String,List<Plan>>> planCategorys = planService.listPlanByCategorys();	
	//开售通知
	Map<String,List<Plan>> openSales = planService.listPlanOpenSalesBrand();
	//品牌预约
	List<Brand> brands = brandService.listSubscribeBrands();
	
	request.setAttribute("secKillNotices",secKillNotices);
	
	request.setAttribute("lastOnLineBrands",lastOnLineBrands);
	
	request.setAttribute("lowestDiscountBrands",lowestDiscountBrands);
	
	request.setAttribute("mostPopularBrands",mostPopularBrands);
	
	request.setAttribute("soonBrands",soonBrands);
	
	request.setAttribute("planCategorys",planCategorys);
	
	request.setAttribute("openSales",openSales);
	
	request.setAttribute("brands",brands);
	
	//当前时间用于倒计时
	request.setAttribute("currentTime",System.currentTimeMillis()/1000);
	
	//将当天的时间设置成当天10点
	request.setAttribute("currentDayTen",DateUtil.setTimeOfDay(new Date(),10,0,0));
	//当前时间  用于预售 预告
	request.setAttribute("currentTimes",new Date());
	request.setAttribute("tomorrow",DateUtil.nextDate(new Date()));
	request.setAttribute("afterTomorrow",DateUtil.nextNumDate(new Date(),2));
	request.setAttribute("threeDayFromToday",DateUtil.nextNumDate(new Date(),3));
	
	List<ProductCategory> productCategorys = productCategoryService.listProductCategory();
	request.setAttribute("productCategorys",productCategorys);
    %>
    <fmt:setLocale value="zh_CN"/>
    <!--秒杀区块-->
    <div class="spike-block">
    <cache:cache time="${_cacheSec}" refresh="${_needRefresh}">
        <div class="bd cf">
          <!-- 今天的秒杀不为空 -->
     	  <c:if test="${not empty secKillNotices}">
            <div class="spike-today">
            	<c:forEach items="${secKillNotices}" begin="0" end="0" var="secKill">
            	<fmt:formatDate var="currentTiemFormat" value="${currentTimes}" pattern="yyyyMMdd"/>
            	<fmt:formatDate var="secKillTime" value="${secKill.startTime}" pattern="yyyyMMdd"/>
                <c:if test="${currentTiemFormat == secKillTime}">
                	<!-- 判断是否是今天10点前 -->
                	<c:if test="${currentTimes < currentDayTen}">
                		<div class="tit"><h6>每日<em>10:00</em>点秒杀</h6>今日<b>秒</b>杀</div>
                	</c:if>
                	<c:if test="${currentTimes > currentDayTen}">
                		<div class="tit"><h6>每日<em>10:00</em>点秒杀</h6><b>秒</b>杀</div>
                	</c:if>
                </c:if>
                <c:if test="${currentTiemFormat != secKillTime}">
                	<div class="tit"><h6>每日<em>10:00</em>点秒杀</h6><b>秒</b>杀预告</div>
               	</c:if>
                <div class="img"><a target="_blank" href="${_ctxPath }/seckill-${secKill.planId}.htm"><img src="<zx:thumbImage originalPath='<%=secKillNotices.get(0).getProducts().get(0).getDefaultImage() %>' imageType='t4'></zx:thumbImage>" width="320" height="320" /></a></div>
                <div class="main">
               	<c:forEach begin="0" end="0" items="${secKill.products}" var="product">
                    <h3>
			            <a target="_blank" href="${_ctxPath }/seckill-${secKill.planId}.htm">
							<c:choose>
			                <c:when test="${fn:length(product.name) > 13}">  
						    	<c:out value="${fn:substring(product.name, 0, 13)}" />  
							</c:when>  
							<c:otherwise>${product.name }</c:otherwise>  
							</c:choose>
						</a>
					</h3>
                    <p>共<var>${product.secKillInventoryCount }</var>件</p>
                    <p>距离秒杀开始还有</p>
                    <p><span class="countdown" data-config="{
                            'endTime':'<fmt:formatDate value="${secKill.startTime}" type="both"/>',
                            'nowTime':'${currentTime }',
                            'stopTips':'<span>开始秒杀</span>',
                            'timeStampTow':true
                          }"></span></p>
                    <p class="quick">秒杀价：<var>¥${product.secKillPrice }</var></p>
                    <a target="_blank" href="${_ctxPath }/seckill-${secKill.planId}.htm" class="go-see">去看看</a>
                </c:forEach>
                </div>
               </c:forEach>
            </div>
          </c:if>
          <!-- 今天的秒杀为空 显示广告-->
     	  <c:if test="${empty secKillNotices and not empty advs}">
            <div class="spike-today">
                <div class="img">
                	<a target="_blank" href="<%=advs.get(0).getLinkUrl()%>">
                	<img src="<zx:thumbImage originalPath='<%=advs.get(0).getImageUrl() %>' imageType='t4'></zx:thumbImage>" width="320" height="320" />
                	</a>
                </div>
                <div class="main">
                    <h3><%=advs.get(0).getName() %></h3>
                </div>
            </div>
          </c:if>
          <!-- 6个秒杀为空 不显示 -->
            <div class="spike-upcoming">
                <ul>
               	<c:if test="${not empty secKillNotices }">
                  <c:forEach items="${secKillNotices}" begin="1" var="secKill">
                    <li>
                        <div class="main-info">
                       <c:forEach begin="0" end="0" items="${secKill.products}" var="product">
                       		<p class="img"><a target="_blank" href="${_ctxPath }/seckill-${secKill.planId}.htm">
                       			<img width="202" height="202" src="<zx:thumbImage originalPath='${product.defaultImage }' imageType='t5'></zx:thumbImage>" alt="${product.name }">
                       		</a></p>
                       </c:forEach>
                       <p class="forecast-time"><fmt:formatDate value="${secKill.startTime }" pattern="EEEE"/><i></i>
                                <span class="countdown"data-config="{
                            'endTime':'<fmt:formatDate value="${secKill.startTime }" type="both"/>',
                            'nowTime':'${currentTime }',
                            'stopTips':'<span>活动结束谢谢参与！</span>'
                          }">1天05小时33分28秒</span>
                            </p>
                        </div>
                       <c:forEach begin="0" end="0" items="${secKill.products}" var="product">
                        <div class="tit">
                        	<a target="_blank" href="${_ctxPath }/seckill-${secKill.planId}.htm">
                        	<c:choose>
	                           <c:when test="${fn:length(product.name) > 13}">  
					    			<c:out value="${fn:substring(product.name, 0, 13)}" />  
							   </c:when>  
						  	<c:otherwise>  
							    ${product.name }
						  	</c:otherwise>  
							</c:choose>
                        	</a>
                       	</div>
                       </c:forEach>
                       <c:forEach begin="0" end="0" items="${secKill.products}" var="product">
                       <p class="price"><var class="vie">¥${product.secKillPrice }</var><i>¥${product.marketPrice }</i></p>
                       </c:forEach>
                    </li>
                  </c:forEach>
                </c:if>
                <!-- 秒杀不够 用默认广告填充 -->
                <c:if test="${not empty secKillNotices }">
                  <c:forEach items="${advs }" var="adv">
                    <li>
                        <div class="main-info">
                            <p class="img">
                            	<a target="_blank" href="${adv.linkUrl }">
                            		<img width="202" height="202" src="<zx:thumbImage originalPath='${adv.imageUrl }' imageType='t5'></zx:thumbImage>" alt="${adv.name }">
                         		</a>
                         	</p>
                        </div>
                        <div class="tit"><a target="_blank" href="${adv.linkUrl }">${adv.name}</a></div>
                    </li>		
                  </c:forEach>
                </c:if>
                <!-- 秒杀为空 用默认广告填充 -->
                <c:if test="${empty secKillNotices and not empty advs }">
                  <c:forEach items="${advs }" var="adv" begin="1">
                    <li>
                        <div class="main-info">
                            <p class="img"><a target="_blank" href="${adv.linkUrl }">
                            	<img width="202" height="202" src="<zx:thumbImage originalPath='${adv.imageUrl }' imageType='t5'></zx:thumbImage>" alt="${adv.name}">
                           	</a></p>
                        </div>
                        <div class="tit"><a target="_blank" href="${adv.linkUrl }">${adv.name}</a></div>
                    </li>
                  </c:forEach>
                </c:if>
                </ul>
            </div>
        </div>
	</cache:cache>
    </div>
    <!--秒杀区块 end-->
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
                            <a href="javascript:;">${pcs.name }</a>
                        </c:forEach>
                        <a href="${_ctxPath}/import.htm" target="_blank">进口商品</a>
                    </div>
                </div>
            </div>
        </div>
        <!--首页品牌导航 end-->
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
                <div class="sort-item" style="display: block;">
                    <!--全部品牌-->
                    <ul class="tab-item" style="display:block;">
                        <!-- 最受欢迎的品牌开始 -->
                        <c:forEach items="${mostPopularBrands}" var="mostPopular">
                        <li>
                            <div class="pic">
                                <span class="discount">
                                <c:forEach items="${mostPopular.products }" var="product" begin="0" end="0">
                                <span><var>${product.rebate }</var>折起</span>
                                <span><fmt:formatDate value="${mostPopular.startTime }" pattern="MM月dd日"/>-<fmt:formatDate value="${mostPopular.endTime }" pattern="MM月dd日"/></span>
                                </c:forEach>
                                </span>
                                <span class="brand-logo">
                                <a target="_blank" href="${_ctxPath }/brand-${mostPopular.brand.brandId}.htm">
                                <img src="<zx:thumbImage originalPath='${mostPopular.brand.logoImageUrl}' imageType='t1'></zx:thumbImage>" alt="${mostPopular.brand.name}">
                                </a>
                                </span>
                                <span class="brand-img">
                                <a target="_blank" href="${_ctxPath }/plan-${mostPopular.planId }.htm">
                                <img src="<zx:thumbImage originalPath='${mostPopular.imageUrl}' imageType='t13'></zx:thumbImage>" alt="${mostPopular.name }">
                                </a>
                                </span>
                            </div>
                            <div class="bm-info">
                                <i class="info-name">${mostPopular.name }</i>
                                <i class="info-time">剩余 <span class="countdown" data-config="{
                                'endTime':'<fmt:formatDate value="${mostPopular.endTime }" type="both"/>',
                                'nowTime':'${currentTime }',
                                'stopTips':'<span>活动结束谢谢参与！</span>'
                                }"></span></i>
                                <i class="info-story"><a target="_blank" href="${_ctxPath }/brand-${mostPopular.brand.brandId}.htm">品牌介绍</a></i>
                            </div>
                        </li>
                        </c:forEach>
                        <!-- 最受欢迎的品牌结束 -->
                    </ul>
                    <ul class="tab-item">
                        <!-- 最新上线开始 -->
                        <c:forEach items="${lastOnLineBrands}" var="lastOnLine">
                        <li>
                            <div class="pic">
                                <span class="discount">
	                                <c:forEach items="${lastOnLine.products }" var="product" begin="0" end="0">
		                                <span><var>${product.rebate }</var>折起</span>
		                                <span><fmt:formatDate value="${lastOnLine.startTime }" pattern="MM月dd日"/>-<fmt:formatDate value="${lastOnLine.endTime }" pattern="MM月dd日"/></span>
	                                </c:forEach>
                                </span>
                                <span class="brand-logo">
	                                <a target="_blank" href="${_ctxPath }/brand-${lastOnLine.brand.brandId}.htm">
	                                	<img src="<zx:thumbImage originalPath='${lastOnLine.brand.logoImageUrl}' imageType='t1'></zx:thumbImage>" alt="${lastOnLine.brand.name}">
	                                </a>
                                </span>
                                <span class="brand-img">
	                                <a target="_blank" href="${_ctxPath }/plan-${lastOnLine.planId }.htm">
	                                	<img src="<zx:thumbImage originalPath='${lastOnLine.imageUrl}' imageType='t13'></zx:thumbImage>" alt="${lastOnLine.name }">
	                                </a>
                                </span>
                            </div>
                            <div class="bm-info">
                                <i class="info-name">${lastOnLine.name }</i>
                                <i class="info-time">剩余 <span class="countdown" data-config="{
                                'endTime':'<fmt:formatDate value="${lastOnLine.endTime }" type="both"/>',
                                'nowTime':'${currentTime }',
                                'stopTips':'<span>活动结束谢谢参与！</span>'
                                }"></span></i>
                                <i class="info-story"><a target="_blank" href="${_ctxPath }/brand-${lastOnLine.brand.brandId}.htm">品牌介绍</a></i>
                            </div>
                        </li>
                        </c:forEach>
                        <!-- 最新上线结束 -->
                    </ul>
                    <ul class="tab-item">
                        <!-- 折扣最低排序开始 -->
                        <c:forEach items="${lowestDiscountBrands}" var="lowestDiscount">
                        <li>
                            <div class="pic">
                                <span class="discount">
	                                <c:forEach items="${lowestDiscount.products }" var="product" begin="0" end="0">
		                                <span><var>${product.rebate }</var>折起</span>
		                                <span><fmt:formatDate value="${lowestDiscount.startTime }" pattern="MM月dd日"/>-<fmt:formatDate value="${lowestDiscount.endTime }" pattern="MM月dd日"/></span>
	                                </c:forEach>
                                </span>
                                <span class="brand-logo">
	                                <a target="_blank" href="${_ctxPath }/brand-${lowestDiscount.brand.brandId}.htm">
	                                	<img src="<zx:thumbImage originalPath='${lowestDiscount.brand.logoImageUrl}' imageType='t1'></zx:thumbImage>" alt="${lowestDiscount.brand.name}">
	                                </a>
                                </span>
                                <span class="brand-img">
	                                <a target="_blank" href="${_ctxPath }/plan-${lowestDiscount.planId }.htm">
	                                	<img src="<zx:thumbImage originalPath='${lowestDiscount.imageUrl}' imageType='t13'></zx:thumbImage>" alt="${lowestDiscount.name }">
	                                </a>
                                </span>
                            </div>
                            <div class="bm-info">
                                <i class="info-name">${lowestDiscount.name }</i>
                                <i class="info-time">剩余 <span class="countdown" data-config="{
                                'endTime':'<fmt:formatDate value="${lowestDiscount.endTime }" type="both"/>',
                                'nowTime':'${currentTime }',
                                'stopTips':'<span>活动结束谢谢参与！</span>'
                                }"></span></i>
                                <i class="info-story"><a target="_blank" href="${_ctxPath }/brand-${lowestDiscount.brand.brandId}.htm">品牌介绍</a></i>
                            </div>
                        </li>
                        </c:forEach>
                        <!-- 折扣最低排序结束 -->
                    </ul>
                    <!--全部品牌 end-->
                </div>
               	<c:forEach items="${planCategorys}" var="planCategory">
	                <!--按照分类-->
	                <div class="sort-item">
	                	<%-- ${planCategory.key} --%>
	                	<c:forEach items="${planCategory.value}" var="planC" varStatus="s">
	                		<%-- ${planC.key}-${s.index} --%>
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
            	<c:if test="${fn:length(soonBrands) > 0}">
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
                            	<img src="<zx:thumbImage originalPath='${soon.brand.logoImageUrl}' imageType='t1'></zx:thumbImage>" alt="${soon.brand.name}">
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
                            <%--<i class="info-time">还有 <span class="countdown" data-config="{--%>
                            <%--'endTime':'<fmt:formatDate value="${soon.startTime }" type="both"/>',--%>
                            <%--'nowTime':'${currentTime }',--%>
                            <%--'stopTips':'<span>活动已经开始,请刷新页面！</span>'--%>
                          <%--}"></span> 上线</i>--%>
                            <%--<i class="info-story"><a target="_blank" href="${_ctxPath }/brand-${soon.brand.brandId}.htm">品牌介绍</a></i>--%>
                            <div class="data-frame"><input type="text" value="" data-default="请输入您的邮箱"  id="postSubSoonEmail${soon.planId}"><a href="javascript:postSubSoonEmail(${soon.brand.brandId},${soon.planId})">开售提醒</a></div>
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
        <cache:cache time="${_cacheSec}" refresh="${_needRefresh}" cron="0 10 * * *">
            <div class="sale-notice">
                <h2><span>每日<em>10:00</em>点新品上线</span>开售通知</h2>
                <div class="tab">
                    <ul>
                       <c:if test="${currentTimes < currentDayTen }">
                        <li class="cur">
                            <span class="today">今天</span>
                            <span>10:00</span>
                        </li>
                       </c:if>
                        <li>
                            <span>明天</span>
                            <span><fmt:formatDate value="${tomorrow }" pattern="MM/dd"/></span>
                        </li>
                        <li>
                            <span>后天</span>
                            <span><fmt:formatDate value="${afterTomorrow }" pattern="MM/dd"/></span>
                        </li>
                       <c:if test="${currentTimes > currentDayTen }">
                        <li>
                            <span><fmt:formatDate value="${threeDayFromToday }" pattern="EEEE"/></span>
                            <span><fmt:formatDate value="${threeDayFromToday }" pattern="MM/dd"/></span>
                        </li>
                       </c:if>
                    </ul>
                    <div class="content-wrap">
                   	   <c:if test="${currentTimes < currentDayTen }">
                        <div class="tab-item" style="display: block;">
                          <c:forEach items="${openSales['today'] }" var="openSales">
                            <p><a target="_blank" href="${_ctxPath }/brand-${openSales.brand.brandId}.htm">
                            	<img src="<zx:thumbImage originalPath='${openSales.brand.logoImageUrl}' imageType='t2'></zx:thumbImage>" alt="">${openSales.brand.name }
                            </a></p>
                          </c:forEach>
                        </div>
                       </c:if>
                        <div class="tab-item">
                          <c:forEach items="${openSales['tomorrow'] }" var="openSales">
                            <p><a target="_blank" href="${_ctxPath }/brand-${openSales.brand.brandId}.htm">
                            	<img src="<zx:thumbImage originalPath='${openSales.brand.logoImageUrl}' imageType='t2'></zx:thumbImage>" alt="">${openSales.brand.name }
                            </a></p>
                          </c:forEach>
                        </div>
                        <div class="tab-item">
                          <c:forEach items="${openSales['afterTomorrow'] }" var="openSales">
                            <p><a target="_blank" href="${_ctxPath }/brand-${openSales.brand.brandId}.htm">
                           	 	<img src="<zx:thumbImage originalPath='${openSales.brand.logoImageUrl}' imageType='t2'></zx:thumbImage>" alt="">${openSales.brand.name }
                            </a></p>
                          </c:forEach>
                        </div>
                       <c:if test="${currentTimes > currentDayTen }">
                        <div class="tab-item">
                          <c:forEach items="${openSales['threeDayFromToday'] }" var="openSales">
                            <p><a target="_blank" href="${_ctxPath }/brand-${openSales.brand.brandId}.htm">
                            	<img src="<zx:thumbImage originalPath='${openSales.brand.logoImageUrl}' imageType='t2'></zx:thumbImage>" alt="">${openSales.brand.name }
                            </a></p>
                          </c:forEach>
                        </div>
                       </c:if>
                    </div>
                </div>
            </div>
        </cache:cache> 
        <cache:cache time="${_cacheSec}" refresh="${_needRefresh}">
            <div class="brand-appointment">
                <h2>品牌预约</h2>
                <div class="main cf">
                    <ul>
                      <c:forEach items="${brands}" var="brand">
                        <li>
                            <a href="javascript:"><img src="<zx:thumbImage originalPath='${brand.logoImageUrl}' imageType='t2'></zx:thumbImage>" alt=""></a>
                            <div class="sale-wrap">
                                <div class="sale-pic">
                                	<a target="_blank" href="${_ctxPath }/brand-${brand.brandId}.htm">
                                	<img src="<zx:thumbImage originalPath='${brand.brandImageUrl}' imageType='t18'></zx:thumbImage>" alt="">
                               		</a>
                               	</div>
                                <div class="sale-info">
	                                <a target="_blank" href="${_ctxPath }/brand-${brand.brandId}.htm">
	                                	${brand.name }专场
	                               	</a>
                                </div>
                                <div class="sale-book cf">
                                    <div class="data-frame"><input id="${brand.brandId }_email" type="text" data-default="输入邮箱" value=""><a href="javascript:void(0);" onclick="brandOpenInform(${brand.brandId },this);" data-brand="${brand.brandId}">开售通知</a></div><span class="email-error">邮箱格式错误</span>
                                </div>
                            </div>
                        </li>
                       </c:forEach>
                    </ul>

                </div>
                <div class="post-us cf">
                    <p>没有你想要的品牌？马上告诉我！</p>
                    <div class="data-frame submit-brand"><input type="text" value="" data-default="输入您想购买的品牌" /><a href="javascript:" id="postBrandInfo">提 交</a></div>
                </div>
            </div>
            <div class="ad-block">
                <img src="${_imagesPath}/two_dim_code.jpg" alt="">
            </div>
            <div class="ad-block">
                <a href="javascript:joinMe('${user}')"  ><img src="${_imagesPath}/uploadfolder/index/7.jpg" alt=""></a>
                <input type="hidden" id="joinMe" />
            </div>
       	</cache:cache>
        </div>
        <!--通告+广告区块 end-->
    </div>
    <!--商品区块 end-->
    <!--返回顶部-->
    <div class="rt-fixed-wrap" style="display: none;">
        <div class="go-top"><a href="#" title="返回顶部">返回顶部</a></div>
    </div>
    <!--返回顶部 end-->
    <!--底部区块-->
   	<%@include file="/WEB-INF/pages/include/foot.jsp"%>
    <!--底部区块 end-->
    <script type="text/javascript" src="${_jsPath }/pages/index.js?d=${_resVerion}"></script>
    <script type="text/javascript">
    //    品牌预约
    var barndApp = $(".brand-appointment .main").find("li");
    var Timer,show,hide;
    var flag = false;
    barndApp.on({
        mouseenter: function(){
            var $this = $(this),
            $wrapBox = $this.find(".sale-wrap");
            window.clearTimeout(Timer);
            window.clearTimeout(hide);
            if(!flag){
                    Timer = setTimeout(function(){
                        $this.addClass("hover");
                        $wrapBox.stop().fadeIn();
                        flag = true;
                    },400);
            }else{
                show = setTimeout(function(){
                    $this.addClass("hover").find(".sale-wrap").show();
                    $this.siblings().removeClass("hover").find(".sale-wrap").hide();
                },200);
            }
        },
        mouseleave: function(){
            window.clearTimeout(show);
            window. clearTimeout(Timer);
            hide = setTimeout(function(){
                $(".brand-appointment").find("li.hover").removeClass("hover").find(".sale-wrap").fadeOut();
                flag =false;
            },200);
            $(this).find(".email-error").hide();
        }
    });
    
    //保存品牌预约
    function brandOpenInform(brandId,id){
    	//获取用户填写的邮箱
    	var vid = "#"+brandId+"_email";
    	var txt = $(vid).val();
    	if(txt == $(vid).attr("data-default")){
    		return;
    	}
    	//邮箱格式不正确
    	var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    	if(!reg.test(txt)){
    		$(id).parent().next(".email-error").show();
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
				'mailSubscribe.brandId':brandId
			},
			dataType:'json',
			success: function(data){
				popupDialog(data.info);
				$(vid).attr("value","");
		        },
	        error: function(data){
	        	//popupDialog("error:"+data.responseText);
	        }
    	});
    }

    //    想要的品牌提交
    var poBrInf = $("#postBrandInfo");
    poBrInf.click(function(){
    	//获取文本框内容  发送ajax请求
    	var txt = poBrInf.prev().val(); 
    	var reg = /^[\w\s\u4E00-\u9FA5]{1,30}$/;
    	if(!reg.test($.trim(txt))){
    		popupDialog("数据格式不正确!","1001");
    		return;
    	}
    	if($.trim(txt) != "" && $.trim(txt).length !=0 && $.trim(txt) != poBrInf.prev().attr("data-default")){
    		$.ajax({
    			type: 'POST',
				url: '${_ctxPath}/suggest-saveSuggest.htm',
				async: true,
				data: {
					'suggest.title':"用户想要的品牌-数据来自于网站首页",
					'suggest.content':txt,
					'suggest.type':<%=Suggest.TYPE_BRAND_SUGGEST%>
				},
				dataType:'json',
				success: function(data){
					popupDialog(data.info);
					poBrInf.prev().attr("value","");
                    poBrInf.removeClass("highlight");
			        },
		        error: function(data){
		        	popupDialog("error:"+data.responseText);
		        }
    		});
    	}
    });
    //邮件订阅
    var subEmail = $("#postSubscriptionEmail");
    subEmail.click(function(){
    	//获取用户的邮箱
    	var txt = subEmail.prev().val();
    	if(txt == subEmail.prev().attr("data-default")){
    		return;
    	}
    	//邮箱格式不正确
    	if(!validtorEmail(txt)){
    		return;
    	}
    	$.ajax({
    		type: 'POST',
			url: '${_ctxPath}/subscriptionEmail.htm',
			async: true,
			data: {
				'mailSubscribe.type':<%=MailSubscribe.TYPE_ALL%>,
				'mailSubscribe.status':<%=MailSubscribe.STATUS_SUBSCRIBE%>,
				'mailSubscribe.email':txt
			},
			dataType:'json',
			success: function(data){
				popupDialog(data.info);
				subEmail.prev().attr("value","");
		        },
	        error: function(data){
	        	popupDialog("error:"+data.responseText);
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
    	popupDialog("邮箱格式不正确!","1000");
    	return false;
    }
    
    function joinMe(user){
    	if(user){
    		window.location.href='${_ctxPath }/user/joinMe.htm';
    	}else{
    		$.ajax({
				type: 'POST',
				url: '${_ctxPath }/user/user-setFormalUrl.htm',
				data:{'urlBeforeLogin':'/user/joinMe.htm'},
				success: function(data){
			    		var popWinClick = $('#joinMe');
			        	popWinClick.addClass('login');
			        	popupLogin(popWinClick);
			        },
				dataType:'json'
				});
    	}
    }
    </script>
</body>
</html>