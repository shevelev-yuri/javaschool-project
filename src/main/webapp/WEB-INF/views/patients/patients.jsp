<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spr"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>

    <title>Patients</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>
<div class="table-post-div" style="overflow-x:auto;">
    <table>
        <thead>
        <tr>
            <th style="width:1%">#</th>
            <th style="width:15%"><spr:message code="patients.patientsName"/></th>
            <th style="width:20%"><spr:message code="patients.diagnosis"/></th>
            <th style="width:20%"><spr:message code="patients.patientsInsuranceNo"/></th>
            <th style="width:15%"><spr:message code="patients.treatingDoctor"/></th>
            <th style="width:15%"><spr:message code="patients.status"/></th>
            <th hidden></th>
            <th hidden></th>
            <th hidden></th>
        </tr>
        </thead>
        <tbody>
        <spr:message code="patients.status.onTreatment" var="onTreatment" scope="request"/>
        <spr:message code="patients.status.discharged" var="discharged" scope="request"/>

        <c:forEach items="${patients}" var="patient" varStatus="i">
            <tr>
                <td>${i.count}</td>
                <td>${patient.name}</td>
                <td>${patient.diagnosis}</td>
                <td>${patient.insuranceNumber}</td>
                <td>${patient.doctorName}</td>
                <td>${patient.patientStatus == 'ON_TREATMENT' ? onTreatment : discharged}</td>
                <td>
                    <c:choose>
                        <c:when test="${patient.patientStatus == 'ON_TREATMENT'}">
                            <form method="get" action="appointments/add">
                                <button type="submit" class="table-button" name="patientId" value="${patient.id}"><spr:message code="patients.addAppointment"/></button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="button-disabled" disabled><spr:message code="patients.addAppointment"/></button>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${patient.patientStatus == 'ON_TREATMENT'}">
                            <form method="get" action="appointments/appointments">
                                <button type="submit" class="table-button" name="patientId" value="${patient.id}"><spr:message code="patients.viewAppointment"/></button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="button-disabled" disabled><spr:message code="patients.viewAppointment"/></button>
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>
                    <c:choose>
                        <c:when test="${patient.patientStatus == 'ON_TREATMENT'}">
                            <form method="get" action="patients/discharge">
                                <button type="submit" name="patientId" class="table-button sml cancel"  value="${patient.id}"><spr:message code="patients.discharge"/></button>
                            </form>
                        </c:when>
                        <c:otherwise>
                            <button type="button" class="button-disabled sml" disabled><spr:message code="patients.discharge"/></button>
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
