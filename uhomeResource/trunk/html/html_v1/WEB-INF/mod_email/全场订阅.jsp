<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>全场订阅</title>
</head>
<body>
<#function thumbnail img type>
<#return fileThumbPath + img + '_' + type + img?substring(img?last_index_of('.'))>
</#function>
<table width="100%" cellpadding="0" cellspacing="0" border="0">
  <tbody>
    <tr>
      <td align="center" style="background-color:#fff;" height="10"><table width="730" cellpadding="0" cellspacing="0">
          <tbody>
            <tr>
              <td style="font-size:12px; color:#999" width="330"> 如果邮件没有正常显示，请直接访问 <a target="_blank" style="color:#6699ff; text-decoration:none;" href="${contextPath }"> 新龙直销</a></td>
            </tr>
          </tbody>
        </table></td>
    </tr>
    <tr>
      <td height="10"></td>
    </tr>
    <tr>
      <td align="center" valign="top"><table width="750" cellpadding="0" cellspacing="0" border="0" style=" border:1px solid #dedede;background-color:#f2f2f2;font-size:12px; line-height:18px;color:#333;font-family:tahoma,arial,5b8b4f53,sans-serif;">
          <tbody>
            <tr>
              <td height="30"></td>
            </tr>
            <tr>
              <td align="center"><table width="730" cellpadding="0" cellspacing="0" style="font-size:12px; line-height:18px;">
                  <tbody>
                    <tr>
                      <td width="208" height="72" valign="bottom"><a target="_blank" href="${contextPath}"> <img style="color:#e95705; font-size:36px; font-weight:bold; line-height:40px;" border="0" src="${imagesPath}/logo.gif" width="208" height="72"></a></td>
                      <td width="460" align="right" valign="bottom"><table width="460" cellpadding="0" cellspacing="0" style="font-size:12px; line-height:28px;">
                          <tbody>
                            <tr>
                              <td align="right"><img border="0" src="${imagesPath}/mail/des1.gif" width="450" height="43" alt="新龙描述" /></td>
                              <td width="10"></td>
                            </tr>
                          </tbody>
                        </table></td>
                    </tr>
                  </tbody>
                </table></td>
            </tr>
            <tr>
              <td height="10"></td>
            </tr>
            <tr>
              <td valign="top" align="center" height="42"><table style="background:url(${imagesPath}/menu_bg.gif) repeat-x;" width="730" cellpadding="0" cellspacing="0" height="38">
                  <tbody>
                    <tr>
                      <td width="5"></td>
                      <td valign="middle" align="center" width="70"><a style="font-size:14px; color:#fff; text-decoration:none;" target="_blank" href="${contextPath}/default.htm"> 首页</a></td>
                      <td width="1" valign="middle" style="color:#44abad;"> |</td>
                      <td valign="middle" align="center" width="90"><a style="font-size:14px; color:#fff; text-decoration:none;" target="_blank" href="${contextPath}/default.htm"> 服装鞋包</a></td>
                      <td width="1" valign="middle" style="color:#44abad;"> |</td>
                      <td valign="middle" align="center" width="84"><a style="font-size:14px; color:#fff; text-decoration:none;" target="_blank" href="${contextPath}/default.htm"> 母婴日用</a></td>
                      <td width="1" valign="middle" style="color:#44abad;"> |</td>
                      <td valign="middle" align="center" width="84"><a style="font-size:14px; color:#fff; text-decoration:none;" target="_blank" href="${contextPath}/default.htm"> 日用百货</a></td>
                      <td width="1" valign="middle" style="color:#44abad;"> |</td>
                      <td valign="middle" align="center" width="70"><a style="font-size:14px; color:#fff; text-decoration:none;" target="_blank" href="${contextPath}/default.htm"> 美容护肤</a></td>
                      <td width="1" valign="middle" style="color:#44abad;"> |</td>
                      <td valign="middle" align="center" width="84"><a style="font-size:14px; color:#fff; text-decoration:none;" target="_blank" href="${contextPath}/default.htm"> 进口商品</a></td>
                      <td width="1" valign="middle" style="color:#44abad;"> |</td>
                      <td valign="middle" align="center" width="70"><a style="font-size:14px; color:#fff; text-decoration:none;" target="_blank" href="${contextPath }/seckills.htm"> 秒杀</a></td>
                      <td></td>
                    </tr>
                  </tbody>
                </table></td>
            </tr>
            <tr>
              <td height="15"></td>
            </tr>
            <#if seckillPlans??>
            <tr>
              <td align="center" valign="top"><table width="730" cellpadding="0" cellspacing="0">
                  <tbody>
                    <tr>
                      <td height="18" colspan="4" align="left"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><div style=" color:#fc7c00;font-size:18px; line-height:18px; font-weight:bold;">·今日秒杀</div></td>
                            <td width="100" align="right"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td height="15"></td>
                    </tr>
                    <tr>
                    	<#list seckillPlans as seckillPlan>
                      <td><table width="360" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #ccc5c2; background-color:#ffffff;">
                          <tr>
                            <td width="200" height="200" rowspan="5">
                            		<#assign seckillProduct = seckillPlan.products[0]>
                            		<#assign imageUrls = seckillProduct.imageUrls?split(',')>
	                            <a href="${contextPath}/item-${seckillProduct.productId?c}.htm" target="_blank">
		                            <img alt="${seckillProduct.name}" border="0" title="${seckillProduct.name}" src="${thumbnail(imageUrls[0], 't5')}" />
	                            </a>
                            </td>
                            <td width="10" rowspan="5">&nbsp;</td>
                            <td height="8"></td>
                            <td width="10" rowspan="5">&nbsp;</td>
                          </tr>
                          <tr>
                            <td height="40" align="left"><a href="${contextPath}/brand-${seckillPlan.brandId?c}.htm" target="_blank" style="font-size:14px; font-weight:bold; text-decoration:none; color:#333;">${seckillPlan.brand.name}</a></td>
                          </tr>
                          <tr>
                            <td align="left" style="font-size:12px; color:#333333;">${seckillProduct.name}</td>
                          </tr>
                          <tr>
                            <td height="40" align="left" valign="bottom"><span style="font-size:12px; color:#3b3b3b; font-weight:bold; vertical-align:text-bottom;">秒杀价：</span><span style="font-size:24px; color:#f96619; font-family:微软雅黑;">￥${seckillProduct.secKillPrice}</span></td>
                          </tr>
                          <tr>
                            <td align="left"><a href="${contextPath}/seckill-${seckillPlan.planId?c}.htm" target="_blank"><img border="0" src="${imagesPath}/mail/btn1.gif" width="107" height="29" alt="立即抢购" /></a></td>
                          </tr>
                        </table></td>
                        <#if seckillPlan_index=1><#break></#if>
                      <td width="10"></td>
                      </#list>
                    </tr>
                    <tr>
                      <td height="12"></td>
                    </tr>
                    <tr>
                      <td colspan="3" align="right"><a target="_blank" href="${contextPath}/seckills.htm" style="font-size:12px; line-height:18px; color:#fc7c00;text-decoration:underline;"> 更多秒杀商品&gt;&gt;</a></td>
                    </tr>
                  </tbody>
                </table></td>
            </tr>
            <tr>
              <td height="30"></td>
            </tr>
            </#if>
            <#if mostPopularBrands??>
            <tr>
              <td align="center" valign="top"><table width="730" cellpadding="0" cellspacing="0">
                  <tbody>
                    <tr>
                      <td height="18" colspan="3" align="left"><div style=" color:#fc7c00;font-size:18px; line-height:18px; font-weight:bold;">·正在热卖</div></td>
                    </tr>
                    <tr>
                      <td height="15"></td>
                    </tr>
                    <#list mostPopularBrands as plan>
                    <tr>
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #ccc5c2; background-color:#ffffff;">
                          <tr>
                            <td align="center"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td height="100"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="258" align="center">
	                                        <a href="${contextPath}/brand-${plan.brand.brandId?c}.htm" target="_blank">
	                                        	<img border="0" src="${thumbnail(plan.brand.logoImageUrl, 't1')}" alt="${plan.brand.name}" width="150" height="75">
	                                        </a>
                                        </td>
                                        <td>&nbsp;</td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr>
                                  <td><table width="100%" height="49" border="0" cellspacing="0" cellpadding="0" style="background:url(${imagesPath}/mail/des2.gif) no-repeat;">
                                      <tr>
                                        <td width="135" align="center"><span style="font-size:36px; font-weight:bold; color:#ffffff; font-family:Tahoma, Geneva, sans-serif;">
                                        <#if plan.products[0]??>
                                        	${plan.products[0].rebate}
                                        </#if>
                                        </span><span style="font-size:18px; font-weight:bold; color:#ffffff; vertical-align:text-bottom;">折起</span></td>
                                        <td align="left"><a href="${contextPath}/brand-${plan.brand.brandId?c}.htm" target="_blank"><img border="0" src="${imagesPath}/mail/btn2.gif" width="85" height="32" alt="去看看" /></a></td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr>
                                  <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td width="258" height="30" align="center" style="font-size:12px; color:#333333;">${plan.startTime?string("yyyy/MM/dd")}—${plan.endTime?string("yyyy/MM/dd")}</td>
                                        <td>&nbsp;</td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table></td>
                            <td width="420">
	                            <a href="${contextPath}/plan-${plan.planId?c}.htm" target="_blank">
	                            	<img border="0" src="${thumbnail(plan.imageUrl, 't13')}" alt="${plan.name}" width="420" height="180">
	                            </a>
                            </td>
                          </tr>
                        </table></td>
                    </tr>
                     <#if plan_index=1><#break></#if>
                    <tr>
                      <td height="10"></td>
                    </tr>
                    </#list>
                    <tr>
                      <td height="15"></td>
                    </tr>
                  </tbody>
                </table></td>
            </tr>
            </#if>
            <tr>
              <td height="15"></td>
            </tr>
            <tr>
              <td height="74" valign="middle" align="center" style="border-top:1px solid #dfdfdf;">
              <table style="padding:0 10px;">
              		<tr>
              			<td width="600" height="55" valign="middle" align="left">
              				<span style="font-size:14px;">欲知更多详情请登录 <a style="color:#f68333;" href="${contextPath }" target="_blank">${contextPath }</a> 查询</span>&nbsp;
              			</td>
              			<td rowspan="2" height="169" valign="middle">
              				<img src="${imagesPath}/mail/QR_Code.jpg" width="122" hieght="122" border="0" />
              			</td>
              		</tr>
              		<tr>
              			<td valign="top" align="left">
              				<span>
              					<a href="http://e.weibo.com/326060800"><img style="vertical-align:-10px;" src="http://img.t.sinajs.cn/t4/style/images/staticlogo/download/Buttons/32x32/Weibo_Buttons_32x32_yellow_back.png" width="32" height="32" border="0"/></a>
              					<a href="http://e.weibo.com/326060800" style="font-size:14px;color:#333;">关注新浪微博</a>
              				</span>
              			</td>
              		</tr>
              	</table>
              </td>
            </tr>
          </tbody>
        </table></td>
    </tr>
    <tr>
      <td align="center" valign="top">
      	<table width="730" cellspacing="0" cellpadding="0" border="0" style="font-size:12px; line-height:18px; color:#999;">
          <tbody>
            <tr>
              <td height="25" align="left"></td>
            </tr>
          </tbody>
        </table>
       </td>
    </tr>
  </tbody>
</table>
</body>
</html>