<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xmlns:wb="http://open.weibo.com/wb">
<head>
	<meta charset="UTF-8">
	<title>活动</title>
	<style type="text/css">
	*{margin:0;padding:0;}
	img{vertical-align:middle;border:0;}
	.wrap{width:960px;margin:0 auto;}
	.main{border:8px solid #e2dfdf;border-top:0;background:url('image/grid.gif') repeat;}
	.main .block{width:860px;margin:0 auto;}
	.rel{position:relative;}
	.abso-a,.abso-b,.abso-c,.abso-d{position:absolute;}
	.abso-a{top:300px;left:412px;}
	.abso-b{top:227px;left:203px;}
	.abso-c{top:274px;left:203px;}
	.abso-d{top:226px;left:270px;}
	#bdshare .bds_tsina{background:url('image/pic_04.gif') no-repeat;width:228px;height:51px;display:block;background-position:0 0!important;}
	</style>
	<!-- 新浪微博js -->
	<script src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
	<div class="wrap">
		<div class="top-block rel">
			<img src="image/pic_01.jpg" alt="" />
			<img src="image/pic_14.jpg" alt="" usemap="#Map" />
            <map name="Map">
              <area shape="poly" coords="676,404,601,480,676,559,758,482" href="http://www.xlbuy365.com/" target="_blank">
            </map>
			<div class="abso-a">
				<!-- Baidu Button start -->
                    <div id="bdshare" class="bdshare_t bds_tools_32 get-codes-bdshare" data="{'url':'${_domain}'}">
                        <a class="bds_tsina" title="分享到新浪微博" href="javascript;;"></a>
              </div>				
					<script type="text/javascript" id="bdshare_js" data="type=tools&amp;uid=0" src="http://bdimg.share.baidu.com/static/js/bds_s_v2.js?cdnversion=378609"></script> 
					<script type="text/javascript" id="bdshell_js"></script> 
					<script type="text/javascript">
						/**
						 * 在这里定义bds_config
						 */
						var bds_config = {
							'bdDes':'购物还能享免单？这是天上掉馅饼的节奏吗？小伙伴们千万别错过咯~快快猛击：${_domain}（分享自@新龙直销 ）',
							'bdText':'购物还能享免单？这是天上掉馅饼的节奏吗？小伙伴们千万别错过咯~快快猛击：${_domain}（分享自@新龙直销）',
							'bdPic':'${_domain}event/201309/image/pic_12.jpg',
							'searchPic':'1'
						}

						document.getElementById('bdshell_js').src = "http://bdimg.share.baidu.com/static/js/shell_v2.js?cdnversion=" + Math.ceil(new Date()/3600000);
					</script>
                <!-- Baidu Button END -->
		  </div>
	  </div>
		<div class="main">
			<div class="block"><img src="image/pic_03.jpg" alt="" /></div>
			<div class="block"><img src="image/pic_05.jpg" alt="" /></div>
			<div class="block"><img src="image/pic_06.jpg" alt="" /></div>
			<div class="block"><img src="image/pic_07.jpg" alt="" /></div>
			<div class="block"><img src="image/pic_08.jpg" alt="" /></div>
			<div class="block rel">
				<img src="image/pic_09.jpg" alt="" />
				<div class="abso-b">
					<wb:follow-button uid="3237821860" type="red_1" width="62" height="24" ></wb:follow-button>
				</div>
				<div class="abso-d"><img src="image/txt_sina.gif" alt="" /></div>
				<div class="abso-c"><a href="http://www.xlbuy365.com/" target="_blank"><img src="image/pic_11.gif" alt="" /></a></div>
			</div>
		</div>
	</div>
</body>
</html>