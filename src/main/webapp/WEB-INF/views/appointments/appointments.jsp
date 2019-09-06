<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>

    <title>Appointments</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>
<div class="table-post-div">
    <h3>All appointments for patient: ${patient.name}</h3>
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
                        <input type="hidden" name="patientId" value="${patient.id}"/>
                        <button type="submit" name="appointmentId" class="table-button sml" value="${appointment.id}">Edit
                        </button>
                    </form>
                </td>
                <td>
                    <form method="post" action="delete">
                        <input type="hidden" name="patientId" value="${patient.id}">
                        <button type="submit" name="appointmentId" class="table-button sml cancel" value="${appointment.id}">Cancel
                        </button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>
