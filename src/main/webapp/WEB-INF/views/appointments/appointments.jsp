<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>

    <title>Appointments</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

<h2>All appointments for patient: ${patient.name}</h2>
<br>
<a href="/ecm/patients">Back to patients list</a>
<br>
<br>
<table>
    <thead>
    <tr>
        <th>№</th>
        <th>Treatment type</th>
        <th>Regimen</th>
        <th>Treatment</th>
        <th>Dose</th>
        <th hidden></th>
        <th hidden></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${appointments}" var="appointment" varStatus="i">
        <tr>
            <td>${i.count}</td>
            <td>${appointment.treatment.treatmentType == 'PROCEDURE' ? "Procedure" : "Medication"}</td>
            <td>${appointment.regimenString}</td>
            <td>${appointment.treatment.treatmentName}</td>
            <td>${appointment.dose}</td>
            <td>
                <form method="get" action="edit">
                    <button type="submit" name="appointmentId" value="${appointment.id}">Edit</button>
                </form>
            </td>
            <td>
                <form method="post" action="delete">
                    <button type="submit" name="appointmentId" value="${appointment.id}">Cancel</button>
                    <input type="hidden" name="patientId" value="${patient.id}">
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>
