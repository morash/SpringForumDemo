<%@ taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:base title="Spring Forum">
	<div class="topBoardsContainer">
		<h2>Top Boards</h2>
		<div class="topBoards">
			<c:forEach var="board" items="${ top_board_list }">
				<div class="topBoard">
					<h2><a href="/board/view/${ board.getName() }">${ board.getName() }</a></h2>
					<p>${ board.getDesc() }</p>
				</div>
			</c:forEach>
		</div>
		<a href="/board/">See all</a>
	</div>
	<div class="blurbTextContainer">
		<h2>Spring Forum Demo</h2>
		<p>This project was made in order to demonstrate skills in Java, Spring, Maven, and Liquibase with
		minor focus on HTML and CSS. Originally build around using a MySQL database.</p>
	</div>
</t:base>