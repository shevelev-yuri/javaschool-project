<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spr"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/style.css"/>

<!DOCTYPE html>
<html>
<head>
    <title>Error 400</title>
</head>
<body style="text-align: center">
<div class="error-div">
<h1 class="error-h1"><spr:message code="error.error400"/></h1>
<h3 class="error-h3"><spr:message code="error.error400string"/></h3>
<p class="error-p"><spr:message code="error.error400line"/></p>
<c:if test="${param.err == 'insuranceAlreadyExists'}"><p style="font-weight: bold"><spr:message code="login.reason"/>: <spr:message code="error.insuranceAlreadyExsists"/></p></c:if>
<a href="/ecm/" role="button"><spr:message code="error.homeLink"/></a>
</div>
</body>
</html>
