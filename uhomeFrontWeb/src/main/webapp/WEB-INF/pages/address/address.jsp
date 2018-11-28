<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
  <meta charset="utf-8"/>
    <title>我的收货地址-个人中心-${_webSiteName}</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/address.css?d=${_resVerion}"/>
</head>
<body>
    <jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include>
    <div class="m-w960p cf">
        <!--面包屑-->
        <div class="crumbs"><a href="${_domain }">${_webSiteName } ></a><a href="${_domain }/order/order-myOrders.htm">个人中心   ></a>我的收货地址</div>
        <!--面包屑 end-->
        <jsp:include page="/WEB-INF/pages/include/left.jsp"></jsp:include>
        <div class="inf-detail m-mt20p">
            <div class="inf-title fon-big">我的收货地址</div>
            <div class="user-bg">
            	<i></i>
	            <div class="m-mt30p addr-tab">
	                <table>
	                    <thead>
	                        <tr>
	                            <th width="10%">收货人</th>
	                            <th>所在地区</th>
	                            <th width="15%">街道地址</th>
	                            <th width="10%">邮编</th>
	                            <th width="15%">联系电话</th>
	                            <th width="13%">&nbsp;</th>
	                            <th width="12%">操作</th>
	                        </tr>
	                    </thead>
	                    <tbody>
	                        <c:forEach items="${receiverAddRegionList}" var="receiverAdd">
	                        <tr class="list-tr">
	                            <td >${receiverAdd.receiverName}</td>
	                            <td> ${receiverAdd.province}&nbsp;&nbsp;${receiverAdd.county}&nbsp;&nbsp;${receiverAdd.city}</td>
	                            <td>${receiverAdd.receiveAddress}</td>
	                             <td>${receiverAdd.postCode}</td>
	                            <td>${receiverAdd.mobile}<br/>${receiverAdd.telephone}</td>
	                            <td class="addr-operation">
	                            	<c:if test="${receiverAdd.isDefault==1}">
		                            <a href="javaScript:">默认地址</a>
		                            </c:if>
	                            	<c:if test="${receiverAdd.isDefault==0}">
		                            <a href="javaScript:setDefualt(${receiverAdd.receiverAddressId})">设为默认地址</a>
		                            </c:if>
	                            </td>
	                            <td class="addr-operation">
	                            <a href="javascript:updateAddress(${receiverAdd.receiverAddressId})">修改</a><a href="javaScript:setDel(${receiverAdd.receiverAddressId})">删除</a></td>
	                        </tr>
	                        </c:forEach>
	                    </tbody>
	                </table>
	            </div>
	          </div>
            <div class="inf-title fon-big" id="addressTitle">新增收货地址</div>
            <div class="user-formdetail layout">
                <form id="form1"  method="post">
                    <div><label>*收货人姓名：</label><input id="addName" type="text" class="add-name txt" name="receiverAdd.receiverName" value="${receiverAdd.receiverName}"/> <span id="addNameTips"></span></div>
                    <div id="address_temp"> 
                    	<label>*所在地区：</label>
                        <select id="province" class="linkagesel m-mr5p"></select>
                    	<select id="city" class="linkagesel m-mr5p"></select>
                    	<select id="region" class="linkagesel"></select>
                        <div class="province-tips"><span id="provinceTips"></span></div>
                    </div>
                    <div class="inf-addr"><label>*街道地址：</label><textarea class="add-address" id="addAddress" ></textarea><span id="addressTips"></span></div>
                    <div><label>邮编：</label><input type="text" class="txt" name="receiverAdd.postCode" value="${receiverAdd.postCode}" id="zipcode"/><span id="zipcodeTips"></span></div>
                    <div><label>*移动电话：</label><input type="text" class="add-telephone txt" id="telephone" maxlength="11" name="receiverAdd.mobile" value="${receiverAdd.mobile}"/><span id="telephoneTips"></span></div>
                    <div><label>固定电话：</label><input type="text" class="add-phone txt" name="receiverAdd.telephone" value="${receiverAdd.telephone}" id="phoneNum"/><span>（格式如：021-5485624）</span><span id="phoneNumTips"></span></div>
                    <c:if test="${addTotal >= 10 }">
                    <div><label>*验证码：</label><span class="layout" ><input type="text" id="jCaptchaResponseImg" value="" name="jCaptchaResponse"/><img src="${_ctxPath}/validationCode.htm"  id="validateCodeimg"/></span><a onclick="nextValidateCode();" class="add-validate">看不清，换一张</a><span id="jCaptchaResponseImgTips"></span></div>
                    </c:if>
                    <div><label>设为默认：</label><input type="checkbox" class="addr-ck" id="checkLoad" /></div>
                    <div class="cf"><a class="btn-c btn-save" id="save"><span>保   存</span></a></div>
                    <input type="hidden" id="isDefault" name="receiverAdd.isDefault" value="${receiverAdd.isDefault}" >
                    <input type="hidden" id="opert" name="opert" value="${opert}">
                    <input type="hidden" value="${userId}" name="userId">
                    <input type="hidden" value="${userId}" name="receiverAdd.userId">
                    <input type="hidden" id="regionCodes" name="receiverAdd.regionCodes" value="${receiverAdd.regionCodes}">
                    <input type="hidden" id="area_prov" value='${receiverAdd.regionModel.provinceId}'/>
                    <input type="hidden" id="area_city" value='${receiverAdd.regionModel.cityId}'/>
                    <input type="hidden" id="area_region" value='${receiverAdd.regionModel.countyId}'/>
                    <input type="hidden" id="regionId" name="receiverAdd.regionId" value="${receiverAdd.regionId}">
                    <input type="hidden" id="receiverAddressId" name="receiverAdd.receiverAddressId" value="${receiverAdd.receiverAddressId}">
                    <input type="hidden" id="_ctxPath" value="${_ctxPath}" >
                    <input type="hidden" id="true_address" name="receiverAdd.receiveAddress" value="${receiverAdd.receiveAddress}">
					<input type="hidden" id="addTotal" name="addTotal" value="${addTotal}"/>
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
    <script src="${_jsPath}/plugin/select/linkage_sel.js"></script>
    <script src="${_jsPath}/pages/address.js?d=${_resVerion}"></script>
    <script type="text/javascript">
    	$(function(){
    		//隔行变色
   	    	$(".addr-tab tr:event").addClass("bg-color");
    	});
    </script>
</body>
</html>