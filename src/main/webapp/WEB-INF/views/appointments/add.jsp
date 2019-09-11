<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spr"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/js/jquery.mousewheel.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/js/scroll.js"></script>

    <spr:message code="appointments.procedures" var="proceduresSelect" scope="request"/>
    <spr:message code="appointments.medications" var="medicationsSelect" scope="request"/>

    <title>Form: add appointment</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

<%--@elvariable id="appointment" type="com.tsystems.ecm.dto.AppointmentDto"--%>
<form action="add" method="post">
    <div class="form-post-div">
        <h3><spr:message code="appointments.addNewAppointmentForPatient"/>: ${patient.name}</h3>
        <label><spr:message code="appointments.treatment"/><select name="treatmentId" class="form-select" style="margin-left: 20px" required>
            <option value="" hidden selected disabled><spr:message code="appointments.selectTreatment"/></option>
            <optgroup label="${proceduresSelect}">
                <c:forEach items="${procedures}" var="var">
                    <option value="${var.id}">${var.treatmentName}</option>
                </c:forEach>
            </optgroup>
            <optgroup label="${medicationsSelect}">
                <c:forEach items="${medications}" var="var">
                    <option value="${var.id}">${var.treatmentName}</option>
                </c:forEach>
            </optgroup>
        </select>
        </label>
        <div><spr:message code="appointments.selectDays"/></div>
        <%@include file="/WEB-INF/views/appointments/_options.jsp" %>
        <br>
        <button type="submit" class="table-button"><spr:message code="appointments.addAppointment"/></button>
    </div>
</form>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>

</body>
</html>