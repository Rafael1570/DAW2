<%@ include file="../../partials/header.jsp" %>
<c:url value="/users" var="usersUrl" />
<fmt:message key="users.delete.confirm" bundle="${messages}" var="deleteConfirm" />

<h1><fmt:message key="users.list.title" bundle="${messages}" /></h1>
<p><a href="${usersUrl}?action=new"><fmt:message key="users.list.add" bundle="${messages}" /></a></p>

<c:choose>
    <c:when test="${empty users}">
        <p><fmt:message key="users.list.empty" bundle="${messages}" /></p>
    </c:when>
    <c:otherwise>
        <table>
            <thead>
                <tr>
                    <th><fmt:message key="users.field.id" bundle="${messages}" /></th>
                    <th><fmt:message key="users.field.username" bundle="${messages}" /></th>
                    <th><fmt:message key="users.field.active" bundle="${messages}" /></th>
                    <th><fmt:message key="users.field.accountNonLocked" bundle="${messages}" /></th>
                    <th><fmt:message key="users.field.lastPasswordChange" bundle="${messages}" /></th>
                    <th><fmt:message key="users.field.passwordExpiresAt" bundle="${messages}" /></th>
                    <th><fmt:message key="users.field.failedLoginAttempts" bundle="${messages}" /></th>
                    <th><fmt:message key="users.field.emailVerified" bundle="${messages}" /></th>
                    <th><fmt:message key="users.field.mustChangePassword" bundle="${messages}" /></th>
                    <th><fmt:message key="common.actions" bundle="${messages}" /></th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td><c:out value="${user.id}" /></td>
                        <td><c:out value="${user.username}" /></td>
                        <td><c:out value="${user.active}" /></td>
                        <td><c:out value="${user.accountNonLocked}" /></td>
                        <td><c:out value="${user.lastPasswordChange}" /></td>
                        <td><c:out value="${user.passwordExpiresAt}" /></td>
                        <td><c:out value="${user.failedLoginAttempts}" /></td>
                        <td><c:out value="${user.emailVerified}" /></td>
                        <td><c:out value="${user.mustChangePassword}" /></td>
                        <td class="actions">
                            <a href="${usersUrl}?action=edit&amp;id=${user.id}"><fmt:message key="common.edit" bundle="${messages}" /></a>
                            <form class="inline" action="${usersUrl}" method="post"
                                  onsubmit="return confirm(this.dataset.confirm);" data-confirm="${deleteConfirm}">
                                <input type="hidden" name="action" value="delete" />
                                <input type="hidden" name="id" value="${user.id}" />
                                <button type="submit"><fmt:message key="common.delete" bundle="${messages}" /></button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:otherwise>
</c:choose>

<%@ include file="../../partials/footer.jsp" %>
