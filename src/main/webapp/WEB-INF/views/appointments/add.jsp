<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Form: add appointment</title>
</head>
<body>
<h2>Patient: ${patient.name}</h2>
<br>
<h2>Add new appointment</h2>
<form:form action="add" method="post" modelAttribute="appointment">
    <div>
        <form:select path="treatment">
            <form:option value="" selected="selected" disabled="true" hidden="true" label="Select treatment.."/>
            <form:options items="${treatments}" itemLabel="treatmentName"/>
            <%--<c:forEach var="treatment" items="${treatments}">
                <form:option value="${treatment}" label="${treatment.treatmentName}"/>
            </c:forEach>--%>
        </form:select>
    </div>
    <br>
    <br>
    <form:checkboxes path="regimen" items="${weekdays}" element="div"/>
    <br>
    <br>
    <label><input type="number" name="weeks" step="1" min="1" max="50">Weeks</label>
    <br>
    <button type="submit">Add appointment</button>
</form:form>
</body>
</html>