<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh">
<head>
<meta charset="utf-8" />
<title>商品预览</title>
	<link rel="stylesheet" type="text/css" href="${_cssPath }/common.css" media="all">
 	<link rel="stylesheet" type="text/css" href="${_cssPath }/pages/productPreview.css" media="all">
<body>
    <div class="m-w980p">
        <!--面包屑-->
        <div class="crumbs"><a href="#">直销商城 ></a><a href="#">品牌特卖 ></a><a href="#">${product.brand.name} ></a>${product.name}</div>
        <!--面包屑 end-->
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
                            <tr>
                                <td width="40" align="right">原价：</td>
                                <td width="100">￥${product.marketPrice }</td>
                                <td width="40" align="center">折扣：</td>
                                <td><span class="g-val">${product.rebate }折扣</span></td>
                            </tr>
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
                            <li>
                                <div class="tit">数量：</div>
                                <div class="info">
                                    <div class="change-sum">
                                        <input type="text" value="1"><a href="javascript:;" class="add"></a><a href="javascript:;" class="reduce"></a>
                                    </div>
                                    <div class="change-txt">
                                        	件（库存${product.saleInventoryCount }件）
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </div>
                    <div class="surplus">
                       	 剩余：<span class="countdown" data-config="{
                             'endTime':'<fmt:formatDate value="${product.sellEndTime }" type="both"/>',
                            'nowTime':'<%=System.currentTimeMillis()/1000 %>',
                             'stopTips':'<span>活动结束谢谢参与！</span>' 
                          }"></span>结束
                    </div>
                    <div class="goods-btn cf">
                        <a class="btn-c"><span>加入购物车</span></a><a class="btn-b"><span>立即购买</span></a>
                    </div>
                </div>
            </div>
        </div>
        <div class="produce-info cf">
            <div class="pro-main">
                <div class="tab-gn">
                    <ul>
                        <li>商品信息</li>
                        <li>服务保障</li>
                        <li>支付方式</li>
                    </ul>
                    <div class="protab-item" style="display: block;">
                    	${product.describe }
                    </div>
                    <div class="protab-item">
                    	 服务保障<br>
                       	<c:forEach items="${helpCategoryMaps['guarantee']}" var="list">
                       		${list.name }
                       		${list.help.content}<br>
                       	</c:forEach> 
                    </div>
                    <div class="protab-item">
                    	 支付方式<br>
                      	<c:forEach items="${helpCategoryMaps['pays']}" var="list">
                       		${list.name }
                       		${list.help.content}<br>
                       	</c:forEach>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!--footer.jsp放置在这--><jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
    <script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
    <script type="text/javascript" src="${_jsPath }/plugin/plugin.js"></script>
    <script type="text/javascript" src="${_jsPath }/plugin/multizoom/multizoom.js"></script>
    <script type="text/javascript" src="${_jsPath }/pages/produce_detail.js"></script>
</body>
</html>