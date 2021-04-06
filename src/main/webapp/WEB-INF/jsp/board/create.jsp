<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base title="Create Board">
	<form action="/board/create" method="POST">
    	<c:import url="../imports/csrf_token.jsp"/>
		<label>Board Name: </label>
		<input name="name" type="text"/>
		<label>Description: </label>
		<input name="desc" type="text"/>
		<button type="submit">Submit</button>
	</form>
</t:base>