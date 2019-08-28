<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<h2>Discharge patient confirmation</h2>

<%--@elvariable id="patient" type="com.tsystems.ecm.dto.PatientDto"--%>
    <label>Patient's name: <input type="text" readonly value="${patient.name}"/></label>
    <br>
    <label>Patient's diagnosis: <input type="text" readonly value="${patient.diagnosis}"/></label>
    <br>
    <label>Patient's insurance number: <input type="text" readonly  value="${patient.insuranceNumber}"/></label>
    <br>
    <label>Patient's treating doctor: <input type="text" readonly  value="${patient.doctorName}"/></label>
<br>
<h4>These appointments will be cancelled:</h4>
<c:forEach items="${appointments}" var="var" varStatus="i">
    <div>${var.regimenString} ${var.treatment.treatmentType == 'MEDICATION' ? ', dose: ' : ""} ${var.treatment.treatmentType == 'MEDICATION' ? var.dose : ""}</div>
</c:forEach>
    <c:if test="${fn:length(appointments) == 0}"><div>The patient has no active appointments</div></c:if>
<br>
<br>
<form action="discharge" method="post">
    <button type="submit" class="form-submit" name="patientId" value="${patient.id}">Discharge patient</button>
</form>
<form action="/ecm/patients" class="form-submit" method="get">
    <button type="submit">Cancel</button>
</form>
</div>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>
