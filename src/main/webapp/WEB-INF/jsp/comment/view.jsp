<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base title="${ comment.getRespondingToPost().getTitle() }">
	<h2><t:viewBoardLink board="${ comment.getRespondingToPost().getBoard() }"/></h2>
	<p>Posted by <t:viewUserLink user="${ comment.getRespondingToPost().getPoster() }" /> - ${ comment.getRespondingToPost().getPostDate() }</p>
	<h3>${ post.getTitle() }</h3>
	<p>${ post.getContents() }</p>
	<a href="/comment/create/respondtopost/${ post.getId() }">Comment</a>
	<div class="comments">
		<t:comment comment="${ comment }" />
	</div>
</t:base>