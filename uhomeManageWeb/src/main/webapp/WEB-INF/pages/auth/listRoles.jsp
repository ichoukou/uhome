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
            <li><a class="current-chose">角色管理</a></li>
            <li><a href="${_ctxPath}/admin/auth/auth-listUsers.htm">子帐号管理</a></li>
          </ul>
        </div>
        <!---star 角色管理--->
        <div class="m-mt10p">
            <input type="button" id="add-role-btn" uroleId="" class="m-btn" value="新增角色"/>
        </div>
      	<!--start 角色管理-->
        <div class="m-mt10p arrange-management">
            <table class="tab-control" id="tab">
                <thead>
                    <tr>
                        <th width="24%">角色名称</th>
                        <th width="26%">角色权限</th>
                        <th width="17%">创建时间</th>
                        <th width="15%">分配帐号</th>
                        <th width="18%">操作</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach items="${urolelist }" var="ul">
                <tr class="list-tr">
                    <td><span>${ul.uroleName }</span></td>
                    <td>
                    <c:forEach items="${ul.uresourceList }" var="rl">
                    <c:if test="${rl.isMenu==1 }">
                     ${rl.uresourceName }：
                      <c:forEach items="${ul.uresourceList }" var="rli">
                      	<c:if test="${rli.parentId==rl.uresourceId}">
                      	${rli.uresourceName }
                      	</c:if>
                      </c:forEach>
                      <br/>
                    </c:if>
                    </c:forEach>
                        <!--  <p><span>排期管理：</span>新增、查看</p>
                        <p><span>优惠券管理：</span>查看、新增、导出</p>
                        -->
                    </td>
                    <td><fmt:formatDate value="${ul.createTime }" pattern="yyyy-MM-dd HH:mm:ss" /> </td>
                    <td>${ul.names }
                    </td>
                    <td>
                        <a uroleId="${ul.uroleId }" id="editUrole" href="javascript:;">编辑</a><a uroleId="${ul.uroleId }" onclick="deleteUrole(${ul.uroleId });" href="javascript:;">删除</a>
                    </td>
                </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
      	<!--end 角色管理-->
       <!---end 角色管理--->
    </div>
    <!--end body-->
    <!--start footer-->
    <!--end footer-->
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
	    				<p class="J-parent">
	    					<em></em>
	    					<label>商品管理</label>
	    				</p>
	    				<ul>
	    					<li class="role-list">
	    						<i></i>
			    				<p class="J-children">
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
</body>
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
</html>