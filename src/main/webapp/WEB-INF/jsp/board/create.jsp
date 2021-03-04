<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>

<t:base title="Create Board">
	<form action="/board/create" method="POST">
		<label>Board Name: </label>
		<input name="name" type="text"/>
		<label>Description: </label>
		<input name="desc" type="text"/>
		<button type="submit">Submit</button>
	</form>
</t:base>