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
<form>
    <label><input type="text" disabled value="${patient.name}"/>Patient's name</label>
    <br>
    <label><input type="text" disabled value="${patient.diagnosis}"/>Patient's diagnosis</label>
    <br>
    <label><input type="text" disabled value="${patient.insuranceNumber}"/>Patient's insurance number</label>
    <br>
    <label><input type="text" disabled value="${patient.doctorName}"/>Patient's treating doctor</label>
</form>
<br>
<h4>These appointments will be cancelled:</h4>
<c:forEach items="appointments" var="appointment" varStatus="i">
    <div>${i.count}. ${appointment.regimenString}</div><br>
</c:forEach>
<br>
<br>
<form action="discharge" method="post">
    <button type="submit" name="patientId" value="${patient.id}">Discharge patient</button>
</form>
<form action="patients" method="get">
    <button type="submit">Cancel</button>
</form>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>
