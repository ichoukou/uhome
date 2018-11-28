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
    <script src="${_jsPath }/pages/listUser.js"></script>
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
            <li><a class="current-chose">子帐号管理</a></li>
          </ul>
        </div>
        <!---star 角色管理--->
        <div class="examine m-mt10p layout">
            <input type="button" id="addSale-btn" onclick="editUser();" class="m-btn" value="新增子帐号"/>
        </div>
      	<!--start 角色管理-->
        <div class="m-mt10p arrange-management">
            <table class="tab-control" id="tab">
                <thead>
                    <tr>
                        <th width="25%">登录名</th>
                        <th width="35%">角色</th>
                        <th width="18%">创建时间</th>
                        <th width="22%">操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${userList }" var="user">
                <c:if test="${user.urole.uroleId!=100003 }">
                <tr class="list-tr">
                    <td><span>${user.username }</span></td>
                    <td>
                        <span>${user.urole.uroleName }</span>
                    </td>
                    <td><fmt:formatDate value="${user.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
                    <td>
                        <a href="${_ctxPath}/admin/auth/auth-toEditUser.htm?myUserId=${user.userId }">编辑</a><a onclick="deleteUser(${user.userId })">删除</a><a id="${user.userId }" onclick="unAbleUser(${user.userId })">${user.status==0?'启用':'禁用' }</a><a onclick="resetPassWd(${user.userId })">重置密码</a>
                    </td>
                </tr>
                </c:if>
                </c:forEach>
                </tbody>
            </table>
        </div>
      	<!--end 角色管理-->
       <!---end 角色管理--->
    </div>
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</body>
</html>