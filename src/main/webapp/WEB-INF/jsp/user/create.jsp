<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base title="Create User">
    <form action="/user/create" method="POST">
    	<c:import url="../imports/csrf_token.jsp"/>
    	<label>Username: </label>
    	<input type="text" name="username" />
    	<label>Password: </label>
    	<input type="password" name="password" />
    	<label>Email: </label>
    	<input type="text" name="email" />
    	<button tpe="submit">Submit</button>
    </form>
</t:base>