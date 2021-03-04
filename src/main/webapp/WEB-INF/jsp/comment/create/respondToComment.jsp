<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base title="Respond to ${ post.getTitle() }">
	<h2>${ post.getBoard().getName() }</h2>
	<h3>${ post.getTitle() }</h3>
	<p>${ post.getContents() }</p>
	<div class="comments">
		<div class="comment comment_depth_0">
			<c:choose>
				<c:when test="${ comment.getRespondingToComment() == null }">
					<p><span>Posted by </span><t:viewUserLink user="${ comment.getPoster() }" /><span> - ${ comment.getPostDate() }</span></p>
					<p>${ comment.getContents() }</p>
					<div class="child_comments">
						<form action="/comment/create/respondtocomment/${ comment.getId() }" method="POST">
							<label>Comment: </label>
							<textarea name="contents"></textarea>
							<button type="submit">Submit</button>
						</form>
					</div>
				</c:when>
				<c:otherwise>
					<p><span>Posted by </span><t:viewUserLink user="${ comment.getRespondingToComment().getPoster() }" /><span> - ${ comment.getRespondingToComment().getPostDate() }</span></p>
					<p>${ comment.getRespondingToComment().getContents() }</p>
					<div class="child_comments">
						<div class="comment comment_depth_1">
							<p><span>Posted by </span><t:viewUserLink user="${ comment.getPoster() }" /><span> - ${ comment.getPostDate() }</span></p>
							<p>${ comment.getContents() }</p>
							<div class="child_comments">
								<form action="/comment/create/respondtocomment/${ comment.getId() }" method="POST">
									<label>Comment: </label>
									<textarea name="contents"></textarea>
									<button type="submit">Submit</button>
								</form>
							</div>
						</div>
					</div>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</t:base>