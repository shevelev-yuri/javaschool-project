<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spr"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%--@elvariable id="patient" type="com.tsystems.ecm.dto.PatientDto"--%>
<%--@elvariable id="filter" type="com.tsystems.ecm.utils.FilterParams"--%>
<%--@elvariable id="pages" type="com.tsystems.ecm.utils.Pagination"--%>
<%--@elvariable id="event" type="com.tsystems.ecm.dto.EventDto"--%>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/static/js/reason-prompt.js"></script>

    <title>Events</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

<div class="table-post-div" style="overflow-x: auto;">
    <c:if test="${fn:length(events) != 0}">
        <%--Events table--%>
        <input id="reason" type="hidden" form="cancel" name="reason" value="">
        <table>
            <thead>
            <tr>
                <th style="width: 1%">#</th>
                <th style="width: 20%"><spr:message code="events.patientName"/></th>
                <th style="width: 20%"><spr:message code="events.treatment"/></th>
                <th style="width: 25%"><spr:message code="events.dateAndTime"/></th>
                <th style="width: 5%"><spr:message code="events.status"/></th>
                <th colspan="2"><spr:message code="events.changeStatus"/></th>
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
                        <input type="hidden" name="filter" value="patient">
                        <button type="submit" class="table-button sml"><spr:message code="events.filter"/></button>
                    </form>
                </td>
                <td></td>
                <td>
                    <form action="events" class="form-filter-buttons" method="get">
                        <button type="submit" class="table-button sml" name="filter" value="today"><spr:message code="events.today"/></button>
                    </form>
                    <form action="events" class="form-filter-buttons" method="get">
                        <button type="submit" class="table-button sml" name="filter" value="closest"><spr:message code="events.nextHour"/></button>
                    </form>
                </td>
                <td></td>
                <td colspan="2"></td>
            </tr>
            <c:forEach items="${events}" var="event" varStatus="i">
                <tr>
                    <spr:message code="events.status.scheduled" var="scheduled" scope="request"/>
                    <spr:message code="events.status.accomplished" var="accomplished" scope="request"/>
                    <spr:message code="events.status.cancelled" var="cancelled" scope="request"/>
                    <c:set var="status" value="${event.eventStatus}" scope="request"/>
                    <td>${i.count}</td>
                    <td>${event.patient.name}</td>
                    <td>${event.treatment.treatmentName}</td>
                    <td>${formatter.format(event.scheduledDatetime)}</td>
                    <td><c:if test="${status == 'SCHEDULED'}">${scheduled}</c:if><c:if test="${status == 'ACCOMPLISHED'}">${accomplished}</c:if><c:if test="${status == 'CANCELLED'}">${cancelled}</c:if></td>
                    <c:if test="${status == 'CANCELLED'}">
                        <td colspan="2"><spr:message code="events.reason"/>: ${event.cancelReason}</td>
                    </c:if>
                    <c:if test="${status != 'CANCELLED'}">
                        <td class="td-buttons">
                            <c:choose>
                                <c:when test="${status == 'SCHEDULED'}">
                                    <form method="post" action="accomplished" class="button-form">
                                        <input type="hidden" name="patientId" value="${patient.id}">
                                        <c:if test="${not empty filter.filter}">
                                            <input type="hidden" name="filter" value="${filter.filter}">
                                        </c:if>
                                        <c:if test="${not empty filter.page}">
                                            <input type="hidden" name="page" value="${filter.page}">
                                        </c:if>
                                        <button type="submit" class="table-button sml" name="eventId" value="${event.id}"><spr:message code="events.done"/></button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="button-disabled sml" disabled><spr:message code="events.done"/></button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="td-buttons">
                            <c:choose>
                                <c:when test="${status == 'SCHEDULED'}">
<%--                                    <form id="cancel" method="post" action="cancelled" class="button-form" onsubmit="return confirm('Are you sure you want to cancel this event?');">--%>
                                    <form id="cancel" method="post" action="cancelled" class="button-form" onsubmit="return getReason();">
                                        <input type="hidden" name="patientId" value="${patient.id}">
                                        <c:if test="${not empty filter.filter}">
                                            <input type="hidden" name="filter" value="${filter.filter}">
                                        </c:if>
                                        <c:if test="${not empty filter.page}">
                                            <input type="hidden" name="page" value="${filter.page}">
                                        </c:if>
                                        <button type="submit" name="eventId" value="${event.id}" class="table-button sml cancel"><spr:message code="events.cancel"/></button>
                                    </form>
                                </c:when>
                                <c:otherwise>
                                    <button type="button" class="button-disabled sml" disabled><spr:message code="events.cancel"/></button>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </c:if>
                </tr>
            </c:forEach>
            </tbody>
        </table>
        <%--Events table end--%>
    </c:if>
    <c:if test="${fn:length(events) eq 0}"><h2><spr:message code="events.noEventsFound"/></h2><br><a href="events"><spr:message code="events.toAllEvents"/></a></c:if>
</div>
<%--Pagination panel--%>
<c:if test="${pages.totalPages > 1}">
    <ul class="pagination">
        <c:choose>
            <c:when test="${pages.currentPage eq 1}">
                <li class="page-item disabled">
                    <span class="page-link"><spr:message code="events.prev"/></span>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <c:if test="${patient.id != 0}">
                        <c:if test="${not empty filter.filter}">
                            <a class="page-link"
                               href="events?page=${pages.currentPage - 1}&patientId=${patient.id}&filter=${filter.filter}"><spr:message code="events.prev"/></a>
                        </c:if>
                        <c:if test="${empty filter.filter}">
                            <a class="page-link" href="events?page=${pages.currentPage - 1}&patientId=${patient.id}"><spr:message code="events.prev"/></a>
                        </c:if>
                    </c:if>
                    <c:if test="${patient.id == 0}">
                        <c:if test="${not empty filter.filter}">
                            <a class="page-link" href="events?page=${pages.currentPage - 1}&filter=${filter.filter}"><spr:message code="events.prev"/></a>
                        </c:if>
                        <c:if test="${empty filter.filter}">
                            <a class="page-link" href="events?page=${pages.currentPage - 1}"><spr:message code="events.prev"/></a>
                        </c:if>
                    </c:if>
                </li>
            </c:otherwise>
        </c:choose>
        <c:forEach items="${pages.pages}" var="page">
            <c:if test="${page != -1}">
                <c:if test="${pages.currentPage != page}">
                    <li class="page-item">
                        <c:if test="${patient.id != 0}">
                            <c:if test="${not empty filter.filter}">
                                <a class="page-link"
                                   href="events?page=${page}&patientId=${patient.id}&filter=${filter.filter}">${page}</a>
                            </c:if>
                            <c:if test="${empty filter.filter}">
                                <a class="page-link" href="events?page=${page}&patientId=${patient.id}">${page}</a>
                            </c:if>
                        </c:if>
                        <c:if test="${patient.id == 0}">
                            <c:if test="${not empty filter.filter}">
                                <a class="page-link" href="events?page=${page}&filter=${filter.filter}">${page}</a>
                            </c:if>
                            <c:if test="${empty filter.filter}">
                                <a class="page-link" href="events?page=${page}">${page}</a>
                            </c:if>
                        </c:if>
                    </li>
                </c:if>
                <c:if test="${pages.currentPage == page}">
                    <li class="page-item active">
                        <span class="page-link">${page}<span class="sr-only">(current)</span></span>
                    </li>
                </c:if>
            </c:if>
            <c:if test="${page == -1}">
                <li class="page-item disabled">
                    <span class="page-link">...</span>
                </li>
            </c:if>
        </c:forEach>
        <c:choose>
            <c:when test="${pages.currentPage == pages.totalPages}">
                <li class="page-item disabled">
                    <span class="page-link"><spr:message code="events.next"/></span>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <c:if test="${patient.id != 0}">
                        <c:if test="${not empty filter.filter}">
                            <a class="page-link"
                               href="events?page=${pages.currentPage + 1}&patientId=${patient.id}&filter=${filter.filter}"><spr:message code="events.next"/></a>
                        </c:if>
                        <c:if test="${empty filter.filter}">
                            <a class="page-link" href="events?page=${pages.currentPage + 1}&patientId=${patient.id}"><spr:message code="events.next"/></a>
                        </c:if>
                    </c:if>
                    <c:if test="${patient.id == 0}">
                        <c:if test="${not empty filter.filter}">
                            <a class="page-link" href="events?page=${pages.currentPage + 1}&filter=${filter.filter}"><spr:message code="events.next"/></a>
                        </c:if>
                        <c:if test="${empty filter.filter}">
                            <a class="page-link" href="events?page=${pages.currentPage + 1}"><spr:message code="events.next"/></a>
                        </c:if>
                    </c:if>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</c:if>
<%--Pagination panel end--%>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>