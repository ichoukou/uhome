<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<%@page import="com.ytoxl.module.core.common.utils.SpringContextUtils"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.service.HelpService"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<cache:cache time="${_cacheSec}" refresh="${_needRefresh}" key="foot">
<%
HelpService helpService=(HelpService)(SpringContextUtils.getContext().getBean("helpServiceImpl"));
pageContext.setAttribute("helpCategorys",helpService.listHelpCategorys());
%>
<div class="footer">
    <div class="footer-info cf">
        <ul>
            <c:forEach items="${helpCategorys}" var="helpCategory" varStatus="status">
                <li <c:if test="${status.index==0 }">class="first"</c:if>>
                    <dl>
                    <dt>${helpCategory.name}</dt>
                        <c:forEach items="${helpCategory.helpCategorys}" var="help" varStatus="index">
                            <dd><a target="_blank" href="${_ctxPath}/help/help-${help.help.helpId }.htm" target="_blank">${help.name}</a></dd>
                        </c:forEach>
                    </dl>
                </li>
            </c:forEach>
            <li class="last">
                <dl>
                    <dt>客服热线</dt>
                    <dd>如果您遇到任何问题，欢迎拨打</dd>
                    <dd><span class="telephone">${_serviceTel }</span><br />${_serviceTime}</dd>
                </dl>
            </li>
        </ul>
    </div>
    <div class="copyright">
        <p>${_footCopyright}</p>
        <p>${_footAddress }</p>
    </div>
</div>
<!-- 全局变量 -->
<script type="text/javascript">
    var _ctxPath = '${_ctxPath}',
    SHOPCART = '<%=com.ytoxl.module.uhome.uhomebase.common.CommonConstants.COOKIE_SHOPPINGCART %>',
    bkEndTxt = "${productPage.params.keyWord }";
</script>
</cache:cache>
<link rel="stylesheet" type="text/css" href="${_jsPath }/plugin/artdialog/skins/ytoxl.css?d=${_resVerion}" media="all">
<script type="text/javascript" src="${_jsPath }/plugin/json2.js"></script>
<script type="text/javascript" src="${_jsPath }/plugin/jquery.cookie.js"></script>
<script type="text/javascript" src="${_jsPath }/plugin/plugin.js"></script>
<script type="text/javascript" src="${_jsPath }/plugin/artdialog/jquery.artDialog.min.js"></script>
<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"></script>
<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js"></script>
<script type="text/javascript" src="${_jsPath }/plugin/mailAutoComplete/jquery.mailAutoComplete.js"></script>
<script type="text/javascript" src="${_jsPath }/lib/header.js?d=${_resVerion}"></script>
<script type="text/javascript" src="${_jsPath }/pages/login.js?d=${_resVerion}"></script>
<!-- 新浪微博js -->
<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js" type="text/javascript" charset="utf-8"></script>
<script type="text/javascript">
//根据url判断是那个菜单
$(function(){
    var url = location.href;
    if(url.indexOf("index") > 0 || url.indexOf("login") > 0 || url.indexOf("default") > 0){
    	$("#home-page").addClass("cur").siblings().removeClass("cur");
    }else if(url.indexOf("special") > 0 || url.indexOf("plan") > 0){
    	$("#special-page").addClass("cur").siblings().removeClass("cur");
    }else if(url.indexOf("seckill") > 0){
    	$("#seckill-page").addClass("cur").siblings().removeClass("cur");
    }else if(url.indexOf("brands") > 0){
    	$("#brands-page").addClass("cur").siblings().removeClass("cur");
    }else{
    	$("#brands-page").removeClass("cur").siblings().removeClass("cur");
    }
    //alert(url);
    //搜索框中的值
    if(bkEndTxt){
    $("#search").val(bkEndTxt);
    }
});
</script>
<tongji:baidu showStatistics="${showStatistic}" ></tongji:baidu>
