<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="aut" uri="http://www.springsecurity.org/jsp"%>

 <div class="body-nav m-mt10p">
        <ul class="m-clear">
            <aut:authorize url="/admin/adv/adv_searchAdvs">
            	<li><a href="${_ctxPath}/admin/adv/adv_searchAdvs.htm"  id="adv">广告页面</a></li>
            </aut:authorize>
			<aut:authorize url="/admin/suggest/suggest-searchSuggests">
				<li><a href="${_ctxPath}/admin/suggest/suggest-searchSuggests.htm"  id="suggest">站内信</a></li>
			</aut:authorize>
            	<li><a href="${_ctxPath}/admin/help/help-listHelps.htm"  id="help">网站地图</a></li>
            <aut:authorize url="/admin/brandSort/brandSort-listBrandSort">
				<li><a href="${_ctxPath}/admin/brandSort/brandSort-listBrandSort.htm?brandSort.type=1"  id="brandSort">品牌排序</a></li>
			</aut:authorize>
            <aut:authorize url="/admin/seo/seo-listSeoConfigs">
				<li><a href="${_ctxPath}/admin/seo/seo-listSeoConfigs.htm" id="seo">SEO信息</a></li>
			</aut:authorize>
			<aut:authorize url="/admin/link/link-searchLinks">
				<li><a href="${_ctxPath}/admin/link/link-searchLinks.htm" id="link">友情链接</a></li>
			</aut:authorize>
			<aut:authorize url="/admin/notice/notice-searchNotice">
			 	<li><a href="${_ctxPath}/admin/notice/notice-searchNotice.htm" id="notice">公告栏</a></li>
			 </aut:authorize>
			<aut:authorize url="/admin/specialtopic/specialTopic_searcheSecialTopicTemplate">
				<li><a href="${_ctxPath}/admin/specialtopic/specialTopic_searcheSecialTopicTemplate.htm" id="specialtopic">专题模板</a></li>
			 </aut:authorize>
        </ul>
   </div>
   
 <script type="text/javascript">
 $(function(){
 	var url = location.href;
 	if(url.indexOf("adv_searchAdvs") > 0){
 		$("#adv").addClass("current-chose").siblings().removeClass("current-chose");
 	}else if(url.indexOf("suggest-searchSuggests") > 0){
 		$("#suggest").addClass("current-chose").siblings().removeClass("current-chose");
 	}else if(url.indexOf("help-listHelps") > 0){
 		$("#help").addClass("current-chose").siblings().removeClass("current-chose");
 	}else if(url.indexOf("brandSort-listBrandSort") > 0){
 		$("#brandSort").addClass("current-chose").siblings().removeClass("current-chose");
 	}else if(url.indexOf('seo-listSeoConfigs')>0){
 		$("#seo").addClass("current-chose").siblings().removeClass("current-chose");
 	}else if(url.indexOf('link-searchLinks')>0){
 		$("#link").addClass("current-chose").siblings().removeClass("current-chose");
 	}else if(url.indexOf('specialTopic')>0){
 		$("#specialtopic").addClass("current-chose").siblings().removeClass("current-chose");
 	}else if(url.indexOf('notice-')>0){
 		$("#notice").addClass("current-chose").siblings().removeClass("current-chose");
 	}
 });
</script>
