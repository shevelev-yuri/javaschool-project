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

<form class="form-login" action="login" method="post">
    <%--    <img class="mb-4" src="" alt="" width="72" height="72">--%>
    <h1 class="h3 mb-3 font-weight-normal">Please login</h1>
    <label for="inputLogin" class="sr-only">Login</label>
    <input type="text" id="inputLogin" name="login" class="form-control" placeholder="Login" required autofocus>
    <label for="inputPassword" class="sr-only">Password</label>
    <input type="password" id="inputPassword" name="password" class="form-control" placeholder="Password" required>
    <div class="checkbox mb-3">
        <label>
            <input type="checkbox" name="rememberMe" value="true"> Remember me
        </label>
    </div>
    <button class="btn btn-lg btn-primary btn-block" type="submit">Login</button>
</form>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>

</body>
</html>
