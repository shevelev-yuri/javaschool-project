<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>

    <title>Form: add patients</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

<h2>Add new patients</h2>
<%--@elvariable id="patient" type="com.tsystems.ecm.dto.PatientDto"--%>
<form:form action="add" method="post" modelAttribute="patient">
    <br>
    <div>
        <form:label path="name">Patients name</form:label>
        <form:input path="name"/>
        <form:errors path="name"/>
    </div>
    <br>
    <div>
        <form:label path="diagnosis">Diagnosis</form:label>
        <form:input path="diagnosis"/>
        <form:errors path="diagnosis"/>
    </div>
    <br>
    <div>
        <form:label path="insuranceNumber">Insurance number</form:label>
        <form:input path="insuranceNumber"/>
        <form:errors path="insuranceNumber"/>
    </div>
    <br>
    <div>
        <form:select path="doctorName">
            <form:option value="" selected="selected" disabled="true" hidden="true" label="Select doctor.."/>
            <c:forEach var="doctor" items="${doctors}">
                <form:option value="${doctor.name}" label="${doctor.name}"/>
            </c:forEach>
        </form:select>
        <form:errors path="doctorName"/>
    </div>
    <form:hidden path="patientStatus" value="ON_TREATMENT"/>
    <br>
    <button type="submit">Add patient</button>
</form:form>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>
