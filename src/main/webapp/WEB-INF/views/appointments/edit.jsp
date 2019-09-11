<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spr"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/js/jquery.mousewheel.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/js/scroll.js"></script>

    <spr:message code="appointments.procedures" var="proceduresSelect" scope="request"/>
    <spr:message code="appointments.medications" var="medicationsSelect" scope="request"/>

    <title>Form: edit appointment</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

<%--@elvariable id="newAppointment" type="com.tsystems.ecm.dto.AppointmentDto"--%>
<spr:message code="appointments.regimenDose" var="doseLine" scope="request"/>
<div class="form-post-div">
    <form action="edit" method="post" style="display: inline">
        <h3 style="margin-bottom: .5rem"><spr:message code="appointments.patient"/>: ${patient.name}</h3>
        <p><spr:message code="appointments.editAppointment"/>:<br>${oldAppointment.regimenString} ${oldAppointment.treatment.treatmentType == 'MEDICATION' ? doseLine : ""} ${oldAppointment.treatment.treatmentType == 'MEDICATION' ? oldAppointment.dose : ""}</p>
        <label><spr:message code="appointments.treatment"/><select name="treatmentId" class="form-select" style="margin-left: 20px" required>
            <option value="" hidden selected disabled><spr:message code="appointments.selectTreatment"/></option>
            <optgroup label="${proceduresSelect}">
                <c:forEach items="${procedures}" var="var">
                    <option value="${var.id}">${var.treatmentName}</option>
                </c:forEach>
            </optgroup>
            <optgroup label="${medicationsSelect}">
                <c:forEach items="${medications}" var="var">
                    <option value="${var.id}">${var.treatmentName}</option>
                </c:forEach>
            </optgroup>
        </select>
        </label>
        <%@include file="/WEB-INF/views/appointments/_options.jsp" %>
        <input type="hidden" name="oldAppointmentId" value="${oldAppointment.id}"/>
        <br>
        <button type="submit" class="table-button" style="display: inline"><spr:message code="appointments.saveChanges"/></button>
    </form>
    <form action="${pageContext.request.contextPath}/patients" method="get" class="form-button-inline-right">
        <button type="submit" class="table-button cancel"><spr:message code="appointments.cancel"/></button>
    </form>
</div>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>