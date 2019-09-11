<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spr" %>

<header>
    <nav class="navbar navbar-expand-lg navbar-light bg-blue"
         style="background-color: #e3f2fd; border-bottom: 1px solid #aebdc8">
        <a class="navbar-brand" href="/ecm/">ECM</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup"
                aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <ul class="navbar-nav mr-auto">
                <sec:authorize access="hasAuthority('DOCTOR')">
                    <li class="nav-item">
                        <a class="nav-link" href="/ecm/patients"><spr:message code="header.patients"/></a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/ecm/patients/add"><spr:message code="header.newPatient"/></a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('NURSE')">
                    <li class="nav-item">
                        <a class="nav-link" href="/ecm/events/events"><spr:message code="header.events"/></a>
                    </li>
                </sec:authorize>
            </ul>
            <div class="lang-set">
                <a href="<c:url value=""><c:set var="name" value="lang"/><c:set var="value" value="en"/><c:forEach items="${paramValues}" var="params"><c:choose><c:when test="${params.key == name}">
                <c:param name="${name}" value="${value}"/></c:when><c:otherwise><c:forEach items="${params.value}" var="val"><c:param name="${params.key}" value="${val}"/></c:forEach></c:otherwise>
                </c:choose></c:forEach><c:if test="${empty param[name] }"><c:param name="${name}" value="${value}"/></c:if></c:url>">
                    <img id='en' alt='' src='${pageContext.request.contextPath}/resources/static/img/uk.png'/></a>

                <a href="<c:url value=""><c:set var="value2" value="ru"/><c:forEach items="${paramValues}" var="params"><c:choose><c:when test="${params.key == name}"><c:param name="${name}" value="${value2}"/>
                 </c:when><c:otherwise><c:forEach items="${params.value}" var="val"><c:param name="${params.key}" value="${val}"/></c:forEach></c:otherwise></c:choose></c:forEach><c:if test="${empty param[name] }">
                 <c:param name="${name}" value="${value2}"/></c:if></c:url>">
                    <img id="ru" alt="" src="${pageContext.request.contextPath}/resources/static/img/ru.png"/></a>
            </div>
            <sec:authorize access="isAuthenticated()">
                <span class="navbar-text"> <sec:authentication property="name"/> | <a
                        class="nav-item nav-link" href="/ecm/logout"
                        style="display: inline-block; padding: 0;"><spr:message code="header.logout"/></a></span>
            </sec:authorize>
        </div>
    </nav>
</header>