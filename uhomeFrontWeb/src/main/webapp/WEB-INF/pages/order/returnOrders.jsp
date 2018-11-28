<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
  <meta charset="utf-8"/>
    <title>退货管理-个人中心-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/return-manage.css?d=${_resVerion}"/>
	<script type="text/javascript" src="${_jsPath }/plugin/calendar/WdatePicker.js"></script>
</head>
<body>
  <jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include>
    <div class="m-w960p cf">
        <!--面包屑-->
        <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a><a href="${_domain }/order/order-myOrders.htm">个人中心 ></a>我的退换货</div>
        <!--面包屑 end-->
        <jsp:include page="/WEB-INF/pages/include/left.jsp"></jsp:include>
   		 <div class="inf-detail m-mt20p">
            <div class="tab-gn">
            	<ul class="tab-gnul">
                   <li><a href="${_ctxPath}/returnOrder/returnOrder-myOrders.htm">申请退货</a></li>
                    <li class="cur"><a href="${_ctxPath}/returnOrder/returnOrder-myReturnOrders.htm">退货记录</a></li>
                </ul>
           			 <div class="protab-item cf" style="display: block;">
           			 	  <div class="ret-select">
           			 	  	 <form method="post" action="${_ctxPath}/returnOrder/returnOrder-myReturnOrders.htm" id="searchForm">
	               				<input class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',qsEnabled:true})"  type="text" name="orderReturnPage.params.beginTime" value="${orderReturnPage.params.beginTime}" style="width:160px">
	                			<label class="date-to">至</label>
	                			<input class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',qsEnabled:true})" type="text" name="orderReturnPage.params.endTime" value="${orderReturnPage.params.endTime}" style="width:160px">
                				<input type='submit' class="submit" value='查询'>
            				</form>
           			 	  </div>
           			 	  <div class="user-tab">
           			 	  	<table>
                           	<thead>
	                            <tr style="width: 100%">
	                                <th width="16%">订单号</th>
                               		<th width="32%">商品名称</th>
                               		<th width="12%">退货状态</th>
                               		<th width="20%">申请时间</th>
                               		<th width="20%">操作</th>
	                            </tr>
                           	</thead>
                         	<tbody>
         			 		<c:forEach items="${orderReturnPage.result}" var="order" >
          					  <tr>
          						<td ><span>${order.orderHead.orderNo}</span></td>
                               	<td class="ret-sales cf" colspan="2">
                             		<table class="ret-sales-table">
                               		<c:forEach items="${order.returnItems}" var="item">
                                		<tr>
                               			 <td width="225">
                               				<ul>
                                			 	<li class="cf bor-none">
	                                                <div class="m-fl ret-bgimg">
			                                            <img src="<zx:thumbImage originalPath='${item.orderItem.productSku.product.defaultImage}' imageType='t16'></zx:thumbImage>" width="65px" height="65px""/>
			                                        </div>
		                                            <div class="over-box">
		                                                <div><a href="${_ctxPath}/item-${item.orderItem.productSku.product.productId}.htm" class="cor-orange">${item.orderItem.productName}</a>
		                                                </div>
		                                                <div>
		                                                    <c:forEach items="${item.orderItem.productSku.productSkuOptionValues}" var="option" >
																<label>${option.skuOptionValue.skuOption.skuOptionName}:
																<c:choose>
																	<c:when test="${not empty option.overrideSkuOptionValue}">
																		${option.overrideSkuOptionValue}&nbsp;&nbsp;</label>
																	</c:when>
																	<c:otherwise>
																		${option.skuOptionValue.skuOptionValue}&nbsp;&nbsp;</label>
																	</c:otherwise>
																</c:choose>
														 	</c:forEach>  	
	                                                	</div>
                                            		</div>
                                        		</li>
		                             		</ul>
                               			</td>
                               				<td align="center" class="no-border">
		                            		  <c:if test="${item.status eq 1}">
		                              		  		<span class="f-weight">未审核</span>
		                              		  </c:if>	
		                              		  <c:if test="${item.status eq 2}">
		                              		  		<span class="f-weight">审核通过</span>
		                              		  </c:if>
                           		                    <c:if test="${item.status eq 3}">
		                              		  		<span class="f-weight">审核不通过</span><br/>
		                              		  		<a class="cor-orange" href="javascript:getNoPassReason(${item.orderReturnItemId})">不通过原因</a>
		                              		  		<a href="javascript:shenSu()" class="cor-orange">申诉</a>
		                              		  </c:if>
		                              		  <c:if test="${item.status eq 4}">
		                              		  	<c:if test="${ order.orderReturnPayment.status == 0}">
		                              		  		<span class="f-weight">审核通过</span>
		                              		  	</c:if>
		                              		  	<c:if test="${ order.orderReturnPayment.status == 1}">
		                              		  		<span class="f-weight">完成</span>
		                              		  	</c:if>
		                              		  </c:if>	
                               				</td>
                                		</tr>
                   			  </c:forEach>
                               		</table>
                             	</td>
                             	<td><fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                              	<td class="ret-tda ret-operation"> 
                              	    <ul class="cf">
                               			<li class="cf bor-none">
								             <a class="cor-orange" href="${_ctxPath}/returnOrder/returnOrder-getReturnOrder.htm?orderReturn.orderReturnId=${order.orderReturnId}&ps=${ order.orderReturnPayment.status}">查看</a>
		                              	      <%-- 
		                              	      <c:if test="${itemss.orderReturn.status eq 3}">
		                              	     	<a href="javascript:shenSu()" class="cor-orange">申诉</a>
		                              		  </c:if>	 
		                              		  --%>
		                              		  <c:choose >
			                              		  <c:when test="${order.express.expressName!=null &&order.express.expressName!=''|| order.express.expressName!=null &&order.express.expressName!=''}">
	                              		  			${order.express.expressName}&nbsp;
	                              		  			${order.express.mailNo}
			                              		  </c:when>
		                              		  	 <c:otherwise>
		                              		  	 <c:if test="${order.isAgree eq '1' }">
		                              		  	 	<!-- 退货审核通过显示 -->
	                              		  	 		<a id="return-add" href="javascript:addMails()">快速填写快递单号</a>
                              		  	 			<div class="ret-altab cf" id="addEmailCompany" style="display:none">
	                              		  	 		<form id="mailForm" method="post">
								                        <table class="write-table">
								                            <tr>
								                                <td width="25%" class="td-right">快递公司：</td>
								                                <td width="45%"><input class="txt" type="text" maxlength="64" name="express.expressName" value="${express.expressName}"/></td>
								                                <td width="25%" class="cor-tips">请填写快递公司</td>
								                            </tr>
								                            <tr>
								                                <td class="td-right">快递单号：</td>
								                                <td><input class="txt" type="text" maxlength="16" name="express.mailNo" value="${express.mailNo}"/></td>
								                                <td class="cor-tips">请填写快递单号</td>
								                            </tr>
								                        </table>
								                        <input type="hidden" name="express.orderReturnId" value="${order.orderReturnId}"/>
								                        <div class="cf ret-kdbtn">
								                            <a id="submitId" href="javascript:addMailsTodb()"class="btn-c"><span>提交</span></a>
								                            <a href="javascript:cancleAdd()"class="btn-d"><span>关闭</span></a>
								                        </div>
	                              		  	 		</form>
								                    </div>
								                    </c:if>
		                              		  	 </c:otherwise>
		                              		</c:choose>
                                       	</li>
                            		</ul> 
                               </td>
                   			  </tr>
                   			  </c:forEach>
                            </tbody>   	
                        	</table>
           			 	  </div>
           			 </div>
           			 <div id="lastFy">
                         <c:if test="${not empty orderReturnPage.result}">
		     				<wms:commPage page="${orderReturnPage}" beanName="orderReturnPage"></wms:commPage>
		     			</c:if>
                 </div>
           </div>
           </div>
           </div>
                  
    <jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
    <script type="text/javascript" >
        function addMails(){
            $.dialog({
              lock: true,
              padding: "20px 30px",
              title:false,
              id:"Companydialog",
              content:$('#addEmailCompany').html(),
              fixed: true,
              cancel: false
              })
          }
        function addMailsTodb(){
        	if($("input[name='express.expressName']").val()==""||$("input[name='express.mailNo']").val()==""){
        		return;
        	}
        	if($("input[name='express.expressName']").val().length>64||$("input[name='express.mailNo']").val().length>16){
    			alert("输入数据有误！");    		
	        	return;
        	}
        	$("#submitId").attr("href","#");
            var formSub = $('#mailForm');
            $.ajax({
                type: 'POST',
                url: _ctxPath+'/returnOrder/returnOrder-addMail.htm',
                data:formSub.serializeArray(),
                success: function(data){
                     $.dialog({
                            title: false,
                            content: "提交成功",
                            time: 5000
                        });
                     $(".d-close").hide();
                     window.location=_ctxPath+'/returnOrder/returnOrder-myReturnOrders.htm';
                    },
                dataType:'json'
            });
          }
        function cancleAdd(){
            $.dialog.get('Companydialog').close();
        }
        function getNoPassReason(orderReturnItemId){
        $.ajax({
            type: 'POST',
            url: _ctxPath+'/returnOrder/returnOrder-getNOpassReason.htm',
            data: {
                'orderReturnItem.orderReturnItemId':orderReturnItemId
            },
            success:function(data){
                setDalogContent(data.info);
                //$.dialog.get('Companydialog').content(data.info);
                },
            dataType:'json'
            });
        }
        function shenSu(){
            var shenSu = '亲爱的用户，如果您对处理有异议,<br/>您可以通过 <a class=\"cor-orange\" href=\"www.xlbuy365.com\" target=\"_blank\">www.xlbuy365.com</a> 网客服进行申诉。<br/>申诉电话：<span class=\"cor-orange\">${_tel}</span>';
            setDalogContent(shenSu);
        }
        function setDalogContent(data){
            $.dialog({
                title: false,
                content: data,
                time:5000
                /*手动关闭申述弹出代码
                fixed: true,
              	cancel: true,
              	cancelValue:"关闭"*/
            });
          $(".d-close").hide();
        }
    </script>
</body>
</html>