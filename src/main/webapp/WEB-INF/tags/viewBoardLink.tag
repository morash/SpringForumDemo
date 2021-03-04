<%@ tag language="java" pageEncoding="ISO-8859-1"%>
<%@ attribute name="board" required="true" type="com.morash.forumdemo.data.entity.Board"%>

<a href="/board/view/${ board.getName() }">${ board.getName() }</a>