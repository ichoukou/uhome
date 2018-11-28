<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
	<!-- 品牌特卖页面  最新上线品牌  最低折扣品牌 公用 -->   
 	<c:forEach items="${specialSellers }" var="special">
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
             <i class="info-story"><a href="${_ctxPath }/brand-${special.brand.brandId }.htm">品牌介绍</a></i>
         </div>
     </li>
    </c:forEach>
