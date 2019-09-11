<%@ taglib uri="http://www.springframework.org/tags" prefix="spr"%>
<div class="inputGroup">
    <input id="option1" name="days[]" type="checkbox" value="Monday"/>
    <label for="option1"><spr:message code="days.monday"/></label>
</div>
<div class="inputGroup">
    <input id="option2" name="days[]" type="checkbox" value="Tuesday"/>
    <label for="option2"><spr:message code="days.tuesday"/></label>
</div>
<div class="inputGroup">
    <input id="option3" name="days[]" type="checkbox" value="Wednesday"/>
    <label for="option3"><spr:message code="days.wednesday"/></label>
</div>
<div class="inputGroup">
    <input id="option4" name="days[]" type="checkbox" value="Thursday"/>
    <label for="option4"><spr:message code="days.thursday"/></label>
</div>
<div class="inputGroup">
    <input id="option5" name="days[]" type="checkbox" value="Friday"/>
    <label for="option5"><spr:message code="days.friday"/></label>
</div>
<div class="inputGroup">
    <input id="option6" name="days[]" type="checkbox" value="Saturday"/>
    <label for="option6"><spr:message code="days.saturday"/></label>
</div>
<div class="inputGroup">
    <input id="option7" name="days[]" type="checkbox" value="Sunday"/>
    <label for="option7"><spr:message code="days.sunday"/></label>
</div>

<div><spr:message code="appointments.selectTime"/></div>
<div class="inputGroup">
    <input id="timeOption1" name="times[]" type="checkbox" value="Morning"/>
    <label for="timeOption1"><spr:message code="times.morning"/></label>
</div>
<div class="inputGroup">
    <input id="timeOption2" name="times[]" type="checkbox" value="Afternoon"/>
    <label for="timeOption2"><spr:message code="times.afternoon"/></label>
</div>
<div class="inputGroup">
    <input id="timeOption3" name="times[]" type="checkbox" value="Evening"/>
    <label for="timeOption3"><spr:message code="times.evening"/></label>
</div>

<label for="duration"><spr:message code="appointments.durationInWeeks"/></label>
<input id="duration" class="form-input" type="number" min="1" max="10" step="1" name="duration" placeholder="1" required/>
<br>
<label for="dose"><spr:message code="appointments.doseLabel"/></label>
<input id="dose" class="form-input" type="text" name="dose"/>

<input type="hidden" name="patientId" value="${patient.id}"/>