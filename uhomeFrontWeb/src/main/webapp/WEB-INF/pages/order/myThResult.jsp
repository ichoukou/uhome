<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
  <meta charset="utf-8"/>
    <title>退货订单-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/return-manage.css?d=${_resVerion}"/>
	<script type="text/javascript" src="${_jsPath }/plugin/calendar/WdatePicker.js"></script>
</head>
<body>
	 <jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include>
     <div class="m-w960p cf">
        <!--面包屑-->
      <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a><a href="${_domain }/order/order-myOrders.htm">个人中心 ></a>退货管理</div>
        <!--面包屑 end-->
        <jsp:include page="/WEB-INF/pages/include/left.jsp"></jsp:include>
   		 <div class="inf-detail m-mt20p">
            <div class="tab-gn">
            	<ul class="tab-gnul">
                    <li class="cur"><a href="${_ctxPath}/returnOrder/returnOrder-myOrders.htm">申请退货</a></li>
                    <li><a href="${_ctxPath}/returnOrder/returnOrder-myReturnOrders.htm">退货记录</a></li>
                </ul>
           			 <div class="protab-item cf" style="display: block;">
           			 	  <div class="ret-select">
           			 	  	 <form method="post" action="${_ctxPath}/returnOrder/returnOrder-myOrders.htm" id="searchForm">
	               				<input class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',qsEnabled:true})" readonly="" type="text" name="orderPage.params.beginTime" value="${orderPage.params.beginTime}" style="width:160px">
	                			<label class="date-to">至</label>
	                			<input class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',qsEnabled:true})" readonly="" type="text" name="orderPage.params.endTime" value="${orderPage.params.endTime}" style="width:160px" />
                				<input type="hidden" name="orderPage.params.userId" value="${orderPage.params.userId }" />
                				<input type='submit' class="submit" value='查询'>
            				</form>
           			 	  </div>
           			 	  <div class="user-tab">
           			 	  	<table>
                           		<thead>
		                            <tr>
		                                <th width="17%">订单号</th>
		                                <th width="70%">订单商品</th>
		                                <th width="13%">下单时间</th>
		                            </tr>
                           		 </thead>
                            	<tbody>
            						 <c:forEach items="${orderPage.result}" var="order" >
            						 <tr>
            						 	<td ><span class="cor-orange" >${order.orderNo}</span></td>
                                		<td>
                                			 	<ul class="return-item cf">
	                                			 <c:forEach items="${order.items}" var="item">
	                               			 		  	<li>
	                                                        <img src="<zx:thumbImage originalPath='${item.productSku.product.defaultImage}' imageType='t16'></zx:thumbImage>" width="55" height="55"/>
	                                                        <%--<input type="checkbox" class="ret-ck" name="${order.orderNo}" value="${item.orderItemId}"/> --%>
	                                                        <a href="#" onclick="tuihuoSimgle('${item.orderItemId}','${order.orderId}')">退货</a>
	                                                        <%--class="over-box"自适应盒子备份--%>
	                                        			</li>
	                             			 	</c:forEach>
	                                            </ul>
                              			</td>
                              		<td>
                              			<fmt:formatDate value='${order.createTime}' pattern='yyyy-MM-dd HH:mm' />
                              		</td>
                        		</tr>
                          		</c:forEach>
                            	</tbody>
                        	</table>
           			 	  </div>
           			 </div>
           			 <div id="lastFy">
                    <c:if test="${not empty orderPage.result}">
     					<wms:commPage page="${orderPage}" beanName="orderPage"></wms:commPage>
     				</c:if>
        	</div>
        	</div>
     		</div>
     	</div>
    </div>	
    <jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</body>
  <script type="text/javascript" >
    	function tuiHuo(orderId,orderNo){
    		var ids='';
    		var flag = 0;
	          $('.ret-ck:checked').each(function(){
	        	  var checkedName = $(this).attr("name");
	        	  if(orderNo==checkedName){
		              $("#searchForm").append('<input type="hidden" name="itemIds" value="'+$(this).val()+'" />');
		              flag = 1;
	        	  }
	          });
    		  if(flag == 1){
	    			$("#searchForm").append('<input type="hidden" name="tuihuoOrderIds" value="'+orderId+'" />');
	    			$("#searchForm").attr("action","${_ctxPath}/returnOrder/returnOrder-myChooseOrders.htm");
	    			$("#searchForm").submit();
    		  }else{
    			 $.dialog({
		                title: false,
		                content: "请选择您要退订的商品！",
		                time: 2000
		            });
				 $(".d-close").hide();
    		  }
    	}
    	function tuihuoSimgle(itmeId,orderId){
    		/*  $("#searchForm").append('<input type="hidden" name="itemIds" value="'+itmeId+'" />');
        		$("#searchForm").append('<input type="hidden" name="tuihuoOrderIds" value="'+orderId+'" />'); */ 
       		$("#searchForm").attr("action","${_ctxPath}/returnOrder/returnOrder-myChooseOrders.htm?itemIds="+itmeId+"&tuihuoOrderIds="+orderId);
    			$("#searchForm").submit();
        	}
    	$(function(){
    		$('.ret-ck').each(function(){
	        	$(this).attr("checked",false);	  
	        });
    	})
    </script>
</html>