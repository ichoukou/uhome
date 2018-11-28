<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.ytoxl.module.uhome.uhomebase.dataobject.Plan"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
<html lang="zh">
<head>
<meta charset="utf-8" />
<title>排期管理</title>
  	<link href="${_cssPath }/common.css" rel="stylesheet" />
	<link href="${_cssPath }/pages/arrangement.css" rel="stylesheet" />
    <script type="text/javascript" src="${_jsPath }/jquery/jquery.1.8.1.js"></script>
    <script type="text/javascript" src="${_jsPath}/plugin/uploadify/swfobject.js"></script>  
	<script type="text/javascript" src="${_jsPath}/plugin/uploadify/jquery.uploadify.v2.1.4.min.js"></script>
    <script type="text/javascript" src="${_jsPath }/plugin/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript" src="${_jsPath }/pages/plan/planCommon.js"></script>
    <script type="text/javascript" src="${_jsPath }/pages/plan/sellPlan.js"></script>
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
        <%--<div class="body-nav arr-nav">
           <ul class="layout" id="arrangeMent">
            <li><a class="current-chose" href="${_ctxPath}/admin/plan/plan-searchPlans.htm?type=<%=Plan.TYPE_SPECIAL_SELLER%>">特&nbsp;卖</a></li>
            <li><a href="${_ctxPath}/admin/plan/plan-searchPlans.htm?type=<%=Plan.TYPE_SEC_KILL%>">秒&nbsp;杀</a></li>
          </ul>
        </div>--%>
        <!---star 特卖tab--->
        <div>
    		<!--start form-->
			<aut:authorize url="/admin/plan/plan-search">
            <form id="searchForm" action="${_ctxPath}/admin/plan/plan-searchPlans.htm" method="post">
            	<div class="m-clear m-mt10p">
                <input type="hidden" id="type" name="type" value="<%=Plan.TYPE_SPECIAL_SELLER%>"/>
                <input type="hidden" id="status" name="planPage.params.status"/>
                <div class="m-fl">
                  <select class="m-sel" name="planPage.params.productCategoryId" id="productCategoryId">
                      <option value="">选择品牌类目</option>
                      <c:forEach items="${productCategories }" var="productCategory">
                      <option value="${productCategory.productCategoryId }"
                          <c:if test="${planPage.params.productCategoryId==productCategory.productCategoryId }">
                          selected = "selected"
                          </c:if>
                      >${productCategory.name }</option>
                      </c:forEach>
                  </select>
                  <input type="text" class="Wdate" name="planPage.params.startTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true,onpicked:function(){endTime.focus();}})" 
                  	value="${planPage.params.startTime }" id="startTime"/>
                  	至
                  <input type="text" class="Wdate" name="planPage.params.endTime" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd',readOnly:true})" 
                  	value="${planPage.params.endTime }" id="endTime"/>
                  <input type="submit" class="m-btn" value="查  询"/>
                </div>
			           <div class="brand-query">
									<ul>
										<li value=<%=Plan.ALL %>>全部</li>
										<li value="<%=Plan.SPECIAL_SELLER_INPROGRESS %>"><span class="in-sale"></span>特卖中</li>
										<li value="<%=Plan.RELEASE %>"><span class="published"></span>已发布</li>
										<li value="<%=Plan.UNRELEASE %>"><span class="unpublished"></span>未发布</li>
										<li value="<%=Plan.EXPRIE %>"><span class="has-expired"></span>已过期</li>
									</ul>
								</div>
               </div>
            </form>
			</aut:authorize>
           <!--end form-->
            <div class="examine m-mt10p m-clear">
                <div class="m-fl">
                	<aut:authorize url="/admin/plan/plan-add">
                	<input type="button" id="addSale-btn" class="m-btn add-arrange" value="新增排期"/>
                	</aut:authorize>
                	<aut:authorize url="/admin/plan/plan-release">
                	<input type="button" id="releaseBatch" class="m-btn" value="一键发布"/>
                	</aut:authorize>
                </div>
                <div class="brand-cat">
                    <ul>
                    <c:forEach items="${categoryAndPlanNumList }" var="obj">
                    <li>${obj.name }:<em>${obj.planNum }</em></li>
                    </c:forEach>
                    </ul>
                </div>
            </div>
            <!--start 排期管理table-->
            <div class="plans cf" id="inSale">
				<c:if test="${not empty planPage.result }">
				<div class="sort-col">
					<div class="top"></div>
					<div class="tit-item cf" style="height: 350px;">
						<ul>
							<c:forEach items="${planPage.result }" var="plan" varStatus="status">
							<li class="num">${status.index+1 }</li>
							<li class="tit" title="${plan.brand.name }" value="${plan.planId }">${plan.name }</li>
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
							<c:set var="offShelf" value="<%=Plan.STATUS_OFF_SHELF%>"></c:set>
							<c:set var="inProgress" value="<%=Plan.ACTIVITYSTATUS_SPECIAL_SELLER_INPROGRESS %>"></c:set>
							<c:set var="expire" value="<%=Plan.ACTIVITYSTATUS_SPECIAL_SELLER_END %>"></c:set>
				            <c:forEach items="${planPage.result }" var="plan">
				            <div class="pline">
				            <c:choose>
				                <c:when test="${plan.activityStatus==inProgress }">
				                <!-- 特卖中  -->
				                <div class="pbox in-sale" style="width: ${plan.days*29}px; left: ${(plan.dayInterval-1)*29 }px;" planId=${plan.planId }>
					                <aut:authorize url="/admin/plan/plan-offShelf">
					                <a href="javascript:;" class="plan-offshelf">下架</a>
					                </aut:authorize>
				                </div>
				                </c:when>
				                <c:when test="${empty plan.activityStatus&&plan.status==release }">
				                <!-- 已发布，即将开始 -->
				                <div class="pbox published" style="width: ${plan.days*29}px; left: ${(plan.dayInterval-1)*29 }px;" planId=${plan.planId }>
				                	<aut:authorize url="/admin/plan/plan-deletePlan">
				                	<span class="plan-del"></span>
				                	</aut:authorize>
					                <aut:authorize url="/admin/plan/plan-edit">
					                <a href="javascript:;" class="plan-edit">编辑</a>
					                </aut:authorize>
				                </div>
				                </c:when>
				                <c:when test="${empty plan.activityStatus&&plan.status==unRelease }">
				                 <!-- 未发布，且还未到开始时间 -->
				                <div class="pbox unpublished" style="width: ${plan.days*29}px; left: ${(plan.dayInterval-1)*29 }px;" planId=${plan.planId }>
				                	<span style="display: none;">特卖日期：<fmt:formatDate value="${plan.startTime }" pattern="yyyy.MM.dd"/>-<fmt:formatDate value="${plan.endTime }" pattern="yyyy.MM.dd"/>；共${plan.days }天。</span>
				                	<aut:authorize url="/admin/plan/plan-deletePlan">
				                	<span class="plan-del"></span>
				                	</aut:authorize>
				                	<aut:authorize url="/admin/plan/plan-edit">
				                	<a href="javascript:;" class="plan-edit">编辑</a>
				                	</aut:authorize>
				                	<aut:authorize url="/admin/plan/plan-release">
				                	<a href="javascript:;" class="plan-pub">发布</a>
				                	</aut:authorize>
				                </div>
				                </c:when>
				                <c:when test="${plan.activityStatus==expire&&plan.status==release || plan.status==offShelf}">
				                 <!-- 已过期，发布过了 -->
				                <div class="pbox has-expired" style="width: ${plan.days*29}px; left: ${(plan.dayInterval-1)*29 }px;"></div>
				                </c:when>
				                <c:when test="${plan.activityStatus==expire&&plan.status==unRelease }">
				                 <!-- 已过期，但未发布 -->
				                <div class="pbox has-expired-unpub" style="width: ${plan.days*29}px; left: ${(plan.dayInterval-1)*29 }px;" planId=${plan.planId }>
				                	<aut:authorize url="/admin/plan/plan-deletePlan">
				                	<span class="plan-del"></span>
				                	</aut:authorize>
				                </div>
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
	<!---end 特卖tab--->
</div>
<!--end body-->
<!--start footer-->
<jsp:include page="/WEB-INF/pages/include/foot.jsp"></jsp:include>
<!--end footer-->
</body>
</html>