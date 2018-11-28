<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.Supplier"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>品牌合作</title>
	<link href="${ _cssPath}/common.css" rel="stylesheet" />
	<script src="${_jsPath }/jquery/jquery.1.8.1.js" language="javascript"></script>
 <script type="text/javascript">
$(function(){
	
	//删除表格行
	$(".del").on("click",function(ev){
		var id=$(this).attr("id");
		if(confirm("你确定要删除吗?")){
	 		$.ajax({   
	 			   url:"${_ctxPath}/admin/supplier/supplier-deleteSupplier.htm",   
	 			   type:"POST",
	 			   data:{'supplierId':id},
				   dataType:"json",   
	 			   success:function(data){ 
	 				   if(data.code == "true"){
	 	 				  $.dialog({
	 	 				        title: false,
	 	 				        content: data.info,
	 	 				        time : 1000
	 	 				    });
	 	 					$(".d-close").hide();
	 	 				    setTimeout("window.location.href='${_ctxPath}/admin/supplier/supplier-searchSuppliers.htm'",1000);	   
	 				   }
	 			   },
	 			   error:function(xhr){
	 				  alert(xhr.responseText);
	 			   }
	 		});
		}
		ev.stopPropagation();
	});
	//模糊查询商家信息
	$("#search").on("click",function(){
		var keywords = $(".J-kewords").val();
		if(keywords == $(".J-kewords").attr("data-default")){
			$(".J-kewords").val("");
			//return false;
		}
		$(".sub-form").submit();
	});
	
	//点击供应商状态筛选供应商
	$(".J-brand-query li").click(function(){
		$("#type").val($(this).attr("paramtype"));
		$("#search").click();
	});
});

</script> 
</head>
<body>
<jsp:include page="../include/header.jsp"  flush="true" />
  <div class="body m-w980p">
		<form class="sub-form m-mt10p m-clear" action="${_ctxPath}/admin/supplier/supplier-searchSuppliers.htm" method="post">
		  <input type="hidden" id="type" name="supplierPage.params.type" value="${supplierPage.params.type }"/>
		  <div class="m-fl">
		  		<input type="text" data-default="输入品牌名称，公司名称，联系人" name="supplierPage.params.name" value="${supplierPage.params.name }" class="J-kewords txt-input input-marks" /><input type="button" class="m-btn m-btn-search" value="查 询" id="search"/>
		  </div>
		  <c:if test="${not empty supplierPage.result}">
			  <div class="m-fr curr-num">
					<label>当前显示： </label> 
	        	<wms:commPageSize page="${supplierPage}" beanName="supplierPage"></wms:commPageSize>
				</div>
      		</c:if>
		    </form>
		    <div class="body-nav subnav m-mt10p">
		        <ul class="J-brand-query m-clear">
			        <li><a <c:if test="${empty supplierPage.params.type }">class="current-chose"</c:if>>全部</a></li>
			        <c:forEach items="${types }" var="obj">
			            <li paramtype="${obj}"><a <c:if test="${obj == supplierPage.params.type }">class="current-chose"</c:if>><spring:message code="supplier.type.${obj}"/></a></li>
			        </c:forEach>
		        </ul>
<%-- 		        <c:if test="${not empty supplierPage.result}"> --%>
<!-- 		        <div class="pagination pagination-right"> -->
<%-- 					<wms:commPage page="${supplierPage}" beanName="supplierPage"></wms:commPage> --%>
<!-- 				</div> -->
<%-- 				</c:if> --%>
		    </div>
			<table id="tab" width="100%">
				<thead class="tab-control">
					<tr>
						<th width="16%">品牌名称</th>
						<th width="17%">公司名称</th>
						<th width="16%">联系人</th>
						<th width="16%">联系电话</th>
						<th width="16%">商家性质</th>
						<th  width="16%">操作</th>
					</tr>
				</thead>
				
				<tbody>
					<c:forEach items="${supplierPage.result}" var="supplier"
						varStatus="status">
						<tr>
							<td colspan="6" class="num-icon">
								<table class="tab-control">
									<tr class="tr-height list-tr">
										<td class="num-icon" width="16%"><i></i><label><a>${supplier.brandName }</a></label></td>
										<td width="17%">${supplier.companyName }</td>
										<td width="16%">${supplier.contactName }</td>
										<td width="16%">${supplier.telephone }</td>
										<td width="16%">
											<c:if test="${not empty supplier.type }">
											<spring:message code="supplier.type.${supplier.type}"/>	
											</c:if>
										</td>
									    <td width="16%"> 
									    	<aut:authorize url="/admin/supplier/supplier-deleteSupplier">
									        <a href="javascript:;" id="${supplier.supplierId }" class="del">删除 </a>
									    	</aut:authorize>
									    </td>
									</tr>
									<tr class="show-tr">
										<td colspan="6" class="show-tab"><p class="order-info">品牌说明：${supplier.describe }</p></td>
									</tr>
								</table>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		<!--end 站内信table-->
		 <div class="pagination pagination-right">
			<c:if test="${not empty supplierPage.result}">
				<wms:commPage page="${supplierPage}" beanName="supplierPage"></wms:commPage>
			</c:if>
		</div>
	</div>
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</body>
</html>