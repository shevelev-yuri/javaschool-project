<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>

    <title>Form: add appointment</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

<%--@elvariable id="appointment" type="com.tsystems.ecm.dto.AppointmentDto"--%>
<form:form action="add" method="post" modelAttribute="appointment">
    <div class="form-post-div">
        <h3>Add new appointment for patient: ${patient.name}</h3>
        <label>Treatment<select name="treatmentId" class="form-select" required>
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
        </label>
        <div>Select days</div>
        <c:forEach items="${weekdays}" var="day" varStatus="i">
            <div class="inputGroup">
                <input id="option${i.count}" name="days[]" type="checkbox" value="${day}"/>
                <label for="option${i.count}">${day}</label>
            </div>
        </c:forEach>
        <div>Select time (optional)</div>
        <c:forEach items="${timesOfDay}" var="time" varStatus="i">
            <div class="inputGroup">
                <input id="timeOption${i.count}" name="times[]" type="checkbox" value="${time}"/>
                <label for="timeOption${i.count}">${time}</label>
            </div>
        </c:forEach>

        <form:label path="duration">Duration (weeks)</form:label>
        <form:input path="duration" cssClass="form-input" required="true"/>
        <form:errors path="duration" cssClass="form-input err"/>
        <br>
        <form:label path="dose">Dose (for medication only)</form:label>
        <form:input path="dose" cssClass="form-input"/>
        <form:errors path="dose" cssClass="form-input err"/>

        <input type="hidden" name="patientId" value="${patient.id}"/>
        <button type="submit" class="form-submit">Add appointment</button>
    </div>
</form:form>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>

</body>
</html>