<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Patients</title>
</head>
<body>
<a href="/ecm/patients/add">Add new patient</a>
<br>
<br>
<a href="/ecm/events/events">View all events</a>
<br>
<br>
<table>
    <thead>
    <tr>
        <th>№</th>
        <th>Patient name</th>
        <th>Diagnosis</th>
        <th>Patient insurance №</th>
        <th>Treating doctor</th>
        <th>Status</th>
        <th hidden></th>
        <th hidden></th>
        <th hidden></th>
        <th hidden></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${patients}" var="patient" varStatus="i">
        <tr>
            <td>${i.count}</td>
            <td>${patient.name}</td>
            <td>${patient.diagnosis}</td>
            <td>${patient.insuranceNumber}</td>
            <td>${patient.doctorName}</td>
            <td>${patient.patientStatus}</td>
            <td>
                <form method="get" action="appointments/add">
                    <button type="submit" name="patientId" value="${patient.id}">Add appointment</button>
                </form>
            </td>
            <td>
                <form method="get" action="events/events">
                    <button type="submit" name="patientId" value="${patient.id}">View events</button>
                </form>
            </td>
            <td>
                <form method="get" action="appointments/appointments">
                    <button type="submit" name="patientId" value="${patient.id}">View appointments</button>
                </form>
            </td>
            <td>
                <form method="get" action="appointments/discharge">
                    <button type="submit" name="patientId" value="${patient.id}">Discharge</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
