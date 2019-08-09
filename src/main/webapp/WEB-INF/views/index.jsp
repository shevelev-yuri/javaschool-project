<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Welcome to ECM-system Web Application Home</h1>
<br>

<c:choose>
    <c:when test="${not cookie.containsKey('AUTH_SESSION')}">
        <div>
            <a href="login">Login page</a>
        </div>
    </c:when>
    <c:otherwise>
            <div>
                <p>Hello, ${cookie.userName == null ? "undefined" : cookie.userName.value}!</p>
            </div>
    </c:otherwise>
</c:choose>
<div>
    <a href="patients">View all patients</a>
</div>

</body>
</html>