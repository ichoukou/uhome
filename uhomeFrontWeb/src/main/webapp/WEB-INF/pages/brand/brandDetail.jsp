<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<cache:cache refresh="${_needRefresh}" key="seo-brand-${brand.brandId}" cron="${_seoRefreshCron}">
		<c:if test="${empty seoTitle }">
			<title>${brand.name }_${_webSiteName}${_domainName}商城</title>
		</c:if>
		<c:if test="${!empty seoTitle }">
			<title>${seoTitle}</title>
		</c:if>
		<c:if test="${empty seoKeyWords }">
			<meta name="keywords" content="${_webSiteName}，${_domainName},天天特价，正品，特卖，限时抢购，品牌折扣">
		</c:if>
		<c:if test="${!empty seoKeyWords }">
			<meta name="keywords" content="${seoKeyWords}">
		</c:if>
		<c:if test="${empty seoDescription }">
			<meta name="description" content="${_webSiteName}商城${brand.name }专场提供各种男女时尚品牌行货特卖，（${_domainName}）${brand.name }折扣，天天特价，属于公司正规品牌销售，限时抢购，100%正品保障。">
		</c:if>
		<c:if test="${!empty seoDescription}">
			<meta name="description" content="${uhome:cutString(seoDescription,80)}">
		</c:if>
	</cache:cache>
	
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/brand-intro.css?d=${_resVerion}" media="all">
</head>
<body>
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
    <div class="m-w960p">
        <!--面包屑-->
        <div class="crumbs"><a href="${_domain }">${_webSiteName } ></a>${brand.name }</div>
        <!--面包屑 end-->
        <div class="cf">
        	<cache:cache time="${_cacheSec}" refresh="${_needRefresh}" key="brand-${brand.brandId}">
	        <div class="intro-l">
	            <div class="box">
	               <div class="b1">
	                   <img src="<zx:thumbImage originalPath='${brand.logoImageUrl }' imageType='t19'></zx:thumbImage>" alt="${brand.name }" width="200" height="100">
	               </div>
	               <div class="brad-tit">${brand.englishName }  ${brand.name }</div>
	               <div class="b2">
	                   <p><span>所属国家：</span>${brand.country }</p>
	                   <p><span>创始时间：</span><fmt:formatDate value="${brand.foundationTime }" pattern="yyyy年MM月"/></p>
	                   <p><span>中文名：</span>${brand.name }</p>
	                   <p><span>英文名：</span>${brand.englishName }</p>
	               </div>
	            </div>
	        </div>
	    	</cache:cache>    
	        <div class="intro-r">
	    	<cache:cache time="${_cacheSec}" refresh="${_needRefresh}" key="barndDescribe-${brand.brandId}">
	            <div class="brand-sale">
	                <div class="inner cf">
	                    <div class="pic">
	                        <img src="<zx:thumbImage originalPath='${brand.brandImageUrl }' imageType='t3'></zx:thumbImage>" width="730" height="210" alt="${brand.name }">
	                    </div>
	                    <div class="main">
						    ${brand.describe }
	                    </div>
	                </div>
	            </div>
	    	</cache:cache>
	        </div>
    	</div>
    </div>
    <!--footer.jsp放置在这-->
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>
    <script type="text/javascript" src="${_jsPath }/pages/brand_intro.js?d=${_resVerion}"></script>
    <script type="text/javascript">
    var subEmail = $("#postSubscriptionEmail");
    subEmail.click(function(){
    	//获取用户的邮箱
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
			async: true,
			data: {
				'mailSubscribe.type':<%=MailSubscribe.TYPE_BRAND_SUBSCRIBE%>,
				'mailSubscribe.status':<%=MailSubscribe.STATUS_SUBSCRIBE%>,
				'mailSubscribe.email':txt,
				'mailSubscribe.brandId':${brand.brandId}
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
    	popupDialog("邮箱格式不正确!","1006");
    	return false;
    }
    </script>
</body>
</html>