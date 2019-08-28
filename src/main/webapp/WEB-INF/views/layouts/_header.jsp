<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
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
                <li class="nav-item">
                    <a class="nav-link" href="/ecm">Home</a>
                </li>
                <sec:authorize access="hasAuthority('DOCTOR')">
                    <li class="nav-item">
                        <a class="nav-link" href="/ecm/patients">Patients</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/ecm/patients/add">New patient</a>
                    </li>
                </sec:authorize>
                <sec:authorize access="hasAuthority('NURSE')">
                    <li class="nav-item">
                        <a class="nav-link" href="/ecm/events/eventsall">Events</a>
                    </li>
                </sec:authorize>
            </ul>
            <sec:authorize access="isAuthenticated()">
                <span class="navbar-text"> <sec:authentication property="name"/> | <a
                        class="nav-item nav-link" href="/ecm/logout" style="display: inline-block; padding: 0;">Logout</a></span>
            </sec:authorize>
        </div>
    </nav>
</header>