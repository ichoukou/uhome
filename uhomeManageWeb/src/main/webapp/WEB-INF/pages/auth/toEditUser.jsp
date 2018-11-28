<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>角色管理</title>
    <link href="${_cssPath }/common.css" rel="stylesheet" />
    <link href="${_cssPath }/pages/arrangement.css" rel="stylesheet" />
    <link href="${_jsPath }/plugin/artdialog/skins/ytoxl.css" rel="stylesheet" />
    <script src="${_jsPath }/jquery/jquery.1.8.1.js"  language="javascript"></script>
    <script src="${_jsPath }/plugin/artdialog/jquery.artDialog.min.js"></script>
    <script src="${_jsPath }/pages/roles.js"></script>
</head>

<body>
    <!--start header-->
    <jsp:include page="../include/header.jsp"></jsp:include> 
    <!--end header-->
    <!--start body-->
    <div class="body m-w980p">
        <div class="body-nav m-mt10p">
          <ul class="m-clear" id="arrangeMent">
             <li><a href="${_ctxPath}/admin/auth/auth-listRoles.htm" >角色管理</a></li>
            <li><a href="${_ctxPath}/admin/auth/auth-listUsers.htm" class="current-chose">子帐号管理</a></li>
          </ul>
        </div>
        <div class="account-manage">
        <form action="${_ctxPath}/admin/auth/auth-saveUser.htm" method="post" id="mainForm">
        	<input type="hidden" name="myUser.userId" value="${myUser.userId}" />
		    	<table>
		    		<tr>
		    			<td width="130" align="right"><i>*</i> <span>登录名：</span></td>
		    			<c:if test="${myUser.username==null }">
		    			<td><input name="myUser.username" type="text" class="txt-input" maxlength="20" /></td>
		    			</c:if>
		    			<c:if test="${myUser.username!=null }">
		    			<td>${myUser.username }</td>
		    			</c:if>
		    		</tr>
		    		<tr valign="top">
		    			<td align="right"><i>*</i> <span>分配角色：</span></td>
		    			<td>
		    				<ul>
		    				<c:forEach items="${urolelist }" var="urole">
		    					<c:if test="${urole.uroleId==myUser.urole.uroleId }">
			    					<li><label><input type="radio" checked name="myUser.urole.uroleId" value="${urole.uroleId }" />${urole.uroleName }</label><a uroleId="${urole.uroleId }" id="editUrole" class="J-edit" href="javascript:;">编辑</a></li>
		    					</c:if>
		    					<c:if test="${urole.uroleId!=myUser.urole.uroleId }">
			    					<li><label><input type="radio" name="myUser.urole.uroleId" value="${urole.uroleId }"  />${urole.uroleName }</label><a class="J-edit" uroleId="${urole.uroleId }" id="editUrole"  href="javascript:;">编辑</a></li>
		    					</c:if>
		    				</c:forEach>
		    					<li><a class="J-add" id="add-role-btn" uroleId="" href="javascript:;">新增角色</a></li>
		    				</ul>
		    			</td>
		    		</tr>
		    		<tr>
		    			<td align="right"><input type="button" id="addKeyword" class="m-btn" value="保存" /></td>
		    			<td>
		    				<input type="button" class="m-btn" onclick="javascript:history.go(-1)" value="取消" />
		    			</td>
		    		</tr>
		    	</table>
		    </form>
		    </div>
    </div>
       <!--权限管理弹出层-->
    <div class="role">
	    <div class="addrole-pop">
	    	<div class="role-name">
	    		<span><i>*</i>角色名称：</span><input type="text" id="uroleName" class="txt-input" maxlength="20"/>
	    	</div>
	    	<div class="pop-box m-clear">
	    		<div class="pop-box-left">
	    			<div class="role-list">
	    				<i></i>
	    				<p>
	    					<em></em>
	    					<label>商品管理</label>
	    				</p>
	    				<ul>
	    					<li class="role-list">
	    						<i></i>
			    				<p>
			    					<em></em>
			    					<label>商品管理</label>
			    				</p>
	    					</li>
	    				</ul>
	    			</div>
	    		</div>
	    		<div class="pop-box-right">
	    			<ul>
	    			</ul>
	    		</div>
	    	</div>
	    </div>
    </div>
    <!--end body-->
    <!--start footer-->
		<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
    <!--end footer-->
</body>

</html>