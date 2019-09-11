<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spr"%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="layouts/_head.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/static/css/login.css"/>

    <title>Login page</title>
</head>
<%@include file="layouts/_header.jsp" %>

<body class="text-center">

<c:url value="/login" var="loginProcessingUrl"/>
<form action="${loginProcessingUrl}" method="post" class="form-login">
    <fieldset>
        <legend class="sr-only">Login form</legend>
        <h1 class="h3 mb-3 font-weight-normal"><spr:message code="login.pleaseLogin"/></h1>
        <c:if test="${param.error != null}">
            <div class="error" style="padding: 8px 0"><spr:message code="login.failed"/>
                <c:if test="${SPRING_SECURITY_LAST_EXCEPTION != null}"><spr:message code="login.reason"/>: <c:out value="${SPRING_SECURITY_LAST_EXCEPTION.message}"/></c:if>
            </div>
        </c:if>
        <c:if test="${param.logout != null}">
            <div style="padding: 8px 0"><spr:message code="login.logoutSuccess"/></div>
        </c:if>

        <label for="inputLogin" class="sr-only"><spr:message code="login.login" var="loginPlaceholder"/></label>
        <input type="text" id="inputLogin" name="login" class="form-control" placeholder="${loginPlaceholder}" required autofocus>

        <label for="inputPassword" class="sr-only"><spr:message code="login.password" var="passPlaceholder"/></label>
        <input type="password" id="inputPassword" name="password" class="form-control" placeholder="${passPlaceholder}" required>

        <div class="checkbox mb-3">
        <label for="remember-me"><spr:message code="login.rememberMe"/></label>
        <input type="checkbox" id="remember-me" name="remember-me"/>
        </div>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <button class="btn btn-lg btn-primary btn-block" type="submit"><spr:message code="login.loginButton"/></button>
    </fieldset>
</form>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>
