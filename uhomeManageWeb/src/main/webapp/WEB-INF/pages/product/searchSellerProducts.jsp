<!DOCTYPE html>
<%@page language="java" import="com.ytoxl.module.uhome.uhomebase.dataobject.*" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh">
<head>
<meta charset="utf-8" />
<title>商品管理</title>
	<link href="${_cssPath }/common.css" rel="stylesheet" />
	<link href="${_cssPath }/index.css" rel="stylesheet" />
	<script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
  <script type="text/javascript" src="${_jsPath}/plugin/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="${_jsPath}/plugin/uploadify/swfobject.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"></script>
	  	
  	<c:set var="SPECIAL_SELLER_INPROGRESS" value="<%= Plan.ACTIVITYSTATUS_SPECIAL_SELLER_INPROGRESS%>"></c:set>
	<c:set var="SECKILL_INPROGRESS" value="<%= Plan.ACTIVITYSTATUS_SECKILL_INPROGRESS%>"></c:set>
	<c:set var="STATUS_DRAFT" value="<%=Product.STATUS_DRAFT %>"></c:set>
	<c:set var="STATUS_PASS" value="<%=Product.STATUS_PASS %>"></c:set>
	<c:set var="STATUS_NO_PASS" value="<%=Product.STATUS_NO_PASS %>"></c:set>
<body>
	<!--start header-->
	<jsp:include page="../include/header.jsp"></jsp:include> 
    <!--end header-->
    <!--start body-->
    <div class="body m-w980p">
       <!--start form-->
        <form class="sub-form" action="${_ctxPath}/seller/product/product-searchSellerProducts.htm" method="post">
        	<div class="m-clear m-mt10p">
            <div class="m-fl">
<!-- 						<label>创建日期：</label>  -->
<%-- 						<input name="productPage.params.beginTime" value="${productPage.params.beginTime}" class="Wdate" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/> --%>
<!-- 						<label>至 </label>  -->
<%-- 						<input name="productPage.params.endTime" value="${productPage.params.endTime }" class="Wdate" type="text" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})"/> --%>
                        <select name="productPage.params.brandId" class="m-sel">
                           <option value="">全部品牌</option>
							<c:forEach items="${sellerBrands }" var="brand">
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
            <div class="m-fr curr-num">
                <c:if test="${not empty productPage.result}">
                <label >当前显示：</label>
                <wms:commPageSize page="${productPage}" beanName="productPage"></wms:commPageSize>
                </c:if>
            </div>
          </div>
            <div class="m-mt10p">
            	<aut:authorize url="/seller/product/product-addProduct">
            		<input type="button" id="addProduct" class="m-btn" value="添加商品" />
            	</aut:authorize>
            	<script type="text/javascript">
            		var userId = $("#userId").val();
           			var scriptStr = '${_ctxPath}/seller/product/product-batchUpload.htm?user.userId='+userId;
            		$(function(){
            			<%-- var uploadDefaultParams = {
							'auto' : false,
							'buttonImg' : '${_jsPath}/plugin/uploadify/myProduct.jpg',
							'cancelImg' : '${_jsPath}/plugin/uploadify/cancel.png',
							'expressInstall' : '${_jsPath}/plugin/uploadify/expressInstall.swf',
							'fileDataName' : 'file',
							'fileDesc' : '请选择zip文件',
							'fileExt' : '*.zip',
							'multi' : false,
							'script' : scriptStr,
							'sizeLimit' : 26214400,
							'uploader' : '${_jsPath}/plugin/uploadify/uploadify.allglyphs.swf',
							'onSelect' : function(event, queueId, fileObj){
								if (fileObj.size > sizelimit) {
	            			        alert(fileObj.name + " 文件大小超过限制");
	            			        return false;
	            			    }
							}
						};
            			var uploadProductsParams = uploadDefaultParams;
            			uploadProductsParams.onSelect = function(event, queueId, fileObj){
            				if(confirm("商品批量上传中!")){
            					console.log(event);
            					//event.uploadifyUpload();
            				}else{
            					event.uploadifyClearQueue();
            				}
            				if (fileObj.size > sizelimit) {
            			        alert(fileObj.name + " 文件大小超过限制");
            			        return false;
            			    }
            			};
						uploadProductsParams.onComplete = function(event,ID,fileObj,response,data) {
							alert($.parseJSON(response).info);
							window.location.reload();
						};
						$('#batchUploadProduct').uploadify(uploadProductsParams); --%>
            			$('#batchUploadProduct').uploadify({
							'auto' : true,
							'buttonImg' : '${_jsPath}/plugin/uploadify/myProduct.jpg',
							'cancelImg' : '${_jsPath}/plugin/uploadify/cancel.png',
							'expressInstall' : '${_jsPath}/plugin/uploadify/expressInstall.swf',
							'fileDataName' : 'file',
							'fileDesc' : '请选择zip文件',
							'fileExt' : '*.zip',
							'multi' : false,
							'script' : scriptStr,
							'sizeLimit' : 26214400,
							'uploader' : '${_jsPath}/plugin/uploadify/uploadify.allglyphs.swf',
							'onSelect' : function(event, queueId, fileObj){
								/*if(confirm("商品批量上传中!")){
									console.log(this.auto)
									//event.uploadifyUpload();
	            				}else{
	            					event.uploadifyClearQueue();
	            				}*/
								if (fileObj.size > 26214400) {
	            			        alert(fileObj.name + " 文件大小超过限制");
	            			        return false;
	            			    }
							},
							'onUploadSuccess' : function(event, queueId, fileObj){
								//alert(1);
							},
							'onComplete': function(event,ID,fileObj,response,data){
								alert($.parseJSON(response).info);
								window.location.reload();
							}
						});
            		});
            	</script>
            	<aut:authorize url="/seller/product/product-batchUpload">
            		<span class="upload-btn"><input type="file" id="batchUploadProduct" name="file"/></span>
            	</aut:authorize>
            </div>
        </form>
       <!--end form-->
        <div class="m-mt10p">
	            <span>草稿-审核中-审核通过-等待特卖/等待秒杀-特卖中/秒杀中-等待排期</span>
	            <a class="download" href="${_ctxPath}/seller/product/product-downloadTemplate.htm">点击下载模版</a>
<!--             <div class="pagination pagination-right"> -->
<%--                 <c:if test="${not empty productPage.result}"> --%>
<%--                 <wms:commPage page="${productPage}" beanName="productPage"></wms:commPage> --%>
<%--                 </c:if> --%>
<!--             </div> -->
        </div>
       <!--start 商品管理table-->
        <div class="m-mt10p goods-management">
            <table class="tab-control">
                <thead>
                    <tr>
                        <th width="12%">品牌</th>
                        <th width="20%">商品名称</th>
                        <th width="12%">图片</th>
                        <th width="12%">价格（元）</th>
                        <th width="12%">商品数量</th>
                        <th width="12%">商品状态</th>
                        <th width="10%">审核状态</th>
                        <th width="10%">操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:choose>
	            <c:when test="${empty productPage.result}">
	                <tr><td colspan="8">无相关数据！</td></tr>
	            </c:when>
                <c:otherwise>
	                <c:forEach items="${productPage.result}" var="product" varStatus="status">
	                <tr class="list-tr" productId="${product.productId }">
	                    <td>${product.brand.name }</td>
	                    <td>
	                        <div>
	                            <p>${product.name}</p>
	                        </div>
	                    </td>
	                    <td>
	                    	<img src="<zx:thumbImage originalPath='${product.defaultImage}' imageType='t16'></zx:thumbImage>" />
	                    </td>
	                    <td>
	                        <div>
	                            <p>原价：${product.marketPrice }</p>
	                            <c:if test="${not empty product.salePrice }">
	                             <p>特卖价：${product.salePrice }</p>
	                            </c:if>
<%-- 	                            <c:if test="${not empty product.secKillPrice }"> --%>
<%-- 	                            <p>秒杀价：${product.secKillPrice }</p> --%>
<%-- 	                            </c:if> --%>
	                        </div>
	                    </td>
	                    <td>
	                        <div>
	                            <p>特卖：${product.saleInventoryCount }</p>
<%-- 	                            <p>秒杀：${product.secKillInventoryCount }</p> --%>
	                        </div>
	                    </td>
	                    <td>
	                        <div>
	                        <c:choose>
	                        <c:when test="${not empty product.plans && not empty product.specialSellerPlan}">
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
<%-- 								<c:if test="${not empty product.secKillPlan}"> --%>
<%-- 								<p><spring:message code="plan.activityStatus.${product.secKillPlan.activityStatus}"/></p> --%>
<%-- 								<p><fmt:formatDate value="${product.secKillPlan.startTime }" pattern="yyyy-MM-dd HH:mm:ss"/></p> --%>
<%-- 								</c:if> --%>
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
	                        </div>
	                    </td>
	                    <td>
	                    	<div class="shenhe">
	                    		<c:if test="${product.status != STATUS_DRAFT }">
			                    	<spring:message code="product.status.${product.status}"/>
		                    		<c:if test="${product.status == STATUS_NO_PASS }">
			                    	<div class="not-through">${product.remark }</div>
			                    	</c:if>
		                    	</c:if>
	                    	</div>
                    	</td>
	                    <td>
	                    	<div>
	                    	 <aut:authorize url="/seller/product/product-sellOut">
		                         <c:if test="${product.specialSellerPlan.activityStatus == SPECIAL_SELLER_INPROGRESS
		                         					&& product.status == STATUS_PASS && product.saleInventoryCount > 0}">
		                         <p><a class="c-blue sellOut" href="javascript:;">售罄（特卖）</a></p>
		                         </c:if>
		                     </aut:authorize>
	                         <aut:authorize url="/seller/product/product-delete">
		                         <c:if test="${!(product.specialSellerPlan.activityStatus == SPECIAL_SELLER_INPROGRESS
		                         					&& product.status == STATUS_PASS)}">
		                         <p><a class="c-blue delete" href="javascript:;">删除</a></p>
		                         </c:if>
	                         </aut:authorize>
	                         <aut:authorize url="/seller/product/product-edit">
	                         	<p><a class="c-blue edit" href="javascript:;">编辑</a></p>
	                         </aut:authorize>
	                         <aut:authorize url="/seller/product/product-quickEdit">
	                         	 <c:if test="${empty product.plans }">
		                         <p><a class="c-blue quickEdit" href="javascript:;">快速编辑</a></p>
		                         </c:if>
	                         </aut:authorize>
                        </div>
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
    <script type="text/javascript" src="${_jsPath }/pages/searchSellerProducts.js"></script>
</body>
</html>