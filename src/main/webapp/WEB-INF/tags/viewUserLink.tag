<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="user" required="true" type="com.morash.forumdemo.data.entity.User"%>

<a href="/user/view/${ user.getId() }">${ user.getUsername() }</a>