<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base title="Create User">
    <form action="/user/create" method="POST">
    	<label>Username: </label>
    	<input type="text" name="username" />
    	<label>Password: </label>
    	<input type="password" name="password" />
    	<label>Email: </label>
    	<input type="text" name="email" />
    	<button tpe="submit">Submit</button>
    </form>
</t:base>