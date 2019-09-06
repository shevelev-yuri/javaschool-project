<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/js/jquery.mousewheel.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/js/scroll.js"></script>

    <title>Form: edit appointment</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

<%--@elvariable id="newAppointment" type="com.tsystems.ecm.dto.AppointmentDto"--%>
<div class="form-post-div">
    <form action="edit" method="post" style="display: inline">
        <h3 style="margin-bottom: .5rem">Patient: ${patient.name}</h3>
        <p>Edit appointment:<br>${oldAppointment.regimenString} ${oldAppointment.treatment.treatmentType == 'MEDICATION' ? ', dose: ' : ""} ${oldAppointment.treatment.treatmentType == 'MEDICATION' ? oldAppointment.dose : ""}</p>
        <label>Treatment<select name="treatmentId" class="form-select" style="margin-left: 20px" required>
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

        <label for="duration">Duration in weeks:</label>
        <input id="duration" class="form-input" type="number" min="1" max="10" step="1" name="duration" placeholder="1" required/>
        <br>
        <label for="dose">Dose (for medications only):</label>
        <input id="dose" class="form-input" type="text" name="dose"/>

        <input type="hidden" name="patientId" value="${patient.id}"/>
        <input type="hidden" name="oldAppointmentId" value="${oldAppointment.id}"/>
        <button type="submit" class="form-submit" style="display: inline">Save changes</button>
    </form>
    <form action="${pageContext.request.contextPath}/patients" method="get" class="form-button-inline-right">
        <button type="submit" class="table-button cancel">Cancel</button>
    </form>
</div>

<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>

</body>
</html>