<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spr"%>
<!DOCTYPE html>
<html>
<head>
    <title>Error 400</title>
</head>
<body>
<h1><spr:message code="error.error400"/></h1>
<h3><spr:message code="error.error400string"/></h3>
<p><spr:message code="error.error400line"/></p>
<a href="/ecm/" class="btn btn-mybutton" role="button"><spr:message code="error.homeLink"/></a>
</body>
</html>