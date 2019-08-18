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
    <div class="form-post-div">
        <form:label path="name" required="true" cssClass="form-label">Patients name</form:label>
        <form:input path="name" placeholder="Name" cssClass="form-input" cssErrorClass="form-input err"/>
        <form:errors path="name" cssClass="error" element="span"/>

        <form:label path="diagnosis" required="true" cssClass="form-label">Diagnosis</form:label>
        <form:input path="diagnosis" placeholder="Diagnosis" cssClass="form-input" cssErrorClass="form-input err"/>
        <form:errors path="diagnosis" cssClass="error" element="span"/>

        <form:label path="insuranceNumber" required="true" cssClass="form-label">Insurance number</form:label>
        <form:input path="insuranceNumber" placeholder="###-####-###" cssClass="form-input" cssErrorClass="form-input err"/>
        <form:errors path="insuranceNumber" cssClass="error" element="span"/>

        <label class="form-label">Treating doctor</label>
        <form:select path="doctorName" cssClass="form-select" cssErrorClass="form-select err">
            <form:option value="" selected="selected" disabled="true" hidden="true" label="Select doctor.."
                         cssClass="form-select" cssStyle="color: #b3b4d3;"/>
            <c:forEach var="doctor" items="${doctors}">
                <form:option value="${doctor.name}" label="${doctor.name}" required="true" cssClass="form-select" cssErrorClass="form-select err"/>
            </c:forEach>
        </form:select>
        <form:errors path="doctorName" cssClass="error"/>
        <form:hidden path="patientStatus" value="ON_TREATMENT"/>

        <button type="submit" class="form-submit">Add patient</button>
    </div>
</form:form>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>
