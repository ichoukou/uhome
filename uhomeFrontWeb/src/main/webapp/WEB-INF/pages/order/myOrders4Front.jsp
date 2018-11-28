<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html>
<head>
    <meta charset="utf-8"/>
    <title>我的订单-个人中心-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/order.css?d=${_resVerion}"/>
</head>
<body>
	<jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include>
    <div class="m-w960p cf">
    	 <!--面包屑-->
        <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a><a href="${_domain }/user/orders.htm">个人中心 ></a>我的订单</div>
        <!--面包屑 end-->
		<jsp:include page="/WEB-INF/pages/include/left.jsp"></jsp:include>
        <div class="inf-detail inf-mine m-mt20p">
            <form method="post" action="${_ctxPath}/order/order-myOrders.htm" id="searchForm">
            <div class="inf-title fon-big">我的订单</div>
            <div class="ord-select cf">
                <div class="ord-search">
	               	<input class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="" type="text" name="orderPage.params.beginTime" value="${orderPage.params.beginTime}" style="width:160px">
	                <label class="date-to">至</label>
	                <input class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="" type="text" name="orderPage.params.endTime" value="${orderPage.params.endTime}" style="width:160px">
                </div>
                <input type='submit' class="ord-submit" value='查询'>
                <input type="hidden" id="status" name="orderPage.params.status" value="${orderPage.params.status}" /> 
            </div>
            </form>
            <div class="order-tab">
                <div class="tab-gn">
                    <ul class="tab-gnul">
                   	<c:forEach items="${status}" var="s" varStatus="varS">
                   	   <%--不显示全部退货 --%>
                   	   <c:if test="${s < 7}">
                       <li value="${s}" <c:if test="${orderPage.params.status==varS.index+1}"> class="cur" </c:if>>
	                       <a>
	                       <spring:message code="order.status.${s}"/> 
	                       </a>
                       </li>
                       </c:if>
                    </c:forEach>
                    </ul>
                    <c:forEach items="${orderPage.result}" var="order" >
                    <%-- 没有使用优惠券的订单 --%>
                    <div class="order-item">
                    	<table>
                    		<thead>
                    			<tr>
			                        <th width="120"><label><!-- <input type="checkbox" class="ord-checkbox" data-value=""> --> 订单号</label></th>
			                        <th>订单商品</th>
			                        <th width="78">收货人</th>
			                        <th width="88">订单金额</th>
			                        <th width="73">下单时间</th>
			                        <th width="68">操作</th>
                    			</tr>
                    		</thead>
                    		<tbody>
                                 <tr>
                                     <td valign="top">
                                      <span>
                                      <label>
                                      <c:if test="${orderPage.params.status == 1 || orderPage.params.status == 3}">
                                      <input type="checkbox" class="ord-checkbox"  status-value="${order.status}" data-value="${order.orderId}">
                                      </c:if>${order.orderNo}</label>
                                      </span>
                                     </td>
                                     <td class="ord-sales">
                                         <ul>
                                             <c:forEach items="${order.items}" var="item">
                                             <li class="cf">
                                                 <div class="pic">
                                                     <img src="<zx:thumbImage originalPath='${item.productSku.product.defaultImage}' imageType='t12'></zx:thumbImage>" width="48" height="48"/>
                                                 </div>
                                                 <div class="info">
                                                     <div class="tit"><a href="${_ctxPath}/item-${item.productSku.product.productId}.htm" class="cor-orange">${item.productName}</a></div>
                                                     <div>
                                                         <c:forEach items="${item.productSku.productSkuOptionValues}" var="option" >
                                                             <label>${option.skuOptionValue.skuOption.skuOptionName}:
                                                             <c:choose>
                                                                 <c:when test="${not empty option.overrideSkuOptionValue}">
                                                                     ${option.overrideSkuOptionValue}
                                                                 </c:when>
                                                                 <c:otherwise>
                                                                     ${option.skuOptionValue.skuOptionValue}
                                                                 </c:otherwise>
                                                             </c:choose>
                                                             </label>
                                                         </c:forEach>
                                                         <label>数量：${item.num}</label>
                                                     </div>
                                                 </div>
                                             </li>
                                             </c:forEach>
                                         </ul>
                                     </td>
                                     <td valign="top">${order.address.receiverName}</td>
                                     <td valign="top">
                                         <div class="cor-red">¥${order.payment.paymentAmount}元</div>
                                         <div class="cor-red">(含运费${order.orderPostage.postage}元)</div>
                                         <c:if test="${order.status >= 2 && order.status != 5}">
                                             <div>获得${uhome:computerPoint(order.payment.paymentAmount)}积分</div>
                                         </c:if>
                                     </td>
                                     <td valign="top">
										<fmt:formatDate value="${order.createTime}" type="date" /><br/>
                                       	<fmt:formatDate value="${order.createTime}" type="time" />
									 </td>
                                     <td valign="top">
                                         <div><a target="_blank" href="${_ctxPath}/order/order-getOrder.htm?orderHead.orderId=${order.orderId}" class="high-light">查看</a></div>
                                         <c:if test="${order.status == 1}">
                                         	<div><a href="${_ctxPath}/order/order-payMyOrder.htm?ordIds=${order.orderId}" class="normal" target="_blank">立即付款</a></div>
                                         	<div><a href="javascript:;" class="normal cancel" value="${order.orderId}">取消</a></div>
                                         </c:if>
                                         <c:if test="${order.status == 3}">
                                         	<div><a href="javascript:;" class="normal receive" value="${order.orderId}">确认收货</a></div>
                                         </c:if>
                                         <c:if test="${order.allowReturn == 1}">
                                         	<div><a href="${_ctxPath}/returnOrder/returnOrder-myOrders.htm?tuihuoOrderIds=${order.orderId}" class="normal">申请退货</a></div>
                                         </c:if>
                                         <c:if test="${order.status == 5}">
                                         	<!-- 取消 -->
                                         	<div><a href="javascript:;" class="normal delete" value="${order.orderId}">删除</a></div>
                                         </c:if>
                                     </td>
                                 </tr>
                    		</tbody>
                    	</table>
                    </div>
                    <%-- 没有使用优惠券的订单 end--%>
                    </c:forEach>
                </div>
            </div>
            <%--如果选项卡是待付款或者已发货显示 --%>
            <c:if test="${not empty orderPage.result}">
            <c:if test="${orderPage.params.status == 1 || orderPage.params.status == 3}">
            <div class="bm-control">
                <ul>
                    <li><label><input type="checkbox" class="ord-checked-all" /> 全选</label></li>
                    <c:if test="${orderPage.params.status == 1}">
                    <li><a href="javascript:;" id="batch-pay">合并付款</a></li>
                    </c:if>
                    <c:if test="${orderPage.params.status == 3}">
                    <li><a href="javascript:;" id="batch-receive">批量确认收货</a></li>
                    </c:if>
                </ul>
            </div>
            </c:if>
	     	<wms:commPage page="${orderPage}" beanName="orderPage"></wms:commPage>
		    </c:if>
        </div>
    </div>

	<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
	<script type="text/javascript" src="${_jsPath }/plugin/calendar/WdatePicker.js"></script>
	<script type="text/javascript" src="${_jsPath }/pages/myOrders.js?d=${_resVerion}"></script>
    <script type="text/javascript">
    	//使用优惠券付款连接
    	$("#corOrangePayment").on("click",function(event){
    		var $this = $(this);
    		var orderIds = $this.attr("value").split("|");
    		var oIds = $.param({'ordIds':orderIds},true);
    		window.open(_ctxPath +'/order/order-payMyOrder.htm?' + oIds,"_blank");
    		return false;
    	});
    	//取消使用优惠券的订单
    	$("#corOrangeCancel").on("click",function(event){
    		 var $this = $(this);
    		 var orderIds = $this.attr("value");
 	    	 $.dialog({
                 title: false,
                 padding: "15px 20px 8px",
                 content: "<span style='color:#C00;'>确定取消该订单吗？</span>",
                 follow: $this.get(0),
                 okValue: "确定",
                 ok: function(){
                	 //取消该订单
                     $.ajax({
                         type : 'POST',
                         url  : '${_ctxPath }/order/order-cancelOrders.htm',
                         data :  {"orderIds":orderIds},
                         success:function(html){
                        	 if(html.code == "true"){
                        		 //window.location.reload();
                        		 $("#searchForm").submit();
                        	 }else{
                        		 alert(html.info);
                        	 }
                         }
                     });
                 },
                 cancelValue: "取消",
                 cancel: true
             });
 	    	 $(".d-close").hide();
			return false;
    	});
    	
 	    $(".cancel").on("click",function(event){
 	    	 var $this = $(this);
 	    	 $.dialog({
                 title: false,
                 padding: "15px 20px 8px",
                 content: "<span style='color:#C00;'>确定取消该订单吗？</span>",
                 follow: $this.get(0),
                 okValue: "确定",
                 ok: function(){
                	 //取消该订单
                     $.ajax({
                         type : 'POST',
                         url  : '${_ctxPath }/order/order-cancelOrder.htm',
                         data :  {"orderHead.orderId" : $this.attr("value")},
                         success:function(html){
                        	 if(html.code == "true"){
                        		 //window.location.reload();
                        		 $("#searchForm").submit();
                        	 }else{
                        		 alert(html.info);
                        	 }
                         }
                     });
                 },
                 cancelValue: "取消",
                 cancel: true
             });
 	    	 $(".d-close").hide();
			return false;
		});
 	    
 	   $(".delete").on("click",function(event){
	    	 var $this = $(this);
	    	 $.dialog({
               title: false,
               padding: "15px 20px 8px",
               content: "<span style='color:#C00;'>确定删除该订单吗？</span>",
               follow: $this.get(0),
               okValue: "确定",
               ok: function(){
              	 //取消该订单
                   $.ajax({
                       type : 'POST',
                       url  : '${_ctxPath }/order/order-deleteOrder.htm',
                       data :  {"orderHead.orderId" : $this.attr("value")},
                       success:function(html){
                      	 if(html.code == "true"){
                      		 //window.location.reload();
                      		 $("#searchForm").submit();
                      	 }else{
                      		 alert(html.info);
                      	 }
                       }
                   });
               },
               cancelValue: "取消",
               cancel: true
           });
	    	 $(".d-close").hide();
			return false;
		}); 
 	    
 	    $(".receive").on("click",function(event){
	    	 var $this = $(this);
	    	 $.dialog({
                title: false,
                padding: "15px 20px 8px",
                content: "<span style='color:#C00;'>确认已经收到该商品？</span>",
                follow: $this.get(0),
                okValue: "确定",
                ok: function(){
               	 //收到该订单
                    $.ajax({
                        type : 'POST',
                        url  : '${_ctxPath }/order/order-receiveOrder.htm',
                        data :  {"ordIds" : $this.attr("value")},
                        success:function(html){
                       	 if(html.code == "true"){
                       		 //window.location.reload();
                       		$("#searchForm").submit();
                       	 }else{
                       		 alert(html.info);
                       	 }
                        }
                    });
                },
                cancelValue: "取消",
                cancel: true
            });
	    	 $(".d-close").hide();
			return false;
		}); 
 	    //批量收货
 	    $("#batch-receive").on("click",function(event){
            var $this = $(this);
            if($('.ord-checkbox:checked').length > 0){
                var chk_value =new Array();
                $('.ord-checkbox:checked').each(function(){
                	var statusValue = $(this).attr("status-value");
                	if(statusValue=='3'){//只添加 已发货 的订单
                        chk_value.push($(this).attr("data-value"));
                    	}
                });
                if(chk_value.length==0){
                	return;
                }
                var oIds = $.param({'ordIds':chk_value},true);
                $.dialog({
                    title: false,
                    padding: "15px 20px 8px",
                    content: "<span style='color:#C00;'>确认已经收到这些商品？</span>",
                    follow: $this.get(0),
                    okValue: "确定",
                    ok: function(){
                    //收到该订单
                    $.ajax({
                    type : 'POST',
                    url  : '${_ctxPath }/order/order-receiveOrder.htm',
                    data :  oIds,
                    success:function(html){
                        if(html.code == "true"){
                            //window.location.reload();
                        	$("#searchForm").submit();
                        }else{
                        alert(html.info);
                        }
                    }
                    });
                    },
                    cancelValue: "取消",
                    cancel: true
                });
                $(".d-close").hide();
                return false;
            }else{
                $.dialog({
                title: false,
                padding: "15px 20px",
                content: "<span style='color:#C00;'>请选择确认收货的商品，可以选择多个</span>",
                follow: $this.get(0),
                okValue: "确定",
                time: 1500
                });
                $(".d-close").hide();
            }
		}); 
 	    //批量付款
 	    $("#batch-pay").on("click",function(event){
 	    	var $this = $(this);
            if($('.ord-checkbox:checked').length > 0){
                var chk_value =new Array();
                $('.ord-checkbox:checked').each(function(){
                	var statusValue = $(this).attr("status-value");
                	if(statusValue=='1'){//只添加 待付款 的订单
                    chk_value.push($(this).attr("data-value"));
                	}
                });
                if(chk_value.length==0){
                	return;
                }
                var oIds = $.param({'ordIds':chk_value},true);
                $.dialog({
                    title: false,
                    padding: "15px 20px 8px",
                    content: "<span style='color:#C00;'>确认选择订单合并付款吗？</span>",
                    follow: $this.get(0),
                    okValue: "确定",
                    ok: function(){
                    //合并支付订单
                    //window.location.href= _ctxPath +'/order/order-payMyOrder.htm?' + oIds;
                    window.open(_ctxPath +'/order/order-payMyOrder.htm?' + oIds,"_blank");
                    },
                    cancelValue: "取消",
                    cancel: true
                });
                $(".d-close").hide();
                return false;
            }else{
                $.dialog({
                    title: false,
                    padding: "15px 20px",
                    content: "<span style='color:#C00;'>请选择商品付款，可以选择多个</span>",
                    follow: $this.get(0),
                    okValue: "确定",
                    time: 1500
                });
                $(".d-close").hide();
             }
		}); 
 	    
 	    //为每个tbl绑定事件
 	    $(".tab-gnul li").on("click",function(event){
 	    	$this = $(this);
 	    	if ($this.val() == 0 || $this.val() == 1) {
 	    		/* 默认是待付款 */
				$('#status').val(1);
				$('#searchForm').attr("action","${_ctxPath}/user/orders.htm");
			} else {
				$('#status').val($this.val());
				$('#searchForm').attr("action","${_ctxPath}/order/order-myOrders.htm");
			}
			$('#searchForm').submit();
 	    });
 	    
    </script>
</body>
</html>