<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <%@include file="layouts/_head.jsp" %>

    <title>Home page</title>
</head>
<body>
<%@include file="layouts/_header.jsp" %>

<main role="main" class="container">
    <h1>Welcome to ECM Web application</h1>
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
    <br>
</main>

<%@ include file="layouts/_footer.jsp" %>
</body>
</html>