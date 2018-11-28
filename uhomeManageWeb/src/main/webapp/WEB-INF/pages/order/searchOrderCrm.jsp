<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!-- 新增记录弹层 -->
<div>
	<form method="post" id="form1">
		<div class="msg">
			<div class="msg-main">
				<ul>
					<c:forEach items="${orderCrmList}" var="orderCrm" varStatus="status">
						<li>
							<p>
								<b>${orderCrm.userName }</b><span> <fmt:formatDate
										value='${orderCrm.recordTime }' pattern='yyyy-MM-dd HH:mm' />
								</span>
							</p>
							<p>${orderCrm.remark }</p>
						</li>
					</c:forEach>
				</ul>
			</div>
			<div class="msg-foot">
				<div class="m-clear">
					<input type="hidden" name="orderCrm.orderId" id='orderIdCrm'/>
					<textarea class="J-txt" name="orderCrm.remark" id="remark" onkeypress="return KeyDown()"></textarea>
					<a class="save-msg J-save" href="javascript:void(0);"	onclick="saveOrderCrm()">记 录</a>
				</div>
				<div id="remarkTip"></div>
			</div>
			
		</div>
	</form>
</div>
<script type="text/javascript">
//验证
$(document).ready(function(){
$.formValidator.initConfig({
  validatorGroup: '100021',
  formID: 'form1',
  errorFocus:false, //错误时不聚焦到第一个控件
  theme: 'Default',
  wideWord : false
});
$("#remark").formValidator({
	validatorGroup: '100021',
	onShow:"",
	onCorrect:"",
	onFocus:"请输入500字符以内的内容",
	tipID:'remarkTip'
	}).inputValidator({
		min:1,
		max:500,
		onErrorMin:"请输入内容",
		onError:"请输入500字符以内的内容"
	}).functionValidator({
        fun: function (val, elem) {
            if(!$.trim(val)){
                return "内容不能为空";
            }
        }
    });
});

</script>