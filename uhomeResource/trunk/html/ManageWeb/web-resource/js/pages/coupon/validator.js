function checkEventCount(obj){
	var value = obj.value;
	var refn = /^[1-9]\d{0,10}$/;
	if (value!=''&&!refn.test(value)) {
		alert("必须填写不超过11位的正整数");
		obj.value = '';
		obj.focus();
	}
}
function checkAllowance(obj){
	var value = obj.value;
	var refn1 =/^0\.\d{1,2}$/,refn2=/^[1-9]{1}\d{0,7}([\.]\d{1,2})?$/;
	if (value!=''&&!(refn1.test(value)||refn2.test(value))) {
		alert("必须填写1-8位的正整数");
		obj.value = '';
		obj.focus();
	}
}
function checkDouble(obj){
	var value = obj.value;
	var refn1 =/^0(\.\d{1,2})?$/,refn2=/^[1-9]{1}\d{0,7}([\.]\d{1,2})?$/;
	if (value!=''&&!(refn1.test(value)||refn2.test(value))) {
		alert("必须填写整数(八位)或者小数(两位)");
		obj.value = '';
		obj.focus();
	}
}
function checkCouponCount(obj){
	var value = obj.value;
	var refn = /^[1-9]\d{0,3}$/;
	if (value!=''&&!refn.test(value)) {
		alert("必须填写不超过4位的正整数");
		obj.value = '';
		obj.focus();
	}
}
