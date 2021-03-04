<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base title="${ post.getTitle() }">
	<h2><t:viewBoardLink board="${ post.getBoard() }"/></h2>
	<p>Posted by <t:viewUserLink user="${ post.getPoster() }" /> - ${ post.getPostDate() }</p>
	<h3>${ post.getTitle() }</h3>
	<p>${ post.getContents() }</p>
	<a href="/comment/create/respondtopost/${ post.getId() }">Comment</a>
	<div class="comments">
		<c:choose>
			<c:when test="${ comment_list.size() == 0 }">
				<p>No comments</p>
			</c:when>
			<c:otherwise>
				<c:forEach var="comment" items="${ comment_list }">
					<t:comment comment="${ comment }" />
				</c:forEach>
			</c:otherwise>
		</c:choose>
	</div>
</t:base>