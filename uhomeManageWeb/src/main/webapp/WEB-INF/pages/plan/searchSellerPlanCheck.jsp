<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="${_cssPath }/common.css" rel="stylesheet" />
<script src="${_jsPath }/jquery/jquery.1.8.1.js" language="javascript"></script>
<title>商家结算管理</title>
<script type="text/javascript">
	function paymentConfirm(value){
		$.ajax({
			type:'post',
			url:_ctxPath+'/seller/planCheck/plancheck-editSellerPlanCheck.htm',
			data:{'planCheckId':value},
			success:function(){
				window.location.reload();
			}
		});
	}
	
	function applyCheck(value){
		$.ajax({
			type:'post',
			url:_ctxPath+'/seller/planCheck/plancheck-saveSellerPlanCheck.htm',
			data:{'planId':value},
			success:function(){
				window.location.reload();
			}
		});
	}
</script>
</head>
<body>
	<jsp:include page="../include/header.jsp"></jsp:include> 
    <div class="body m-w980p">
        <div class="m-mt10p">
            <table class="tab-control">
                <thead>
                    <tr>
                        <th width="33%">特卖名称</th>
                        <th width="33%">特卖时间</th>
                        <th width="33%">操作</th>
                    </tr>
                </thead>
                <c:if test="${planCheckPagination.result !=null }">
	            	<tbody>
	            	<c:forEach items="${planCheckPagination.result }" var="planCheck">
	            		<tr class="list-tr" >
		                    <td>${planCheck.name}</td>
		                    <td><fmt:formatDate value='${planCheck.startTime }' pattern='yyyy-MM-dd HH:mm'/> — 
		                    	<fmt:formatDate value='${planCheck.endTime }' pattern='yyyy-MM-dd HH:mm'/></td>
		                    <td>
		                    	<c:choose>
			                    	<c:when test="${planCheck.feedbackCount == null}">
			                    		<aut:authorize url="/seller/planCheck/plancheck-saveSellerPlanCheck">
			                    			<input type="button" value="申请结算" class="m-btn-done" onclick="applyCheck(${planCheck.planId})">
			                    		</aut:authorize>
			                    	</c:when>
		                    		
		                    		
			                    	<c:when test="${planCheck.feedbackCount == 2 && planCheck.feedbackStatus == 1 && planCheck.isConfirm == null  }">
			                    		<aut:authorize url="/seller/planCheck/plancheck-editSellerPlanCheck">
			                    			<input type="button" value="收款确认" class="m-btn-done" onclick="paymentConfirm(${planCheck.planCheckId})">
			                    		 </aut:authorize>
			                    	</c:when>
		                    		<c:when test="${planCheck.isConfirm == 1  }">
		                    			<span class="c-green">结算成功</span>
		                    		</c:when>
		                    		<c:otherwise>
		                    			<aut:authorize url="/seller/planCheck/plancheck-editSellerPlanCheck">
		                    				<input type="button" value="收款确认" class="m-btn-done" disabled="disabled" style="color:gray">
		                    			</aut:authorize>
		                    		</c:otherwise>
		                    	</c:choose>
		                    </td>
		                </tr>
	            	</c:forEach>
                	</tbody>
	            </c:if>
            </table>
        </div>
        <div class="pagination pagination-right">
			<c:if test="${not empty planCheckPagination.result}">
				<wms:commPage page="${planCheckPagination}" beanName="planCheckPage"></wms:commPage>
			</c:if>
		</div>
    </div>
    <jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</body>
</html>