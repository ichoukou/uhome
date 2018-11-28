<%@page import="com.ytoxl.module.uhome.uhomecontent.service.HotWordService"%>
<%@page import="com.ytoxl.module.uhome.uhomeorder.service.OrderService"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.HotWord"%>
<%@page import="com.ytoxl.module.core.common.utils.SpringContextUtils"%>
<%@page import="java.util.List" %>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
<html xmlns:wb=“http://open.weibo.com/wb”>
    <div class="topbar-wrap">
        <div class="topbar">
            <div class="wel">
            	<c:if test="${empty session.SPRING_SECURITY_CONTEXT.authentication.principal}">
            		您好，欢迎光临${_webSiteName }！ <a href="javascript:" class="login">登录</a><a href="javascript:" class="register">免费注册</a>
           		</c:if>
           		<c:if test="${not empty session.SPRING_SECURITY_CONTEXT.authentication.principal}">
           		<sec:authentication property="principal.authorities" var="auth_value"/>
           		<c:if test="${auth_value eq '[100000]' }">
           			您好<sec:authentication property="name"/>，欢迎光临${_webSiteName }！<font color="yellow"><a href="${_ctxPath}/user/orders.htm"><span>我的新龙</span></a></font>
           			<font color="yellow"><a href="${_ctxPath}/j_spring_security_logout" class="topbar-logout">退出<span></a></font>
           		</c:if>
           		<c:if test="${!(auth_value eq '[100000]') }">
           		您好，欢迎光临${_webSiteName }！ <a href="javascript:" class="login">登录</a><a href="javascript:" class="register">免费注册</a>
           		</c:if>
           		</c:if>
           	</div>
            <div class="about">
                <ul>
                    <li><a href="javascript:addFavorite();">收藏本站</a></li>
                    <li><a href="${_ctxPath }/user/suggest.htm">建议意见</a></li>
                    <li class="follow">
                        <a href="javascript:">关注${_webSiteName }</a><b></b>
                        <div class="follow-box">
                            <p><a href="javascript:"><wb:follow-button uid="3237821860" type="red_1" width="136" height="24" ></wb:follow-button></a></p>
                            <!-- 
                            <p><a href="javascript:">
									<iframe src="http://follow.v.t.qq.com/index.php?c=follow&a=quick&name=wanx_594566751&style=5&t=1363142296235&f=0" marginwidth="0" marginheight="0" allowtransparency="true" frameborder="0" height="24" scrolling="auto" width="125"></iframe>                            
                            </a></p>
                             -->
                        </div>
                    </li>
                    <li class="cs">客服热线:<em>${_tel}</em></li>
                </ul>
            </div>
        </div>
    </div>
    <div class="top-info-wrap">
        <div class="top-info">
            <div class="logo"><a href="${_ctxPath }/default.htm">新龙直销 - www.xlbuy365.com</a></div>
            <div class="slogan">天天特价到你家</div>
            <div class="agreement">
                <ul>
                    <li><span class="zp"></span>100%<br>正品保障</li>
                    <li><span class="sd"></span>品牌商<br>直接供货</li>
                    <li><span class="mr"></span>每日<br>10:00上新</li>
                    <li class="last"><span class="qc"></span>全场包邮</li>
                </ul>
            </div>
            <cache:cache time="${_cacheSec}" refresh="${_needRefresh}">
            <div class="hot">
            	<%
            		HotWordService hotWordService = (HotWordService)(SpringContextUtils.getContext().getBean("hotWordServiceImpl"));
            		List<HotWord> hotWords = hotWordService.litHotWords4Front();
            		request.setAttribute("hotWords",hotWords);
            	%>
            	<c:if test="${not empty hotWords}">
                	<span>热搜：</span>
                </c:if>
                <c:forEach items="${hotWords}" var="hotWord">
                	<a href="javascript:searchHotWord('${hotWord.word }');">${hotWord.word }</a>
                </c:forEach>
            </div>
            </cache:cache>
        </div>
    </div>
    <div class="menu-wrap">
        <div class="menu cf">
            <ul class="m-menu">
            	<!--  class="cur" -->

                <li id="home-page"><a href="${_ctxPath }/default.htm">首页</a></li>
                <li id="special-page"><a href="${_ctxPath }/specials.htm">品牌特卖</a></li>
                <%-- <li id="seckill-page"><a href="${_ctxPath }/seckills.htm">秒杀</a></li> --%>
                <li id="brands-page"><a href="${_ctxPath }/brands.htm">品牌集中营</a></li>
            </ul>
            <div class="box">
                <div class="data-frame"><input id="search" type="text" value="" data-default="搜索商品/品牌"><a href="javascript:search();">搜 索</a></div>
                <div class="shopping-cart">
                    <%
	                    OrderService orderService = (OrderService)(SpringContextUtils.getContext().getBean("orderServiceImpl"));
	            		Integer total = orderService.getValidCartCount(request, response);
	            		request.setAttribute("totalNum",total);
            		%>
                    <a href="${_ctxPath}/order/order-shoppingCart.htm" class="show-sum">${totalNum}</a>
					<!-- 购物车列表 -->
				    <div class="shopping-box">  </div>
                </div>
            </div>
        </div>
    </div>
    <!--登录注册-->
    <div id="log-reg-wrap">
        <div class="log-reg-panel">
            <ul class="tab">
                <li>登录</li>
                <li>注册</li>
            </ul>
        </div>
        <div class="tab-item" style="display: block;">
       		<form  id="login_form"  method="post">
            <table>
                <tr>
                    <td class="tit" valign="top">登录名</td>
                    <td>
                    <%-- 
                    <input type="text" id="j_username" name="username"  class="login-name" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" >
                    --%>
                    <input type="text" id="j_username" name="username"  class="login-name" value="" >
                    <div class="tip-wp"><p id="account" class="onError" style="display:none"><span class="onError-txt"></span></p></div>
                    </td>
                </tr>
                <tr>
                    <td class="tit">密码</td>
                    <td><input id="j_password" name="password"  class="password"  type="password">
                    <div class="tip-wp"><p id="passWord" class="onError" style="display:none"><span class="onError-txt"></span></p></div>
                    </td>
                </tr>
                <tr id="yanzhengma" style="display: none;">
                    <td valign="top" class="tit">验证码</td>
                    <td>
                   	 	<input type="text" value="" onfocus="javascript:$('#verifiCode').html('');" class="verific"  id="j_captcha" name="jCaptchaResponse"><img src="${_ctxPath}/validationCode.htm"  id="j_validateCode" class="verific-img" alt="">
                        <a class="yzmtxt" style="cursor:pointer;" onclick="nextValidateCode();">看不清换一张</a>
                        <div class="tip-wp"><p id="verifiCode"></p></div>
                     </td>
                </tr>
                <tr><td></td><td><p class="line"><label for="safeLogin"><input type="checkbox" checked="checked" id="safeLogin">记住用户名</label></p></td></tr>
                <tr>
                    <td></td>
                    <td><a class="btn-a" id="login_btn" href="javascript:;"><span style="width:48px;text-align:center;">登录</span></a><a href="${_ctxPath}/user/user-findPassWord.htm" class="forget-password">忘记密码？</a></td>
                </tr>
            </table>
            <input name="opertNum"  type="hidden" id ="opertNum"  value="${opertNum}"/>
       		</form>  
            <%--<div class="open-login">--%>
                <%--<div class="open-tit">--%>
                   	 <%--使用合作网站账号登录：--%>
                <%--</div>--%>
                <%--<div class="open-list cf">--%>
                    <%--<ul>--%>
                        <%--<li><a href="#" class="qq">QQ</a></li>--%>
                        <%--<li><a href="#" class="sina">新浪</a></li>--%>
                        <%--<li><a href="#" class="alipay">支付宝</a></li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>
        <div class="tab-item">
            <form id="registerForm" method="post" > 
            <table>
              <%-- 
                <tr>
                    <td class="tit">登录名</td>
                    <td><input type="text" value="" class="login-name"  name="username" id="r_userName">
                    <div class="tip-wp"><p id="r_userNameReg"></p></div>
                    </td>
                </tr>
               --%>
                <tr>
                    <td class="tit">登录名</td>
                    <td><input type="text" value="" class="email"  name="username" id="r_userName">
                    <div class="tip-wp"><p id="r_userNameReg"></p></div>
                    </td>
                </tr>
             
                <tr>
                    <td class="tit">密码</td>
                    <td><input type="password" value="" class="password" name="password" id="r_password">
                    <div class="tip-wp"><p id="r_passwordReg"></p></div>
                    </td>
                </tr>
                <tr>
                    <td valign="top" class="tit"> 确认密码</td>
                    <td><input type="password" value="" class="password"  name="queryPassWord" id="r_querypassword">
                    <div class="tip-wp"><p id="r_querypasswordReg"></p></div>
                    </td>
                </tr>
                <tr><td></td><td><p class="line"><label ><input type="checkbox" id="readed" checked="checked">我已阅读并接受</label><a href="${_ctxPath }/user/serviceTerms.htm" target="_blank">${_webSiteName }服务条款</a></p></td></tr>
                <tr>
                    <td></td>
                    <td><a class="btn-a" href="javascript:;" id="registerBtn"><span>完成注册</span></a></td>
                </tr>
            </table>
     		</form>      
            <%--<div class="open-login">--%>
                <%--<div class="open-tit">--%>
                   	 <%--使用合作网站账号登录：--%>
                <%--</div>--%>
                <%--<div class="open-list cf">--%>
                    <%--<ul>--%>
                        <%--<li><a href="#" class="qq">QQ</a></li>--%>
                        <%--<li><a href="#" class="sina">新浪</a></li>--%>
                        <%--<li><a href="#" class="alipay">支付宝</a></li>--%>
                    <%--</ul>--%>
                <%--</div>--%>
            <%--</div>--%>
        </div>
    </div>
    <form id="true_form" action="${_ctxPath}/j_spring_security_check" style="display:none;"  method="post">
    	<input type="text"  id="tname" name="j_username" >
    	<input name="j_password" id="tpassword"  type="password">
    	<input type="checkbox" id="remberMe" name="_spring_security_remember_me">
    </form>
    <!--登录注册 end-->
</html>
