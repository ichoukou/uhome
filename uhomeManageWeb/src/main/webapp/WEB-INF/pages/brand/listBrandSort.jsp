<%@page language="java" import="com.ytoxl.module.uhome.uhomebase.dataobject.Product" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xmlns="http://www.w3.org/1999/html">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>品牌排序</title>
    <link href="${_cssPath}/common.css" rel="stylesheet" />
    <link href="${_cssPath}/pages/brand.css" rel="stylesheet" />
    <script src="${_jsPath}/jquery/jquery.1.8.1.js"  language="javascript"></script>
</head>

<body>
    <!--start header-->
    <jsp:include page="../include/header.jsp"></jsp:include> 
    <!--end header-->
    <!--start body-->
    <div class="body m-w980p m-mt10p">
    	<jsp:include page="../include/pageManageMenu.jsp"  flush="true" />
      <!--start form-->
      <div class="body-nav subnav m-mt10p">
		    <ul class="m-clear">
		      <li><a <c:if test="${brandSort.type eq '1'}"> class="current-chose" </c:if> href="${_ctxPath}/admin/brandSort/brandSort-listBrandSort.htm?brandSort.type=1">服饰箱包</a></li>
		      <li><a <c:if test="${brandSort.type eq '2'}"> class="current-chose" </c:if> href="${_ctxPath}/admin/brandSort/brandSort-listBrandSort.htm?brandSort.type=2">母婴用品</a></li>
		      <li><a <c:if test="${brandSort.type eq '3'}"> class="current-chose" </c:if> href="${_ctxPath}/admin/brandSort/brandSort-listBrandSort.htm?brandSort.type=3">家具用品</a></li>
		      <li><a <c:if test="${brandSort.type eq '4'}"> class="current-chose" </c:if> href="${_ctxPath}/admin/brandSort/brandSort-listBrandSort.htm?brandSort.type=4">美容护肤</a></li>
		    </ul>
		  </div>
      <form class="sub-form layout" id="brandSortForm" action="${_ctxPath}/admin/brandSort/brandSort-sortBrands.htm" method="post" onSubmit="return checkNum(); " autocomplete="off">
      <input type="hidden" name="brandIds" id="brandIds" value="" />
      <input type="hidden" name="brandSort.type" id="type" value="${brandSort.type}"/>
      <div class="reorder">
        <ul class="bra-camp m-clear">
	        <div class="m-hide J-first"></div>
	        <c:forEach items="${brandSortList}" var="brandSort" varStatus="status">
	 	        <li id="${brandSort.brandId }">
		            <a>
		                <img src="<zx:thumbImage originalPath='${brandSort.logoImageUrl}' imageType='t21'/>" alt="${brandSort.name}" width="94" height="48"/>
		            </a>
		            <div class="brand-reorder">
		            	<div class="reorder-bg"></div>
		            	<i class="pre"></i>
		            		<input type="text" class="reorder-val" value="${status.index+1 }" id="brand-${status.index+1 }" data_id="${status.index+1 }" />
		            	<i class="next"></i>
		            </div>
	             </li>
             </c:forEach>
        </ul>
       
      </div>
      <!--所有品牌-->
      <div class="all-brand">
        <ul class="bra-camp m-clear">
         <c:forEach items="${allBrandList}" var="brand" varStatus="status">
            <li>
                <a>
                    <img src="<zx:thumbImage originalPath='${brand.logoImageUrl}' imageType='t21'/>" alt="${brand.name}" width="94" height="48"/>
                </a>
                <input type="checkbox" value="${brand.brandId}" class="brand-checked" <c:if test='${brand.isCheck}'>checked="checked"</c:if>  />
            </li>
        </c:forEach>         
        </ul>
      </div>
      <input type="submit" class="m-btn m-mt10p J-save" value="保存" />
      </form>
     	<!--end form-->
    </div>
    <!--end body-->
    <!--start footer-->
    <!--end footer-->
<script>
 
	function checkNum(){
		//alert($("#brandIds").val());
		var len = $(".reorder li").length;
		if(len<=36){
			 return true;
		}else 
			alert("最多选择36个品牌");
			return false;
	}
	
	$(document).ready(function(){
		$(".all-brand li").each(function(i){
			var $this = $(this),
				$check = $this.find(".brand-checked"),
				i = $(this).index()+1,
				a_href = $this.find("a").attr("href"),	
				img_url = $this.find("img").attr("src"),
				img_alt = $this.find("img").attr("alt");
				$check.on("click",function(){
					var flag = $check.attr("checked");
					if(flag){
						var brandId = $check.attr("value");
						var html = '<li id="'+ brandId +'"><a><img src="'+img_url+'" alt="'+img_alt+'" width="94" height="48"></a>';
				        html +='<div class="brand-reorder"><div class="reorder-bg"></div><i class="pre"></i><input type="text" class="reorder-val" value="'+i+'" id="brand-'+i+'" />';
				        html +='<i class="next"></i></div></li>';
						$(".reorder ul").append(html);
						reorderId();
					}else{
						$("input[id = brand-"+i+"]").parents("li").remove();
						reorderId();
					}
				});
		});
			var $li = $(".reorder li"),
				$val = $(".reorder-val");
			$li.live("mouseenter",function(){
				$(this).addClass("hover");
			});
			$li.live("mouseleave",function(){
				var $this = $(this),
					$val = $(this).find(".reorder-val");
				if(!$val.hasClass("focus")){
					$this.removeClass("hover");
				}
			});
			$val.live("focus",function(){
				$(this).addClass("focus");
			});
			$val.live("blur",function(){
				var $this = $(this),
					$this_val = parseInt($this.val()),
					$this_data_id = parseInt($this.attr("data_id"));
					$this_li = $this.parents("li");
				if($this_val<1||$this_val>36||!$this_val){
					alert("请输入1-36之间的数字");
					$this.val("").focus();
				}else{
					$this.removeClass("focus");
					$this_li.removeClass("hover");
					if($this_val>$this_data_id){
						insert($this_val,$this_li,true);
					}else if($this_val<$this_data_id){
						insert($this_val,$this_li,false);
					}
				}
			});
			//修改顺序函数
			var insert=function(val,li_html,isAfter){
				var $pre_li,
					len = $(".reorder li").length,
					$this_ul = $(".reorder ul");
				if(val > len){
					$this_ul.append(li_html);
				}else{
					if(1 == val){
						var $pre_li = $(".J-first");
					}else	if(isAfter){
						$pre_li = $(".reorder-val[data_id="+val+"]").parents("li");
					}else{
						$pre_li = $(".reorder-val[data_id="+(val-1)+"]").parents("li");
					}
					$($pre_li).after(li_html);
				}
				reorderId();
			}
			//data_id排序
			var reorderId = function(){
				var bandIds ="";
				$(".reorder li").each(function(){
					bandIds += 	$(this).attr('id') +",";
					var i = parseInt($(this).index());
					$(this).find(".reorder-val").attr("data_id",i);
					if(i>0&&i<37){
						$(this).find(".reorder-val").val(i);
					}else{
						$(this).find(".reorder-val").val("");
					}
				});
				$(".reorder li").removeClass("hover");
				$("#brandIds").val(bandIds);
				 
			}
			//品牌向前排序
  		$(".pre").live("click",function(){
  			var $this_temp = $(this).parents("li"),
  				$this_input =$(this).next(".reorder-val"),
  				$this_val = $this_input.val(),
  				$pre_temp = $($this_temp).prev("li");
  			if($pre_temp.get(0)){
  				var $pre_input = $pre_temp.find(".reorder-val"),
  					$pre_val = $pre_input.val();
  				$($pre_temp).before($this_temp);
  				$pre_input.val($this_val);
  				$this_input.val($pre_val);
  				reorderId();
  			}
  		});
  		//品牌向后排序
  		$(".next").live("click",function(){
  			var $this_temp = $(this).parents("li"),
  				$this_input =$(this).prev(".reorder-val"),
  				$this_val = $this_input.val(),
  				$next_temp = $($this_temp).next("li");
  			if($next_temp.get(0)){
  				var $next_temp_input = $next_temp.find(".reorder-val"),
  					$next_temp_val = $next_temp_input.val();
  				$($next_temp).after($this_temp);
  				$next_temp_input.val($this_val);
  				$this_input.val($next_temp_val);
  				reorderId();
  			}
  		});
	});
</script>
</body>
</html>
