<!doctype html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Event"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>配置优惠券礼包</title>
    <link href="${_cssPath }/common.css" rel="stylesheet" />
    <link href="${_cssPath }/pages/arrangement.css" rel="stylesheet" />
    <script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${_jsPath }/pages/coupon/editCouponPackage.js"></script>
    <script type="text/javascript" src="${_jsPath }/pages/coupon/validator.js"></script>
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
      <li><a href="${_ctxPath}/admin/coupon/coupon-searchCoupons.htm">查看优惠券</a></li>
      </aut:authorize>
      <aut:authorize url="/admin/coupon/coupon-editCommonCoupon">
      <li><a href="${_ctxPath}/admin/coupon/coupon-editCommonCoupon.htm">配置优惠券</a></li>
      </aut:authorize>
      <aut:authorize url="/admin/coupon/coupon-editWechatCoupon">
      <li><a href="${_ctxPath}/admin/coupon/coupon-editWechatCoupon.htm">微信优惠券</a></li>
      </aut:authorize>
      <aut:authorize url="/admin/coupon/coupon-editCouponPackage">
      <li><a class="current-chose" href="${_ctxPath}/admin/coupon/coupon-editCouponPackage.htm">配置优惠券礼包</a></li>
      </aut:authorize>	
    </ul>
  </div>
<!--start 新增活动table-->
<div class="m-mt10p">
<form id="addForm">
	<input type="hidden" name="event.type" value="<%=Event.TYPE_PROMOTION%>"/>
	<input type="hidden" name="event.status" value="<%=Event.STATUS_NORMAL%>"/>
	<table id="addTable" class="tab-wechat" width="100%">
		<thead class="tab-control">
		<tr>
			<th width="15%">活动名称</th>
            <th width="30%">活动时间</th>
            <th width="10%">名额</th>
			<th width="10%">金额（元）</th>
			<th width="16%">最小消费金额（元）</th>
			<th width="10%">数量</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
            <tr>
                <td class="hb-td"><input type="text" class="j-edited input-nobor" name="event.eventName"/></td>
                <td class="hb-td">
                   <input value="" class="j-edited Wdate" type="text" name="event.couponConfigList[0].startTime" 
						id="startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'endTime\')}',readOnly:true})" /> 
					至
					<input value="" class="j-edited Wdate" type="text" name="event.couponConfigList[0].endTime" 
						id="endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startTime\')||\'%y-%M-%d\'}',readOnly:true})" />
                </td>
                <td class="hb-td"><input type="text" class="j-edited input-nobor" name="event.eventCount" onblur="checkEventCount(this)"/></td>
                <td colspan="4" class="data-td">
                    <table width="100%" height="100%" id="dataTable">
                        <tbody>
                            <tr class="list-tr">
                                <td width="82"><input type="text" class="j-edited input-nobor allowance" onblur="checkAllowance(this)" /></td>
                                <td width="141"><input type="text" class="j-edited input-nobor limitCharge" onblur="checkDouble(this)" /></td>
                                <td width="82"><input type="text" class="j-edited input-nobor couponCount" onblur="checkCouponCount(this)" /></td>
                                <td><a href="javascript:void(0);" class="new_tr">新增</a></td>
                            </tr>
                        </tbody>
                    </table>
                </td>
            </tr>
		</tbody>
	</table>
	<div class="m-mt10p">
		<input type="button" class="m-btn" value="生成" id="create">
		<input type="button" class="m-btn j-reset" value="取消">
	</div>
</form>
</div>
<!--end 新增活动table-->
<!--start 已添加活动table-->
<div class="order-management">
	<c:set var="abnormalDel" value="<%=Event.STATUS_ABNORMAL_DELETE %>"></c:set>
	<c:set var="notStarted" value="<%=Event.ACTIVITYSTATUS_NOT_START %>"></c:set>
	<c:set var="inProgress" value="<%=Event.ACTIVITYSTATUS_IN_PROGRESS %>"></c:set>
	<c:set var="expired" value="<%=Event.ACTIVITYSTATUS_EXPIRED %>"></c:set>
	<c:forEach items="${events }" var="event">
	<form>
		<input type="hidden" name="event.eventId" value="${event.eventId }" class="eventId"/>
		<input type="hidden" name="event.status" value="${event.status }"/>
		<table class="tab-bor" width="100%">
			<thead class="tab-control">
			<tr>
				<th width="13%">活动名称</th>
				<th width="18%">活动时间</th>
				<th width="10%">名额</th>
				<th width="10%">金额（元）</th>
				<th width="14%">最小消费金额（元）</th>
				<th width="8%">数量</th>
				<th width="10%">激活码</th>
				<th width="9%">状态</th>
				<th width="13%">操作</th>
			</tr>
			</thead>
			<tbody>
				<c:set var="size" value="${fn:length(event.couponConfigList) }"></c:set>
				<c:forEach items="${event.couponConfigList }" var="couponConfig" varStatus="status">
				<tr class="list-tr">
					<c:if test="${status.index == 0 }">
					<td rowspan="${size }">
						<span class="j-normal">${event.eventName }</span>
						<input type="text" class='j-edited input-nobor m-hide' name="event.eventName" value="${event.eventName }"/>
					</td>
					<td rowspan="${size }">
						<label class="j-normal"><fmt:formatDate value="${couponConfig.startTime }" pattern="yyyy-MM-dd"/></label> 
						<input type="text" class='j-edited Wdate m-hide' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'%y-%M-%d'})" 
							name="event.couponConfigList[${status.index }].startTime" value="<fmt:formatDate value='${couponConfig.startTime }' pattern='yyyy-MM-dd'/>"/>
						至
						<label class="j-normal"><fmt:formatDate value="${couponConfig.endTime }" pattern="yyyy-MM-dd"/></label>
						<input type="text" class='j-edited Wdate m-hide' onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,minDate:'%y-%M-%d'})" 
							name="event.couponConfigList[${status.index }].endTime" value="<fmt:formatDate value='${couponConfig.endTime }' pattern='yyyy-MM-dd'/>"/>
					</td>
					<td rowspan="${size }">
						<p>可激活${event.eventCount }次</p>
						<p>已激活${event.activeNum }次</p>
					</td>
					</c:if>
					<td>
						<span class="j-normal">${couponConfig.allowance }</span>
						<input type="text" class='j-edited input-nobor m-hide allowance' name="event.couponConfigList[${status.index }].allowance" value="${couponConfig.allowance }"/>
						<input type="hidden" name="event.couponConfigList[${status.index }].couponConfigId" value="${couponConfig.couponConfigId }"/>
					</td>
					<td>
						<span class="j-normal">${couponConfig.limitCharge }</span>
						<input type="text" class='j-edited input-nobor m-hide limitCharge' name="event.couponConfigList[${status.index }].limitCharge" value="${couponConfig.limitCharge }"/>
					</td>
					<td>
						<span class="j-normal">${couponConfig.couponCount }</span>
						<input type="text" class='j-edited input-nobor m-hide' name="event.couponConfigList[${status.index }].couponCount" value="${couponConfig.couponCount }"/>
					</td>
					<c:if test="${status.index == 0 }">
					<td rowspan="${size }">
						<span class="j-normal">${event.activeCode }</span>
					</td>
					<td rowspan="${size }">
						<spring:message code="event.activityStatus.${event.activityStatus}"/>
					</td>
					<td rowspan="${size }">
						<div class="j-normal">
						<c:if test="${event.activityStatus != expired }">
<%-- 							<c:if test="${event.status != abnormalDel }"> --%>
<%-- 							<aut:authorize url="/admin/coupon/coupon-edit"> --%>
<!-- 							<a href="javascript:void(0);" class="j-edit">编辑</a> -->
<%-- 							</aut:authorize> --%>
<%-- 							</c:if> --%>
							<aut:authorize url="/admin/coupon/coupon-delete">
							<c:if test="${event.activityStatus == inProgress }">
							<a href="javascript:void(0);" class="j-abnormalDel">删除</a>
							</c:if>
							</aut:authorize>
						</c:if>
						<aut:authorize url="/admin/coupon/coupon-delete">
							<c:if test="${event.activityStatus != inProgress }">
							<a href="javascript:void(0);" class="j-delete">删除</a>
							</c:if>
						</aut:authorize>
						</div>
						<div class="j-edited m-hide">
							<a href="javascript:void(0);" class="j-done">完成</a>
							<a href="javascript:void(0);" class="j-cencal">取消</a>
						</div>
					</td>
					</c:if>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
	</c:forEach>
</div>
<!--end 已添加活动table-->
</div>
<!--end body-->
<!--start footer-->
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<!--end footer-->
</body>
</html>