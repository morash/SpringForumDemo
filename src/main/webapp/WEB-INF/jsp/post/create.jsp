<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base title="Create Post">
	<h2>${ board.getName() }</h2>
	<form action="/post/create/${ board.getName() }" method="POST">
		<label>Title:</label>
		<input name="title" type="text"/>
		<label>Contents: </label>
		<textarea name="contents"></textarea>
		<button type="submit">Submit</button>
	</form>
</t:base>