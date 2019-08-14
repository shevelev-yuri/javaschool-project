<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>

    <title>Events</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

<a href="/ecm/patients">Back to patients list</a>
<table>
    <thead>
    <tr>
        <th>ID</th>
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
            <td>${event.eventStatus == 'SCHEDULED' ? "Scheduled" : event.eventStatus == 'ACCOMPLISHED' ? "Accomplished" : "Cancelled"}</td>
            <td>
                <c:choose>
                    <c:when test="${event.eventStatus == 'SCHEDULED'}">
                        <form method="post" action="accomplished">
                            <input type="hidden" name="patientId" value="${patient.id}">
                            <button type="submit" name="eventId" value="${event.id}">Done</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <button type="button" disabled>Done</button>
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${event.eventStatus == 'SCHEDULED'}">
                        <form method="post" action="cancelled">
                            <input type="hidden" name="patientId" value="${patient.id}">
                            <button type="submit" name="eventId" value="${event.id}">Cancel</button>
                        </form>
                    </c:when>
                    <c:otherwise>
                        <button type="button" disabled>Cancel</button>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>
