<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh-CN" class="ua-window">
<head>
    <meta charset="utf-8"/>
    <title>确认订单信息-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath}/pages/shopping-cart.css?d=${_resVerion}" media="all">
</head>
<body>
	<jsp:include page="/WEB-INF/pages/include/head.jsp"></jsp:include> 
    <div class="m-w960p">
        <%--<div class="m-rel">--%>
            <%--<div class="cs-tel">客服热线  <span>021-64703131</span></div>--%>
        <%--</div>--%>
        <div class="cart-wrap m-mt5p">
        	<%--<div class="my-cart">收货信息</div>--%>
            <div class="step-status step-2"><span class="step">我的购物车</span><span class="step">确认订单信息</span><span>成功提交订单</span></div>
            <div class="order-show">
	            <div class="cart-item-count">
		           	 购物袋中的商品，为您保留   <span class="countdown" id="J_cartCount" data-config="{
		            'endTime':'<fmt:formatDate value="${cartExpireTime}" type="both"/>',
		            'nowTime':'<%=System.currentTimeMillis()/1000 %>',
		            'stopTips':'<span>活动结束谢谢参与！</span>'
		            }"></span> ，请及时结算。
	            </div>
        		<c:choose>
					<c:when test="${empty address}">
				          <div class="order-tit">收货信息</div>
                      <!--新注册用户-->
                          <jsp:include page="/WEB-INF/pages/order/editAddress.jsp"></jsp:include>
                          <div class="new-enter-btn cf">
                              <a class="btn-c" id="saveNew"><span>保存收货人地址</span></a>
                          </div>
                          <div class="receipt"></div>
                          <script type="text/javascript">
                              $("#saveNew").on("click",function(){
                              var $this = $(this);
                              var isDefault = $('#ckb_default').prop("checked") ? 1 : 0;
                              $('#isDefault').val(isDefault);
                              $("#regionId").val($("#region").val());
                              //校验数据格式
                              var result = $.formValidator.pageIsValid('101');
                                if(result){
                                  $.ajax({
                                      type:'POST',
                                      url: _ctxPath +'/order/order-saveAddress.htm',
                                      data:$("#addressForm").serialize(),
                                      success:function(data){
                                        if(data.code == "true"){
	                                        arr = JSON.parse(data.info);
	                                        renderDomAdd(arr);  //新增
	                                        $("#btn_newAddr").show();
	                                    	$(".address-wrap").hide();
                                        	$this.parent().remove();
                                            $(".editAddress").trigger("click");
                                        } else {
                                        	  if(data.info == "请先登陆!"){
                                                  //hack the common login
                                                  $this.addClass("login");
                                                  popupLogin($this);
                                              }else{
                                            	  popupDialog("保存地址出错！" + data.info);
                                              }
                                        }
                                  	}
                                  });
                                } else {
                                	return false;
                                }
                              });
                          </script>
                  <!--新注册用户 end-->
					</c:when>
						<c:otherwise>
					        <!--未登录时显示-->
				            <!-- <div class="un-login">
				                <div class="line cf"><a class="btn-a login" href="javascript:;"><span>登录</span></a><div class="mt"><a href="javascript:;" class="quick register">快速注册</a></div></div>
				                <div class="line cf"><a id='addAddress' class="btn-a" href="javascript:;"><span>填写收货信息</span></a><div class="mt">温馨提示：未注册用户，可直接填写收货地址快速购物。</div></div>
				            </div> -->
				            <!--未登录时显示 end-->
				                 <div class="receipt">
				                 	<div class="order-tit">收货信息</div>
				                    <c:set var="addressId" value="0" />
	                    			<c:forEach items="${address}" var="addr" varStatus="status" >
	                    			    <c:if test="${addr.isDefault == 1 ||  status.index == 0}">
	                    			    	<c:set var="addressId" value="${addr.receiverAddressId}" />
	                    			    </c:if>
										<div class="editAddress <c:if test="${addr.isDefault == 1  ||  status.index == 0}">selected</c:if> cf">
                                            <a href="javascript:;" value="${addr.receiverAddressId}" class="J-edit edit">修改本地址</a>
											<i>送至</i>
                                            <label><input type="radio" name="defaultAddress" <c:if test="${addr.isDefault == 1 ||  status.index == 0}">checked</c:if>><em class="province">${addr.region.province}</em><em class="city">${addr.region.city}</em><em class="county">${addr.region.county}</em><em class="receive-address">${addr.receiveAddress}</em>（<em class="receiver-name">${addr.receiverName}</em>收）<em class="mobile">${addr.mobile}</em></label>
                                            <!--默认地址显示<span>默认地址</span>-->
                                            <!--<c:if test="${addr.isDefault != 1}">
                                            非默认地址显示<a href="javascript:;" class="edit J-default">设为默认地址</a>
                                            </c:if>-->					</div>
									</c:forEach>
				                </div>
						</c:otherwise>
				</c:choose>
				<div class="btn cf <c:if test="${empty address}">m-hidden </c:if>"  id="btn_newAddr"><a href="javascript:;" class="btn-a" id="useNew"><span>使用新地址</span></a></div>
				<form  id="orderForm"  method="post"  action="${_ctxPath}/order/order-submitOrder.htm">    
            	<s:token/>
                <div class="order-tit">支付方式</div>
                <div class="pay-way">
                    <ul>
                        <li><label><input type="radio" checked><img src="${_imagesPath}/uploadfolder/zhifubao.jpg" alt=""></label></li>
                    </ul>
                </div>
                <div class="order-tit">发票信息</div>
                <div class="invoice-info cf">
                    <ul>
                        <li>
                            <div class="radio selected"><label><input type="radio" name="payment.hasInvoice" value="0" checked>暂时不需要发票</label></div>
                        </li>
                        <li>
                            <div class="radio"><label><input type="radio" name="payment.hasInvoice" value="1" id="hasInvoice" <c:if test="${payment.hasInvoice == 1 }">checked</c:if>>需要发票</label></div>
                            <div id="invoice-detail" <c:if test="${payment.hasInvoice == 1}">style='display: block;'</c:if>>
                            	<i></i>
                                <table width="100%">
                                    <tr>
                                        <td width="72">发票类型：</td>
                                        <td><label><input type="radio" checked> 普通发票</label></td>
                                    </tr>
                                    <tr>
                                        <td>发票抬头：</td>
                                        <td>
                                        	<span class="inv"><label><input type="radio" name="taitou" id="personal" checked> 个人</label></span>
                                        	<span class="inv"><label><input type="radio" name="taitou" <c:if test="${not empty payment.invoiceTitle}">checked</c:if>> 单位</label></span>
                                        </td>
                                    </tr>
                                    <tr id="gain" <c:if test="${not empty payment.invoiceTitle}">style="display: table-row;"</c:if>>
                                        <td valign="top">单位名称：</td>
                                        <td>
                                            <p class="unit-name"><input type="text" class="company-name" name="payment.invoiceTitle"  value="${payment.invoiceTitle}" id="payInvoice" onkeydown='if(event.keyCode==13) return false;'> <b class="c-red">*</b></p>
                                            <p class="tip">温馨提示：您所填写的所有内容都将被系统自动打印到发票上，所以请不要填写和发票抬头无关的信息。受品牌商客观因素影响，发票可能会发生延迟；如果延迟请见谅。</p>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>发票内容：</td>
                                        <td><label><input type="radio" checked> 明细</label></td>
                                    </tr>
                                </table>
                            </div>
                        </li>
                    </ul>
                </div>
                <input type="hidden" name="type" value="${param.type}" />
                <input type="hidden" id="isSubmit" value="false"/>
                <input type="hidden" id="userCouponId"  name="userCouponId"  value=""/>
                <div id="show">
                    <div class="order-list-hd m-mt8p">
                        <span class="c1">商品信息</span>
                        <span class="c2">商品名称</span>
                        <span class="c3">积分</span>
                        <span class="c4">单价（元）</span>
                        <span class="c5">数量</span>
                        <span class="c6">金额</span>
                        <span class="c7"></span>
                        <div class="cl"></div>
                    </div>
                    <c:set value="0" var="total" />
                    <c:set value="0" var="totalPostage"/>
                    <c:set var="_allProduct" value="$" />
                    <c:forEach items="${sellers}" var="seller" varStatus="status">
                    	<c:set value="${totalPostage + seller.postage}" var="totalPostage" />
                    	<input type="hidden" name="orders[${ status.index }].orderPostage.postage" value="${seller.postage}"/>
                    	<input type="hidden" name="orders[${ status.index }].receiverAddressId" class="hd_addr" value="${addressId}"/>
	                    <div class="order-brand m-mt20p" id="235">
	                        <div class="order-brand-top">
	                        	<span>${orderSource}商家： ${seller.companyName}</span>
	                        	<span>运费:
		                        	<c:choose>
		                        		<c:when test="${seller.postage == 0}">免运费</c:when>
		                        		<c:otherwise>${seller.postage }元</c:otherwise>
		                        	</c:choose>
		                        </span>
	                        </div>
	                        <div class="order-brand-main">
	                            <table width="100%">
	                                <tbody>
	                                <c:set value="0" var="sum" />
	                                <c:forEach items="${seller.productSkus}" var="sku" varStatus="product">
		                                <c:choose>
								            <c:when test="${param.type == 1}">
								             	<c:set value="${sku.product.secKillPrice}" var="salePrice" />
								            </c:when>
											<c:otherwise>
											    <c:set value="${sku.product.salePrice}" var="salePrice" />
											</c:otherwise>
										</c:choose>
	                                	<input type="hidden" name="orders[${ status.index }].items[${product.index}].productSkuId" value="${sku.productSkuId}" />
	                                	<input type="hidden" name="orders[${ status.index }].items[${product.index}].num" value="${sku.num}" />
										<c:set value="${_allProduct}${sku.productSkuId}-${sku.num}$" var="_allProduct"/>
	                                	<c:set value="${sum + salePrice * sku.num}" var="sum" />
		                                <tr id="1256" data-item="235">
		                                    <td width="105"><img src="<zx:thumbImage originalPath='${sku.product.defaultImage}' imageType='t12'></zx:thumbImage>" style="margin-left:15px" alt=""></td>
		                                    <td width="303">
		                                    	<span class="order-link">${sku.product.name}</span>
			                                    <p class="order-detail">
		                              		        <c:forEach items="${sku.productSkuOptionValues}" var="option" >
															${option.skuOptionValue.skuOption.skuOptionName}:
															<c:choose>
																<c:when test="${not empty option.overrideSkuOptionValue}">
																	${option.overrideSkuOptionValue}&nbsp;&nbsp;
																</c:when>
																<c:otherwise>
																	${option.skuOptionValue.skuOptionValue}&nbsp;&nbsp;
																</c:otherwise>
															</c:choose>
													</c:forEach>
			                                    </p> 
			                                    <c:forEach items="${sku.events}" var="event">
                                            	<p class="order-detail">优惠券：${event.eventName}</p>
                                            	</c:forEach>
		                                    </td>
		                                    <td width="95">${uhome:computerPoint(salePrice * sku.num)}</td>
		                                    <td width="105">${salePrice} <p class="origina">${sku.product.marketPrice}</p></td>
		                                    <td width="152">${sku.num}</td>
		                                    <td><span class="order-price">${salePrice * sku.num}</span></td>
		                                </tr>
	                                </c:forEach>
	                                <c:set value="${total + sum}" var="total" />
	                                </tbody>
	                            </table>
	                        </div>
	                    </div>
                    </c:forEach>
                </div>
                </form>
            </div>
           <input type="hidden" name="_allProduct" value="${_allProduct}"/>
            <p class="m-mt15p">*温馨提示：订单提交后将为你保留30分钟，30分钟后未能成功付款的订单；将视为自动放弃，订单将自动关闭。</p>
            <!--使用优惠券-->
            <div class="coupon-wp cf">
                <div class="inner">
                    <div class="hd cf"><span class="coup-action">使用优惠劵</span><span class="show-status"><a href="javascript:editCoupon();">修改</a><a href="javascript:releCoupon();">取消</a>已使用<em id="allowanceShow"></em>元优惠劵</span></div>
                    <div class="bd">
                        <div class="title">使用已添加优惠劵：</div>
                        <div class="item-details">
                            <div class="item-hd">
                                <ul>
                                    <li class="t1">优惠劵号</li>
                                    <li class="t2">金额（元）</li>
                                    <li class="t3">最小消费金额</li>
                                    <li class="t4">有效期</li>
                                    <li class="t6">使用范围</li>
                                    <li class="t5">操作</li>
                                </ul>
                            </div>
                            <div id="showCoupons" class="item-bd">
                            
                            </div>
                        </div>
                        <div class="activ-coup cf">
                            <span class="activ-txt">激活新优惠劵：</span><input type="text" id="couponNo" /><a class="btn-a" href="javascript:atcAnduseCoupon();"><span>激活</span></a>
                        </div>
                    </div>
                </div>
            </div>
            <!--使用优惠券 end-->
            <div class="sum-wp m-mt10p">
                <div class="coup-sum-txt">商品总额(<span class="coin">¥</span><em>${total}</em>) + 运费(<span class="coin">¥</span><em>${totalPostage }</em>) - 优惠金额(<span class="coin">¥</span><em id="allowanceShow1">0.00</em>)</div>
                <div class="cf m-mt5p">
                    <div class="sum-price m-fr">
                        <p class="pro-sum"><span>应付总额：</span><var id="total">¥${total+totalPostage}</var></p>
                        <input type="hidden" id="totalValue" value="${total+totalPostage }" />
                        <%--<p class="transport-sum"><span>运费：</span><var>¥0.00</var></p>--%>
                    </div>
                </div>
                <div class="cf m-mt10p">
                    <div class="m-fl"><a class="go-back" href="javascript:history.go(-1);"><i></i>返回修改购物车</a></div>
                    <div class="m-fr" style="padding-right: 5px;"><a class="btn-c" href="javascript:;" id='btn_accept'><span>提交订单</span></a></div>
                </div>
            </div>
       </div>
    </div>
    </div>
    <!--footer.jsp放置在这-->
    <jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
    <script type="text/javascript" src="${_jsPath}/plugin/bigdecimal/mathcontext.js"></script>
    <script type="text/javascript" src="${_jsPath}/plugin/bigdecimal/bigdecimal.js"></script>
    <script type="text/javascript" src="${_jsPath}/pages/confirm_order.js?d=${_resVerion}"></script>
    <script type="text/javascript">
        var pass = '${default_password}';
        var type = ${param.type};
        var secKill = <%=com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku.TYPE_SEC_KILL%>;
        var specialSeller = <%=com.ytoxl.module.uhome.uhomebase.dataobject.ProductSku.TYPE_SPECIAL_SELLER%>;
        var isSubmit = '<%=com.ytoxl.module.uhome.uhomebase.common.CodeConstants.MULTIPLE_SUBMIT %>';
        var empty = '<%=com.ytoxl.module.uhome.uhomebase.common.CodeConstants.PARAMETER_ERROR %>';
        var unlogin = '<%=com.ytoxl.module.uhome.uhomebase.common.CodeConstants.UNLOGIN_ERROR %>';
        var timeout = '<%=com.ytoxl.module.uhome.uhomebase.common.CodeConstants.LOGIN_TIMEOUT_ERROR %>';
        var notActivity = '<%=com.ytoxl.module.uhome.uhomebase.common.CodeConstants.PRODUCT_NOTACTIVITY_ERROR %>';
        var underStock = '<%=com.ytoxl.module.uhome.uhomebase.common.CodeConstants.PRODUCT_UNDERSTOCK_ERROR %>';
        var address = '<%=com.ytoxl.module.uhome.uhomebase.common.CodeConstants.ORDER_ADDRESS_ERROR %>';
	    <%--$(".btn-a .login,.quick .register").click(function(){--%>
	    	<%--var obj = $(this)--%>
	    	<%--popupLogin(obj);--%>
	    <%--});--%>
    </script>
</body>
</html>