<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>

    <title>Title</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

<form action="login" method="post">
    <div>
        <label for="login">Login</label>
        <input id="login" name="login" placeholder="Login" />
    </div>

    <div>
        <label for="password">Password</label>
        <input id="password" type="password" name="password" placeholder="Password" />
    </div>
    <button type="submit">Login</button>
</form>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>
