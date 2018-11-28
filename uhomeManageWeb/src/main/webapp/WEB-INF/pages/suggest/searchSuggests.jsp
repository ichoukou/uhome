<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>站内信</title>
<link href="${ _cssPath}/common.css" rel="stylesheet" />
<script src="${_jsPath }/jquery/jquery.1.8.1.js" language="javascript"></script>
<script type="text/javascript">
var deleteSuggest=function(id){
	if(confirm("你确定要删除吗?")){
// 		$.ajax({   
// 			   url:"${_ctxPath}/suggest/suggest-deleteSuggest.htm",   
// 			   type:"POST",
// 			   data:{'suggestId':id},
// 			   dataType:"json",   
// 			   success:function(data){   
// 					alert(data.info);
// 			   },
// 			   error:function(data){
// 				   console.log(data);
// 			   }
// 		});
		window.location.href="${_ctxPath}/admin/suggest/suggest-deleteSuggest.htm?suggestId="+id
	}
};
$(function(){
	//新邮件阅读
	$(".no-read").click(function(){
		var id=$(this).attr('id');
		$.ajax({
			url : "${_ctxPath}/admin/suggest/suggest-updateSuggestStatus.htm",
			type : "POST",
			data : {
				'suggestId' : id
			},
			dataType : "json",
			success : function(data) {
// 				alert(data.info)
			},
			error : function(data) {
				console.log(data);
			}
		});
		$(this).removeClass("no-read");
	});
});
</script>
</head>
<body>
<jsp:include page="../include/header.jsp"  flush="true" />
	<div class="body m-w980p">
		<!--start nav-->
		<jsp:include page="../include/pageManageMenu.jsp"  flush="true" />
		<!--end nav-->
		<div class="m-mt10p m-clear">
			<div class="body-nav subnav m-fl">
		        <ul>
		            <li ><a <c:if test="${suggestPage.params.status==null }">class="current-chose"</c:if> href="${_ctxPath}/admin/suggest/suggest-searchSuggests.htm">全部</a> </li>
		            <li><a  <c:if test="${suggestPage.params.status==2 }">class="current-chose"</c:if>
				href="${_ctxPath}/admin/suggest/suggest-searchSuggests.htm?suggestPage.params.status=2">已读</a></li>
		            <li><a <c:if test="${suggestPage.params.status==1 }">class="current-chose"</c:if>
				href="${_ctxPath}/admin/suggest/suggest-searchSuggests.htm?suggestPage.params.status=1">未读</a></li>
		        </ul>
		    </div>
			<c:if test="${not empty suggestPage.result}">
			    <div class="m-fr curr-num">
					<label>当前显示： </label> 
	               	<wms:commPageSize page="${suggestPage}" beanName="suggestPage"></wms:commPageSize>
				</div>
            </c:if>
		</div>
			<table id="tab" width="100%">
				<thead class="tab-control">
					<tr>
						<th width="20%">用户名</th>
						<th width="40%">标题</th>
						<th width="20%">时间</th>
						<th width="20%">操作</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${suggestPage.result}" var="suggest"
						varStatus="status">
						<tr>
							<td colspan="4" class="num-icon">
								<table class="tab-control">
								
									<tr id="${suggest.suggestId }" class="tr-height list-tr<c:if test="${suggest.status==1 }"> no-read</c:if>">
										<td class="num-icon" width="20%"><i></i><label><a>${suggest.user.username}</a>
										</label></td>
										<td width="40%">${suggest.title}</td>
										<td width="20%"><fmt:formatDate
												value="${suggest.createTime}" pattern="yyyy-MM-dd HH:mm:ss"></fmt:formatDate>
										</td>
										<td width="20%">
											<aut:authorize url="/admin/suggest/suggest-deleteSuggest">
											<a class="c-blue" href="javascript:void(0)" onclick="deleteSuggest(${suggest.suggestId})">删除 </a>
											</aut:authorize>
										</td>
									</tr>
									<tr class="show-tr">
										<td colspan="4" class="show-tab">
											<p class="order-info">内容：${suggest.content}</p></td>
									</tr>
								</table>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		<!--end 站内信table-->
		 <div class="pagination pagination-right">
			<c:if test="${not empty suggestPage.result}">
				<wms:commPage page="${suggestPage}" beanName="suggestPage"></wms:commPage>
			</c:if>
		</div>
	</div>
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</body>
</html>