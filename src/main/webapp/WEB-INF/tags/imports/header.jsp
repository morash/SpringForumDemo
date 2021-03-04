<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@tag import="com.morash.forumdemo.data.constants.SessionKeyNames"  %>
<%@tag import="com.morash.forumdemo.data.entity.User" %>

<header>
	<a href="/">Home</a>
	<a href="/board/">Boards</a>
	
	<div class="headerAccountInfo">
		<% if (session.getAttribute(SessionKeyNames.USER_KEY) != null) { %>
			<p>Logged in as <%= ((User) session.getAttribute(SessionKeyNames.USER_KEY)).getUsername() %></p>
			<a href="/user/logout">Logout</a>
		<% } else { %>
			<a href="/user/login">Login</a>
			<a href="/user/create">Create Account</a>
		<% } %>
	</div>
</header>