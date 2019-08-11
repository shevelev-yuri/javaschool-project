<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<a href="/ecm/patients/add">Add new patient</a>
<table>
    <thead>
    <tr>
        <th>№</th>
        <th>Patient name</th>
        <th>Diagnosis</th>
        <th>Patient insurance №</th>
        <th>Treating doctorName</th>
        <th>Status</th>
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
                <form method="get" action="appointments/add${patient.id}">
                    <button type="submit"name="id" value="${patient.id}">Add appointment</button>
                </form>
            </td>
            <td>
                <form method="get" action="appointments/appointments${patient.id}">
                    <button type="submit" name="id" value="${patient.id}">View appointments</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
