<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>

    <title>Patients</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

<a href="/ecm/patients/add">Add new patient</a>
<br>
<div style="overflow-x:auto;">
    <table>
        <thead>
        <tr>
            <th>#</th>
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
                <td>${patient.patientStatus == 'ON_TREATMENT' ? "On treatment" : "Discharged"}</td>
                <td>
                    <c:choose>
                        <c:when test="${patient.patientStatus == 'ON_TREATMENT'}">
                            <form method="get" action="appointments/add">
                                <button type="submit" class="table-button" name="patientId" value="${patient.id}">Add appointment</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="button-disabled" disabled>Add appointment</button>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <form method="get" action="events/events">
                        <button type="submit" class="table-button" name="patientId" value="${patient.id}">View events</button>
                    </form>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${patient.patientStatus == 'ON_TREATMENT'}">
                            <form method="get" action="appointments/appointments">
                                <button type="submit" class="table-button" name="patientId" value="${patient.id}">View appointments</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="button-disabled" disabled>View appointments</button>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${patient.patientStatus == 'ON_TREATMENT'}">
                            <form method="get" action="patients/discharge">
                                <button type="submit" name="patientId" class="table-button"  value="${patient.id}">Discharge</button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="button-disabled" disabled>Discharge</button>
                        </c:otherwise>
                    </c:choose>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>
