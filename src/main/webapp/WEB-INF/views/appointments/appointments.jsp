<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spr"%>
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
    <h3><spr:message code="appointments.allAppointmentsForPatient"/>: ${patient.name}</h3>
    <table>
        <thead>
        <tr>
            <th style="width: 1%">#</th>
            <th style="width: 20%"><spr:message code="appointments.treatmentType"/></th>
            <th style="width: 45%"><spr:message code="appointments.regimen"/></th>
            <th style="width: 20%"><spr:message code="appointments.treatment"/></th>
            <th style="width: 10%"><spr:message code="appointments.dose"/></th>
            <th hidden></th>
            <th hidden></th>
        </tr>
        </thead>
        <tbody>
        <spr:message code="appointments.type.procedure" var="procedure" scope="request"/>
        <spr:message code="appointments.type.medication" var="medication" scope="request"/>
        <c:forEach items="${appointments}" var="appointment" varStatus="i">
            <c:set var="type" value="${appointment.treatment.treatmentType}" scope="request"/>
            <tr>
                <td>${i.count}</td>
                <td><c:if test="${type == 'PROCEDURE'}">${procedure}</c:if>
                    <c:if test="${type == 'MEDICATION'}">${medication}</c:if></td>
                <td>${appointment.regimenString}</td>
                <td>${appointment.treatment.treatmentName}</td>
                <td>${appointment.dose}</td>
                <td>
                    <form method="get" action="edit">
                        <input type="hidden" name="patientId" value="${patient.id}"/>
                        <button type="submit" name="appointmentId" class="table-button sml" value="${appointment.id}"><spr:message code="appointments.edit"/></button>
                    </form>
                </td>
                <td>
                    <form method="post" action="delete">
                        <input type="hidden" name="patientId" value="${patient.id}">
                        <button type="submit" name="appointmentId" class="table-button sml cancel" value="${appointment.id}"><spr:message code="appointments.cancel"/></button>
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
