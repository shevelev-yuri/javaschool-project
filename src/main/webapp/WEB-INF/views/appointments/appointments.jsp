<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Appointments</title>
</head>
<body>
<h2>All appointments for patient: ${patient.name}</h2>
<br>
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
            <td>${i.count}</td>
            <td>${appointment.type}</td>
            <td>${appointment.regimen}</td>
            <td>${appointment.duration}</td>
            <td>${appointment.treatment}</td>
            <td>
                <form method="get" action="patients/appointment"><button type="submit" name="id" value="${appointment.id}">Edit appointment</button>
                </form>
            </td>
            <td>
                <form method="post" action="patients/appointments"><button type="submit" name="id" value="${appointment.id}">Delete appointment</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
