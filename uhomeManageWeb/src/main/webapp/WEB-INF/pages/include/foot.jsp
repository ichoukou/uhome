<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<script type="text/javascript">
    var _ctxPath = '${_ctxPath}',
    		_filePath = '${_filePath}',
    		_fileThumbPath = '${_fileThumbPath }',
    		_jsPath = '${_jsPath }';
</script>
<link href="${_jsPath }/plugin/artdialog/skins/ytoxl.css" rel="stylesheet" />
<script type="text/javascript" src="${_jsPath }/plugin/artdialog/jquery.artDialog.min.js"></script>
<script src="${_jsPath }/plugin/plugin.js" language="javascript"></script>
<script type="text/javascript" src="${_jsPath }/pages/index.js"></script>
<script type="text/javascript">
//分页数量只可以输入数字
$("#pageIndex").keyup(function(event) {
    $this = $(this);
    var value = $this.val();
    if(value!=""){
		var reg = /[^0-9]/g;
		value = value.replace(reg,"")
		if(parseInt(value)==0){
			value = "";
		}
		if(value.length>9){
			value = value.substring(0,9);
		}
		$this.val(value);
	}
});
</script>
<tongji:baidu showStatistics="${showStatistic}" ></tongji:baidu>