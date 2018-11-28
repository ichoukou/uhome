<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>特卖订阅</title>
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
              <td align="right" style="font-size:12px; color:#999;"> 如果您不愿意继续接收，请:<a target="_blank" style="color:#6699ff; text-decoration:none;" href="${mailUrl}"> 退订本类邮件</a></td>
            </tr>
          </tbody>
        </table></td>
    </tr>
    <tr>
      <td height="10"></td>
    </tr>
    <tr>
      <td align="center" valign="top"><table width="750" cellpadding="0" cellspacing="0" border="0" style="font-size:12px; line-height:18px;color:#333;font-family:tahoma,arial,5b8b4f53,sans-serif;">
          <tbody>
            <tr>
              <td height="30"></td>
            </tr>
            <tr>
              <td align="center"><table width="730" cellpadding="0" cellspacing="0" style="font-size:12px; line-height:18px;">
                  <tbody>
                    <tr>
                      <td width="319" height="89" valign="bottom"><a target="_blank" href="${contextPath }"> <img style="color:#e95705; font-size:36px; font-weight:bold; line-height:40px;" border="0" src="${imagesPath}/uploadfolder/logo.jpg" width="319" height="89"> </a></td>
                      <td width="460" align="right" valign="bottom"><img border="0" src="${imagesPath}/mail/des1.gif" width="222" height="50" alt="新龙描述" /></td>
                    </tr>
                  </tbody>
                </table></td>
            </tr>
            <tr>
              <td height="10"></td>
            </tr>
            <tr>
              <td valign="top" align="center" height="42"><table style="background:#ff5702;" width="100%" cellpadding="0" cellspacing="0" height="38">
                  <tbody>
                    <tr>
                      <td width="5"></td>
                      <td valign="middle" align="center" width="110"><a style="font-size:14px; color:#fff; text-decoration:none;" target="_blank" href="${contextPath }/">全部特卖</a></td>
                      <td valign="middle" align="center" width="110"><a style="font-size:14px; color:#fff; text-decoration:none;" target="_blank" href="${contextPath }/fsxb/">服饰箱包</a></td>
                      <td valign="middle" align="center" width="110"><a style="font-size:14px; color:#fff; text-decoration:none;" target="_blank" href="${contextPath }/myyp/">母婴用品</a></td>
                      <td valign="middle" align="center" width="110"><a style="font-size:14px; color:#fff; text-decoration:none;" target="_blank" href="${contextPath }/jjyp/">家居用品</a></td>
                      <td valign="middle" align="center" width="110"><a style="font-size:14px; color:#fff; text-decoration:none;" target="_blank" href="${contextPath }/mrhf/">美容护肤</a></td>
                      <td>&nbsp;</td>
                    </tr>
                  </tbody>
                </table></td>
            </tr>
            <tr>
              <td height="15"></td>
            </tr>
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
	                                        <a href="${contextPath}/p/${plan.brand.brandId?c}/" target="_blank">
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
                                        <td width="135" align="center"><span style="font-size:36px; line-height:49px; font-weight:bold; color:#ffffff; font-family:Tahoma, Geneva, sans-serif;">
                                        	${plan.product.rebate}
                                        </span><span style="font-size:18px; line-height:49px; font-weight:bold; color:#ffffff; vertical-align:text-bottom;">折起</span></td>
                                        <td align="left"><a href="${contextPath}/plan-${plan.planId?c}.htm" target="_blank"><img border="0" src="${imagesPath}/mail/btn2.gif" width="85" height="32" alt="去看看" /></a></td>
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
              			<td width="600" height="55" align="left" valign="middle">
              				<span style="font-size:14px;">欲知更多详情请登录 <a style="color:#f68333;" href="${contextPath }" target="_blank">www.xlbuy365.com</a> 查询</span>&nbsp;
              				<span style="font-size:14px;">如果您不想继续收到此类邮件，请 <a style="color:#333;" href="${mailUrl};">点击退订</a></span>
              			</td>
              			<td rowspan="2" height="169" valign="middle">
              				<img src="${imagesPath}/mail/qr_code_2.gif" width="122" height="122" border="0" />
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