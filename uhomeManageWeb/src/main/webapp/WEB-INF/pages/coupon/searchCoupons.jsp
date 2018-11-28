<!doctype html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Event"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html>
<head>
    <meta charset="utf-8"/>
    <title></title>
    <link href="${_cssPath }/common.css" rel="stylesheet" />
    <link href="${_cssPath }/pages/arrangement.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
     <script type="text/javascript" src="${_jsPath }/pages/coupon/searchCoupons.js"></script>
</head>
<body>
<!--start header-->
<jsp:include page="../include/header.jsp"></jsp:include> 
<!--end header-->
<!--start body-->
<div class="body m-w980p">
	<div class="body-nav m-mt10p">
    <ul class="m-clear">
      <aut:authorize url="/admin/coupon/coupon-search">
      <li><a class="current-chose" href="${_ctxPath}/admin/coupon/coupon-searchCoupons.htm">查看优惠券</a></li>
  	  </aut:authorize>
  	  <aut:authorize url="/admin/coupon/coupon-editCommonCoupon">
      <li><a href="${_ctxPath}/admin/coupon/coupon-editCommonCoupon.htm">配置优惠券</a></li>
      </aut:authorize>
      <aut:authorize url="/admin/coupon/coupon-editWechatCoupon">
      <li><a href="${_ctxPath}/admin/coupon/coupon-editWechatCoupon.htm">微信优惠券</a></li>
      </aut:authorize>
      <aut:authorize url="/admin/coupon/coupon-editCouponPackage">
      <li><a href="${_ctxPath}/admin/coupon/coupon-editCouponPackage.htm">配置优惠券礼包</a></li>
      </aut:authorize>
    </ul>
  </div>
<!--start form-->
<form class="sub-form m-mt10p m-clear" action="${_ctxPath }/admin/coupon/coupon-searchCoupons.htm" method="post">
    <aut:authorize url="/admin/coupon/coupon-search">
    <div class="m-fl">
            <label>开始日期：</label>
            <input class="Wdate" type="text" name="couponPage.params.startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" value="${couponPage.params.startTime }">
             <select name="couponPage.params.status" class="m-sel" id="status">
                  <option value="">全部状态</option>
                  	<c:forEach items="${statuses }" var="obj" varStatus="status">
					<option value="${obj }"
					<c:if test="${not empty couponPage.params.status and couponPage.params.status eq obj }">
						selected = "selected"
					</c:if>
					><spring:message code="coupon.status.${status.index}"/></option>
				</c:forEach>
            </select>
            <select name="couponPage.params.allowance" class="m-sel">
            	<option value="">全部金额</option>
            	<c:forEach items="${allowanceList }" var="allowance">
            	<option value="${allowance }"
            	<c:if test="${not empty couponPage.params.allowance and couponPage.params.allowance eq allowance }">
						selected = "selected"
					</c:if>
            	>${allowance }</option>
            	</c:forEach>
            </select>
            <select name="couponPage.params.isActive" class="m-sel" id="isActive">
            	<option value="">激活状态</option>
            	<c:forEach items="${activeStatuses }" var="obj" varStatus="status">
            	<option value="${obj }"
            	<c:if test="${not empty couponPage.params.isActive and couponPage.params.isActive eq obj }">
						selected = "selected"
					</c:if>
            	><spring:message code="coupon.isActive.${status.index}"/></option>
            	</c:forEach>
            </select>
            <input type="text" data-default="输入用户名称、活动名称、优惠券号" class="J-Keywords txt-input input-marks" name="couponPage.params.keyWords" value="${couponPage.params.keyWords }" id="txtKeywords"/><input type="button" class="m-btn m-btn-search" value="查 询" id="search"/>
    </div>
    <div class="m-fr curr-num">
        <c:if test="${not empty couponPage.result}">
        <label >当前显示：</label>
        <wms:commPageSize page="${couponPage}" beanName="couponPage"></wms:commPageSize>
        </c:if>
    </div>
    </aut:authorize>
<!--     <div class="pagination pagination-right"> -->
<%--        <c:if test="${not empty couponPage.result}"> --%>
<%--        <wms:commPage page="${couponPage}" beanName="couponPage"></wms:commPage> --%>
<%--        </c:if> --%>
<!--     </div> -->
</form>
<!--end form-->
<!--start 下单管理table-->
<div>
<table id="tab" class="tab-bor tab-control" width="100%">
	<thead>
	<tr>
		<th width="23%">用户名</th>
		<th width="11%">优惠券号</th>
		<th width="9%">金额（元）</th>
		<th width="15%">最小消费金额（元）</th>
		<th width="12%">活动名称</th>
		<th width="16%">有效期</th>
		<th width="8%">激活状态</th>
		<th width="6%">状态</th>
	</tr>
	</thead>
	<tbody>
		<c:forEach items="${couponPage.result }" var="coupon">
		<tr class="list-tr">
			<td>${coupon.userCoupon.username }</td>
			<td>${coupon.couponNo }</td>
			<td>${coupon.couponConfig.allowance }</td>
			<td>${coupon.couponConfig.limitCharge }</td>
			<td>${coupon.event.eventName }</td>
			<td><span><fmt:formatDate value="${coupon.couponConfig.startTime }" pattern="yyyy-MM-dd"/></span>至<span><fmt:formatDate value="${coupon.couponConfig.endTime }" pattern="yyyy-MM-dd"/></span></td>
			<td><spring:message code="coupon.isActive.${coupon.isActive}"/></td>
			<td><spring:message code="coupon.status.${coupon.status}"/></td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<div class="pagination pagination-right">
	 <c:if test="${not empty couponPage.result}">
     <wms:commPage page="${couponPage}" beanName="couponPage"></wms:commPage>
     </c:if>
</div>
</div>
<!--end 下单管理table-->
</div>
<!--end body-->
<!--start footer-->
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<!--end footer-->
<script type="text/javascript">
	var used = <%=com.ytoxl.module.uhome.uhomebase.dataobject.Coupon.STATUS_USED_YES%>;
	var deleted = <%=com.ytoxl.module.uhome.uhomebase.dataobject.Coupon.STATUS_DELETE%>;
	var active = <%=com.ytoxl.module.uhome.uhomebase.dataobject.Coupon.ACTIVE_STATUS_ON%>;
	function changeFun(){
		var value = $("#status").val();
		if(value == used || value == deleted){
			$("#isActive").find("option[value!="+active+"]").hide();
			$("#isActive").find("option[value="+active+"]").attr("selected", "selected");
		}else{
			$("#isActive").find("option[value!="+active+"]").show();
		}
	}
	changeFun();
	$("#status").on("change", function(){
		changeFun();
	});
</script>
</body>
</html>