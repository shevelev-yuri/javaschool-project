<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>

    <title>Form: add appointment</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

<h2>Patient: ${patient.name}</h2>
<br>
<a href="/ecm/patients">Back to patients list</a>
<br>
<h2>Add new appointment</h2>
<%--@elvariable id="appointment" type="com.tsystems.ecm.dto.AppointmentDto"--%>
<form:form action="add" method="post" modelAttribute="appointment">
    <div>
        <select name="treatmentId">
            <option value="" hidden selected disabled>Select treatment..</option>
            <optgroup label="Procedures">
                <c:forEach items="${procedures}" var="var">
                    <option value="${var.id}">${var.treatmentName}</option>
                </c:forEach>
            </optgroup>
            <optgroup label="Medications">
                <c:forEach items="${medications}" var="var">
                    <option value="${var.id}">${var.treatmentName}</option>
                </c:forEach>
            </optgroup>
        </select>
    </div>
    <br>
    <br>
    <c:forEach items="${weekdays}" var="day">
        <label><input type="checkbox" name="days[]" value="${day}">${day}</label><br>
    </c:forEach>
    <br>
    <br>
    <c:forEach items="${timesOfDay}" var="time">
        <label><input type="checkbox" name="times[]" value="${time}">${time}</label><br>
    </c:forEach>
    <br>
    <br>
    <form:label path="duration">Duration (weeks)</form:label>
    <form:input path="duration"/>
    <br>
    <br>
    <form:label path="dose">Dose (for medication only)</form:label>
    <form:input path="dose"/>
    <input type="hidden" name="patientId" value="${patient.id}" />
    <br>
    <br>
    <button type="submit">Add appointment</button>
</form:form>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>

</body>
</html>