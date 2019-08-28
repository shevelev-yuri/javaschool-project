<div class="table-post-div" style="overflow-x: auto;">
    <c:if test="${fn:length(events) != 0}">
        <table>
            <thead>
            <tr>
                <th>#</th>
                <th>Patient name</th>
                <th>Treatment</th>
                <th>Date and time</th>
                <th>Status</th>
                <th hidden></th>
                <th hidden></th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td></td>
                <td>
                    <form action="events" method="get">
                        <select name="patientId">
                            <option value="" hidden disabled selected>Select patient..</option>
                            <c:forEach items="${patients}" var="patient">
                                <option value="${patient.id}">${patient.name}</option>
                            </c:forEach>
                        </select>
                        <button type="submit" class="table-button sml">Filter</button>
                    </form>
                </td>
                <td></td>
                <td>
                    <form action="eventstoday" class="form-filter-buttons" method="get">
                        <button type="submit" class="table-button sml" name="date" value="today">Today</button>
                    </form>
                    <form action="eventsclosest" class="form-filter-buttons" method="get">
                        <button type="submit" class="table-button sml" name="date" value="closest">Next hour</button>
                    </form>
                </td>
                <td></td>
            </tr>
            <c:forEach items="${events}" var="event" varStatus="i">
                <tr>
                    <td>${i.count}</td>
                    <td>${event.patient.name}</td>
                    <td>${event.treatment.treatmentName}</td>
                    <td>${formatter.format(event.scheduledDatetime)}</td>
                    <td>${event.eventStatus == 'SCHEDULED' ? "Scheduled" : event.eventStatus == 'ACCOMPLISHED' ? "Accomplished" : "Cancelled"}</td>
                    <td>
                        <c:choose>
                            <c:when test="${event.eventStatus == 'SCHEDULED'}">
                                <form method="post" action="accomplished" class="button-form">
                                    <input type="hidden" name="patientId" value="${patient.id}">
                                    <input type="hidden" name="date" value="${dateFilter}">
                                    <button type="submit" class="table-button sml" name="eventId" value="${event.id}">Done</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="button-disabled sml" disabled>Done</button>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td>
                        <c:choose>
                            <c:when test="${event.eventStatus == 'SCHEDULED'}">
                                <form method="post" action="cancelled" class="button-form">
                                    <input type="hidden" name="date" value="${dateFilter}">
                                    <input type="hidden" name="patientId" value="${patient.id}">
                                    <button type="submit" class="table-button sml" name="eventId" value="${event.id}">Cancel</button>
                                </form>
                            </c:when>
                            <c:otherwise>
                                <button type="button" class="button-disabled sml" disabled>Cancel</button>
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${fn:length(events) eq 0}"><h2>No events found!</h2><br><a href="eventsall">Go to all events</a></c:if>
</div>