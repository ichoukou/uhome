<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.Notice"%>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%> 
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>公告栏</title>
    <link href="${_cssPath}/common.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
    <c:set var="TYPE_REBATE" value="<%=Notice.TYPE_REBATE %>" ></c:set>
    <c:set var="TYPE_NETWORK" value="<%=Notice.TYPE_NETWORK %>"></c:set>
    <c:set var="CHECKED_NO" value="<%=Notice.CHECKED_NO %>"></c:set>
    <c:set var="CHECKED_YES" value="<%=Notice.CHECKED_YES %>"></c:set>
    <c:set var="TOP_NO" value="<%=Notice.TOP_NO %>"></c:set>
    <c:set var="TOP_YES" value="<%=Notice.TOP_YES %>"></c:set>
</head>
<body>
<!--start header-->
<jsp:include page="../include/header.jsp" flush="true" />
<!--end header-->
<!--start body-->
<div class="body m-w980p">
	<jsp:include page="../include/pageManageMenu.jsp"  flush="true" />
  	<form class="m-clear m-mt10p" id="searchForm"  method="post">
	    <div class="m-fl">
	    	<span>标题：</span>
	    	<input type="text" class="txt-input" name="noticePage.params.title" value="${noticePage.params.title}"/>
	    	<span>类型：</span>
	    	<select class="m-sel" name="noticePage.params.type">
	    		<option value="">请选择</option>
	    		<option value="${TYPE_REBATE }" <c:if test="${noticePage.params.type eq TYPE_REBATE }">selected = "selected"</c:if>><spring:message code="notice.type.${TYPE_REBATE }"/></option>
	    		<option value="${TYPE_NETWORK }" <c:if test="${noticePage.params.type eq TYPE_NETWORK }">selected = "selected"</c:if>><spring:message code="notice.type.${TYPE_NETWORK }"/></option>
	    	</select>
	    	<span>状态：</span>
	    	<select class="m-sel" name="noticePage.params.status">
	    		<option value="">请选择</option>
	    		<option value="${CHECKED_NO }" <c:if test="${not empty noticePage.params.status &&(noticePage.params.status eq CHECKED_NO )}">selected = "selected"</c:if>><spring:message code="notice.status.${CHECKED_NO }"/></option>
	    		<option value="${CHECKED_YES }" <c:if test="${noticePage.params.status eq CHECKED_YES }">selected = "selected"</c:if>><spring:message code="notice.status.${CHECKED_YES }"/></option>
	    	</select>
	    	<input type="button" class="m-btn" value="查询" id="search"/>
	    	<aut:authorize url="/admin/notice/notice-addNotice">
	    		<input type="button" class="m-btn J-editAd" value="添加公告" />
	    	</aut:authorize>
	    </div>
	    <!--右侧显示分页控件 start-->
	    <c:if test="${not empty noticePage.result}">
			<div class="m-fr curr-num">
				<label>当前显示： </label>
				<wms:commPageSize page="${noticePage}" beanName="noticePage"></wms:commPageSize>
			</div>
		</c:if>
    	<!--右侧显示分页控件 end-->
	</form>
	<!--100%表格样式-->
	<c:if test="${not empty noticePage}">
		<div class="m-mt10p">
			<table id="tab" class="tab-control">
		      <thead>
		        <tr>
		          <th width="7%"><input type="checkbox" name="checkAll" /> ID</th>
		          <th width="30%">标题</th>
		          <th width="15%">公告类型</th>
		          <th width="18%">发布时间</th>
		          <th width="10%">状态</th>
		          <th width="20%">操作</th>
		        </tr>
		      </thead>
		      <tbody>
		      	<c:forEach items="${noticePage.result }" var="notice" varStatus="status">
					<tr class="list-tr">
						<td><input type="checkbox" name="checkSub" value="${notice.noticeId }"/> ${notice.noticeId }</td>
						<td><a href="#" class="c-blue" title="${fn:escapeXml(notice.title) }" >${notice.title }</a></td>
						<td><spring:message code="notice.type.${notice.type }"/></td>
						<td><fmt:formatDate value='${notice.publishTime}' pattern='yyyy-MM-dd HH:mm:ss'/></td>
						<td><span class="c-green"><spring:message code="notice.status.${notice.status }"/></span></td>
						<td>
		      			<a class="c-blue" target="_blank" href="${_ctxPath}/admin/notice/notice-viewNotice.htm?notice.noticeId=${notice.noticeId}" >查询</a>
		      			<aut:authorize url="/admin/notice/notice-updateNotice">
		      				<a class="c-blue" href="${_ctxPath}/admin/notice/notice-updateNotice.htm?notice.noticeId=${notice.noticeId}">修改</a>
		      			</aut:authorize>
		      			<aut:authorize url="/admin/notice/notice-deleteNotice">
		      				<a class="c-blue" href="javascript:;" onclick="deleteNotice('${notice.noticeId}')">删除</a>
		      			</aut:authorize>
		      			<c:if test="${CHECKED_YES eq notice.status }">
			      			<aut:authorize url="/admin/notice/notice-updateNoticeTop">
				      			<a class="c-blue" href="javascript:;" onclick="updateNoticeTop('${notice.noticeId}','${TOP_YES}')">置顶</a>
				      			<c:if test="${TOP_YES eq notice.isTop }">
				      				<a class="c-blue" href="javascript:;" onclick="updateNoticeTop('${notice.noticeId}','${TOP_NO}')">取消置顶</a>
				      			</c:if>
			      			</aut:authorize>
		      			</c:if>
		      		</td>
					</tr>
				</c:forEach>
		      </tbody>
    		</table>
	    	<div class="m-mt10p">
	    		<aut:authorize url="/admin/notice/notice-checkNotice">
		    		<input type="button" class="m-btn" value="审核通过" id="checkNotice"/>
		    	</aut:authorize>
		    	<aut:authorize url="/admin/notice/notice-deleteNotice">
		    		<input type="button" class="m-btn" value="删除" id="batchDel"/>
		    	</aut:authorize>
		    </div>
		</div>
	</c:if>
	<!--分页插件 start -->
	<div class="pagination pagination-right">
		<c:if test="${not empty noticePage.result}">
			<wms:commPage page="${noticePage}" beanName="noticePage"></wms:commPage>
		</c:if>
	</div>
	<!--分页插件 end -->
</div>
<!--start footer-->
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<!--end footer-->
</body>
<!--end body-->
<script type="text/javascript">
$(function(){
	//查询按钮绑定事件
	$('#search').bind('click', function() {
		$("#searchForm").attr("action","${_ctxPath}/admin/notice/notice-searchNotice.htm");
		$("#searchForm").submit();
	});
	//复选框事件
	$('.tab-control').Merlin({
			// 全选
	    "checkAll": {
	      "eType":'on', // 事件类型
        "checkAll":'input[name="checkAll"]', // 全选按钮
        "checkSub":'input[name="checkSub"]' // 子选择按钮
	    }
    });
	//“新增公告”按钮绑定事件
	$(".J-editAd").bind('click', function() {
		window.location.href="${_ctxPath}/admin/notice/notice-addNotice.htm";
	});
	//“审核通过”按钮绑定事件
	$('#checkNotice').bind('click', function() {
		var noticeIds = allCheckedVal();
		if(!noticeIds){
			alert("请选中要“审核”的记录！");
			return false;
		}
		if(confirm("确认“审核通过”选中的记录？")){
			$.ajax({
				type : 'POST',
				url : '${_ctxPath}/admin/notice/notice-checkNotice.htm',
				data : {"noticeIds" : noticeIds},
				success : function(data) {
					if(data){
						alert(data.info);
					}
					window.location.reload();
					}
			});
		}
	});
	//“批量删除”按钮绑定事件
	$('#batchDel').bind('click', function() {
		var noticeIds = allCheckedVal();
		if(!noticeIds){
			alert("请选中要“删除”的记录！");
			return false;
		}
		if(confirm("确认删除选中的记录？")){
			$.ajax({
				type : 'POST',
				url : '${_ctxPath}/admin/notice/notice-deleteNotice.htm',
				data : {"noticeIds" : noticeIds},
				success : function(data) {
					if(data){
						alert(data.info);
					}
					window.location.reload();
					}
			});
		}
	});
});
//获取所有选中复选框的值
function allCheckedVal(){
	var str="";
	$("input:checkbox[name='checkSub']:checked").each(function(){
		str += $(this).val()+",";
	});
	return str.substring(0,str.length-1);
}
//删除单条公告
function deleteNotice(noticeId){
	if(confirm("确认删除该条记录？")){
		$.ajax({
			type : 'POST',
			url : '${_ctxPath}/admin/notice/notice-deleteNotice.htm',
			data : {"noticeIds" : noticeId},
			success : function(data) {
				if(data){
					alert(data.info);
				}
				window.location.reload();
				}
		});
	}
}
//更新公告置顶状态
function updateNoticeTop(noticeId,isTop){
	var noTop = '${TOP_NO}'; //取消置顶
	var yesTop = '${TOP_YES}'; //置顶
	var msg="确认“置顶”该条记录？";
	if(noTop == isTop){
		msg = "确认该条记录“取消置顶”？";
	}
	if(confirm(msg)){
		$.ajax({
			type : 'POST',
			url : '${_ctxPath}/admin/notice/notice-updateNoticeTop.htm',
			data : {"notice.noticeId" : noticeId,"notice.isTop":isTop},
			success : function(data) {
				if(data){
					alert(data.info);
				}
				window.location.reload();
				}
		});
	}
}
</script>
</html>