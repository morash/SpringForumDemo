<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base title="Boards">
	<a href="/board/create">Create board</a>
	<div class="bodyContainer">
		<c:forEach var="board" items="${ board_index_list }">
			<h2><a href="/board/view/${ board.getName() }">${ board.getName() }</a></h2>
		</c:forEach>
	</div>
</t:base>