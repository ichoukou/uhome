<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>邀请好友-个人中心-${_webSiteName }</title>
    <link rel="shortcut icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
    <link rel="icon" type="image/x-icon" href="${_ctxPath}/favicon.ico" media="screen" />
<link type="text/css"  rel="stylesheet" href="${_cssPath }/pages/invite.css?d=${_resVerion}"/>
</head>
<body>
	<!--头部-->
	<%@include file="/WEB-INF/pages/include/head.jsp"%>
    <!--头部 end-->
    <div class="m-w960p cf">
    <!--面包屑-->
   <div class="crumbs"><a href="${_domain }">${_webSiteName} ></a><a href="${_domain }/user/orders.htm">个人中心  ></a>邀请好友</div>
    <!--面包屑 end-->
    	<%@include file="/WEB-INF/pages/include/left.jsp"%>
        <div class="inf-detail m-mt20p m-mb10p">
            <div class="inf-title fon-big">
              	  邀请好友
            </div>
            <div class="inv-cont cf">
                <div class="cf">
                    <div class="m-fl">
                        <div class="inv-title">这是您的邀请链接，您可以通过QQ、MSN、论坛、博客、邮件发给好友：</div>
                        <div class="inv-tips">（可复制邀请链接发送给您的好友）</div>
                        <div>
                        <textarea class="inv-text input-tip"
                        data-default="刚发现了一个时尚家居母婴类的特卖网站，在里面能已低至一折的价格买到
                        各大名牌产品，我已经是会员了，大家也来看看吧！ url">${_shareContent}</textarea>
                        </div>
                    </div>
                    <div class="btn-save m-fr"><a class="btn-c"><span>复制</span></a></div>
                </div>
                <div class="inv-fenx cf">
                    <div class="inv-title m-fl">分享至：</div>
                    <!-- Baidu Button start -->
                    <div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare" data="{'url':'${_domain}'}">
                        <a class="bds_tsina" title="分享到新浪微博" href="#"></a>
                        <a class="bds_tqq" title="分享到腾讯微博" href="#"></a>
                        <a class="bds_renren" title="分享到人人网" href="#"></a>
                        <a class="bds_tqf" title="分享到腾讯朋友" href="#"></a>
                        <a class="bds_douban" title="分享到豆瓣网" href="#"></a>
                        <a class="bds_taobao" title="淘江湖" href="#"></a>
                        <a class="bds_qzone" title="QQ空间"  href="#"></a>
                    </div>
                    <script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=0" ></script>
	                <script type="text/javascript" id="bdshell_js"></script>
	                <script type="text/javascript">
	                    /**
		    			 * 在这里定义bds_config
		    			 */
		    			var bds_config = {
		    				'bdDes':'${_shareContent}',
		    				'bdText':'${_shareContent}',
		    				'bdPic':'${_domain}web-resource/images/uploadfolder/logo.jpg',
		    				'searchPic':'1'
		    			}
                        document.getElementById("bdshell_js").src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000)
                    </script>
                    <!-- Baidu Button END -->
                </div>
            </div>
        </div>
    </div>
    <!--底部区块-->
    <%@include file="/WEB-INF/pages/include/foot.jsp"%>	
    <!--底部区块 end-->
    <script type="text/javascript">
    	/*复制功能*/
			function copy2border(){
			 try{
				 	share_url = $(".inv-text").text();
					window.clipboardData.setData('Text',share_url);
				}catch(e){
					alert('您的浏览器不支持复制，请手动选择复制！');
					$(".inv-text").select();
				}
			}
			$(".btn-c").live("click",function(){
	    		copy2border();
	    	});
    </script>
</body>
</html>