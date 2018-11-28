<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>${_webSiteName}（${_domainName}）_${_webSiteName}是一家天天特价的网上特卖商城，品牌折扣，正品保障，限时抢购。</title>
    <meta name="keywords" content="${_webSiteName}，${_domainName}，天天特价，正品，特卖，限时抢购，品牌折扣" >
    <meta name="description" content="${_webSiteName}${_domainName}_是一个以天天特价、品牌折扣、限时抢购为特色的网上特卖商城，全球各大品牌官方授权入驻，全国配送，100%正品保障，超值购物，首选${_webSiteName}。" >
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath}/pages/email-back.css?d=${_resVerion}" media="all">
</head>
<body>
    <!--头部-->
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
    <!--头部 end-->
    <!-- 中间部分 -->
    <div class="m-w960p">
        <!--面包屑-->
        <div class="crumbs"><a href="${_ctxPath }">${_webSiteName} ></a>邮件退订</div>
        <!--面包屑 end-->
         <div class="unsubscribe-email">
            <div class="inner">
           		<c:choose>
           		<c:when test="${message.code eq 'false' }">
					<span id="resultInfo" class="show-result">${message.info }</span>
           		</c:when>
           		<c:otherwise>
	           		<p id="unsubsTxt"><a id="unsubscribe" href="javascript:;" class="btn-a"><span>取消订阅</span></a><spring:message code="mailSubscribe.type.${originalType }"/></p>
	           		<span id="resultInfo" class="show-result"></span>
	           		<input type="hidden" value="${email }" id="email"/>
	           		<input type="hidden" value="${type }" id="type"/>
	           		 <script type="text/javascript">
				    	$('#unsubscribe').on('click',function(){
				    		$.ajax({
				    			type:'POST',
				    			url: "${_ctxPath }/unsubscribe.htm",
				    			data: "email=" + $("#email").val() + "&type=" + $("#type").val(),
				    			success:function(msg){
				    				$("#unsubsTxt").hide();
				   					$("#resultInfo").text(msg.info);
				    			}
				    		});
				    	});
				    </script>
           		</c:otherwise>
           		</c:choose>
            </div>
         </div>
    </div>
    <!-- 中间部分 end-->
    <!--底部区块 -->
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
    <!--底部区块 end-->
</body>
</html>