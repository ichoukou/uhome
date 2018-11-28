<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../include/taglibs.jsp"%>
 			<div class="show-bra-con" style="display: block;">
				<ul class="bra-camp cf">
					<c:forEach items="${brands}" var="brandSort">
						<c:if test="${brandSort.type==1 && brandSort.sort<37}">
							<li>
								<a href="${_ctxPath }/p/${brandSort.brand.brandId}/" target="_blank">
									<img src="<zx:thumbImage originalPath='${brandSort.brand.logoImageUrl}' imageType='t21'/>" alt="${brandSort1.brand.name}" width="94" height="48"> 
									<span>${brandSort.brand.name}</span>
								</a>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			 <div class="show-bra-con">
				<ul class="bra-camp cf">
					<c:forEach items="${brands}" var="brandSort">
						<c:if test="${brandSort.type==2 && brandSort.sort<37}">
							<li>
								<a href="${_ctxPath }/p/${brandSort.brand.brandId}/" target="_blank">
									<img src="<zx:thumbImage originalPath='${brandSort.brand.logoImageUrl}' imageType='t21'/>" alt="${brandSort2.brand.name}" width="94" height="48"> 
									<span>${brandSort.brand.name}</span>
								</a>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			 <div class="show-bra-con">
				<ul class="bra-camp cf">
					<c:forEach items="${brands}" var="brandSort">
						<c:if test="${brandSort.type==3 && brandSort.sort<37}">
							<li>
								<a href="${_ctxPath }/p/${brandSort.brand.brandId}/" target="_blank">
									<img src="<zx:thumbImage originalPath='${brandSort.brand.logoImageUrl}' imageType='t21'/>" alt="${brandSort3.brand.name}" width="94" height="48"> 
									<span>${brandSort.brand.name}</span>
								</a>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>
			  <div class="show-bra-con">
				<ul class="bra-camp cf">
					<c:forEach items="${brands}" var="brandSort">
						<c:if test="${brandSort.type==4 && brandSort.sort<37}">
							<li>
								<a href="${_ctxPath }/p/${brandSort.brand.brandId}/" target="_blank">
									<img src="<zx:thumbImage originalPath='${brandSort.brand.logoImageUrl}' imageType='t21'/>" alt="${brandSort4.brand.name}" width="94" height="48"> 
									<span>${brandSort.brand.name}</span>
								</a>
							</li>
						</c:if>
					</c:forEach>
				</ul>
			</div>

