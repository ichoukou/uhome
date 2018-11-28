<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Plan" %>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh">
<head>
<meta charset="utf-8" />
<title>排期管理</title>
	<link href="${_cssPath }/pages/arrangement.css" rel="stylesheet" />
	<link href="${_jsPath }/plugin/artdialog/skins/ytoxl.css" rel="stylesheet" />
	<script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
	<script type="text/javascript" src="${_jsPath}/plugin/uploadify/swfobject.js"></script>  
	<script type="text/javascript" src="${_jsPath}/plugin/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/plugin.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/artdialog/jquery.artDialog.min.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidatorRegex.js"></script>
	<script type="text/javascript" src="${_jsPath }/plugin/formvalidator/formValidator-4.1.3.js"></script>
	<script type="text/javascript" src="${_jsPath }/pages/index.js"></script>
	<script type="text/javascript" src="${_jsPath }/pages/plan/planCommon.js"></script>
	<script type="text/javascript" src="${_jsPath }/pages/plan/seckillPlan.js"></script>
	<script type="text/javascript">
		var TYPE_SPECIAL_SELLER = <%=Plan.TYPE_SPECIAL_SELLER%>;
		var TYPE_SEC_KILL = <%=Plan.TYPE_SEC_KILL%>;
		var STATUS_UNRELEASE = <%=Plan.STATUS_UNRELEASE%>;
	</script>
<body>
 	<!--start header-->
 	<jsp:include page="../include/header.jsp"></jsp:include>
    <!--end header-->
    <!--start body-->
    <div class="m-w980p">
        <div class="body-nav arr-nav">
          <ul class="layout" id="arrangeMent">
            <li><a href="${_ctxPath}/admin/plan/plan-searchPlans.htm?type=<%=Plan.TYPE_SPECIAL_SELLER%>">特&nbsp;卖</a></li>
            <li><a class="current-chose" href="${_ctxPath}/admin/plan/plan-searchPlans.htm?type=<%=Plan.TYPE_SEC_KILL%>">秒&nbsp;杀</a></li>
          </ul>
        </div>
        <!---star 秒杀tab--->
        <div>
    		<!--start form-->
            <form class="sub-form cf" id="searchForm" action="${_ctxPath}/admin/plan/plan-searchPlans.htm" method="post">
                <input type="hidden" id="type" name="type" value="<%=Plan.TYPE_SEC_KILL%>"/>
                <input type="hidden" id="status" name="planPage.params.status"/>
                <div class="query">
                    <ul class="date-so">
                        <li>
                            <select class="chose-brand" name="planPage.params.productCategoryId" id="productCategoryId">
                                <option value="">选择商品品类</option>
                                <c:forEach items="${childProductCategories }" var="productCategory">
                                <option value="${productCategory.productCategoryId }"
                                    <c:if test="${planPage.params.productCategoryId==productCategory.productCategoryId }">
                                    selected = "selected"
                                    </c:if>
                                >${productCategory.name }</option>
                                </c:forEach>
                            </select>
                        </li>
                        <li>
                            <input type="text" class="Wdate" name="planPage.params.startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){endTime.focus();}})" 
                            	value="${planPage.params.startTime }" id="startTime"/>
                            	至
                            <input type="text" class="Wdate" name="planPage.params.endTime"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" 
                            	value="${planPage.params.endTime }" id="endTime"/>
                        </li>
                        <li class="last">
                            <input type="submit" class="btn btn-danger" value="查  询"/>
                        </li>
                    </ul>
                    <div class="brand-query">
						<ul>
							<li value=<%=Plan.ALL %>>全部</li>
							<li value="<%=Plan.SPECIAL_SELLER_INPROGRESS %>"><span class="in-sale"></span>秒杀中</li>
							<li value="<%=Plan.RELEASE %>"><span class="published"></span>已发布</li>
							<li value="<%=Plan.UNRELEASE %>"><span class="unpublished"></span>未发布</li>
							<li value="<%=Plan.EXPRIE %>"><span class="has-expired"></span>已过期</li>
						</ul>
					</div>
                </div>
            </form>
           <!--end form-->
            <div class="examine m-mt10p cf">
                <div class="m-fl"><input type="button" id="addSeckill-btn" class="btn btn-danger add-arrange" value="新增排期"/></div>
                <div class="brand-cat">
                    <ul>
                    <c:forEach items="${categoryAndPlanNumList }"  var="obj">
                    <li>${obj.name }:<em>${obj.planNum }</em></li>
                    </c:forEach>
                    </ul>
                </div>
                <div class="m-fr"><input type="button" id="releaseBatch" class="btn btn-danger" value="一键发布"/></div>
            </div>
            <!--start 排期管理table-->
            <div class="plans cf" id="inSale">
				<c:if test="${not empty planPage.result }">
				<div class="sort-col">
					<div class="top seckill"></div>
					<div class="tit-item cf" style="height: 350px;">
						<ul>
							<c:forEach items="${planPage.result }" var="plan" varStatus="status">
							<li class="num">${status.index+1 }</li>
							<li class="tit" title="${plan.brand.name }" value="${plan.planId }">${plan.seckillPlanProduct.product.name }</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="con-wrap">
				    <div class="plans-date-wp">
				        <div class="plans-date cf" style="width: ${totalDays*29}px;">
				            <c:forEach items="${dates }" var="map">
				                <c:forEach items="${map }" var="entry">
				                    <div class="lats <c:if test='${entry.value }'>highlight</c:if>">
				                    <fmt:formatDate value='${entry.key }' pattern='dd'/>
				                    </div>
				                </c:forEach>
				            </c:forEach>
				        </div>
				    </div>
				    <div class="plans-list-wp" style="height: 350px;">
				        <div class="plans-list" style="width: ${totalDays*29}px;">
				        	<c:set var="unRelease" value="<%=Plan.STATUS_UNRELEASE%>"></c:set>
							<c:set var="release" value="<%=Plan.STATUS_RELEASE%>"></c:set>
							<c:set var="inProgress" value="<%=Plan.ACTIVITYSTATUS_SECKILL_INPROGRESS %>"></c:set>
							<c:set var="expire" value="<%=Plan.ACTIVITYSTATUS_SECKILL_END %>"></c:set>
				            <c:forEach items="${planPage.result }" var="plan">
				            <div class="pline">
				            <c:choose>
				                <c:when test="${plan.activityStatus==inProgress }">
				                <!-- 秒杀中 -->
				                <div class="pbox in-sale" style="width: ${plan.days*29}px; left: ${(plan.dayInterval-1)*29 }px;"></div>
				                </c:when>
				                <c:when test="${empty plan.activityStatus&&plan.status==release }">
				                <!-- 已发布，即将开始 -->
				                <div class="pbox published" style="width: ${plan.days*29}px; left: ${(plan.dayInterval-1)*29 }px;" planId="${plan.planId }"><div class="f-pop"><a href="javascript:;" class="edit">编辑</a></div><span class="plan-del"></span></div>
				                </c:when>
				                <c:when test="${empty plan.activityStatus&&plan.status==unRelease }">
				                <!-- 未发布，且还未到开始时间 -->
				                <div class="pbox unpublished" style="width: ${plan.days*29}px; left: ${(plan.dayInterval-1)*29 }px;" planId="${plan.planId }"><div class="f-pop"><a href="javascript:;" class="edit">编辑</a><a href="javascript:;" class="pub">发布</a></div><span class="plan-del"></span></div>
				                </c:when>
				                <c:when test="${plan.activityStatus==expire&&plan.status==release }">
				                <!-- 已过期，发布过了 -->
				                <div class="pbox has-expired" style="width: ${plan.days*29}px; left: ${(plan.dayInterval-1)*29 }px;"></div>
				                </c:when>
				                <c:when test="${plan.activityStatus==expire&&plan.status==unRelease }">
				                <!-- 已过期，但未发布 -->
				                <div class="pbox has-expired-unpub" style="width: ${plan.days*29}px; left: ${(plan.dayInterval-1)*29 }px;" planId="${plan.planId }"><span class="plan-del"></span></div>
				                </c:when>
				            </c:choose>
				            </div>
				            </c:forEach>
				        </div>
				    </div>
				</div>
				</c:if>
			</div>
		<!--end 排期管理table-->
	</div>
	<!---end 秒杀tab--->
</div>
</body>
</html>