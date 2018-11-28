<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
  <meta charset="utf-8"/>
    <title>积分管理-个人中心-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/integral.css?d=${_resVerion}"/>
</head>
<body>
<jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include>
<div class="m-w960p cf">
		<!--面包屑-->
        <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a><a href="${_domain }/user/orders.htm">个人中心 ></a>积分管理</div>
        <!--面包屑 end-->
        <jsp:include page="/WEB-INF/pages/include/left.jsp"></jsp:include>
        <div class="inf-detail m-mt20p">
            <div class="inf-title  cf">
                <div class="fon-big m-fl">积分管理</div>
            </div>
            <div class="user-t">当前积分</div>
            <div class="user-bg">
            	<i></i>
            	<div class="int-argement">
                <p>您目前的会员积分：<b class="c-orange">${point.total }</b>    可用积分：<b class="c-orange">${point.total }</b><br/>
                    （1）订单付款后（订单状态变为“待发货”），可收到相应积分；<br/>
                    （2）如果发生退货的情况，将扣除所退商品的相应积分；如果该商品产生的双倍积分、首单消费积分，也会因为退货而全部扣回。</p>
            	</div>
            </div>
            
            <div class="user-tab">
                <table>
                    <thead>
                    <tr>
                        <th width="30%">所获积分</th>
                        <th width="30%">积分来源</th>
                        <th width="40%">获取时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${point.pointDetails }" var="pointDetail">
                    <tr>
                        <td>${pointDetail.pointSource==0?'-':''}${pointDetail.point }</td>
                        <td><spring:message code="point.pointSource.${pointDetail.pointSource}"/></td>
                        <td class="int-tdtime">
                            <span><fmt:formatDate value="${pointDetail.createTime }" pattern="yyyy-MM-dd HH:mm:ss"/></span>
                        </td>
                    </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
    <script type="text/javascript">
	$(function(){
		$("#pointRule").click(function(){
			$.ajax({
	    		type: 'GET',
				url: '${_ctxPath}/user/pointRules.htm',
				success: function(html){
					$.dialog({
			            title: false,
                        lock: true,
			            content: html,
			            cancelValue: "关闭",
			            cancel: true
			        });
                    $(".d-close").hide();
			    },
		        error: function(data){
		        	alert("error:"+data.responseText);
		        }
			});
		});
	});
	</script>
</body>
</html>