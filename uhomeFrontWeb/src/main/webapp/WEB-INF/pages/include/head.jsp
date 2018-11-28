<%@page import="com.ytoxl.module.core.common.utils.SpringContextUtils"%>
<%@page import="com.ytoxl.module.uhome.uhomeorder.service.OrderService"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.service.ProductCategoryService"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.ProductCategory"%>
<%@page import="java.util.List" %>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script type="text/javascript" src="${_jsPath }/jquery/jquery-1.8.1.js"></script>
<html xmlns:wb="http://open.weibo.com/wb">
	<%
	//ProductCategoryService productCategoryService = (ProductCategoryService)(SpringContextUtils.getContext().getBean("productCategoryServiceImpl"));
	//List<ProductCategory> productCategorys = productCategoryService.listProductCategory();
	//request.setAttribute("productCategorys",productCategorys);
	%>
  <!--头部 start-->
	<div class="topbar-wrap">
		<div class="topbar-info cf">
			<!--头部迷你导航 start-->
			<div class="top-mini-menu">
			<%--findPassWord passwordBackQueryMsg resetpassword updatePassword--%>
			<%if(!request.getServletPath().contains("passWordstep") && !request.getServletPath().contains("accouterror") && !request.getServletPath().contains("yanzhemaerror") && !request.getServletPath().contains("updateerror") && !request.getServletPath().contains("updatesuccess")){%>
				<ul>
					<%--<li class="act"><a href="${_ctxPath }/default.htm">品牌特卖</a>|</li>--%>
					<%--<li><a href="${_ctxPath}/import.htm">进口商品</a>|</li>--%>
					<%--<li><a href="${_ctxPath }/brands.htm">品牌集中营</a></li>--%>
				<c:if test="${empty session.SPRING_SECURITY_CONTEXT.authentication.principal}">
                    <li class="welcome">欢迎来到${_webSiteName }</li>
                    <li class="link"><a href="#" class="login">登录</a>|</li>
					<li class="link"><a href="#" class="register">免费注册</a></li>
           		</c:if>
           		<c:if test="${not empty session.SPRING_SECURITY_CONTEXT.authentication.principal}">
           		<sec:authentication property="principal.authorities" var="auth_value"/>
           		<c:if test="${auth_value eq '[100000]' }"><%-- 前台买家--%>
           			<li class="link">您好${fn:split(session.SPRING_SECURITY_CONTEXT.authentication.principal.username,'@')[0] }   <a href="${_ctxPath}/user/orders.htm">我的新龙</a>|<a href="${_ctxPath}/j_spring_security_logout" class="topbar-logout">退出</a></li>
           		</c:if>
           		<c:if test="${!(auth_value eq '[100000]') }"><%--不为前台买家--%>
           			<li class="welcome">欢迎来到${_webSiteName }</li>
	           		<li class="link"><a href="javascript:;" class="login">登录</a>|</li>
					<li class="link"><a href="javascript:;" class="register">免费注册</a></li>
           		</c:if>
           		</c:if>
				</ul>
			<%} %>
			</div>
			<!--头部迷你导航 end-->
			<!--头部右侧→登录 start-->
			<div class="topbar-right">
				<ul>
                    <li class="weibo">
                    <wb:follow-button uid="3237821860" type="red_1" width="62" height="24" ></wb:follow-button>
                    </li>
            		<li><a href="javascript:addFavorite();">加入收藏</a>|</li>
					<li><a href="${_ctxPath }/user/suggest.htm" target="_blank">建议意见</a>|</li>
					<li class="hot-line"><span>客服热线<b>${_tel}</b></span></li>
				</ul>
			</div>
			<!--头部右侧→登录 end-->
		</div>
	</div>
	<!--头部logo及协议 start-->
	<div class="top-info-wrap cf">
		<div class="logo">
			<a href="${_ctxPath}/" title="新龙直销商城"></a>
		</div>
		<div class="agreement cf">
			<div class="agreement-ico"></div>
		</div>
	</div>
	<!--头部logo及协议 end-->
	<!--导航菜单 start-->
	<%if(!request.getServletPath().contains("shoppingCart") && !request.getServletPath().contains("checkOrder")){ %>
	<!-- 在购物车不现实导航 -->
	<div class="menu-wrap">
		<div class="menu cf">
			<ul class="menu-list">
				<li>
					<div class="menu-item">
						<a id="cateAll" href="${_ctxPath}/">全部特卖</a>
					</div>
				</li>
				<%-- 
				<c:forEach items="${productCategorys }" var="pcs">
				<li>
					<div class="menu-item">
						<a id="cate${pcs.productCategoryId}" href="${_ctxPath }/cate-${pcs.productCategoryId}.htm">${pcs.name}</a>
					</div>
				</li>
				</c:forEach>
				 --%>
				<li>
					<div class="menu-item">
						<a id="fsxb" href="${_ctxPath }/fsxb/">服饰箱包</a>
					</div>
				</li>
				<li>
					<div class="menu-item">
						<a id="myyp" href="${_ctxPath }/myyp/">母婴用品</a>
					</div>
				</li>
				<li>
					<div class="menu-item">
						<a id="jjyp" href="${_ctxPath }/jjyp/">家居用品</a>
					</div>
				</li>
				<li>
					<div class="menu-item">
						<a id="mrhf" href="${_ctxPath }/mrhf/">美容护肤</a>
					</div>
				</li>
				<li>
					<div class="menu-item">
						<a id="mrhf" href="${_ctxPath }/event/20131030/index.html" target="_blank">一城一品</a>
					</div>
				</li>
				<!--弹出品牌 start-->
				<!-- 
				<div class="menu-barnd-list cf">
					<a href="#"><img src="../../web-resource/images/uploadfolder/brand.jpg" /></a>
				</div> 
				-->
				<!--弹出品牌 end-->
			</ul>
			<!--购物袋 start-->
			<div class="shopping-cart">
				<%
	                    OrderService orderService = (OrderService)(SpringContextUtils.getContext().getBean("orderServiceImpl"));
	            		Integer total = orderService.getValidCartCount(request, response);
	            		request.setAttribute("totalNum",total);
            	%>
				<div class="cart-sum"><a href="${_ctxPath}/order/order-shoppingCart.htm"><i></i>购物车（<span class="show-sum">${totalNum}</span>）</a></div>
				<!--购物袋详细信息 start-->
				<div class="cart-list">
					
				</div>
				<!--购物袋详细信息 end-->
			</div>
			<!--购物袋 end-->
		</div>
	</div>
	<%} %>
	<!--导航菜单 start-->
	<!--头部 end-->
    <!--登录注册-->
    <div id="log-reg-wrap">
        <!--<div class="log-reg-panel">
            <ul class="tab">
                <li>登录</li>
                <li>注册</li>
            </ul>
        </div>-->
    <div class="tab-item" style="display: block;">
    	<div class="log-reg-tit">
    		<span>用户登录</span>
    		<b>Login</b>
    	</div>
        <form  id="login_form"  autocomplete="off" method="post">
            <table>
                <tr>
                    <td width="60"></td>
                    <td>
                    <%--
                    <input type="text" id="j_username" name="username"  class="login-name" value="${sessionScope['SPRING_SECURITY_LAST_USERNAME']}" >
                    --%>
                    <div class="log-position log-z">
                    		<div class="email"></div>
                    		<div class="email-tip">请输入您的注册邮箱地址</div>
                        <input type="text" id="j_username" name="username" class="J-email input-default"  value="" >
                       <%--  <div class="tip-wp"><p id="account" class="onError" style="display:none"><span class="onError-txt"></span></p></div>--%>
                       <div class="tip-wp"><p id="account"></p></div>
                    </div>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                    <%-- 
                    <input id="j_password" name="password"  class="password"  type="password"/>
                        <div class="tip-wp"><p id="passWord" class="onError" style="display:none"><span class="onError-txt"></span></p></div>--%>
                      <div class="log-position">
                        <div class="password"></div>
                        <input id="j_password" name="password"  class="input-default" onchange="$('#password_t').val($(this).val())" type="password" style="ime-mode: disabled; display: none;"/>
                         <input type="text" value="" class="input-default" name="password_t" id="password_t"  style="ime-mode: disabled; display: inline;"/>
                         <a href="${_ctxPath}/user/user-findPassWord.htm" class=" forget-password">忘记密码？</a>
                        <div class="tip-wp"><p id="passWord"></p></div>
                      </div>
                    </td>
                </tr>
                <tr id="yanzhengma" style="display: none;">
                    <td></td>
                    <td>
                        <input type="text" value="验证码不区分大小写" class="verific"  id="j_captcha" name="jCaptchaResponse">
                        <img src="${_ctxPath}/validationCode.htm"  id="j_validateCode" class="verific-img" alt="">
                        <a class="yzmtxt" style="cursor:pointer;" onclick="nextValidateCode();">看不清换一张</a>
                        <div class="cl"></div>
                        <div class="tip-wp"><p id="verifiCode"></p></div>
                    </td>
                </tr>
                <tr>
                    <td></td>
                    <td>
                    	<a class="login-btn" id="login_btn" href="javascript:;"><b>登&nbsp;&nbsp;&nbsp;&nbsp;录</b></a>
                    	<div class="remember"><label for="safeLogin"><input type="checkbox" checked="checked" id="safeLogin">记住用户名</label></div>
                    	<a href="#" class="J-log-reg free-reg" id="one" >免费注册</a>
                   </td>
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
    	<div class="log-reg-tit">
    		<span>用户注册</span>
    		<b>Sign Up</b>
    	</div>
        <form id="registerForm"  autocomplete="off" method="post" >
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
                <td class="tit" valign="top">邮箱：</td>
                <td>
                	<div class="log-position log-z">
                		<div class="email"></div>
                		<div class="email-tip">请输入您的邮箱地址</div>
                    <input type="text" value="" class="J-email input-default"  name="username" id="r_userName">
                    <div class="tip-wp"><p id="r_userNameReg"></p></div>
                  </div>
                </td>
                </tr>

                <tr>
                    <td class="tit">密码：</td>
                    <td>
                    <%-- 
                    <input type="password" value="" class="password" name="password" id="r_password" />--%>
                    	<div class="log-position">
                    		<div class="password"></div>
                        <input type="password" value="" class="input-default" name="password" onchange="$('#r_password_t').val($(this).val())" id="r_password"  style="ime-mode: disabled; display: none;"/>
                        <input type="text" value="" class="input-default" name="password_t" id="r_password_t"  style="ime-mode: disabled; display: inline;"/>
                        <div class="tip-wp"><p id="r_passwordReg"></p></div>
                    	</div>
                    </td>
                </tr>
                <tr>
                    <td valign="top" class="tit">确认密码：</td>
                    <td><%--
                     <input type="password" value="" class="password"  name="queryPassWord" id="r_querypassword"/>
                     --%>
                     <div class="log-position">
                    		<div class="password"></div>
                        <input type="password" value="" class="input-default"  onchange="$('#r_querypassword_t').val($(this).val())" name="queryPassWord" id="r_querypassword" style="ime-mode: disabled; display: none;"/>
                        <input type="text" value="" class="input-default"  name="queryPassWord_t" id="r_querypassword_t" style="ime-mode: disabled; display: inline;"/>
                        <div class="tip-wp"><p id="r_querypasswordReg"></p></div>
                      </div>
                    </td>
                </tr>
                <tr id="registerYanzhengma" >
                    <td></td>
                    <td>
                        <input type="text" class="verific"  id="j_captcha_register" name="jCaptchaResponse">
                        <img src="${_ctxPath}/validationCode.htm"  id="j_validateCode_register" class="verific-img" alt="">
                        <a class="yzmtxt" style="cursor:pointer;" onclick="nextValidateCodeRegister();">看不清换一张</a>
                        <div class="cl"></div>
                        <div class="tip-wp"><p id="verifiCodeRegister"></p></div>
                    </td>
                </tr>
                <tr><td></td><td><p class="line"><label ><input type="checkbox" id="readed" checked="checked">我已阅读并接受</label><a href="${_ctxPath }/user/serviceTerms.htm" target="_blank"><u>${_webSiteName }服务条款</u></a></p></td></tr>
                <tr>
                    <td></td>
                    <td><a class="login-btn" href="#" id="registerBtn"><span>完成注册</span></a><a href="#" id="two" class="J-log-reg free-reg">我已注册，点击登录</a></td>
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
    <script type="text/javascript">
    $("#password_t").focus(function(){
	    $("#password_t").hide();
	    $("#j_password").show();
	    $("#j_password").focus();
    })
    $("#r_password_t").focus(function(){
	    $("#r_password_t").hide();
	    $("#r_password").show();
	    $("#r_password").focus();
    })
     $("#r_querypassword_t").focus(function(){
	    $("#r_querypassword_t").hide();
	    $("#r_querypassword").show();
	    $("#r_querypassword").focus();
    })
    </script>
</html>
