<%@tag description="Website Base Template"%>
<%@attribute name="title" required="true"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/stylesheets/stylesheet.css" />
    <title>${ title }</title>
</head>
<body>
	<%@ include file="./imports/header.jsp"%>
	<div class="bodyContainer">
		<jsp:doBody/>
	</div>
</body>
</html>
	