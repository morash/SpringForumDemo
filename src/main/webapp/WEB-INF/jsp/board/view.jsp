<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base title="${ board.getName() }">
	<h1>${ board.getName() }</h1>
	<a href="/post/create/${ board.getName() }">Create post</a>
	<c:choose>
		<c:when test="${ board.getPosts().size() == 0 }">
			<p>No posts found</p>
		</c:when>
		<c:otherwise>
			<c:forEach var="post" items="${ post_list }">
				<h2><a href="/post/view/${ post.getId() }">${ post.getTitle() }</a></h2>
			</c:forEach>
		</c:otherwise>
	</c:choose>
</t:base>