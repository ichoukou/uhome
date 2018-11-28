<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>公告信息 — ${_webSiteName}。</title>
	<meta name="keywords" content="品牌折扣，限时特卖，品牌打折网" >
    <meta name="description" content="新龙直销主打品牌折扣，限时打折、特卖、低至1-7折。商城数万种品牌折扣商品，每天限时打折、限时特卖，所有商品100%正品。" >
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/notice.css?d=${_resVerion}" media="all">
    <script type="text/javascript" >
		function getDetail(noticeId,e) {
			var total = ${noticePage.total};
			var currentPage = ${noticePage.currentPage};
			var url = '${_ctxPath }/notice/detail-' + noticeId + '-' + total + '-' + currentPage + '.htm';
			window.location.href = url;
			//阻止默认浏览器动作(W3C) 
			if ( e && e.preventDefault ) 
				e.preventDefault(); 
			//IE中阻止函数器默认动作的方式 
			else
				window.event.returnValue = false; 
			return false; 
		}
    </script>
</head>
<body>
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
	<div class="m-w960p">
		<!--面包屑-->
        <div class="crumbs"><a href="${_ctxPath }">${_webSiteName } ></a>网站公告</div>
        <!--面包屑 end-->
        <div class="notice-main cf">
	        <div class="notice-lt">
	        	<h3>公告区</h3>
	        	<div class="sideNav">
	        		<ul>
	        			<li><a href="${_ctxPath }/notice/index-1.htm" <c:if test="${noticePage.params.type == '1'}">class="cur"</c:if>>活动资讯</a></li>
	        			<li><a href="${_ctxPath }/notice/index-2.htm" <c:if test="${noticePage.params.type == '2'}">class="cur"</c:if>>网站公告</a></li>
	        		</ul>
	        	</div>
	        </div>
	        <div class="notice-rt">
	        	<ul>
		        	<c:forEach items="${noticePage.result}" var="noticePage" varStatus="status">
						<li><span class="date"><fmt:formatDate value='${noticePage.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></span><a href="javascript:;" onclick="getDetail(${noticePage.noticeId},this)">${noticePage.title}</a></li>
					</c:forEach>
	        	</ul>
	        </div>
        </div>
        <div class="navigation-wp m-mt10p">
           	<c:if test="${not empty noticePage.result}">
          		<wms:uhomeFrontPage urlEnd=".htm" urlStart="${_ctxPath }/notice/index-${noticePage.params.type}-${noticePage.total}-" page="${noticePage}"></wms:uhomeFrontPage>
         	</c:if>
        </div>
	</div>
	<%@include file="/WEB-INF/pages/include/foot.jsp"%>
</body>
</html>