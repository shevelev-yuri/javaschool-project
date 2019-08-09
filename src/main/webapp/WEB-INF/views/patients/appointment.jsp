<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Form: add appointment</title>
</head>
<body>
<h2>Patient: ${patient.name == null ? name : patient.name}</h2>
<br>
<h2>Add new appointment</h2>
<form action="appointment" method="post">
    <div>
        <select id="treatment" name="treatmentId">
            <option value="" selected disabled hidden>Select treatment..</option>
            <c:forEach var="treatment" items="${treatments}">
                <option value="${treatment.treatmentId}">${treatment.treatmentName}</option>
            </c:forEach>
        </select>
    </div>
    <br>
    <input hidden type="text" name="patientId" value="${patient.patientId}"/>
    <input hidden type="text" name="name" value="${patient.name}"/>
    <br>
    <div>
        <label><input type="checkbox" name="weekday" value="1"/>Monday</label>
    </div>
    <div>
        <label><input type="checkbox" name="weekday" value="2"/>Tuesday</label>
    </div>
    <div>
        <label><input type="checkbox" name="weekday" value="3"/>Wednesday</label>
    </div>
    <div>
        <label><input type="checkbox" name="weekday" value="4"/>Thursday</label>
    </div>
    <div>
        <label><input type="checkbox" name="weekday" value="5"/>Friday</label>
    </div>
    <div>
        <label><input type="checkbox" name="weekday" value="6"/>Saturday</label>
    </div>
    <div>
        <label><input type="checkbox" name="weekday" value="7"/>Sunday</label>
    </div>
    <br>
    <br>
    <label><input type="number" name="weeks" step="1" min="1" max="50">Weeks</label>
    <br>
    <button type="submit">Add appointment</button>

</form>
</body>
</html>
