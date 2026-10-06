<%@ include file="partials/header.jsp" %>
<main>
    <h1><fmt:message key="app.welcome" bundle="${messages}" /></h1>
    <p><fmt:message key="app.home.description" bundle="${messages}" /></p>
    <p><a href="${usersUrl}"><fmt:message key="nav.users" bundle="${messages}" /></a></p>
</main>
<%@ include file="partials/footer.jsp" %>
