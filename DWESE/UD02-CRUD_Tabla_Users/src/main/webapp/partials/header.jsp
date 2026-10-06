<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>
<fmt:setLocale value="${empty sessionScope.locale ? 'es' : sessionScope.locale.language}" scope="session" />
<fmt:setBundle basename="messages" var="messages" scope="page" />
<c:url value="/" var="homeUrl" />
<c:url value="/users" var="usersUrl" />
<c:url value="/changeLanguage" var="languageUrl" />
<!DOCTYPE html>
<html lang="${empty sessionScope.locale ? 'es' : sessionScope.locale.language}">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title><fmt:message key="app.title" bundle="${messages}" /></title>
    <style>
        body { font-family: system-ui, sans-serif; margin: 2rem auto; max-width: 1100px; padding: 0 1rem; color: #20252b; }
        nav { display: flex; align-items: center; justify-content: space-between; gap: 1rem; border-bottom: 1px solid #ccd2d8; margin-bottom: 2rem; }
        nav ul { display: flex; gap: 1.25rem; list-style: none; padding: 0; }
        table { border-collapse: collapse; width: 100%; margin: 1rem 0; }
        th, td { border: 1px solid #ccd2d8; padding: .55rem; text-align: left; }
        form.inline { display: inline; }
        .field { margin: .8rem 0; }
        .field label { display: block; font-weight: 600; margin-bottom: .25rem; }
        .checks label { display: inline; font-weight: 400; }
        .error { background: #fff0f0; border: 1px solid #d77; padding: .75rem; margin: 1rem 0; }
        .actions { display: flex; gap: .7rem; align-items: center; }
    </style>
</head>
<body>
<nav>
    <ul>
        <li><a href="${homeUrl}"><fmt:message key="nav.home" bundle="${messages}" /></a></li>
        <li><a href="${usersUrl}"><fmt:message key="nav.users" bundle="${messages}" /></a></li>
    </ul>
    <form action="${languageUrl}" method="get">
        <label for="lang"><fmt:message key="language.label" bundle="${messages}" /></label>
        <select id="lang" name="lang">
            <option value="es" ${empty sessionScope.locale or sessionScope.locale.language eq 'es' ? 'selected' : ''}>Español</option>
            <option value="en" ${not empty sessionScope.locale and sessionScope.locale.language eq 'en' ? 'selected' : ''}>English</option>
        </select>
        <button type="submit"><fmt:message key="language.change" bundle="${messages}" /></button>
    </form>
</nav>
