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
            <td>${appointment.treatment.treatmentType}</td>
            <td>${appointment.regimenString}</td>
            <td>${appointment.treatment.treatmentName}</td>
            <td>${appointment.dose}</td>
            <td>
                <form method="get" action="appointments/edit"><button type="submit" name="id" value="${appointment.id}">Edit</button>
                </form>
            </td>
            <td>
                <form method="post" action="appointments/delete"><button type="submit" name="id" value="${appointment.id}">Cancel</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
