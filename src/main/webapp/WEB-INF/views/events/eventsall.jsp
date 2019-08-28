<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <%@include file="/WEB-INF/views/layouts/_head.jsp" %>

    <title>Events</title>
</head>
<body>
<%@include file="/WEB-INF/views/layouts/_header.jsp" %>

<%@include file="_table.jsp"%>

<c:if test="${pages.totalPages > 1}">
    <ul class="pagination">
        <c:choose>
            <%--@elvariable id="pages" type="com.tsystems.ecm.utils.Pagination"--%>
            <c:when test="${pages.currentPage eq 1}">
                <li class="page-item disabled">
                    <span class="page-link">Previous</span>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <a class="page-link" href="eventsall?page=${pages.currentPage - 1}">Previous</a>
                </li>
            </c:otherwise>
        </c:choose>
        <c:forEach items="${pages.pages}" var="page">
            <c:if test="${page != -1}">
                <c:if test="${pages.currentPage != page}">
                    <li class="page-item"><a class="page-link"
                                             href="eventsall?page=${page}">${page}</a>
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
                    <span class="page-link">Next</span>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <a class="page-link" href="eventsall?page=${pages.currentPage + 1}">Next</a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</c:if>
<%@ include file="/WEB-INF/views/layouts/_footer.jsp" %>
</body>
</html>