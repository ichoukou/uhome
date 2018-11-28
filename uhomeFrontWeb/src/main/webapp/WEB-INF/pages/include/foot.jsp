<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<%@page import="com.ytoxl.module.core.common.utils.SpringContextUtils"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.service.HelpService"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<cache:cache time="${_footCacheSec}" refresh="${_needRefresh}" key="foot"> 
<%
	HelpService helpService=(HelpService)(SpringContextUtils.getContext().getBean("helpServiceImpl"));
	pageContext.setAttribute("helpCategorys",helpService.listHelpCategorys());
%>
<!--底部 start-->
	<div class="footer">
		<!--底部服务保障 start-->
		<div class="footer-top cf">
			<ul>
				<li>
					<i class="ensure-1"></i>
					<h4>正品保证</h4>
					<p>确保所有产品均为原装正品</p>
				</li>
				<li>
					<i class="ensure-2"></i>
					<h4>品牌商直供</h4>
					<p>严格入驻流程，审核品牌商资质</p>
				</li>
				<li class="no-margin">
					<i class="ensure-3"></i>
					<h4>10点准时上新</h4>
					<p>每天10点新品准时开售</p>
				</li>
			</ul>
		</div>
		<!--底部服务保障 end-->
		<div class="footer-middle-wp">
			<!--底部导航 start-->
			<div class="footer-middle cf">
				<!--帮助信息 start-->
				<div class="help">
				<c:forEach items="${helpCategorys}" var="helpCategory" varStatus="status">
					<dl>
						<dt>${helpCategory.name}</dt>
						<c:forEach items="${helpCategory.helpCategorys}" var="help" varStatus="index">
                            <dd><a target="_blank" href="${_ctxPath}/help/help-${help.help.helpId}.htm" target="_blank" rel="nofollow">${help.name}</a></dd>
                        </c:forEach>
                        <c:if test="${status.last}">
                        	 <dd><a href="${_ctxPath}/help/siteMap.htm" target="_blank">网站地图</a></dd>
                        </c:if> 
					</dl>
				</c:forEach>
				</div>
				<!--帮助信息 end-->
				<!--客服热线 start-->
				<div class="hot-line">
					<dl>
						<dt>客服热线</dt>
						<dd>如果您遇到任何问题，欢迎拨打</dd>
					</dl>
					<div class="line-num">
						<h4>${_serviceTel}</h4>
						<p>接听时间：${_serviceTime}</p>
					</div>
<!--                     <div class="site-map"><a href="javascript:;">网站地图</a></div> -->
						<div class="site-map">
							<a href="${_ctxPath }/supplier.htm">品牌合作</a>&nbsp;
							<a href="${_ctxPath }/Links/" target="_blank">友情链接</a>
						</div>
				</div>
				<!--客服热线 end-->
			</div>
			<!--底部导航 start-->
		</div>
        <div class="footer-bot-wp">
            <div class="footer-bot">
                <p><span>${_footCopyright}</span>${_footAddress}</p>
            </div>
        </div>
	</div>
	<!--底部 end-->
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
<script type="text/javascript">
(function(){    
	var s = document.createElement('script');
	s.type = 'text/javascript';
	s.async = true;
	s.src = 'http://tjs.sjs.sinajs.cn/open/api/js/wb.js';
	var x = document.getElementsByTagName('script')[0];
	x.parentNode.insertBefore(s, x);
})(); 
//根据url判断是那个菜单
$(function(){
    var url = location.href;
    var servletPath =  "<%=request.getServletPath()%>";
    if(url.indexOf("fsxb") > 0){
    	$("#fsxb").addClass("current").siblings().removeClass("current");
    }else if(url.indexOf("myyp") > 0){
    	$("#myyp").addClass("current").siblings().removeClass("current");
    }else if(url.indexOf("jjyp") > 0){
    	$("#jjyp").addClass("current").siblings().removeClass("current");
    }else if(url.indexOf("mrhf") > 0){
    	$("#mrhf").addClass("current").siblings().removeClass("current");
    }else{
    	if(servletPath == "/index.jsp"){
    		$("#cateAll").addClass("current").siblings().removeClass("current");
    	}
    }
});
//分页数量只可以输入数字
$("#pageIndex").keyup(function(event) {
    $this = $(this);
    var value = $this.val();
    if(value!=""){
		var reg = /[^0-9]/g;
		value = value.replace(reg,"")
		if(parseInt(value)==0){
			value = "";
		}
		if(value.length>9){
			value = value.substring(0,9);
		}
		$this.val(value);
	}
});
</script>
<tongji:baidu showStatistics="${showStatistic}" ></tongji:baidu>
