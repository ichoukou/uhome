<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
 			<c:choose>
			<c:when test="${ sellers!=null && fn:length(sellers)>0}">
                    <div class="cart-item-count">
                    <span class="countdown" id="J_cartCount" data-config="{
                    'endTime':'<fmt:formatDate value="${cartExpireTime}" type="both"/>',
                    'nowTime':'<%=System.currentTimeMillis()/1000 %>',
                    'stopTips':'<span>购物车中还没有商品，赶紧选购吧！ </span>'
                    }"></span> 后购物车将清空，请及时结算。
                        <script type="text/javascript">
                    		var cartFlag = true;       
                            $("#J_cartCount").each(function () {
                            	var $this = $(this),
                            		data = $this.attr('data-config');
                            	data = eval('(' + data + ')');
                            	data.callback = function(){
                            		if(cartFlag){
                            			var listBox = $(".cart-list");
                                		listBox.empty();
                                		$(".show-sum").html("0");
                                		listBox.append("<div class='cart-empty'>购物车中还没有商品，赶紧选购吧！</div>");
                                		bFlag = false;
                            		}
                                };
                            	$this.Merlin({
                            		"countDown": data
                            	});
                            });
                        </script>
                    </div>
				   <ul>
				      <c:set value="0" var="sum" />
				      <c:set value="0" var="tatal" />
				      <c:forEach items="${sellers}" var="seller" varStatus="status">
				      	<c:forEach items="${seller.productSkus}" var="sku" varStatus="status">
					          <li>
					          	  <c:set value="${sum + sku.num}" var="sum" />
					          	  <c:set value="${tatal + sku.product.salePrice * sku.num}" var="tatal" />
					          	  <div class="cart-pro-img"><a href="${_ctxPath}/item-${sku.product.productId}.htm"><img src="<zx:thumbImage originalPath='${sku.product.defaultImage}' imageType='t666'></zx:thumbImage>" width="80" height="80" /></a></div>
								  <div class="cart-pro-info">
										<p><a href="${_ctxPath}/item-${sku.product.productId}.htm">${sku.product.brand.name}<br/>${sku.product.name}</a></p>
										<%-- 
										<p>
											<c:forEach items="${sku.productSkuOptionValues}" var="option" varStatus="status">
												<span>${option.skuOptionValue.skuOption.skuOptionName}:
												<c:choose>
													<c:when test="${not empty option.overrideSkuOptionValue}">
														${option.overrideSkuOptionValue}<br>
													</c:when>
													<c:otherwise>
														${option.skuOptionValue.skuOptionValue}<br>
													</c:otherwise>
												</c:choose></span>
											</c:forEach>
										</p>
										 --%>
								  </div>
								  <div class="cart-pro-price">
										<span class="coin">&yen;</span>
										<span>${sku.product.salePrice}</span>
										<b>x</b>
										<span>${sku.num}</span>
								  </div>
								  <div class="del" value="${sku.productSkuId}"></div>
					          </li>
				          </c:forEach>
				       </c:forEach>
			      </ul>
			      <div class="cart-total">
						共计<span class="show-text">${sum}</span> 件商品        总计：<span class="symbol">&yen;</span>
						<span>${tatal}</span>
						<a href="${_ctxPath}/order/order-shoppingCart.htm" class="clearing">去购物车结算</a>
				 </div>
			</c:when>
			<c:otherwise>
				<!--购物车为空-->
				<div class="cart-empty">
					购物车中还没有商品，赶紧选购吧！
				</div>
			</c:otherwise>
		</c:choose>