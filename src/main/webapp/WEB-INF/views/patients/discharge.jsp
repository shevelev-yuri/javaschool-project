<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spr" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>

    <title>Discharge patient</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

<div class="form-post-div">
<h2><spr:message code="patients.dischargePatientConfirmation"/></h2>

<%--@elvariable id="patient" type="com.tsystems.ecm.dto.PatientDto"--%>
    <label class="label-disc"><spr:message code="patients.patientsName"/>: <input type="text" readonly value="${patient.name}"/></label>
    <label class="label-disc"><spr:message code="patients.patientsDiagnosis"/>: <input type="text" readonly value="${patient.diagnosis}"/></label>
    <label class="label-disc"><spr:message code="patients.patientsInsuranceNumber"/>: <input type="text" readonly  value="${patient.insuranceNumber}"/></label>
    <label class="label-disc"><spr:message code="patients.patientsTreatingDoctor"/>: <input type="text" readonly  value="${patient.doctorName}"/></label>
<br>
<h4><spr:message code="patients.appointmentsToBeCancelled" />:</h4>
    <spr:message code="appointments.regimenDose" var="doseLine" scope="request"/>
    <c:forEach items="${appointments}" var="var" varStatus="i">
    <div>${var.regimenString} ${var.treatment.treatmentType == 'MEDICATION' ? doseLine : ""} ${var.treatment.treatmentType == 'MEDICATION' ? var.dose : ""}</div>
</c:forEach>
    <c:if test="${fn:length(appointments) == 0}"><div><spr:message code="patients.noAppointments"/></div></c:if>
<br>
<br>
<form action="discharge" method="post" style="display: inline">
    <button type="submit" class="table-button" name="patientId" value="${patient.id}"><spr:message code="patients.dischargePatient"/></button>
</form>
<form action="/ecm/patients" method="get" class="form-button-inline-right">
    <button type="submit" class="table-button cancel" style="display: inline"><spr:message code="patients.cancel"/> </button>
</form>
</div>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>