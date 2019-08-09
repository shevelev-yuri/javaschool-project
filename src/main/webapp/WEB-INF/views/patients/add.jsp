<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Form: add patients</title>
</head>
<body>
<h2>Add new patients</h2>
<form action="add" method="post">

    <div>
        <label for="name">Patients name</label>
        <input id="name" name="name"/>
    </div>

    <div>
        <label for="diagnosis">Diagnosis</label>
        <input id="diagnosis" name="diagnosis"/>
    </div>

    <div>
        <label for="insurance">Insurance number</label>
        <input id="insurance" name="insuranceNumber"/>
    </div>

    <div>
        <select id="doctor" name="doctorName">
            <option value="" selected disabled hidden>Select doctor..</option>
            <c:forEach var="doctor" items="${doctors}">
                <option value="${doctor.name}">${doctor.name}</option>
            </c:forEach>
        </select>
    </div>

    <button type="submit">Add patient</button>

</form>
</body>
</html>
