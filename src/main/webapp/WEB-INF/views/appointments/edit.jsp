<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>

    <title>Form: edit appointment</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

<h2>Patient: ${patient.name}</h2>
<br>
<br>
<h2>Edit appointment:</h2>
<div>${oldAppointment.regimenString} ${oldAppointment.treatment.treatmentType == 'MEDICATION' ? ', dose: ' : ""} ${oldAppointment.treatment.treatmentType == 'MEDICATION' ? oldAppointment.dose : ""}</div>
<%--@elvariable id="newAppointment" type="com.tsystems.ecm.dto.AppointmentDto"--%>
<form:form action="edit" method="post" modelAttribute="newAppointment">
    <div class="form-post-div">
        <select name="treatmentId" class="form-select" required>
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
        <c:forEach items="${weekdays}" var="day" varStatus="i">
            <div class="inputGroup">
                <input id="option${i.count}" name="days[]" type="checkbox" value="${day}"/>
                <label for="option${i.count}">${day}</label>
            </div>
        </c:forEach>

        <c:forEach items="${timesOfDay}" var="time">
            <label><input type="checkbox" class="form-checkbox" name="times[]" value="${time}">${time}</label><br>
        </c:forEach>

        <form:label path="duration" required="true">Duration (weeks)</form:label>
        <form:input path="duration" cssClass="form-input" required="true"/>
        <form:errors path="duration" cssClass="form-input err"/>
        <br>
        <form:label path="dose">Dose (for medication only)</form:label>
        <form:input path="dose" cssClass="form-input"/>
        <form:errors path="dose" cssClass="form-input err"/>

        <input type="hidden" name="patientId" value="${patient.id}"/>
        <input type="hidden" name="oldAppointmentId" value="${oldAppointment.id}"/>
        <button type="submit" class="form-submit">Save changes</button>
    </div>
</form:form>
<form action="${pageContext.request.contextPath}/patients" method="get">
    <button type="submit" class="form-submit">Cancel</button>
</form>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>

</body>
</html>