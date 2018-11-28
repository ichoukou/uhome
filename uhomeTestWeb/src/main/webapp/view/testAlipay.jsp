<%@page import="java.util.Date"%>
<%@page
	import="com.ytoxl.module.uhome.uhomeorder.alipay.util.AlipayCore"%>
<%@page import="com.ytoxl.module.core.common.utils.DateUtil"%>
<%@page import="java.util.TreeMap"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String body = request.getParameter("body");
body  = "并发测试数据(1)orderNo:["+body+"]";
String subject = request.getParameter("subject");
String out_trade_no = request.getParameter("out_trade_no");
out_trade_no = "EfI370Sw7x4URK34ijEHCg==";
String sign = request.getParameter("sign");
String _input_charset = request.getParameter("_input_charset");
String total_fee = request.getParameter("total_fee");
String service = request.getParameter("service");
String partner = request.getParameter("partner");
String payment_type = request.getParameter("payment_type");
String seller_email = request.getParameter("seller_email");

String time = DateUtil.toSeconds(new Date());
String notify_url = request.getParameter("notify_url");
notify_url = "http://localhost:8080/uHomeFront/order/alipayReturn.action";
String return_url = request.getParameter("return_url");
Map<String,String> parameterMap = new TreeMap<String,String>();
parameterMap.put("discount","0.00");
parameterMap.put("payment_type","1");
parameterMap.put("subject","特卖商品支付");
parameterMap.put("trade_no","2013080544752296");
parameterMap.put("gmt_create",time);
parameterMap.put("notify_type","trade_status_sync");
parameterMap.put("quantity","1");
parameterMap.put("out_trade_no",out_trade_no);
parameterMap.put("seller_id","2088901966886173");
parameterMap.put("notify_time",time);
parameterMap.put("body",body);
parameterMap.put("trade_status","TRADE_SUCCESS");
parameterMap.put("is_total_fee_adjust","N");
parameterMap.put("total_fee",total_fee);
parameterMap.put("gmt_payment",time);
parameterMap.put("seller_email","xlbuy365@ytoxl.com");
parameterMap.put("price",total_fee);
parameterMap.put("buyer_email","payment@ytoxl.com");
parameterMap.put("buyer_id","2088002158580964");
parameterMap.put("use_coupon","N");

parameterMap.put("sign", AlipayCore.buildMysign(parameterMap));
parameterMap.put("sign_type","MD5");

parameterMap.put("notify_id","a66499d4c6a0972d7cf51eb57086450d3s");

StringBuilder sb =  new StringBuilder(notify_url+"?");
sb.append("body").append("=").append(parameterMap.get("body")).append("&");
sb.append("discount").append("=").append(parameterMap.get("discount")).append("&");
sb.append("payment_type").append("=").append("1").append("&");
sb.append("subject").append("=").append("特卖商品支付").append("&");
sb.append("trade_no").append("=").append(parameterMap.get("trade_no")).append("&");
sb.append("buyer_email").append("=").append(parameterMap.get("buyer_email")).append("&");
sb.append("gmt_create").append("=").append(parameterMap.get("gmt_create")).append("&");
sb.append("notify_type").append("=").append(parameterMap.get("notify_type")).append("&");
sb.append("quantity").append("=").append(parameterMap.get("quantity")).append("&");
sb.append("seller_id").append("=").append("2088901966886173").append("&");
sb.append("notify_time").append("=").append(parameterMap.get("notify_time")).append("&");
sb.append("trade_status").append("=").append(parameterMap.get("trade_status")).append("&");
sb.append("is_total_fee_adjust").append("=").append(parameterMap.get("is_total_fee_adjust")).append("&");
sb.append("total_fee").append("=").append(parameterMap.get("total_fee")).append("&");
sb.append("gmt_payment").append("=").append(parameterMap.get("gmt_payment")).append("&");
sb.append("seller_email").append("=").append("xlbuy365@ytoxl.com").append("&");
sb.append("price").append("=").append(parameterMap.get("price")).append("&");
sb.append("buyer_id").append("=").append(parameterMap.get("buyer_id")).append("&");
//sb.append("notify_id").append("=").append(parameterMap.get("notify_id"));
sb.append("use_coupon").append("=").append(parameterMap.get("use_coupon")).append("&");
sb.append("sign_type").append("=").append(parameterMap.get("sign_type")).append("&");
sb.append("sign").append("=").append(parameterMap.get("sign"));
System.out.println("请求url:"+sb.toString());
String s = "http://test.xlbuy365.com/testAlipay.jsp?body=13081900419&total_fee=95.00";
%>


<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>支付宝</title>
</head>
<form id="alipaysubmit" name="alipaysubmit" action="<%=notify_url %>"
	method="post">
	<input type="hidden" name="body" value='<%=parameterMap.get("body")%>' />
	<input type="hidden" name="discount"
		value='<%=parameterMap.get("discount")%>' /> <input type="hidden"
		name="payment_type" value='1' /> <input type="hidden" name="subject"
		value='特卖商品支付' /> <input type="hidden" name="out_trade_no"
		value='<%=parameterMap.get("out_trade_no")%>' /> <input type="hidden"
		name="trade_no" value='<%=parameterMap.get("trade_no")%>' /> <input
		type="hidden" name="buyer_email"
		value='<%=parameterMap.get("buyer_email")%>' /> <input type="hidden"
		name="gmt_create" value='<%=parameterMap.get("gmt_create")%>' /> <input
		type="hidden" name="notify_type"
		value='<%=parameterMap.get("notify_type")%>' /> <input type="hidden"
		name="quantity" value='<%=parameterMap.get("quantity")%>' /> <input
		type="hidden" name="seller_id" value='2088901966886173' /> <input
		type="hidden" name="notify_time"
		value='<%=parameterMap.get("notify_time")%>' /> <input type="hidden"
		name="trade_status" value='<%=parameterMap.get("trade_status")%>' /> <input
		type="hidden" name="is_total_fee_adjust"
		value='<%=parameterMap.get("is_total_fee_adjust")%>' /> <input
		type="hidden" name="total_fee"
		value='<%=parameterMap.get("total_fee")%>' /> <input type="hidden"
		name="gmt_payment" value='<%=parameterMap.get("gmt_payment")%>' /> <input
		type="hidden" name="seller_email" value='xlbuy365@ytoxl.com' /> <input
		type="hidden" name="price" value='<%=parameterMap.get("price")%>' /> <input
		type="hidden" name="buyer_id"
		value='<%=parameterMap.get("buyer_id")%>' /> <input type="hidden"
		name="use_coupon" value='<%=parameterMap.get("use_coupon")%>' /> <input
		type="hidden" name="notify_id"
		value='<%=parameterMap.get("notify_id")%>' /> <input type="hidden"
		name="sign_type" value='<%=parameterMap.get("sign_type")%>' /> <input
		type="hidden" name="sign" value='<%=parameterMap.get("sign")%>' />
</form>
<script>document.forms['alipaysubmit'].submit();</script>

<body>
</body>
</html>