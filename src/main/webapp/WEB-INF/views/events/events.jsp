<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Events</title>
</head>
<body>
<a href="/ecm/patients">Back to patients list</a>
<table>
    <thead>
    <tr>
        <th>№</th>
        <th>Patient name</th>
        <th>Treatment</th>
        <th>Date and time</th>
        <th>Status</th>
        <th hidden></th>
        <th hidden></th>
        <th hidden></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${events}" var="event" varStatus="i">
        <tr>
            <td>${i.count}</td>
            <td>${event.patient.name}</td>
            <td>${event.treatment.treatmentName}</td>
            <td>${event.scheduledDatetime}</td>
            <td>${event.eventStatus}</td>
            <td>
                <form method="get" action="events/accomplished">
                    <button type="submit" name="patientId" value="${event.patient.id}">Done</button>
                </form>
            </td>
            <td>
                <form method="get" action="events/canceled">
                    <button type="submit" name="patientId" value="${event.patient.id}">Cancel</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
