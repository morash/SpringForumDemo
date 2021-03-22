<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base title="Login">
	<c:choose>
		<c:when test="${ param.error != null }">
			<p>Error: Username or password is incorrect</p>
		</c:when>
		<c:when test="${ param.logout != null }">
			<p>Successfully logged out</p>
		</c:when>
	</c:choose>
    <form action="/user/login" method="POST">
    	<c:import url="../imports/csrf_token.jsp"/>
    	<label>Username: </label>
    	<input type="text" name="username" />
    	<label>Password: </label>
    	<input type="password" name="password" />
    	<button action="submit">Submit</button>
	</form>
</t:base>