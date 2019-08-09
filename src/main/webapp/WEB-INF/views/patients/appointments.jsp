<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Appointments</title>
</head>
<body>
<h2>All appointments for patient: ${patient.name}, ID: ${patient.patientId}</h2>
<br>
<c:forEach var="weekday" items="weekdayList" varStatus="i">
    <p>${weekday}</p>
</c:forEach>
<br>
<table>
    <thead>
    <tr>
        <th>№</th>
        <th>Appointment type</th>
        <th>Regimen</th>
        <th>Duration</th>
        <th>Treatment</th>
        <th hidden></th>
        <th hidden></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${appointments}" var="appointment" varStatus="i">
        <tr>
            <!--TODO-->
            <td>${i.count}</td>
            <td>${appointment.type}</td>
            <td>${appointment.regimen}</td>
            <td>${appointment.duration}</td>
            <td>${appointment.treatment}</td>
            <td>
                <form method="get" action="patients/appointment"><button type="submit" name="appointmentId" value="${appointment.appointmentId}">Edit appointment</button>
                </form>
            </td>
            <td>
                <form method="post" action="patients/appointments"><button type="submit" name="appointmentId" value="${appointment.appointmentId}">Delete appointment</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
