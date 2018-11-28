<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>商家管理</title>
    <link href="${ _cssPath}/common.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
<script type="text/javascript">
	var reset=function(userId){
		if(confirm("你确定要重置密码吗?")){
			$.ajax({   
				   url:"${_ctxPath}/admin/user/user-resetPassword.htm",   
				   type:"POST",
				   data:{'user.userId':userId},
				   dataType:"json",   
				   success:function(data){   
						alert(data.info);
				   },
				   error:function(data){
					   console.log(data);
				   }
			});
		}
	};
	var updateStatus=function(userId,status){
		var confirmMessage;
		
		if(status == 1){
			confirmMessage="是否激活？";
		}else{
			confirmMessage="是否禁用？";
		}
// 		alert(userId+"---"+status);
		if(confirm(confirmMessage)){
// 			$.ajax({   
// 				   url:"${_ctxPath}/user/user-resetPassword.htm",   
// 				   type:"POST",
// 				   data:{'user.userId':userId},
// 				   dataType:"json",   
// 				   success:function(data){   
// 						alert(data.info);
// 				   },
// 				   error:function(data){
// 					   console.log(data);
// 				   }
// 			});
window.location.href="${_ctxPath}/admin/user/user-updateUserStatus.htm?user.userId="+userId+"&user.status="+status;
		}
	};
	
	var addSeller=function(userId,status){
		window.location.href="${_ctxPath}/admin/user/user-seller.htm";
	};
	var exportSeller=function(userId,status){
		window.location.href="${_ctxPath}/admin/user/user-listExportSellers.htm";
	};
	$(function(){
		$("#search").click(function(){
    		var keywords = $(".J-keywords").val();
    		if(keywords == $(".J-keywords").attr("data-default")){
    			$(".J-keywords").val("");
    		}
    		$(".sub-form").submit();
    	});
	});
</script>
</head>
<body>
<jsp:include page="../include/header.jsp"  flush="true" />
 <div class="body m-w980p">
<aut:authorize url="/admin/user/seller-search">
 <form class="sub-form m-clear m-mt10p" action="${_ctxPath}/admin/user/user-searchSellers.htm" id="search-form" method="post">
	    <div class="m-fl">
          <input type="text" data-default="输入登陆名、商家名称、联系电话、业务联系人、支付宝帐号" name="sellerPage.params.name" value="${sellerPage.params.name }" class="J-keywords txt-input txt-input-w input-marks" style="width:330px;" /><input type="button" class="m-btn m-btn-search" value="查 询" id="search"/>
	    </div>
			<c:if test="${not empty sellerPage.result}">
				 <div class="m-fr curr-num">
					 	<label>当前显示： </label> 
		        <wms:commPageSize page="${sellerPage}" beanName="sellerPage"></wms:commPageSize>
				 </div>
      </c:if>
	</form>
 </aut:authorize>
       <div class="m-mt10p">
        <aut:authorize url="/admin/user/user-add">
				<input type="button" class="m-btn" value="添加新商家" onclick="addSeller()"/>
				</aut:authorize>
				<aut:authorize url="/admin/user/user-listExportSellers">
				<input type="button" class="m-btn" value="导出" onclick="exportSeller();"/>
				</aut:authorize>
			</div>
<!-- 			<div class="pagination pagination-right"> -->
<%-- 				<c:if test="${not empty sellerPage.result}"> --%>
<%-- 					<wms:commPage page="${sellerPage}" beanName="sellerPage"></wms:commPage> --%>
<%-- 				</c:if> --%>
<!-- 		  </div> -->
<!--         <div class="clear"></div> -->
         <!--start 商家管理table-->
        <div class="m-mt10p business-management">
            <table class="tab-control" id="tab">
                <thead>
                    <tr>
                        <th width="12%">登录名</th>
                        <th width="20%">商家名称</th>
                        <th width="10%">法人代表</th>
                        <th width="10%">业务联系人</th>
                        <th width="12%">联系电话</th>
                        <th width="12%">支付宝账号</th>
                        <th width="13%">可售品牌</th>
                        <th width="10%">操作</th>

                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${sellerPage.result}" var="seller" varStatus="status">
					<tr class="list-tr">
						<td ><a href="${_ctxPath}/admin/user/user-sellerDetail.htm?seller.sellerId=${seller.sellerId}">${seller.user.username}</a></td>
						<td >${seller.companyName}</td>
						<td >${seller.companyCorporation}</td>
						<td >${seller.contactName}</td>
						<td >
							<div>
	                            <p>${seller.mobile}</p>
	                            <p>${seller.tel}</p>
                        	</div>
                        </td>
						<td >${seller.alipayNo}</td>
						<td >${seller.brandNames}</td>
						<td>
							<c:if test="${seller.user.status==0 }">
								<aut:authorize url="/admin/user/user-updateUserStatus">
								<a class="c-blue"  href="javascript:void(0)" onclick="updateStatus(${seller.user.userId},1)">激活</a>
								</aut:authorize>
							</c:if>
							<c:if test="${seller.user.status==1 }">
								<aut:authorize url="/admin/user/user-updateUserStatus">
								<a class="c-blue"  href="javascript:void(0)" onclick="updateStatus(${seller.user.userId},0)">禁用</a> 
								</aut:authorize>
							</c:if>
							<aut:authorize url="/admin/user/user-edit">
							<br/> <a class="c-blue" href="${_ctxPath}/admin/user/user-seller.htm?seller.sellerId=${seller.sellerId}">编辑</a> 
							</aut:authorize>
							<aut:authorize url="/admin/user/user-resetPassword">
							<br/> <a class="c-blue" onclick="reset(${seller.user.userId})" href="#">重置密码</a>
							</aut:authorize>
						</td>
					</tr>
				</c:forEach>
                </tbody>
            </table>
        </div>
       <!--end 商家管理table-->
       <div class="pagination pagination-right">
			<c:if test="${not empty sellerPage.result}">
				<wms:commPage page="${sellerPage}" beanName="sellerPage"></wms:commPage>
			</c:if>
	   </div>
 </div>
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>	
</body>
</html>