<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.Suggest"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.common.CodeConstants"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <cache:cache refresh="${_needRefresh}" key="seo-cate-${productCategory.productCategoryId}" cron="${_seoRefreshCron }">
	  	<c:if test="${empty seoTitle }">
	    	<title>品牌折扣，限时特卖，品牌打折网－新龙直销</title>
		</c:if>
		<c:if test="${!empty seoTitle }">
			<title>${seoTitle}</title>
		</c:if>
		<c:if test="${empty seoKeyWords }">
	    	<meta name="keywords" content="品牌折扣，限时特卖，品牌打折网" >
		</c:if>
		<c:if test="${!empty seoKeyWords }">
			<meta name="keywords" content="${seoKeyWords}">
		</c:if>
		
		<c:if test="${empty seoDescription }">
	    	<meta name="description" content="新龙直销主打品牌折扣，限时打折、特卖、低至1-7折。商城数万种品牌折扣商品，每天限时打折、限时特卖，所有商品100%正品。" >
		</c:if>
		<c:if test="${!empty seoDescription}">
			<meta name="description" content="${seoDescription}">
		</c:if>
  	</cache:cache>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath}/pages/index.css?d=${_resVerion}" media="all">
</head>
<body>
	<!--顶部广告-->
	<c:if test="${not empty homeTopAdv}">
	<div class="top-ad-wp" id="J_topAd">
		<div class="top-ad">
		<a href="${homeTopAdv.linkUrl}" target="_blank">
			<img src="<zx:thumbImage originalPath='${homeTopAdv.imageUrl}' imageType='t26'></zx:thumbImage>" width="960" height="70" />
		</a>
		</div>
	</div>
	</c:if>
	<!--顶部广告 end-->
    <!--头部-->
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
    <!--头部 end-->
    <fmt:setLocale value="zh_CN"/>
    <!--主体 start-->
	<div class="wrap cf">
		<!--首页焦点图 end-->
		<div class="mian-block cf">
			<!--商品区块 start-->
			<div class="goods-block">
	            <div class="goods-show">
	              <!-- 最新上线开始 -->
	              <ul class="tab-item" style="display:block;">
	                <!--单个商品循环部分 start-->
	                <c:forEach items="${lastOnLineBrands}" var="lastOnLine">
	                <li class="cf">
	                    <div class="tab-brand">
	                        <!--品牌logo-->
	                        <div class="brand-logo">
	                        <a target="_blank" href="${_ctxPath }/plan-${lastOnLine.planId}.htm"><%-- ${_ctxPath }/brand-${lastOnLine.brand.brandId}.htm --%>
	                            <img src="<zx:thumbImage originalPath='${lastOnLine.brand.logoImageUrl}' imageType='t1'></zx:thumbImage>" alt="${lastOnLine.brand.name}">
	                        </a>
	                        </div>
	                        <div class="discount">
	                        <!--折扣-->
	                        <p><span><var>${lastOnLine.product.rebate }</var>折起</span></p>
	                        <p><span><fmt:formatDate value="${lastOnLine.startTime}" pattern="MM月dd日"/>-<fmt:formatDate value="${lastOnLine.endTime}" pattern="MM月dd日"/></span></p>
	                    </div>
	                    </div>
	                    <!--特卖活动图片-->
	                  <div class="pic">
	                    <a target="_blank" href="${_ctxPath }/plan-${lastOnLine.planId}.htm">
	                        <img src="<zx:thumbImage originalPath='${lastOnLine.imageUrl}' imageType='t13'></zx:thumbImage>" alt="${lastOnLine.name}">
	                    </a>
	                  </div>
					  <br class="cl" />
					  <div class="bm-info">
                            <i class="info-name">
								<a target="_blank" href="${_ctxPath }/plan-${lastOnLine.planId}.htm">
	                        	${uhome:cutString(lastOnLine.name,10)}
	                        	</a>
							</i>
                            <i class="info-time">剩余 <span class="J-countdown" data-config="{
	                          'endTime':'<fmt:formatDate value="${lastOnLine.endTime }" type="both"/>',
	                          'nowTime':'${currentTime}',
	                          'stopTips':'<span>活动结束谢谢参与！</span>',
	                          'timeStampTow':true
	                            }"></span>
							</i>
                            <i class="info-story"><a target="_blank" href="${_ctxPath }/p/${lastOnLine.brand.brandId}/">品牌介绍 ></a></i>
                      </div>
	                </li>
	                </c:forEach>
	                <!--单个商品循环部分 end-->
	              </ul>
	              <!-- 最新上线结束 -->
	              <!-- 最受欢迎的品牌开始  -->
	              <ul class="tab-item">
	                <!--单个商品循环部分 start-->
	                <c:forEach items="${mostPopularBrands}" var="mostPopular">
	                <li class="cf">
	                    <div class="tab-brand">
	                        <!--品牌logo-->
	                        <div class="brand-logo">
	                        <a target="_blank" href="${_ctxPath }/p/${mostPopular.brand.brandId}/">
	                        	<img src="<zx:thumbImage originalPath='${mostPopular.brand.logoImageUrl}' imageType='t1'></zx:thumbImage>" alt="${mostPopular.brand.name}">
	                        </a>
	                        </div>
	                        <div class="discount">
	                        <!--折扣-->
	                        <p><var>${mostPopular.product.rebate}</var>折起</p>
	                        <p>
	                        	<a target="_blank" href="${_ctxPath }/plan-${mostPopular.planId}.htm">
	                        	${uhome:cutString(mostPopular.name,10)}
	                        	</a>
	                       	</p>
	                        <span><fmt:formatDate value="${mostPopular.startTime }" pattern="MM月dd日"/>-<fmt:formatDate value="${mostPopular.endTime }" pattern="MM月dd日"/></span>
	                      <div class="info-time">
	                        <span class="J-countdown" data-config="{
	                          'endTime':'<fmt:formatDate value="${mostPopular.endTime}" type="both"/>',
	                          'nowTime':'${currentTime}',
	                          'stopTips':'<span>活动结束谢谢参与！</span>',
	                          'timeStampTow':true
	                            }"></span>
	                        </div>
	                    </div>
	                    </div>
	                    <!--特卖活动图片-->
	                  <div class="pic">
	                    <a target="_blank" href="${_ctxPath }/plan-${mostPopular.planId}.htm">
	                    <img src="<zx:thumbImage originalPath='${mostPopular.imageUrl}' imageType='t13'></zx:thumbImage>" alt="${mostPopular.name}">
	                    </a>
	                  </div>
	                </li>
	                </c:forEach>
	                <!--单个商品循环部分 end-->
	              </ul>
	              <!-- 最受欢迎的品牌结束  -->
	              <!-- 折扣最低排序开始 -->
	              <ul class="tab-item">
	                <!--单个商品循环部分 start-->
	                <c:forEach items="${lowestDiscountBrands}" var="lowestDiscount">
	                <li class="cf">
	                    <div class="tab-brand">
	                        <!--品牌logo-->
	                        <div class="brand-logo">
	                         <a target="_blank" href="${_ctxPath }/p/${lowestDiscount.brand.brandId}/">
	                            <img src="<zx:thumbImage originalPath='${lowestDiscount.brand.logoImageUrl}' imageType='t1'></zx:thumbImage>" alt="${lowestDiscount.brand.name}">
	                         </a>
	                        </div>
	                        <div class="discount">
	                        <!--折扣-->
	                        <p><var>${lowestDiscount.product.rebate}</var>折起</p>
	                        <p>
	                        	<a target="_blank" href="${_ctxPath}/plan-${lowestDiscount.planId}.htm">
	                        	${uhome:cutString(lowestDiscount.name,10)}
	                        	</a>
	                       	</p>
	                        <span><fmt:formatDate value="${lowestDiscount.startTime}" pattern="MM月dd日"/>-<fmt:formatDate value="${lowestDiscount.endTime}" pattern="MM月dd日"/></span>
	                      <div class="info-time">
	                        <span class="J-countdown" data-config="{
	                          'endTime':'<fmt:formatDate value="${lowestDiscount.endTime}" type="both"/>',
	                          'nowTime':'${currentTime}',
	                          'stopTips':'<span>活动结束谢谢参与！</span>',
	                          'timeStampTow':true
	                            }"></span>
	                        </div>
	                    </div>
	                    </div>
	                    <!--特卖活动图片-->
	                  <div class="pic">
	                    <a target="_blank" href="${_ctxPath}/plan-${lowestDiscount.planId}.htm">
	                        <img src="<zx:thumbImage originalPath='${lowestDiscount.imageUrl}' imageType='t13'></zx:thumbImage>" alt="${lowestDiscount.name}">
	                    </a>
	                  </div>
	                </li>
	                </c:forEach>
	                <!--单个商品循环部分 end-->
	              </ul>
	              <!-- 折扣最低排序结束 -->
	            </div>
	      	<!--即将上线区块 start-->
			<div class="upcom-product" id="J_forOnline">
			<c:if test="${fn:length(soonBrands) > 0}">
				<div class="upcom-pro-tit">
					<span class="impor">即将上线</span>
				</div>
				<ul class="upcom-item">
				<c:forEach items="${soonBrands}" var="soon">
					<li>
						<!-- <a href="javascript:;"> -->
							<!--?新图  后台上传 t25-->
							<img src="<zx:thumbImage originalPath='${soon.coverImageUrl}' imageType='t25'></zx:thumbImage>" width="316" height="250"/>
						<!-- </a> -->
						<div class="upcom-brand-info">
							<p>${soon.name}</p>
							<div class="sale-book">
	                            <div class="tipbox"><span>请输入正确的邮箱</span><b>◆</b><i>◆</i></div>
								<input id="postSubSoonEmail${soon.planId}" type="text" class="text-input" data-default="输入邮箱，订阅优惠信息" /><a href="javascript:;" onclick="postSubSoonEmail(${soon.brand.brandId},${soon.planId},this,<%=MailSubscribe.TYPE_TOBEONTHELINE%>,<%=MailSubscribe.STATUS_SUBSCRIBE%>)" class="index-btn"><span>开售通知</span></a>
							</div>
						</div>
					</li>
				</c:forEach>	
				</ul>
			</c:if>
			</div>
			<!--即将上线区块 end-->
			<!--往期特卖区块 start-->
			<cache:cache time="${_cacheSec}" refresh="${_needRefresh}" key="historySpecial">
			<div class="upcom-product" id="J_spike">
			<c:if test="${fn:length(advs) > 0}">
				<div class="upcom-pro-tit">
					<span class="impor">往期特卖</span>
				</div>
				<ul class="upcom-item">
				<c:forEach items="${advs}" var="adv">
					<li>
						<a href="${adv.linkUrl}" target="_blank">
							<img src="<zx:thumbImage originalPath='${adv.imageUrl}' imageType='t25'></zx:thumbImage>" width="316" height="250"/>
						</a>
	                    <div class="upcom-brand-info">
	                    	<p>${adv.name}</p>
	                    	<div class="spike-over"><span>此特卖专场已结束</span></div>
	                    </div>
					</li>
				</c:forEach>	
				</ul>
			</c:if>
			</div>
			</cache:cache>
			<!--往期特卖区块 end-->
			<div class="all-sale-book">
				<div class="all-sale-book-b cf">
					<div class="sale-book-input">
	                    <div class="tipbox"><span>请输入正确的邮箱</span><b>◆</b><i>◆</i></div>
						<input type="text" class="text-input" data-default="请输入邮箱" /><a id="postSubscriptionEmail" href="javascript:;" class="index-btn"><span>订阅品牌特卖</span></a>
					</div>
	        		<!-- Baidu Button START -->
	        		<div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare" data="'url':'${_domain}'">
	        				<span class="bds_txt">分享到：</span>
						   	<a class="bds_tsina" href="#" title="新浪微博"></a>
		            		<a class="bds_tqq" href="#" title="腾讯微博"></a>
							<a class="bds_qzone" href="#" title="QQ空间"></a>
							<a class="bds_douban" href="#" title="豆瓣网"></a>
							<a class="bds_kaixin001" href="#" title="开心网"></a>
							<a class="bds_renren" href="#" title="人人网"></a>
							<a class="bds_taobao" href="#" title="淘江湖"></a>
	        		</div>
	                <script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=0" ></script>
	                <script type="text/javascript" id="bdshell_js"></script>
	                <script type="text/javascript">
		                /**
		    			 * 在这里定义bds_config
		    			 */
		    			var bds_config = {
		    				'bdDes':'品牌折扣，限时特卖，品牌打折网－新龙直销-${_domain}',
		    				'bdText':'品牌折扣，限时特卖，品牌打折网－新龙直销-${_domain}',
		    				'bdPic':'${_domain}web-resource/images/uploadfolder/logo.jpg',
		    				'searchPic':'1'
		    			}
	                    document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
	                </script>
	                <!-- Baidu Button END -->
				</div>
			</div>
			</div>
			<!--商品区块 end-->
			<!--通告+广告区块 start-->
			<div class="notice">
				
				<!--热销品牌 start-->
				<cache:cache time="${_cacheSec}" refresh="${_needRefresh}" key="hotBrands">
				<div class="right-block-t">
					<b><%-- 热销品牌 --%>品牌预告 </b>
				</div>
				<div class="bespeak-brand">
				<ul class="cf">
				<c:forEach items="${hotBrands}" var="brand">
					<li>
		            <a href="javascript:" class="bra-pic">
		            	<img src="<zx:thumbImage originalPath='${brand.logoImageUrl}' imageType='t21'></zx:thumbImage>" alt="" width="94" height="48">
		            </a>
		            <div class="sale-wrap">
		                <div class="sale-pic">
		                	<a target="_blank" href="${_ctxPath }/p/${brand.brandId}/">
		                	<img src="<zx:thumbImage originalPath='${brand.brandBannerImageUrl}' imageType='t18'></zx:thumbImage>" alt="" width="320" height="180">
		                	</a>
		                </div>
		                <div class="sale-book">
		                	<div class="tipbox"><span>请输入正确的邮箱</span><b>◆</b><i>◆</i></div>
		                    <input id="${brand.brandId }_email" type="text" class="text-input" data-default="输入邮箱，订阅优惠信息" /><a onclick="brandOpenInform(${brand.brandId },this,<%=MailSubscribe.TYPE_BRAND_SUBSCRIBE%>,<%=MailSubscribe.STATUS_SUBSCRIBE%>);" href="javascript:;" class="index-btn"><span>开售通知</span></a>
		                </div>
					</div>
		        	</li>
	        	</c:forEach>
				</ul>
				<div class="cl"></div>
	      		</div>
	      		</cache:cache>
	      		<!--热销品牌 end-->
	      		<!--人气商品 start-->
	      		<cache:cache time="${_cacheSec}" refresh="${_needRefresh}" key="index-2-popularProduct">
				<div class="right-block-t">
					<b>人气商品</b>
				</div>
				<div class="hot-goods-block">
					<ul>
					<c:forEach items="${mostHits}" var="mostHit" begin="0" end="1" varStatus="status">
		                <li class="hot-top">
		                    <div class="hot-goods-img">
		                    	<a target="_blank" href="${_ctxPath }/item-${mostHit.productId}.htm" title="${mostHit.name}">
		                        <img src="<zx:thumbImage originalPath='${mostHit.defaultImage}' imageType='t666'/>" alt="${mostHit.name}" width="80" height="80">
		                      </a>
		                    </div>
		                    <div class="hot-goods-info">
		                    	<a target="_blank" href="${_ctxPath }/item-${mostHit.productId}.htm">
		                        <p>${uhome:cutString(mostHit.brand.name,6)}</p>
		                        <p>${uhome:cutString(mostHit.name,9)}</p>
		                        <div class="price"><span class="symbol">&yen;</span>${mostHit.salePrice}</div>
		                        <p><del>原价：${mostHit.marketPrice}</del></p>
		                      </a>
		                    </div>
		                    <div class="hot-num"><!--排序class--><i class="top-${status.index+1}"></i></div>
		                </li>
					</c:forEach>
	                </ul>
	                <ul id="J-topGood">
	                <c:forEach items="${mostHits}" var="mostHit" begin="2" varStatus="status">
						<li <c:if test='${status.index == 2}'>class="hot-top"</c:if>>
							<div class="hot-goods-name">${uhome:cutString(mostHit.brand.name,6)} ${uhome:cutString(mostHit.name,13-fn:length(mostHit.brand.name))}</div>
							<div class="hot-goods-price price"><span class="symbol">&yen;</span>${mostHit.salePrice}</div>
							<div class="hot-goods-img">
								<a target="_blank" href="${_ctxPath }/item-${mostHit.productId}.htm" title="${mostHit.name}">
									<img src="<zx:thumbImage originalPath='${mostHit.defaultImage}' imageType='t666'/>" alt="${mostHit.name}" width="80" height="80">
								</a>
							</div>
							<div class="hot-goods-info">
								<a target="_blank" href="${_ctxPath }/item-${mostHit.productId}.htm">
									<p>${uhome:cutString(mostHit.brand.name,6)}</p>
									<p>${uhome:cutString(mostHit.name,9)}</p>
									<div class="price"><span class="symbol">&yen;</span>${mostHit.salePrice}</div>
									<p><del>原价：${mostHit.marketPrice}</del></p>
								</a>
							</div>
							<div class="hot-num"><i class="top-${status.index+1}"></i></div>
						</li>
					</c:forEach>
					</ul>
				</div>
				</cache:cache>
				<!--人气商品 end-->
	            <!--广告区块 start-->
	          	 	 <c:forEach items="${fiveAdvs}" var="five">
		           		<c:if test="${five.isLogin==null||five.isLogin==0 }">
		           			<div class="m-mt10p">
		           				<c:if test="${five.linkUrl != null&&five.linkUrl !=''}">
		           					<a href="${five.linkUrl}" target="_blank" >
										<c:if test="${five.advPosition.code =='home_right_adv_300'}">
											<img src="<zx:thumbImage originalPath='${five.imageUrl}' imageType='t27'></zx:thumbImage>" width="${five.advPosition.width}" height="${five.advPosition.height}"/>
										</c:if>
										<c:if test="${five.advPosition.code =='home_right_adv_80'}">
											<img src="<zx:thumbImage originalPath='${five.imageUrl}' imageType='t28'></zx:thumbImage>" width="${five.advPosition.width}" height="${five.advPosition.height}"/>
										</c:if>
									</a>
		           				</c:if>
		           				<c:if test="${five.linkUrl == null||five.linkUrl ==''}">
		           					<c:if test="${five.advPosition.code =='home_right_adv_300'}">
										<img src="<zx:thumbImage originalPath='${five.imageUrl}' imageType='t27'></zx:thumbImage>" width="${five.advPosition.width}" height="${five.advPosition.height}"/>
									</c:if>
									<c:if test="${five.advPosition.code =='home_right_adv_80'}">
										<img src="<zx:thumbImage originalPath='${five.imageUrl}' imageType='t28'></zx:thumbImage>" width="${five.advPosition.width}" height="${five.advPosition.height}"/>
									</c:if>
		           				</c:if>
							</div>
		           		</c:if>
		           		<c:if test="${five.isLogin==1}">
			           		<div class="m-mt10p">
				           		<a href="javascript:void(0);" onclick="checkLogin('${five.linkUrl}','${five.advertisementId}',<%=CodeConstants.UNLOGIN_ERROR %>,<%=CodeConstants.LOGIN_TIMEOUT_ERROR %>)" >
										<c:if test="${five.advPosition.code =='home_right_adv_300'}">
											<img src="<zx:thumbImage originalPath='${five.imageUrl}' imageType='t27'></zx:thumbImage>" width="${five.advPosition.width}" height="${five.advPosition.height}"/>
										</c:if>
										<c:if test="${five.advPosition.code =='home_right_adv_80'}">
											<img src="<zx:thumbImage originalPath='${five.imageUrl}' imageType='t28'></zx:thumbImage>" width="${five.advPosition.width}" height="${five.advPosition.height}"/>
										</c:if></a>
								<input type="hidden" id="${five.advertisementId}" />
							</div>
		           		</c:if>
					</c:forEach>
	            <!--广告区块 end-->
			</div>
			<!--通告+广告区块 end-->
		</div>
		<div class="cl"></div>
		<!-- 品牌集中营 start-->
 		 <cache:cache time="${_cacheSec}" refresh="${_needRefresh}" key="brands"> 
			<div class="upcom-product" id="J_camp">
			 <div class="upcom-pro-tit">
			   	<a href="${_ctxPath }/brand/brands.htm" target="_blank" class="m-fr">更多</a>
			       <span class="impor"><a href="${_ctxPath }/brand/brands.htm" target="_blank">品牌集中营</a></span>
			       <span class="tab-ctrl"><i class="cur">服装箱包</i>|<i id=2>母婴用品</i>|<i id=3>家具用品</i>|<i id=4>美容护肤</i></span>
			 </div>
				<%@include file="/WEB-INF/pages/include/brandCamp.jsp"%>
		  </div>
		 </cache:cache>
		<!-- 品牌集中营 end-->
	</div>
	<!--主体 end-->
    <!--底部区块-->
   	<%@include file="/WEB-INF/pages/include/foot.jsp"%>
    <!--底部区块 end-->
    <!--右侧导航 start-->
    <div class="sidebar-block screen-default">
        <ul class="sidebar-list">
            <li class="hover"><a href="javascript:;"><i></i>最新上线</a></li>
            <li><a href="javascript:;"><i></i>最受欢迎</a></li>
            <li><a href="javascript:;"><i></i>最低折扣</a></li>
            <li><a href="javascript:;"><i></i>即将上线</a></li>
            <li><a href="javascript:;"><i></i>往期特卖</a></li>
        </ul>
        <ul>
            <li class="sidebar-cart">
                <a href="${_ctxPath}/order/order-shoppingCart.htm"><div class="sidebar-cart-ico"><i></i></div>购物车(<span class="show-sum">${totalNum}</span>)</a>
            </li>
            <li class="sidebar-totop"><a href="javascript:;"><i></i></a></li>
        </ul>
    </div>
    <!--右侧导航 end-->
    <!--微信扫描层 start-->
    <div class="left-sider"><img src="${_ctxPath}/web-resource/images/qr_code_2.gif" width="130" height="131" /></div>
    <!--微信扫描层 end-->
    <script type="text/javascript" src="${_jsPath }/pages/index.js?d=${_resVerion}"></script>
    <script type="text/javascript">
    //邮件订阅
    subEmail("#postSubscriptionEmail",<%=MailSubscribe.TYPE_SPECIAL_SELLER%>,<%=MailSubscribe.STATUS_SUBSCRIBE%>)
    </script>
</body>
</html>