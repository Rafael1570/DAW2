<%@ include file="../../partials/header.jsp" %>
<c:url value="/users" var="usersUrl" />

<h1>
    <c:choose>
        <c:when test="${user == null}"><fmt:message key="users.form.new" bundle="${messages}" /></c:when>
        <c:otherwise><fmt:message key="users.form.edit" bundle="${messages}" /></c:otherwise>
    </c:choose>
</h1>

<c:if test="${not empty errorKey}">
    <div class="error"><fmt:message key="${errorKey}" bundle="${messages}" /></div>
</c:if>

<form action="${usersUrl}" method="post">
    <input type="hidden" name="id" value="${user.id}" />
    <input type="hidden" name="action" value="${user == null ? 'insert' : 'update'}" />

    <div class="field">
        <label for="username"><fmt:message key="users.field.username" bundle="${messages}" /></label>
        <input type="text" id="username" name="username" maxlength="40" required autocomplete="off"
               value="<c:out value='${formUsername != null ? formUsername : user.username}' />" />
    </div>

    <div class="field">
        <label for="passwordHash"><fmt:message key="users.field.passwordHash" bundle="${messages}" /></label>
        <input type="password" id="passwordHash" name="passwordHash" maxlength="500" required autocomplete="new-password" />
        <small><fmt:message key="users.form.password.help" bundle="${messages}" /></small>
    </div>

    <div class="field">
        <label for="lastPasswordChange"><fmt:message key="users.field.lastPasswordChange" bundle="${messages}" /></label>
        <input type="datetime-local" id="lastPasswordChange" name="lastPasswordChange" required
               value="<c:out value='${formLastPasswordChange != null ? formLastPasswordChange : lastPasswordChangeInput}' />" />
    </div>

    <div class="field">
        <label for="failedLoginAttempts"><fmt:message key="users.field.failedLoginAttempts" bundle="${messages}" /></label>
        <input type="number" id="failedLoginAttempts" name="failedLoginAttempts" min="0" step="1" required
               value="<c:out value='${formFailedLoginAttempts != null ? formFailedLoginAttempts : 0}' />" />
    </div>

    <div class="field checks">
        <input type="checkbox" id="active" name="active" value="true"
               <c:if test="${formActive != null ? formActive : (user == null or user.active)}">checked="checked"</c:if> />
        <label for="active"><fmt:message key="users.field.active" bundle="${messages}" /></label>
    </div>
    <div class="field checks">
        <input type="checkbox" id="accountNonLocked" name="accountNonLocked" value="true"
               <c:if test="${formAccountNonLocked != null ? formAccountNonLocked : (user == null or user.accountNonLocked)}">checked="checked"</c:if> />
        <label for="accountNonLocked"><fmt:message key="users.field.accountNonLocked" bundle="${messages}" /></label>
    </div>
    <div class="field checks">
        <input type="checkbox" id="emailVerified" name="emailVerified" value="true"
               <c:if test="${formEmailVerified != null ? formEmailVerified : (user != null and user.emailVerified)}">checked="checked"</c:if> />
        <label for="emailVerified"><fmt:message key="users.field.emailVerified" bundle="${messages}" /></label>
    </div>
    <div class="field checks">
        <input type="checkbox" id="mustChangePassword" name="mustChangePassword" value="true"
               <c:if test="${formMustChangePassword != null ? formMustChangePassword : (user != null and user.mustChangePassword)}">checked="checked"</c:if> />
        <label for="mustChangePassword"><fmt:message key="users.field.mustChangePassword" bundle="${messages}" /></label>
    </div>

    <div class="actions">
        <button type="submit">
            <c:choose>
                <c:when test="${user == null}"><fmt:message key="common.create" bundle="${messages}" /></c:when>
                <c:otherwise><fmt:message key="common.save" bundle="${messages}" /></c:otherwise>
            </c:choose>
        </button>
        <a href="${usersUrl}"><fmt:message key="common.cancel" bundle="${messages}" /></a>
    </div>
</form>

<%@ include file="../../partials/footer.jsp" %>
