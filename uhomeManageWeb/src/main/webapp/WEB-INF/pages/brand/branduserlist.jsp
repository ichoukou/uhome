<!DOCTYPE html>
<%@page language="java" import="com.ytoxl.module.uhome.uhomebase.dataobject.Product" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh">
<head>
<meta charset="utf-8" />
<title>品牌管理</title>
	<link href="${_cssPath }/common.css" rel="stylesheet" />
	<script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
    <script type="text/javascript">
    	$(function(){
    		$("#search").click(function(){
    			var keywords = $("#txtKeywords").val();
    			if(keywords == $("#txtKeywords").attr("data-default")){
    				$("#txtKeywords").val("");
    			}
    			$("#brandForm").submit();
    		});
    	});
    	function add(){
    		window.location.href='${_ctxPath}/admin/brand/brand-singleBrand.htm?opert=add';
    	}
    	function clickForbidden(brandId,status){
    		var msg;
    		if(status==1){
    			msg="是否确认删除该品牌？";
    		}else{
    			msg="是否确认激活该品牌？";
    		}
    		 var value = "brand.brandId=" + brandId + "&brand.isForbidden=" + status;
    		if(confirm(msg)){
				$.ajax({
					url:_ctxPath + "/admin/brand/brand-forbiddenBrand.htm",
					type:"POST",
					data:{"brand.brandId":brandId,"brand.isForbidden":status},
					dataType:"html",
					async: false,
					success:function(data){
						 alert(data.info);
						 window.location.href="${_ctxPath}/admin/brand/brand-listUserBrans.htm";
					},
					dataType:'json'
				});
    		}
    		
		}
    </script>
<body>
	<!--start header-->
	<jsp:include page="../include/header.jsp"></jsp:include> 
    <!--end header-->
    <!--start body-->
       <div class="body m-w980p">
       <!--start form-->
    	<aut:authorize url="/admin/brand/brand-search">
         <form class="sub-form layout" id="brandForm" action="${_ctxPath}/admin/brand/brand-listUserBrans.htm" method="post">
            <div class="m-mt10p m-clear">
	            <div class="m-fl">
	            			<input type="text"  class="txt-input input-marks input-default" data-default="输入品牌名称" id="txtKeywords" name="pagebrands.params.name" value="${pagebrands.params.name}"/><input type="button" class="m-btn m-btn-search" value="查 询"  id="search"/>
	            </div>
	            <div class="m-fr curr-num">
	                <c:if test="${not empty pagebrands.result}">
	                <label >当前显示：</label>
	                <wms:commPageSize page="${pagebrands}" beanName="pagebrands"></wms:commPageSize>
	                </c:if>
	            </div>
            </div>
        </form>
</aut:authorize>
        <div class="m-mt10p">
        		<aut:authorize url="/admin/brand/brand-add">
  		   		<input type="button" class="m-btn" value="添加新品牌" onclick="javascript:add()"/>
  		   		</aut:authorize>
  		   </div>
<!--   		   <div class="pagination pagination-right"> -->
<%--         		 <c:if test="${not empty pagebrands.result}"> --%>
<%--          			<wms:commPage page="${pagebrands}" beanName="pagebrands"></wms:commPage> --%>
<%--          		 </c:if> --%>
<!--      			</div> -->
<!--      			<div class="clear"></div> -->
       <!--end form-->
       <!--start 品牌管理table-->
        <div class="m-mt10p">
            <table class="tab-control">
                <thead>
                    <tr>
                        <th width="40%">品牌</th>
                        <th width="40%">是否热卖</th>
                        <th width="20%">操作</th>
                    </tr>
                </thead>
                <tbody>
                	<c:choose>
		              <c:when test="${empty pagebrands.result}">
		               		<tr><td colspan="3">无相关数据！</td></tr>
		              </c:when>
		              <c:otherwise>
		              		<c:forEach items="${pagebrands.result}" var="brand" varStatus="status">
		                	 <tr class="list-tr">
			                    <td>
	                            <p>${brand.englishName}</p>
	                            <p>${brand.name}</p>
			                    </td>
			                    <td>
			                    	<c:if test="${brand.isHotSeller == 1}">是</c:if>
			                    	<c:if test="${brand.isHotSeller != 1}">否</c:if>
			                    </td>
			                    <td>
			                    	<aut:authorize url="/admin/brand/brand-edit">
			                        <a class="c-blue" href="${_ctxPath}/admin/brand/brand-singleBrand.htm?brand.brandId=${brand.brandId}&opert=edit">编辑</a>
			                    	</aut:authorize>
			                    	<c:if test="${brand.isForbidden==0 }">
			                    	<aut:authorize url="/admin/brand/brand-forbiddenBrand">
			                        <a class="c-blue" href="javascript:void(0);" onclick="clickForbidden(${brand.brandId},1)">禁用</a>
			                    	</aut:authorize>
			                    	</c:if>
			                    	<c:if test="${brand.isForbidden==1 }">
			                    	<aut:authorize url="/admin/brand/brand-forbiddenBrand">
			                        <a class="c-blue" href="javascript:void(0);" onclick="clickForbidden(${brand.brandId},0)">激活</a>
			                    	</aut:authorize>
			                    	</c:if>
			                    </td>
		                </tr>
		         		</c:forEach>
		           </c:otherwise>	
        		</c:choose>
            </tbody>
       	</table>
     </div>
     <div class="pagination pagination-right">
         <c:if test="${not empty pagebrands.result}">
         <wms:commPage page="${pagebrands}" beanName="pagebrands"></wms:commPage>
         </c:if>
     </div>
       <!--end 品牌管理table-->
</div>
    <!--end body-->
    <!--start footer-->
    <!--end footer-->
</body>
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</html>