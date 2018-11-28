<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomecontent.dataobject.MailSubscribe"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="zh-CN" class="ua-window">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>公告详情 — ${_webSiteName}。</title>
	<meta name="keywords" content="品牌折扣，限时特卖，品牌打折网" >
    <meta name="description" content="新龙直销主打品牌折扣，限时打折、特卖、低至1-7折。商城数万种品牌折扣商品，每天限时打折、限时特卖，所有商品100%正品。" >
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="stylesheet" type="text/css" href="${_cssPath }/pages/index.css?d=${_resVerion}" media="all">
    <style type="text/css">
    .notice-wrap{width:622px;float:left;margin:10px 0 0;border:1px solid #e6e6e6;padding:10px;}
    .notice-wrap .no-h3{text-align:center;border-bottom:1px solid #e6e6e6;padding-bottom:5px;}
    .notice-meta{padding:10px 30px 18px;}
    .notice-meta .remark{float:left;}
    .notice-meta .remark .stem{float:left;width:170px;text-overflow:ellipsis;overflow:hidden;white-space: nowrap;color:#0255bc;}
    .notice-meta .share{float:right;margin-top:-5px;}
	.notice-meta .share .m-fl{line-height:27px;}
    .notice-contxt{padding:0 30px;font-size:14px;line-height:1.6em;}
	.notice-contxt img{max-width:562px;scale:expression((this.offsetWidth > this.offsetHeight)?(this.style.width = this.offsetWidth >= 562 ? "562px" : "auto"):"auto");}
    .notice-nav{padding:20px 30px;}
    .notice-nav p{padding:3px 0;}
    .notice-nav p a:hover{text-decoration:underline;}
    </style>
    
    <script type="text/javascript" >
	    function getList(type,e) {
	    	var total = ${noticePage.total};
			var currentPage = ${noticePage.currentPage};
			var url = '${_ctxPath }/notice/index-' + type + '-' + total + '-' + currentPage + '.htm';
			window.location.href = url;
			//阻止默认浏览器动作(W3C) 
			if ( e && e.preventDefault ) 
				e.preventDefault(); 
			//IE中阻止函数器默认动作的方式 
			else
				window.event.returnValue = false; 
			return false;
		}
    
		function getDetail(noticeId,e) {
			var total = ${noticePage.total};
			var currentPage = ${noticePage.currentPage};
			var url = '${_ctxPath }/notice/detail-' + noticeId + '-' + total + '-' + currentPage + '.htm';
			window.location.href = url;
			//阻止默认浏览器动作(W3C) 
			if ( e && e.preventDefault ) 
				e.preventDefault(); 
			//IE中阻止函数器默认动作的方式 
			else
				window.event.returnValue = false; 
			return false; 
		}

    </script>
</head>
<body>
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
	<div class="m-w960p">
		<!--面包屑-->
        <div class="crumbs"><a href="${_ctxPath }">${_webSiteName } ></a><a href="javascript:;" onclick="getList(${notice.type},this)">公告区  ></a><c:if test="${notice.type == '1'}">活动资讯</c:if><c:if test="${notice.type == '2'}">网站公告</c:if></div>
        <!--面包屑 end-->
        <div class="notice-detail cf">
        	<div class="notice-wrap">
        		<h2 class="no-h3">${notice.title}</h2>
        		<div class="notice-meta cf">
        			<div class="remark"><span class="stem">来源：${notice.source}</span> | <fmt:formatDate value='${notice.createTime}' pattern='yyyy-MM-dd HH:mm:ss'/></div>
        			<div class="share">
						<!-- Baidu Button START -->
	        		
	                
	                <div class="share-wrap cf">
                        <span class="m-fl">分享到：</span>
                        <!-- Baidu Button BEGIN -->
                        <div id="bdshare" class="bdshare_t bds_tools get-codes-bdshare">
	        				<a class="bds_tsina" title="新浪微博"></a>
	        				<a class="bds_tqq" title="腾讯微博"></a>
							<a class="bds_qzone" title="QQ空间"></a>
							<a class="bds_douban" title="豆瓣网"></a>
							<a class="bds_kaixin001" title="开心网"></a>
							<a class="bds_renren" title="人人网"></a>
							<a class="bds_taobao" title="淘江湖"></a>
    					</div>
                    </div>
	                <!-- Baidu Button END -->
					</div>
        		</div>
        		<div class="notice-contxt">
        			<!-- 编辑器内容发布于此 -->
        			${notice.content}
        			<!-- 编辑器内容发布于此 end-->
        		</div>
        		<div class="notice-nav">
        			<c:if test="${previousNotice.noticeId != null && previousNotice.noticeId != ''}"><p><a href="javascript:;" onclick="getDetail(${previousNotice.noticeId},this)">上一篇：${previousNotice.title}</a></p></c:if>
					<c:if test="${nextNotice.noticeId != null && nextNotice.noticeId != ''}"><p><a href="javascript:;" onclick="getDetail(${nextNotice.noticeId},this)">下一篇：${nextNotice.title}</a></p></c:if>
        		</div>
        	</div>
			<div class="notice">
				<!--热销品牌 start-->
				<cache:cache time="${_cacheSec}" refresh="${_needRefresh}" key="hotBrands">
				<div class="right-block-t">
					<b>品牌预告</b>
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
			                    <input id="${brand.brandId }_email" type="text" class="text-input" data-default="输入邮箱，订阅优惠信息" /><a onclick="brandOpenInform(${brand.brandId },this);" href="javascript:;" class="index-btn"><span>开售通知</span></a>
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
	      		<cache:cache time="${_cacheSec}" refresh="${_needRefresh}" key="popularProduct">
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
		    </div>
	    </div>
	</div>
	<%@include file="/WEB-INF/pages/include/foot.jsp"%>
	<script type="text/javascript" src="${_jsPath }/pages/index.js?d=${_resVerion}"></script>
	
	<script type="text/javascript" id="bdshare_js" data="type=tools&mini=1" ></script> 
    <script type="text/javascript" id="bdshell_js"></script> 
    <script type="text/javascript">
	/**
	 * 在这里定义bds_config
	 */
	var pic = "${notice.imgUrl}";
	if("" == pic){
		pic = "${_domain}web-resource/images/uploadfolder/logo.jpg";
	}
	var bds_config = {
		'bdDes':'品牌折扣，限时特卖，品牌打折网－新龙直销-${notice.title}-${_ctxPath }/notice/detail-${notice.noticeId}-${noticePage.total}-${noticePage.currentPage}.htm',	
		'bdText':'品牌折扣，限时特卖，品牌打折网－新龙直销-${notice.title}-${_ctxPath }/notice/detail-${notice.noticeId}-${noticePage.total}-${noticePage.currentPage}.htm',		
		'bdPic':pic,	
		'searchPic':'1'

	}
	document.getElementById('bdshell_js').src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000);
</script>
</body>
</html>