<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
<meta charset="utf-8" />
<title>帮助中心-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
	<link rel="stylesheet" type="text/css" href="${_cssPath}/pages/overview.css?d=${_resVerion}" media="all">
</head>
<body>
<jsp:include page="../include/head.jsp" flush="true" />
	<div class="m-w960p">
		<!--面包屑-->
		<div class="crumbs">
			<a href="${_domain }">${_webSiteName }></a>帮助中心 
		</div>
		<!--面包屑 end-->
		<div class="oview-wrap m-mt20p cf">
			<div class="left-nav">
				<c:forEach items="${helpCategorys}" var="helpCategory" varStatus="status">
						<dl>
				    		<dt>${helpCategory.name}</dt>
				    		<c:forEach items="${helpCategory.helpCategorys}" var="help1" varStatus="index">
								<dd>
									<c:if test="${help1.help.helpId==help.helpId}">
										<c:set var="helpName" value="${help1.name}"></c:set>
									</c:if>
									<a href="javascript:void(0)" onclick="getContent(this,${help1.help.helpId})">${help1.name}</a>
                                    <i></i>
								</dd>
							</c:forEach>
				   		</dl>
				</c:forEach>
			</div>
			<div class="content">
				<div class="hd">
					<h2 id="title">${helpName}</h2>
				</div>
				<div class="bd cf" id="content">${help.content }</div>
			</div>
		</div>
	</div>
	<!-- foot -->
	<%@include file="/WEB-INF/pages/include/foot.jsp"%>
    <script type="text/javascript">
        var getContent=function(a, id){
            $.ajax({
            url : "${_ctxPath}/help/getContentByHelpId.htm",
            type : "POST",
            data : {
            	'help.helpId' : id
            },
            dataType : "json",
            success : function(data) {
	            $('#title').text($(a).text());
                $(".oview-wrap").find("dd").removeClass("cur");
                $(a).parent().addClass("cur");
	            $('#content').html(data.info);
            },
            error : function(data) {
            	console.log(data);
            }
            });
        }
    </script>
</body>
</html>