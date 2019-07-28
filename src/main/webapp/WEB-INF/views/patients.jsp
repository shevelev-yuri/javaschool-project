<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Patient name</th>
        <th>Diagnosis</th>
        <th>Patient insurance №</th>
        <th>Treating doctor</th>
        <th>Status</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${patients}"  var="patient">
        <tr>
            <td>${patient.patientId}</td>
            <td>${patient.name}</td>
            <td>${patient.diagnosis}</td>
            <td>${patient.insuranceNumber}</td>
            <td>${patient.doctor.name}</td>
            <td>${patient.status}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
