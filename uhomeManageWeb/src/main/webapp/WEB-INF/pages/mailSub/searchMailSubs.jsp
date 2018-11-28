<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<title>站内信</title>
    <link href="${ _cssPath}/common.css" rel="stylesheet" />
    <link href="${ _cssPath}/pages/brandManagement.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
    <script src="${_jsPath }/plugin/plugin.js"  language="javascript"></script>
    <script src="${_jsPath }/pages/index.js"></script>
<script type="text/javascript">
</script>
</head>
<body>
 <div class="body m-w980p">
        <div class="examine m-mt10p">
        </div>
         <!--start 站内信  table-->
        <div class="m-mt10p business-management">
            <table class="tab-control" id="tab">
                <thead>
                    <tr>
                        <th width="12%">邮箱地址</th>
                        <th width="20%">订阅活动</th>
                        <th width="10%">订阅品牌</th>
                        <th width="10%">订阅状态</th>
                        <th width="10%">操作</th>

                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${mailSubPage.result}" var="mailSub" varStatus="status">
					<tr class="list-tr">
						<td >${mailSub.email}</td>
						<td >${mailSub.type}</td>
						<td >${mailSub.brandNames}</td>
						<td ><c:if test="${mailSub.status==0}">
								取消
							</c:if>
							<c:if test="${mailSub.status==1}">
								开启
							</c:if>
						</td>
						<td >
							<c:if test="${mailSub.status==0}">
								<a href="${_ctxPath}/admin/mailsub/mailsub-updateStatus.htm?mailSubscribe.mailSubscribeId=${mailSub.mailSubscribeId}&mailSubscribe.status=1">开启</a>
							</c:if>
							<c:if test="${mailSub.status==1}">
								<a href="${_ctxPath}/admin/mailsub/mailsub-updateStatus.htm?mailSubscribe.mailSubscribeId=${mailSub.mailSubscribeId}&mailSubscribe.status=0">取消</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
                </tbody>
            </table>
        </div>
       <!--end 站内信table-->
       <div class="table-bm-wrap cf">
		<!--S 右功能区-->
		<div class="fn-right">
			<!--S 分页-->
			<div class="page-navigation">
				<c:if test="${not empty suggestPage.result}">
					<wms:commPage page="${suggestPage}" beanName="suggestPage"></wms:commPage>
				</c:if>
			</div>
			<!--E 分页-->
		</div>
		<!--E 右功能区-->
	</div>
 </div>
	
</body>
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</html>