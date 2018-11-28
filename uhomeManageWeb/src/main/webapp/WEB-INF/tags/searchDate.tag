<%@ tag pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<%@ attribute name="pageModelStartTime" required="true" type="java.lang.String" description="pageModel的名字"%>
<%@ attribute name="pageModelEndTime" required="true" type="java.lang.String" description="pageModel的名字"%>
<%@ attribute name="pageStartTime" type="java.lang.String" description="开始时间"%>
<%@ attribute name="pageEndTime" type="java.lang.String" description="结束时间"%>
<script type="text/javascript">
$(function(){
	// 元素集合
	var els_date = {
		dateStart: $('#date_start'),
		dateEnd: $('#date_end'),
		lastestA:$('#lastest_a'),
		lastestB:$('#lastest_b'),
		lastestC:$('#lastest_c'),
		lastestD:$('#lastest_d')
	};
	
	
	els_date.dateStart.focus(function() {
		WdatePicker({
			onpicked: function() {		
				var _this = this;
				
				_this.blur();
				
			},
			dateFmt:'yyyy-MM-dd',
			maxDate: '%y-%M-%d',			// 最大时间：系统当前
			isShowClear: false,
			readOnly: true,
			doubleCalendar: true		// 双月历
		});
	});
	els_date.dateEnd.focus(function() {
		WdatePicker({
			onpicked: function() {		
				var _this = this;
				
				_this.blur();
				
			},
			dateFmt:'yyyy-MM-dd',
			startDate: '#F{$dp.$D(\'date_start\')}',
			minDate: '#F{$dp.$D(\'date_start\')}',	// 终止日期大于起始日期
			maxDate: '%y-%M-%d ',			// 最大时间：系统当前
			isShowClear: false,
			readOnly: true,
			doubleCalendar: true		// 双月历
		});
	});
	Date.prototype.format = function(fmt)
	{ //author: meizz
	  var o = {
		"M+" : this.getMonth()+1,                 //月份
		"d+" : this.getDate(),                    //日
		"h+" : this.getHours(),                   //小时
		"m+" : this.getMinutes(),                 //分
		"s+" : this.getSeconds(),                 //秒
		"q+" : Math.floor((this.getMonth()+3)/3), //季度
		"S"  : this.getMilliseconds()             //毫秒
	  };
	  if(/(y+)/.test(fmt))
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
	  for(var k in o)
		if(new RegExp("("+ k +")").test(fmt))
	  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
	  return fmt;
	}

	
		/**
	 * @description 填写开始和结束日期（时间段）
	 * @param days {Number} 最近 N 天的起止日期
	 * @param startEl {Element} 起始日期的 input 元素
	 * @param endEl {Element} 结束日期的 input 元素
	**/
	var lastestDays = function(days, startEl, endEl) {
		var sysDate = new Date(),
			latestDate = new Date(),
			sysDateY = parseInt(latestDate.getFullYear(), 10),
			sysDateM = parseInt(latestDate.getMonth(), 10) + 1,
			sysDateD = parseInt(latestDate.getDate(), 10),
			newtimems = latestDate.getTime() - (days * 24 * 60 * 60 * 1000);
			
		latestDate.setTime(newtimems);
		
		// 填开始时间
		//startEl.val(latestDate);
			startEl.val(latestDate.format('yyyy-MM-dd'));
		// 填结束时间
		//endEl.val(sysDate);
		endEl.val(sysDate.format('yyyy-MM-dd'));
	};
		/**
	 * @description 填写开始和结束日期（时间点）
	 * @param days {Number} 前 N 天
	 * @param startEl {Element} 起始日期的 input 元素
	 * @param endEl {Element} 结束日期的 input 元素
	**/
	var beforeDays = function(days, start, end) {
		var sysDate = new Date(),
			BeforeDate = new Date(),
			sysDateY = parseInt(BeforeDate.getFullYear(), 10),
			sysDateM = parseInt(BeforeDate.getMonth(), 10) + 1,
			sysDateD = parseInt(BeforeDate.getDate(), 10),
			newtimems = BeforeDate.getTime() - (days * 24 * 60 * 60 * 1000);
			
		BeforeDate.setTime(newtimems);
		
		// 填开始时间
		start.val(BeforeDate.format('yyyy-MM-dd'));
	//	start.val(BeforeDate);
		// 填结束时间
		end.val(BeforeDate.format('yyyy-MM-dd'));
//  	end.val(BeforeDate);
	};
	//"" 今天
	els_date.lastestA.change(function() {
		lastestDays(0, els_date.dateStart, els_date.dateEnd);
	});
	
	// "已发货" 近3天
	els_date.lastestB.change(function() {
		lastestDays(2, els_date.dateStart, els_date.dateEnd);
	});
	
	// "已发货" 近7天
	els_date.lastestC.change(function() {
		lastestDays(6, els_date.dateStart, els_date.dateEnd);
	});
	//近30天
	els_date.lastestD.change(function() {
		lastestDays(29, els_date.dateStart, els_date.dateEnd);
	});
	//lastestDays(7);
	if(els_date.dateStart.val()=="" || els_date.dateEnd.val()==""){
		$("#lastest_c").attr("checked","checked");
		lastestDays(6,els_date.dateStart, els_date.dateEnd); 
	}
	$("#date_start,#date_end").click(function(){
		$("input[name='orderDate']").each(function(index,item){
			$(this).removeAttr("checked");
		});
	});
});
</script>
<label for="lastest_a" class="label_raido"><input type="radio" id="lastest_a" class="input_radio" value="1" name="orderDate" <c:if test="${param.orderDate=='1'}">checked="checked"</c:if> />今天</label>
<label for="lastest_b" class="label_raido"><input type="radio" id="lastest_b" class="input_radio" value="2" name="orderDate" <c:if test="${param.orderDate=='2'}">checked="checked"</c:if> />近3天</label>
<label for="lastest_c" class="label_raido"><input type="radio" id="lastest_c" class="input_radio" value="3" name="orderDate" <c:if test="${param.orderDate=='3'}">checked="checked"</c:if> />近7天</label>
<label for="lastest_d" class="label_raido"><input type="radio" id="lastest_d" class="input_radio" value="4" name="orderDate" <c:if test="${param.orderDate=='4'}">checked="checked"</c:if> />近30天</label>
<input id="date_start" name="${pageModelStartTime}" value="${pageStartTime}" class="Wdate" type="text" style="border: 1px solid rgb(130, 130, 130);"/> 
<label class="date-to">至</label>
<input id="date_end" name="${pageModelEndTime}" value="${pageEndTime}" class="Wdate" type="text" style="border: 1px solid rgb(130, 130, 130);"/>
