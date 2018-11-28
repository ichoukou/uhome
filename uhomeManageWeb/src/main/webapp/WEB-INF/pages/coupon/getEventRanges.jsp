 <%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="/WEB-INF/pages/include/taglibs.jsp"%>
 <!--权限管理弹出层-->
<!--     <div> -->
<!-- 	    <div class="addrole-pop"> -->
<!-- 	    	<div class="pop-box m-clear"> -->
	    		<div class="pop-box-left">
	    			<c:forEach items="${eventRangeList }" var="eventRange">
	    			<div class="role-list">
	    				<i></i>
	    				<p class="J-parent" id="${eventRange.type }_${eventRange.outId }">
	    					<em></em>
	    					<label outId="${eventRange.outId }" type="${eventRange.type }">${eventRange.name }</label>
	    				</p>
	    				<ul>
	    					<c:forEach items="${eventRange.childrenEventRanges }" var="childrenEventRange">
	    					<li class="role-list">
	    						<i></i>
			    				<p class="J-children" id="${childrenEventRange.type }_${childrenEventRange.outId }">
			    					<em></em>
			    					<label outId="${childrenEventRange.outId }" type="${childrenEventRange.type }">${childrenEventRange.name }</label>
			    				</p>
	    					</li>
	    					</c:forEach>
	    				</ul>
	    			</div>
	    			</c:forEach>
	    		</div>
<!-- 	    	</div> -->
<!-- 	    </div> -->
<!--     </div> -->