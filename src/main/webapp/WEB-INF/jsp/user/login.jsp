<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base title="Login">
   	<p>${ error_message }</p>
    <form action="/user/login" method="POST">
    	<label>Username: </label>
    	<input type="text" name="username" />
    	<label>Password: </label>
    	<input type="password" name="password" />
    	<button action="submit">Submit</button>
	</form>
</t:base>