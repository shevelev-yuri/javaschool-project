<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>

    <title>Discharge patient</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

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
<br>
<br>
<form action="discharge" method="post">
    <button type="submit" name="patientId" value="${patient.id}">Discharge patient</button>
</form>
<form action="/ecm/patients" method="get">
    <button type="submit">Cancel</button>
</form>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>
