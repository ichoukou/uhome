<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>秒杀专场_${_webSiteName}${_domainName}</title>
	<meta name="keywords" content="${_webSiteName}，${_domainName}，秒杀">
	<meta name="description" content="${_webSiteName}${_domainName}_是一个以天天特价、品牌折扣、限时抢购为特色的网上特卖商城，全球各大品牌官方授权入驻，全国配送，100%正品保障，超值购物，首选${_webSiteName}。">
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/spike.css?d=${_resVerion}" media="all">
</head>
<body>
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
    <div class="m-w960p">
        <!--面包屑-->
        <div class="crumbs"><a href="${_ctxPath }/default.htm">${_webSiteName} ></a>秒杀</div>
        <!--面包屑 end-->
        <c:if test="${not empty todaySecKills }">
        <div class="spike-today item-wrap m-mt5p">
            <div class="hd cf">
                <h2>上午<span>10:00</span>整点秒杀</h2>
                <div class="data-frame"><input type="text" value="" data-default="输入您的邮箱订阅最新优惠活动"><a href="javascript:" id="postSubscriptionEmail">订阅</a></div>
            </div>
            <div class="bd">
            <div class="spi-tip">今日秒杀</div>
            <c:forEach items="${todaySecKills }" var="today">
                <c:forEach items="${today.products }" var="product" begin="0" end="0">
                <div class="item-box cf">
                    <div class="pic">
                        <div class="big-pic"><img width="350" height="350" src="<zx:thumbImage originalPath='${product.defaultImage }' imageType='t8'></zx:thumbImage>" alt="${product.name}"></div>
                        <div class="smll-pic">
                            <c:forEach items="${product.imageList }" var="img" begin="1" end="2">
                            <img alt="${product.name }" width="175" height="175" src="<zx:thumbImage originalPath='${img }' imageType='t20'></zx:thumbImage>" />
                            </c:forEach>
                        </div>
                    </div>
                    <div class="main">
                        <dl>
                            <dt><a target="_blank" href="${_ctxPath }/seckill-${today.planId}.htm">${product.name }</a></dt>
                            <dd class="price">¥ ${product.secKillPrice }</dd>
                            <dd class="list">
                                <table width="100%">
                                    <thead>
                                    <tr>
                                        <td width="120">原价</td>
                                        <td width="100">折扣</td>
                                        <td>运费</td>
                                    </tr>
                                    </thead>
                                    <tr>
                                        <td><span class="g-org">¥${product.marketPrice }</span></td>
                                        <td><span class="g-val">
                                        <c:set value="${(product.secKillPrice / product.marketPrice)*10 }" var="secKillPrice"></c:set>
		                                <c:if test="${secKillPrice < 0.1}">0.1</c:if>
		                                <c:if test="${secKillPrice > 0.1}">
		                                	<!-- 保留1位小数 -->
		                                	<fmt:formatNumber value="${secKillPrice }" pattern="#.#" minFractionDigits="1"></fmt:formatNumber>
		                                </c:if>
                                		折
                                        </span></td>
                                        <td>¥0.00（全场免邮）</td>
                                    </tr>
                                </table>
                            </dd>
                        </dl>
                        <div class="bm">
                            <div class="btn cf">
							<c:set var="nowDate" value="<%=System.currentTimeMillis()%>"></c:set>
		                    <c:if test="${today.endTime.time-nowDate > 0 and product.secKillInventoryCount > 0 or(today.endTime.time-nowDate > 0 and product.secKillInventoryCount == 0)}">
		                       <a target="_blank" class="btn-b" href="${_ctxPath }/seckill-${today.planId}.htm"><span>去看看</span></a>
		                  	</c:if>
		                  	<c:if test="${(nowDate - today.endTime.time > _secKillTime and product.secKillInventoryCount <= 0) or (nowDate - today.endTime.time > _secKillTime)}">
		                       <a href="javascript:;" class="btn-e"><span>秒杀结束</span></a>
		                  	</c:if>
		                  	<c:if test="${today.endTime.time-nowDate < 0 and nowDate - today.endTime.time <= _secKillTime}">
		                  		<c:if test="${product.secKillInventoryCount > 0}">
                  					<a target="_blank" href="${_ctxPath }/seckill-${today.planId}.htm" class="btn-b"><span>正在秒杀</span></a>
                  				</c:if>
                  				<c:if test="${product.secKillInventoryCount <= 0}">
                  					<a href="javascript:;" class="btn-e"><span>秒杀结束</span></a>
                  				</c:if> 
                  			</c:if>
                                <span class="countdown" data-config="{
                            'endTime':'<fmt:formatDate value="${today.endTime }" type="both"/>',
                            'nowTime':'<%=System.currentTimeMillis()/1000 %>',
                            'stopTips':'<span><!-- 活动结束谢谢参与 --></span>',
                            'timeStampTow':true
                          }"></span>
                            </div>
                            <div class="txt">
                            <c:choose>
                               <c:when test="${fn:length(product.secKillDescribe) > 100}">  
							    	<c:out value="${fn:substring(product.secKillDescribe, 0, 100)}" />  
							   </c:when>  
							  <c:otherwise>  
							    ${product.secKillDescribe }
							  </c:otherwise>  
							</c:choose>
                            </div>
                            <div class="share-wrap cf">
                                <span class="m-fl">分享到：</span>
                               <!-- Baidu Button BEGIN -->
								<div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare">
									<a class="bds_tsina" href="#" title="新浪微博"></a>
									<a class="bds_tqq" href="#" title="腾讯微博"></a>
									<a class="bds_qzone" href="#" title="QQ空间"></a>
									<a class="bds_mogujie" href="#" title="蘑菇街"></a>
									<a class="bds_meilishuo" href="#" title="美丽说"></a>
									<a class="bds_douban" href="#" title="豆瓣网"></a>
									<a class="bds_kaixin001" href="#" title="开心网"></a>
									<a class="bds_renren" href="#" title="人人网"></a>
									<a class="bds_taobao" href="#" title="淘江湖"></a>
								</div>
								<script type="text/javascript" id="bdshare_js" data="type=tools&amp;mini=1&amp;uid=0" ></script>
								<script type="text/javascript" id="bdshell_js"></script>
								<script type="text/javascript">
									document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
								</script>
								<!-- Baidu Button END -->
                            </div>
                        </div>
                    </div>
                </div>
            	</c:forEach>
            </c:forEach>
            </div>
        </div>
        </c:if>
        
        <c:if test="${not empty tomorrowSecKills }">
        <div class="spike-upcoming item-wrap" name="tomorrowSecKill" id="tomorrowSecKill">
            <!-- 
            <div class="hd cf">
                <h2>上午<span>10:00</span>整点秒杀</h2>
            </div>
             -->
            <div class="bd">
                <div class="spi-tip">明日秒杀</div>
                <c:forEach items="${tomorrowSecKills }" var="tomorrow">
                <c:forEach items="${tomorrow.products }" var="product">
                <div class="item-box cf">
                    <div class="pic">
                        <div class="big-pic"><img width="350" height="350" src="<zx:thumbImage originalPath='${product.defaultImage }' imageType='t8'></zx:thumbImage>" alt="${product.name}"></div>
                        <div class="smll-pic">
                            <c:forEach items="${product.imageList }" var="img" begin="1" end="2">
                            <img alt="${product.name }" width="175" height="175" src="<zx:thumbImage originalPath='${img }' imageType='t20'></zx:thumbImage>" />
                            </c:forEach>
                        </div>
                    </div>
                    <div class="main">
                        <dl>
                            <dt><a target="_blank" href="${_ctxPath }/seckill-${tomorrow.planId}.htm">${product.name }</a></dt>
                            <dd class="price">¥ ${product.secKillPrice }</dd>
                            <dd class="list">
                                <table width="100%">
                                    <thead>
                                    <tr>
                                        <td width="120">原价：</td>
                                        <td width="100">折扣：</td>
                                        <td>运费：</td>
                                    </tr>
                                    </thead>
                                    <tr>
                                        <td><span class="g-org">¥${product.marketPrice }</span></td>
                                        <td><span class="g-val">
                                        	<c:set value="${(product.secKillPrice / product.marketPrice)*10 }" var="tSecKillPrice"></c:set>
			                                <c:if test="${tSecKillPrice < 0.1}">0.1</c:if>
			                                <c:if test="${tSecKillPrice > 0.1}">
			                                	<!-- 保留1位小数 -->
			                                	<fmt:formatNumber value="${tSecKillPrice }" pattern="#.#" minFractionDigits="1"></fmt:formatNumber>
			                                </c:if>
                                        	折</span></td>
                                        <td>¥0.00（全场免邮）</td>
                                    </tr>
                                </table>
                            </dd>
                        </dl>
                        <div class="bm">
                            <div class="btn cf">
                                <a target="_blank" class="btn-b" href="${_ctxPath }/seckill-${tomorrow.planId}.htm"><span>去看看</span></a>
                                <span class="countdown" data-config="{
                            'endTime':'<fmt:formatDate value="${tomorrow.endTime }" type="both"/>',
                            'nowTime':'<%=System.currentTimeMillis()/1000 %>',
                            'stopTips':'<span>活动结束谢谢参与！</span>',
                            'timeStampTow':true
                          }"></span>
                            </div>
                            <div class="txt">
                            <c:choose>
                               <c:when test="${fn:length(product.secKillDescribe) > 100}">  
							    	<c:out value="${fn:substring(product.secKillDescribe, 0, 100)}" />  
							   </c:when>  
							  <c:otherwise>  
							    ${product.secKillDescribe }
							  </c:otherwise>  
							</c:choose>
                            </div>
                            <div class="share-wrap cf">
                                <span class="m-fl">分享到：</span>
                                <!-- Baidu Button BEGIN -->
								<div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare">
									<a class="bds_tsina" href="#" title="新浪微博"></a>
									<a class="bds_tqq" href="#" title="腾讯微博"></a>
									<a class="bds_qzone" href="#" title="QQ空间"></a>
									<a class="bds_mogujie" href="#" title="蘑菇街"></a>
									<a class="bds_meilishuo" href="#" title="美丽说"></a>
									<a class="bds_douban" href="#" title="豆瓣网"></a>
									<a class="bds_kaixin001" href="#" title="开心网"></a>
									<a class="bds_renren" href="#" title="人人网"></a>
									<a class="bds_taobao" href="#" title="淘江湖"></a>
								</div>
								<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=0" ></script>
								<script type="text/javascript" id="bdshell_js"></script>
								<script type="text/javascript">
									document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
								</script>
								<!-- Baidu Button END -->
                            </div>
                        </div>
                    </div>
                </div>
               </c:forEach> 
            </c:forEach>
            </div>
        </div>
    	</c:if>
    	<c:if test="${empty tomorrowSecKills and empty todaySecKills }">
            <div class="no-spike">抱歉暂时没有秒杀内容!</div>
        </c:if>
    </div>
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
    <script type="text/javascript" src="${_jsPath }/pages/spike.js?d=${_resVerion}"></script>
    <script type="text/javascript">
    var subEmail = $("#postSubscriptionEmail");
    subEmail.click(function(){
    	//获取用户的邮箱
    	//var txt = $(".hd.cf input[type='text']").val();
    	var txt = subEmail.prev().val();
    	if(txt == subEmail.prev().attr("data-default")){
    		return;
    	}
    	if(!validtorEmail(txt)){
    		return;
    	}
    	$.ajax({
    		type: 'POST',
			url: '${_ctxPath}/subscriptionEmail.htm',
			async: false,
			data: {
				'mailSubscribe.type':<%=MailSubscribe.TYPE_SEC_KILL%>,
				'mailSubscribe.status':<%=MailSubscribe.STATUS_SUBSCRIBE%>,
				'mailSubscribe.email':txt
			},
			dataType:'json',
			success: function(data){
				popupDialog(data.info,"10000");
				subEmail.prev().attr("value","");
		        },
	        error: function(data){
	        	popupDialog("error:"+data.responseText,"10001");
	        }
    	});
    });
	//  验证邮箱TODO
    function validtorEmail(email){
    	var reg = /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
    	if(reg.test(email)){
    		return true;
    	}
    	popupDialog("邮箱格式不正确!","1003");
    	return false;
    }
    </script>
</body>
</html>