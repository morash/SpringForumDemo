<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base title="Respond to ${ post.getTitle() }">
	<h2>${ post.getBoard().getName() }</h2>
	<h3>${ post.getTitle() }</h3>
	<p>${ post.getContents() }</p>
	<form action="/comment/create/respondtopost/${ post.getId() }" method="POST">
    	<c:import url="../../imports/csrf_token.jsp"/>
		<label>Comment: </label>
		<textarea name="contents"></textarea>
		<button type="submit">Submit</button>
	</form>
</t:base>