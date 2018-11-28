<!DOCTYPE html>
<%@page language="java" import="com.ytoxl.module.uhome.uhomebase.dataobject.Product" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh">
<head>
<meta charset="utf-8" />
<title>商品管理</title>
	<link href="${_cssPath }/common.css" rel="stylesheet" />
	<script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
  <script type="text/javascript">
    $(function(){
    	$("#search").on("click",function(){
				var keywords = $(".J-keywords").val();
				if(keywords == $(".J-keywords").attr("data-default")){
					$(".J-keywords").val("");
				}
				$(".sub-form").submit();
			});
    	//查看
    	$(".view").click(function(){
    		var productId = $(this).closest("tr").attr("productId");
    		window.location.href = _ctxPath + "/admin/product/product-view.htm?product.productId="+productId;
    	});
    	//审核
    	$(".review").click(function(){
    		var productId = $(this).closest("tr").attr("productId");
    		window.location.href = _ctxPath + "/admin/product/product-review.htm?product.productId="+productId;
    	});
    });
    </script>
    <c:set var="STATUS_DRAFT" value="<%=Product.STATUS_DRAFT %>"></c:set>
    <c:set var="STATUS_PASS" value="<%=Product.STATUS_PASS %>"></c:set>
    <c:set var="CHECK_PEND" value="<%= Product.STATUS_CHECK_PEND %>"></c:set>
<body>
	<!--start header-->
	<jsp:include page="../include/header.jsp"></jsp:include> 
    <!--end header-->
    <!--start body-->
    <div class="body m-w980p">
       <!--start form-->
        <form class="sub-form m-clear m-mt10p" action="${_ctxPath}/admin/product/product-searchProducts.htm" method="post">
    <aut:authorize url="/admin/product/product-search">
            <div class="m-fl">
<!-- 						<label>创建日期：</label>  -->
<%-- 						<input name="productPage.params.beginTime" value="${productPage.params.beginTime}" class="Wdate" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/> --%>
<!-- 						<label>至 </label>  -->
<%-- 						<input name="productPage.params.endTime" value="${productPage.params.endTime }" class="Wdate" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/> --%>
                <select name="productPage.params.productCategoryId" id="productCategory" class="m-sel">
                   <option value="">全部品类</option>
							<c:forEach items="${productCategories }" var="productCategory">
								<option value="${productCategory.productCategoryId }"
								<c:if test="${productPage.params.productCategoryId==productCategory.productCategoryId }">
								selected = "selected"
								</c:if>
								>${productCategory.name }</option>
							</c:forEach>
                </select>
                <select name="productPage.params.brandId" id="brand" class="m-sel">
                   <option value="">全部品牌</option>
                           	<c:forEach items="${brands }" var="brand">
								<option value="${brand.brandId }"
								<c:if test="${productPage.params.brandId==brand.brandId }">
								selected = "selected"
								</c:if>
								>${brand.name }</option>
							</c:forEach>
                        </select>
                        <select name="productPage.params.status" class="m-sel">
                           	<option value="">选择商品状态</option>
							<c:forEach items="${statuses }" var="obj">
								<option value="${obj }"
								<c:if test="${productPage.params.status==obj }">
								selected = "selected"
								</c:if>
								><spring:message code="product.status.${obj}"/></option>
							</c:forEach>
                        </select>
                        <select name="productPage.params.reviewStatus" class="m-sel">
                            <option value="">选择审核状态</option>
							<c:forEach items="${reviewStatuses }" var="obj">
								<option value="${obj }"
								<c:if test="${productPage.params.reviewStatus==obj }">
								selected = "selected"
								</c:if>
								><spring:message code="product.status.${obj}"/></option>
							</c:forEach>
                        </select>
              <input type="text" data-default="输入品牌名称，商品名称，SKU码" name="productPage.params.name" value="${productPage.params.name }" class="J-keywords txt-input input-marks" /><input type="button" class="m-btn m-btn-search" value="查 询" id="search"/>
            </div>
            </aut:authorize>
           	<c:if test="${not empty productPage.result}">
	            <div class="m-fr curr-num">
	              <label >当前显示：</label>
	              <wms:commPageSize page="${productPage}" beanName="productPage"></wms:commPageSize>
	            </div>
           	</c:if>
<!--             <div class="pagination pagination-right"> -->
<%--                <c:if test="${not empty productPage.result}"> --%>
<%--                <wms:commPage page="${productPage}" beanName="productPage"></wms:commPage> --%>
<%--                </c:if> --%>
<!--             </div> -->
        </form>
       <!--end form-->
        <div class="m-mt10p">
            <span>草稿-审核中-审核通过-等待特卖/等待秒杀-特卖中/秒杀中-等待排期</span>
        </div>
       <!--start 商品管理table-->
        <div class="m-mt10p goods-management">
            <table class="tab-control">
                <thead>
                    <tr>
                        <th width="12%">品牌</th>
                        <th width="20%">商品名称</th>
                        <th width="8%">商品编码</th>
                        <th width="8%">图片</th>
                        <th width="10%">价格（元）</th>
                        <th width="10%">商品数量</th>
                        <th width="16%">商品状态</th>
                        <th width="8%">审核状态</th>
                        <th width="8%">操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:choose>
                	<c:when test="${empty productPage.result}">
                		<tr class="list-tr"><td colspan="9" align="center">无相关数据！</td></tr>
                	</c:when>
                	<c:otherwise>
                		 <c:forEach items="${productPage.result}" var="product" varStatus="status">
		                <tr class="list-tr" productId="${product.productId }">
		                    <td>${product.brand.name }</td>
		                    <td>${product.name}</td>
		                    <td>${product.productId }</td>
		                    <td>
		                    	<img src="<zx:thumbImage originalPath='${product.defaultImage}' imageType='t16'></zx:thumbImage>" />
		                    </td>
		                    <td>
		                            <p>原价：${product.marketPrice }</p>
		                            <c:if test="${not empty product.salePrice }">
		                            <p>特卖价：${product.salePrice }</p>
		                            </c:if>
<%-- 		                            <c:if test="${not empty product.secKillPrice }"> --%>
<%-- 		                            <p>秒杀价：${product.secKillPrice }</p> --%>
<%-- 		                            </c:if> --%>
		                    </td>
		                    <td>
		                            <p>特卖：${product.saleInventoryCount }</p>
<%-- 		                            <p>秒杀：${product.secKillInventoryCount }</p> --%>
		                    </td>
		                    <td>
		                        <c:choose>
		                        <c:when test="${not empty product.plans  && not empty product.specialSellerPlan}">
		                        	<c:if test="${not empty product.specialSellerPlan}">
									<p>
										<c:choose>
										<c:when test="${product.saleInventoryCount == 0 }">
											已售罄
										</c:when>
										<c:otherwise>
											<spring:message code="plan.activityStatus.${product.specialSellerPlan.activityStatus}"/>
										</c:otherwise>
										</c:choose>
									</p>
									<p><fmt:formatDate value="${product.specialSellerPlan.startTime }" pattern="yyyy.MM.dd"/>-<fmt:formatDate value="${product.specialSellerPlan.endTime }" pattern="yyyy.MM.dd"/></p>
									</c:if>
<%-- 									<c:if test="${not empty product.secKillPlan}"> --%>
<%-- 									<p><spring:message code="plan.activityStatus.${product.secKillPlan.activityStatus}"/></p> --%>
<%-- 									<p><fmt:formatDate value="${product.secKillPlan.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/></p>	 --%>
<%-- 									</c:if> --%>
	                        	</c:when>
	                        	<c:otherwise>
	                        		<c:choose>
	                        		<c:when test="${product.expireFlag == '0' }">
	                        			<c:choose>
	                        			<c:when test="${product.status == STATUS_PASS }">
	                        				<p>等待排期</p>
	                        			</c:when>
	                        			<c:otherwise>
	                        				<p>草稿</p>
	                       				</c:otherwise>
                        				</c:choose>
	                        		</c:when>
	                        		<c:otherwise>
	                        			<p>已过期</p>
	                        		</c:otherwise>
	                        		</c:choose>
	                        	</c:otherwise>
		                        </c:choose>
								<c:if test="${not empty product.sellStartTime && not empty product.sellEndTime}">
									<p>可售日期</p>
									<p><fmt:formatDate value="${product.sellStartTime }" pattern="yyyy.MM.dd"/>-<fmt:formatDate value="${product.sellEndTime }" pattern="yyyy.MM.dd"/></p>
								</c:if>
		                    </td>
		                    <td>
			                    <c:if test="${product.status != STATUS_DRAFT }">
			                    <spring:message code="product.status.${product.status}"/>
			                    </c:if>
		                    </td>
		                    <td>
		                           <p>
			                           <aut:authorize url="/admin/product/product-view">
			                           <a class="c-blue view" href="javascript:;">查看</a>
			                           </aut:authorize>
		                           </p>
		                           <c:if test="${product.expireFlag == '0' && product.status == CHECK_PEND}">
		                           <p>
			                           <aut:authorize url="/admin/product/product-review">
			                           <a class="c-blue review" href="javascript:;">审核</a>
			                           </aut:authorize>
		                           </p>
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
            <c:if test="${not empty productPage.result}">
            <wms:commPage page="${productPage}" beanName="productPage"></wms:commPage>
            </c:if>
        </div>
       <!--end 商品管理table-->
    </div>
    <!--end body-->
    <!--start footer-->
    <jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
    <!--end footer-->
</body>
</html>