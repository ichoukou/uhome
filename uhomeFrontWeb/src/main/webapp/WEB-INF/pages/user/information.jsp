<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
  <meta charset="utf-8"/>
    <title>我的资料-个人中心-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link type="text/css"  rel="stylesheet" href="${_cssPath}/pages/changepwd.css?d=${_resVerion}" media="all"/>
</head>
<body >
    <%@include file="/WEB-INF/pages/include/head.jsp"%>
    <div class="m-w960p cf">
	 <!--面包屑-->
      <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a><a href="${_domain }/user/orders.htm">个人中心  ></a>我的资料</div>
        <!--面包屑 end-->
        <jsp:include page="/WEB-INF/pages/include/left.jsp"></jsp:include>
        <div class="inf-detail m-mt20p">
            <div class="inf-title fon-big">我的资料</div>
            <div class="user-formdetail layout">
                <form id="form1" action="" >
                    <%-- <p>亲爱的<span class="fon-big">${userinfo.name}</span>，请填写真实的资料，有助于好友找到你哦。</p> --%>
                    <div><label class="col-l">登录名：</label><span>${username}</span></div>
                    <div><label class="col-l">真实姓名：</label><input type="text" id="addName"  class="add-name txt" name="userinfo.name" value="${userinfo.name}"/><span id="addNameTips"></span></div>
                    <div>
                    	<label class="col-l">性别：</label>
                    	<label class="inf-fomlf"><input type="radio" name="userinfo.gender" value="1" <c:if test='${userinfo.gender eq 1}'>checked="checked"</c:if> class="inf-sex"/>男</label>
                    	<label class="inf-fomlf"><input type="radio" name="userinfo.gender" value="0" <c:if test="${userinfo.gender eq 0}">checked="checked"</c:if> class="inf-sex"/>女</label>
                    </div>
                    <div>
                    	<label class="col-l">生日：</label>
                        <input class="Wdate txt" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd'})" readonly="" type="text" name="userinfo.birthday" value="<fmt:formatDate value='${userinfo.birthday}' pattern='yyyy-MM-dd' />" ><span>（生日年份为保密）</span>
                    </div>
                    <div><label class="col-l">移动电话：</label><input type="text" class="add-telephone txt" id="mobile"  name="userinfo.mobile" value="${userinfo.mobile}" maxlength="11"  /><span id="mobileTips"></span></div>
                    <div><label class="col-l">固定电话：</label><!-- <input type="text" size="3"/> --><input type="text" class="txt" id="telephone" name="userinfo.telephone" value="${userinfo.telephone}"/><span>（格式如：021-60904618）</span><span id="telephoneTips"></span></div>
                    <div>	
                    	<label class="col-l">*所在地区：</label>
                    	<select id="province" class="linkagesel m-mr5p"></select>
                    	<select id="city" class="linkagesel m-mr5p"></select>
                    	<select id="region" class="linkagesel"></select>
                    	<span class="region-tip"><span id="regionTips"></span></span>
                    	<input type="hidden"  id="regionId" value="${userinfo.regionId}" name="userinfo.regionId"/>
			            <input type="hidden"  id="area_prov" value='${userinfo.regionModel.provinceId}'/>
						<input type="hidden"  id="area_city" value='${userinfo.regionModel.cityId}'/>
						<input type="hidden"  id="area_region" value='${userinfo.regionModel.countyId}'/>
                   	</div> 
                    <div><label class="col-l">地址：</label><textarea rows="" cols="" name="userinfo.address" class="inf-addr txt" id="userAddress" style="width:282px;height:48px;">${userinfo.address}</textarea><span id="userAddressTips"></span></div>
                    <div><label class="col-l">*联系邮箱：</label><input type="text" class="add-email txt" id="addrEmail" maxlength="20" name="userinfo.email" value="${userinfo.email}"/><span id="emailTips"></span></div>
                    <p class="btn-wp cf"><a class="btn-c btn-save" id="save" href="javascript:;"><span>保   存</span></a>
                    </div>
                    <input type="hidden" name="userinfo.userInfoId" value="${userinfo.userInfoId}" />
                    <input type="hidden" name="userinfo.userId" value="${userinfo.userId}" />
                    <input type="hidden" id="_ctxPath" value="${_ctxPath}" >
                </form>
            </div>
        </div>
    </div>
    <jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
    <script src="${_jsPath}/plugin/select/linkage_sel.js"></script>
    <script type="text/javascript" src="${_jsPath }/plugin/calendar/WdatePicker.js"></script>
    <script type="text/javascript">
		 $(function(){
			 //验证
			  $.formValidator.initConfig({
   	    	    validatorGroup: '2001',
   	    	    formID: 'form1',
   	    	 	errorFocus:false,
   	    	    theme: 'yto'
   	    	});
			 $("#addName").formValidator({
			    	validatorGroup: '2001',
			    	empty:true,
			        onFocus:"请输入1—20个字符组成的姓名",
			        tipID:'addNameTips'
			    }).inputValidator({
			        min:1,
			        max:20,
			        empty:false,
			        onError:"请输入1—20个字符组成的姓名"
			    });
			 $("#mobile").formValidator({
			    	validatorGroup: '2001',
			    	empty:true,
			        onFocus:"请输入正确的手机号码",
			        tipID:'mobileTips'
			    }).inputValidator({
			        min:11,
			        max:11,
			        onError:"请输入正确的手机号码"
			    }).regexValidator({
			    	regExp:"^1\\d{10}$",
			    	dataType:"string",
			        onError:"请输入正确的手机号码"
			    });
			 $("#telephone").formValidator({
			    	validatorGroup: '2001',
			    	empty:true,
			        onFocus:"请输入正确的固定电话",
			        tipID:'telephoneTips'
			    }).inputValidator({
			        onError:"请输入正确的固定电话"
			    }).regexValidator({
			        regExp:"tel",
			        dataType:"enum",
			        onError:"请输入正确的固定电话"
			    });
			 $("#userAddress").formValidator({
			    	validatorGroup: '2001',
			    	empty:true,
			        onFocus:"请填写地址",
			        tipID:'userAddressTips'
			    }).inputValidator({
        			max:50,
        			empty:false,
			        onError:"请输入少于50个字符的地址"
			    });
			 $("#addrEmail").formValidator({
			    	validatorGroup: '2001',
			    	empty:false,
			        onFocus:"请输入正确的邮箱",
			        tipID:'emailTips'
			    }).inputValidator({
			        min:6,
			        max:100,
			        onError:"请输入的正确的邮箱"
			    }).regexValidator({
			        regExp:"email",
			        dataType:"enum",
			        onError:"请输入的正确的邮箱"
			    });
			 	//验证三级联动地址
			 	$(".linkagesel").formValidator({
		        	validatorGroup: '2001',
		            onShow:"请选择地区",
		            onFocus:"请选择具体地区",
		            onCorrect:"",
		            tipID:'regionTips'
		        }).inputValidator({min:1,onError: "请选择具体地区!"});
				var _ctxPath =$('#_ctxPath').val();
                var prov = $("#area_prov").val(), city = $("#area_city").val(), district = $("#area_region").val();
                var linkage = new linkageSelect();
                linkage.init({
                    "oneSel":['#province','请选择',prov],
                    "twoSel":['#city', '请选择',city],
                    "threeSel":['#region', '请选择',district]
                });
				 
			    $("#save").on("click",function(){
			    	var result = $.formValidator.pageIsValid('2001');
					if (result){
						var getpassFrom =$('#form1');
				    	//将最后一级的code赋值给 regionId 后台通过regionCode查询到regionId
				    	$("#regionId").val($("#region").val());
				    	$.ajax({
							type: 'POST',
							url: _ctxPath+'/user/user-updateUserInfo.htm',
							data: getpassFrom.serializeArray(),
							success: function(data){
								 $.dialog({
						                title: false,
						                content: data.info,
						                time: 2000
						            });
								 $(".d-close").hide();
						        },
							dataType:'json'
						});
					}
			    });
			   
			});
		
    </script>
</body>
</html>