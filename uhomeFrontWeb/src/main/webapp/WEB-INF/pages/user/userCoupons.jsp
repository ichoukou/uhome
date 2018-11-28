<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>我的优惠券</title>
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/coupon.css?d=${_resVerion}"/>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
</head>
<body>
<jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include>
	<div class="m-w960p cf">
		<!--面包屑-->
        <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a><a href="${_domain }/user/orders.htm">个人中心 ></a>我的优惠券</div>
        <!--面包屑 end-->
        <jsp:include page="/WEB-INF/pages/include/left.jsp"></jsp:include>
	<div class="inf-detail m-mt20p" style="padding-bottom:60px;">
    	<div class="inf-title fon-big">我的优惠券</div>
    	<div class="coupon-detail-wp">
        <div class="user-t">激活微信优惠券</div>
        <div class="user-bg">
        	<i></i>
            <div class="form-text cf">
                <input id="couponNoShow" onfocus="javascript:$('#infoShow').html('')" type="text" class="txt">
                <a id="activateCouponId" href="javascript:activateCoupon();" class="btn-a"><span>激&nbsp;&nbsp;&nbsp;&nbsp;活</span></a>
            </div>
            <div class="btn-wp cf">
            	<!--优惠券验证信息--><div id="infoShow" class="coupon-tips"></div><!--优惠券验证信息-->
            </div>
        </div>
        <div class="coupon-sort-wp">
            <div class="panel">
                <ul>
                    <li id="sli-1" onclick="searchUserCoupon(-1);">全部优惠劵</li>
                    <li id="sli0" onclick="searchUserCoupon(0);">未使用</li>
                    <li id="sli1" onclick="searchUserCoupon(1);">已使用</li>
                    <li id="sli2" onclick="searchUserCoupon(2);">已过期</li>
                </ul>
            </div>
            <div class="list-wp">
              
                <div class="list" style="display: block;">
                    <table width="100%">
                        <thead>
                        <tr>
                            <td width="12%">优惠劵号</td>
                            <td width="10%">金额（元）</td>
                            <td width="15%">使用范围</td>
                            <td width="15%">活动</td>
                            <td width="20%">有效期</td>
                            <td width="20%">单次付款最低金额（元）</td>
                            <td>状态</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${userCouponPage.result }" var="uc">
	                        <tr>
	                        <td>${uc.couponNo }</td>
	                        <td align="right"><span class="coin">&yen;</span><fmt:formatNumber value="${uc.couponConfig.allowance }" pattern="0.00"/></td>
	                        <td>${uc.couponUseScope}</td>
	                        <td>${uc.event.eventName }</td>
	                        <td><fmt:formatDate value="${uc.couponConfig.startTime }" pattern="yyyy-MM-dd"/>
	                        至<fmt:formatDate value="${uc.couponConfig.endTime }" pattern="yyyy-MM-dd"/></td>
	                        <td align="right"><span class="coin">&yen;</span><fmt:formatNumber value="${uc.couponConfig.limitCharge }" pattern="0.00"/></td>
	                        <td><spring:message code="userCoupon.userCouponStatusShow.${uc.userCouponStatusShow }" text="" /></td>
	                        </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        <!--分页导航-->
        <div id="lastFy">
                         <c:if test="${not empty userCouponPage.result}">
		     				<wms:commPage page="${userCouponPage}" beanName="userCouponPage"></wms:commPage>
		     			</c:if>
                 </div>
        <!--分页导航 end-->
	    </div>
	</div>
<div class="coupon-rule-wp m-hidden">
    <h5>优惠券使用规则</h5>
    <div class="rule-detail">
        <ul>
            <li>1.优惠券有效期：30天（从发放日起）</li>
            <li>2. 一张总订单仅限使用一张优惠券</li>
            <li>3.如果取消订单或退货，优惠券金额将不退回。</li>
        </ul>
    </div>
    <div class="weixin-wp cf">
        <div class="weixin">
            <img src="${_ctxPath}/web-resource/images/QR_Code.jpg" alt="">
        </div>
        <div class="w-list">
            <div class="tit">扫描二维码并且关注微信官方账户，即送价值200元优惠券礼包</div>
            <div class="bd">
                <table width="512" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                        <th scope="row">50元优惠劵</th>
                        <td>1张</td>
                        <td>500元以上订单即可使用</td>
                    </tr>
                    <tr>
                        <th scope="row">20元优惠劵</th>
                        <td>3张</td>
                        <td>200元以上订单即可使用</td>
                    </tr>
                    <tr>
                        <th scope="row">10元优惠劵</th>
                        <td>5张</td>
                        <td>100元以上订单即可使用</td>
                    </tr>
                    <tr>
                        <th scope="row">5元优惠劵</th>
                        <td>8张</td>
                        <td>50元以上订单即可使用</td>
                    </tr>
                </table>
            </div>
        </div>
    </div>
</div>
</div>
<form id="searchForm" action="${_ctxPath}/user/userCoupons.htm" method="post">
	 <input type="hidden" id="couponStatus" name="couponStatus" value="${couponStatus}"/>
	 <input type="hidden" id="couponNo" name="couponNo" value="${couponNo }"/>
</form>
<input type="hidden" id="activeStatus0" value='<spring:message code="userCoupon.ACTIVE_EM.0" text="" />'/>
<input type="hidden" id="activeStatus1" value='<spring:message code="userCoupon.ACTIVE_EM.1" text="" />'/>
<input type="hidden" id="activeStatus2" value='<spring:message code="userCoupon.ACTIVE_EM.2" text="" />'/>
<input type="hidden" id="activeStatus3" value='<spring:message code="userCoupon.ACTIVE_EM.3" text="" />'/>
<input type="hidden" id="activeStatus4" value='<spring:message code="userCoupon.ACTIVE_EM.4" text="" />'/>
<input type="hidden" id="activeStatus5" value='<spring:message code="userCoupon.ACTIVE_EM.5" text="" />'/>
<input type="hidden" id="activeStatus6" value='激活码已被激活，不能重复激活'/>
<input type="hidden" id="activeStatus99" value='激活名额已满'/>
 <%@include file="/WEB-INF/pages/include/foot.jsp"%>
<script type="text/javascript">
/**
 * 按状态查询优惠券信息
 */
function searchUserCoupon(para){
	$("#couponStatus").val(para);
	$("#searchForm").submit();
}
/**
 * 激活优惠券
 */
 function activateCoupon(){
	var couponNoShow = $("#couponNoShow").val();
	if(couponNoShow==""){
		return;
	}
	$("#activateCouponId").attr("href","#");
	//var patter = /^[0-9]{0,12}$/;
	//if(!patter.test(couponNoShow)){
	//	$("#infoShow").html($("#activeStatus4").val());
	//	return;
	//}
	$.ajax({
		type: 'POST',
		url: '${_ctxPath}/user/activateCoupon.htm',
		async: true,
		data: {
			'couponNo':couponNoShow
		},
		dataType:'json',
		success: function(data){
			var info = data.info;
			if(info.indexOf("100")>-1){
				var infoArr = info.split("-");
				$("#infoShow").html("活动已结束，活动时间："+infoArr[1]+" 到 "+infoArr[2]);
			}else{
				$("#infoShow").html($("#activeStatus"+info).val());
			}
			if(info=="3"){
				setTimeout("searchUserCoupon(0)", 1000);
			}else{
				$("#activateCouponId").attr("href","javascript:activateCoupon();");
			}
	    }
	});
}
$(function(){
	var couponStatus = "${userCouponPage.params.couponStatus}";
	switch(couponStatus){
	case "0":
		$("#sli0").attr("class","cur");break;
	case "1":
		$("#sli1").attr("class","cur");break;
	case "2":
		$("#sli2").attr("class","cur");break;
	default :
		$("#sli-1").attr("class","cur");
	}
})
</script>
</body>
</html>