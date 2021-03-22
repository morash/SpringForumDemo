<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<%@tag import="com.morash.forumdemo.data.constants.SessionKeyNames"  %>
<%@tag import="com.morash.forumdemo.data.entity.User" %>

<header>
	<a href="/">Home</a>
	<a href="/board/">Boards</a>
	
	<div class="headerAccountInfo">
		<sec:authorize access="isAuthenticated()">
			<p>Logged in as <sec:authentication property="name"/></p>
			<form action="/user/logout" method="POST">
				<sec:csrfInput/>
				<button type="submit">Logout</button>
			</form>
		</sec:authorize>
		<sec:authorize access="!isAuthenticated()">
			<a href="/user/login">Login</a>
			<a href="/user/create">Create Account</a>
		</sec:authorize>
	</div>
</header>