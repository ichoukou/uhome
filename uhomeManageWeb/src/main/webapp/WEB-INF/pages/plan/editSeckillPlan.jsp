<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Plan" %>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!--star 新增秒杀 -->
<form id="seckill">
<div class="addSale-table">
	<input type="hidden" name="plan.planId" value="${plan.planId }" id="planId"/>
	<input type="hidden" name="plan.type"  value="<%=Plan.TYPE_SEC_KILL%>"/>
	<input type="hidden" name="plan.status"  value="<%=Plan.STATUS_UNRELEASE%>"/>
	<input type="hidden" value="${plan.planProducts[0].productId }" name="plan.planProducts[0].productId" id="planProductId"/>
	<div class="addSale-time">
		<span>选择秒杀日期：</span> 
		<input type="text" class="Wdate" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d+1}',readOnly:true,onpicked:pickedFunc})" 
			value="<fmt:formatDate value='${plan.startTime }' pattern='yyyy-MM-dd'/>" name="plan.startTime" id="seckillStartTime" />
		<select class="addSale-select-brand" name="plan.brandId" id="brand">
			<option value="">选择品牌</option>
			<c:forEach items="${brands }" var="brand">
				<option value="${brand.brandId }"
					<c:if test="${brand.brandId == plan.brandId }">
						selected="selected"
					</c:if>
				>${brand.name }</option>
			</c:forEach>
		</select>
		<select class="addSale-select-brand" name="plan.productCategoryId" id="productCategory">
            <option value="">选择商品品类</option>
			<c:forEach items="${childProductCategories }" var="productCategory">
			<option value="${productCategory.productCategoryId }"
				<c:if test="${plan.productCategoryId==productCategory.productCategoryId }">
				selected = "selected"
				</c:if>
			>${productCategory.name}</option>
			</c:forEach>
		</select>
		<div id="seckillTip"></div>
	</div>
	<div class="addSale-select-product">
		<span id="loading" style="display: none;">数据正在加载中……</span>
	</div>
	<div class="addSale-img">
		<table>
			<tr>
				<td>
					<div class="position">
						商品权重：<input type="text" class="addSale-name" value="${plan.planProducts[0].rank }" name="plan.planProducts[0].rank" id="rank" />
						<div id="rankTip"></div>
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>
</form>
<!--end 新增秒杀 -->
<script type="text/javascript">
	//加载商品信息方法
	function loadProducts(){
		$("#loading").show();
		var value = {
				"startTime" : $("#seckillStartTime").val(),
				"brandId" : $("#brand").val(),
				"productCategoryId" : $("#productCategory").val(),
				"planId" : $("#planId").val()
		};
		$.ajax({
			type:'POST',
			url: _ctxPath + '/admin/plan/plan-listSellersForSeckill.htm',
			data: value,
			success:function(html){
				$("#loading").hide();
				$(".addSale-select-product").append(html);
				//当操作为编辑时
				<c:if test="${not empty plan}">
					$(".addSale-product").find(":radio[value='${plan.planProducts[0].productSkuId}']").attr("checked","checked");
				</c:if>
			}
		});
	}

	<c:if test="${not empty plan}">
		loadProducts();
	</c:if>
	//日期控件事件
	function pickedFunc(){
		$(".addSale-saler-list").empty();
		if($("#brand").val() != "" && $("#productCategory").val() != ""){
			loadProducts();
		}
	}
	//品牌下拉框事件
	$("#brand").bind({
		focus:function(){
			$(this).change(function(){
				$(".addSale-saler-list").empty();
				if($("#seckillStartTime").val() != "" && $("#productCategory").val() != "" && $(this).val() != ""){
					loadProducts();
				}
			});
		},
		blur:function(){
			$(this).unbind("change");
		}
	});
	
 	//品类下拉框change事件
	$("#productCategory").bind({
		focus:function(){
			$(this).change(function(){
				$(".addSale-saler-list").empty();
				if($("#seckillStartTime").val() != "" && $("#brand").val() != "" && $(this).val() != ""){
					loadProducts();
				}
			});
		},
		blur:function(){
			$(this).unbind("change");
		}
	});
 	
	//验证
	$.formValidator.initConfig({formID:"seckill",theme:"Default",errorFocus:false,onError:function(){}});
	$("#seckillStartTime").formValidator({tipID:"seckillTip",onShow:"请选择排期时间",onFocus:"请选择排期时间",onCorrect:"时间选择正确"}).regexValidator({regExp:"date",dataType:"enum",onError:"日期格式不正确"});
	$("#brand").formValidator({tipID:"seckillTip",onShow:"请选择排期品牌",onFocus:"排期品牌必须选择",onCorrect:"谢谢你的配合"}).inputValidator({min:1,onError: "排期品牌必须选择!"});
	$("#productCategory").formValidator({tipID:"seckillTip",onShow:"请选择品类",onFocus:"品类必须选择",onCorrect:"谢谢你的配合"}).inputValidator({min:1,onError: "品类必须选择!"});
	$("#rank").formValidator({empty:true,onShow:"请输入1—10之间的正整数",onFocus:"请输入1—10之间的正整数",onCorrect:"商品权重输入正确"}).inputValidator({min:1,max:10,type:"number",onError:"权重必须为1—10之间的正整数"}).regexValidator({regExp:"^([1-9]|10)$",dataType:"string",onError:"权重必须为1—10之间的正整数"});
</script>