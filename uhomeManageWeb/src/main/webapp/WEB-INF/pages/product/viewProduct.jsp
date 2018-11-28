<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Product"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看商品</title>
	<link href="${_cssPath }/common.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/produce-detail.css">
    <script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
    <script type="text/javascript" src="${_jsPath }/plugin/multizoom/multizoom.js"></script>
	<script type="text/javascript" src="${_jsPath}/plugin/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
    <script type="text/javascript" src="${_jsPath }/pages/viewProduct.js"></script>
    <script type="text/javascript">
	    var STATUS_PASS = <%= Product.STATUS_PASS%>;
	    var STATUS_NO_PASS = <%= Product.STATUS_NO_PASS%>;
    </script>
<body>
	<!--start header-->
	<jsp:include page="../include/header.jsp"></jsp:include> 
    <!--end header-->
    <!--start body-->
    <div class="body m-w980p">
    	<input type="hidden" value="${product.productId }" id="productId"/>
        <div class="produce-top cf">
            <div class="multizoom thumbs">
	            <a href="<zx:thumbImage originalPath='${product.defaultImage}' imageType='t10'></zx:thumbImage>" data-magsize="468,400" data-large="${_filePath}${product.defaultImage }" data-title="${product.name }" class="cur">
                    <img src="<zx:thumbImage originalPath='${product.defaultImage}' imageType='t9'></zx:thumbImage>"/>
                    <i></i>
	            </a>
            	<c:forEach items="${product.imageList}" var="imageUrl" begin="1">
                <a href="<zx:thumbImage originalPath='${imageUrl}' imageType='t10'></zx:thumbImage>" data-magsize="488,400" data-large="${_filePath}${imageUrl }" data-title="${product.name }">
                    <img src="<zx:thumbImage originalPath='${imageUrl}' imageType='t9'></zx:thumbImage>"/>
                    <i></i>
                </a>
            	</c:forEach>
            </div>
            <div class="property">
                <div class="targetarea">
                    <img id="multizoom" alt="zoomable" title="" src=""/>
                </div>
                <div class="pro-details">
                    <div class="goods-name">${product.name }</div>
                    <div class="goods-price"><span class="g-val">￥${product.salePrice }</span></div>
                    <div class="goods-org-item">
                        <table width="100%">
                            <thead>
                            <tr>
                                <td width="40" align="right">原价：</td>
                                <td width="100">￥ ${product.marketPrice }</td>
                                <td width="40" align="center">折扣：</td>
                                <td><span class="g-val">${product.rebate }折</span></span></td>
                            </tr>
                            </thead>
                        </table>
                    </div>
                    <div class="goods-sort-list cf">
                        <ul>
                        <c:forEach items="${product.skuOptions }" var="skuOpt">
                        	<li>
                                <div class="tit">${skuOpt.skuOptionName }：</div>
                                <div class="info">
                                 <c:forEach items="${skuOpt.skuOptionValues }" var="obj" varStatus="status">
						         	<span <c:if test="${status.index == 0 }">class="selected"</c:if>>${obj.skuOptionValue }</span>
						         </c:forEach>
                                </div>
                            </li>
                        </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
        <div class="layout  goods-dis">
            <div>
                <ul class="panel">
                  <li class="cur">商品信息</li>
                  <div class="m-fr goods-items">
			                 <a href="javascript:;" id="previous">上一条</a><a href="javascript:;" id="next">下一条</a>
			            </div>
              	</ul>
            </div>
            
            <div class="clear"></div>
            <div class="pro-detail">
            	${product.describe }
            </div>
        </div>
        <c:set var="notPass" value="<%= Product.STATUS_NO_PASS %>"></c:set>
        <c:set var="checkPend" value="<%= Product.STATUS_CHECK_PEND %>"></c:set>
        <c:if test="${product.status == notPass || product.status == checkPend}">
        <div class="goods-check">
            <label>不通过原因：</label>
            <textarea data-default="如果审核不通过，请填写不通过原因" class="text-area input-marks" id="remark">${product.remark }</textarea>
            <span id="remarkTip" style="display: none;"><font color="red">请填写不通过原因</font></span>
        </div>
        </c:if> 
        <div class="btn-check">
        	<aut:authorize url="/admin/product/product-review">
        	<c:if test="${product.status == checkPend}">
            <input type="button" class="m-btn" value="审核通过" id="pass"/>
            <input type="button" class="m-btn" value="审核不通过" id="notPass"/>
        	</c:if>
        	</aut:authorize> 
            <input type="button" class="m-btn" value="返   回" id="back"/>
        </div>
    </div>
    <!--footer.jsp放置在这--><jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</body>
</html>