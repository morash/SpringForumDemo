<%@ tag language="java" pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="comment" required="true" type="com.morash.forumdemo.data.entity.Comment"%>
<%@ attribute name="depth" required="false" type="Integer" %>
<%@ attribute name="maxDepth" required="false" type="Integer" %>

<c:set var="depth" value="${ (empty depth) ? 0 : depth }"/>
<c:set var="maxDepth" value="${ (empty maxDepth) ? 3 : maxDepth }"/>

<div class="comment comment_depth_${ depth }">
	<p><span>Posted by </span><t:viewUserLink user="${ comment.getPoster() }" /><span> - ${ comment.getPostDate() }</span></p>
	<p>${ comment.getContents() }</p>
	<a href="/comment/create/respondtocomment/${ comment.getId() }">Comment</a>
	<c:if test="${ comment.getComments().size() > 0 }">
		<c:choose>
			<c:when test="${ depth < maxDepth }">
				<div class="child_comments">
					<c:forEach var="childComment" items="${ comment.getComments() }">
						<t:comment comment="${ childComment }" depth="${ depth + 1 }" maxDepth="${ maxDepth }"/>
					</c:forEach>
				</div>
			</c:when>
			<c:otherwise>
				<a href="/comment/view/${ comment.getId() }">See More</a>
			</c:otherwise>
		</c:choose>
	</c:if>
</div>