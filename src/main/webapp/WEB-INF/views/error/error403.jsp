<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spr"%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/style.css"/>

<!DOCTYPE html>
<html>
<head>
    <title>Error 403</title>
</head>
<body style="text-align: center">
<div class="error-div">
<h1 class="error-h1"><spr:message code="error.error403"/></h1>
<h3 class="error-h3"><spr:message code="error.error403string"/></h3>
<p class="error-p"><spr:message code="error.error403line"/></p>
<a href="/ecm/" role="button"><spr:message code="error.homeLink"/></a>
</div>
</body>
</html>
