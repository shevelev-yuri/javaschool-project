<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="layouts/_head.jsp" %>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/login.css"/>

    <title>Login page</title>
</head>
<%@include file="layouts/_header.jsp" %>

<body class="text-center">

<c:url value="/login" var="loginProcessingUrl"/>
<form action="${loginProcessingUrl}" method="post" class="form-login">
    <fieldset>
        <legend class="sr-only">Login form</legend>
        <h1 class="h3 mb-3 font-weight-normal">Please login</h1>
        <c:if test="${param.error != null}">
            <div>
                Failed to login.
                <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}">
                    Reason: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/>
                </c:if>
            </div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div>
                You have been logged out.
            </div>
        </c:if>
        <label for="inputLogin" class="sr-only">Login</label>
        <input type="text" id="inputLogin" name="login" class="form-control" placeholder="Login" required autofocus>

        <label for="inputPassword" class="sr-only">Password</label>
        <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>

        <div class="checkbox mb-3">
        <label for="remember-me">Remember Me?</label>
        <input type="checkbox" id="remember-me" name="remember-me"/>
        </div>

        <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
    </fieldset>
</form>

<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>

</body>
</html>
