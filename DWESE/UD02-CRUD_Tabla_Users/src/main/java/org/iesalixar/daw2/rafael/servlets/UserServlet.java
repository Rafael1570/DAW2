package org.iesalixar.daw2.rafael.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iesalixar.daw2.rafael.daos.UserDAO;
import org.iesalixar.daw2.rafael.daos.UserDAOImpl;
import org.iesalixar.daw2.rafael.entities.User;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/users")
public class UserServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(UserServlet.class.getName());
    private static final DateTimeFormatter FORM_DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private UserDAO userDAO;

    @Override
    public void init() {
        logger.entering(getClass().getName(), "init");
        userDAO = new UserDAOImpl();
        logger.exiting(getClass().getName(), "init");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.entering(getClass().getName(), "doGet");
        try {
            response.setContentType("text/html;charset=UTF-8");
            String action = request.getParameter("action");
            if ("new".equals(action)) {
                showNewForm(request, response);
            } else if ("edit".equals(action)) {
                showEditForm(request, response);
            } else {
                listUsers(request, response);
            }
        } finally {
            logger.exiting(getClass().getName(), "doGet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.entering(getClass().getName(), "doPost");
        try {
            request.setCharacterEncoding("UTF-8");
            String action = request.getParameter("action");
            switch (action == null ? "" : action) {
                case "insert" -> insertUser(request, response);
                case "update" -> updateUser(request, response);
                case "delete" -> deleteUser(request, response);
                default -> response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Acción no válida.");
            }
        } finally {
            logger.exiting(getClass().getName(), "doPost");
        }
    }

    /**
     * Carga los usuarios y entrega la lista a la JSP durante esta petición.
     *
     * @param request petición que transporta el atributo {@code users}
     * @param response respuesta del servlet
     */
    private void listUsers(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.entering(getClass().getName(), "listUsers");
        try {
            List<User> users = userDAO.findAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/views/user/user-list.jsp").forward(request, response);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No se pudo cargar el listado de usuarios.", e);
            throw new ServletException("No se pudo cargar el listado de usuarios.", e);
        } finally {
            logger.exiting(getClass().getName(), "listUsers");
        }
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.entering(getClass().getName(), "showNewForm");
        try {
            request.setAttribute("lastPasswordChangeInput", FORM_DATE_FORMAT.format(LocalDateTime.now()));
            request.setAttribute("formFailedLoginAttempts", 0);
            request.setAttribute("formActive", true);
            request.setAttribute("formAccountNonLocked", true);
            request.getRequestDispatcher("/views/user/user-form.jsp").forward(request, response);
        } finally {
            logger.exiting(getClass().getName(), "showNewForm");
        }
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.entering(getClass().getName(), "showEditForm");
        try {
            Long id = parseId(request.getParameter("id"));
            if (id == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de usuario no válido.");
                return;
            }
            User user = userDAO.findById(id);
            if (user == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró el usuario.");
                return;
            }
            prepareExistingUserForm(request, user);
            request.getRequestDispatcher("/views/user/user-form.jsp").forward(request, response);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "No se pudo cargar el usuario para editar.", e);
            throw new ServletException("No se pudo cargar el usuario.", e);
        } finally {
            logger.exiting(getClass().getName(), "showEditForm");
        }
    }

    private void insertUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.entering(getClass().getName(), "insertUser");
        try {
            User user = parseSubmittedUser(request, response, null);
            if (user == null) return;
            if (userDAO.usernameExists(user.getUsername(), null)) {
                showFormError(request, response, null, "users.error.duplicate");
                return;
            }
            userDAO.insert(user);
            logger.info("Usuario creado.");
            redirectToList(request, response);
        } catch (SQLException e) {
            if (isDuplicateUsername(e)) {
                showFormError(request, response, null, "users.error.duplicate");
                return;
            }
            logger.log(Level.SEVERE, "Error al crear el usuario.", e);
            throw new ServletException("No se pudo crear el usuario.", e);
        } finally {
            logger.exiting(getClass().getName(), "insertUser");
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.entering(getClass().getName(), "updateUser");
        Long id = parseId(request.getParameter("id"));
        try {
            if (id == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de usuario no válido.");
                return;
            }
            User existing = userDAO.findById(id);
            if (existing == null) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "No se encontró el usuario.");
                return;
            }
            User user = parseSubmittedUser(request, response, id);
            if (user == null) return;
            if (userDAO.usernameExists(user.getUsername(), id)) {
                showFormError(request, response, user, "users.error.duplicate");
                return;
            }
            userDAO.update(user);
            logger.info("Usuario actualizado.");
            redirectToList(request, response);
        } catch (SQLException e) {
            if (isDuplicateUsername(e)) {
                showFormError(request, response, id == null ? null : new User(id, null, null,
                        null, null, null, null, null, null, null), "users.error.duplicate");
                return;
            }
            logger.log(Level.SEVERE, "Error al actualizar el usuario.", e);
            throw new ServletException("No se pudo actualizar el usuario.", e);
        } finally {
            logger.exiting(getClass().getName(), "updateUser");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.entering(getClass().getName(), "deleteUser");
        try {
            Long id = parseId(request.getParameter("id"));
            if (id == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "ID de usuario no válido.");
                return;
            }
            userDAO.delete(id);
            logger.info("Usuario eliminado.");
            redirectToList(request, response);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar el usuario.", e);
            throw new ServletException("No se pudo eliminar el usuario.", e);
        } finally {
            logger.exiting(getClass().getName(), "deleteUser");
        }
    }

    private User parseSubmittedUser(HttpServletRequest request, HttpServletResponse response, Long id)
            throws ServletException, IOException {
        logger.entering(getClass().getName(), "parseSubmittedUser");
        try {
            String username = trim(request.getParameter("username"));
            String passwordHash = request.getParameter("passwordHash");
            String lastPasswordChangeValue = trim(request.getParameter("lastPasswordChange"));
            String[] requiredFields = {
                    username,
                    passwordHash,
                    request.getParameter("lastPasswordChange")
            };
            for (String field : requiredFields) {
                if (field == null || field.isBlank()) {
                    showFormError(request, response, id == null ? null : new User(id,
                            username, null, null, null, null, null, null, null, null),
                            "users.error.required");
                    return null;
                }
            }
            if (username.length() > 40 || passwordHash.length() > 500) {
                showFormError(request, response, id == null ? null : new User(id,
                        username, null, null, null, null, null, null, null, null),
                        "users.error.length");
                return null;
            }

            LocalDateTime lastPasswordChange;
            try {
                lastPasswordChange = LocalDateTime.parse(lastPasswordChangeValue,
                        DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            } catch (DateTimeParseException e) {
                showFormError(request, response, id == null ? null : new User(id,
                        username, null, null, null, null, null, null, null, null),
                        "users.error.date");
                return null;
            }

            String failedAttemptsValue = trim(request.getParameter("failedLoginAttempts"));
            int failedAttempts;
            try {
                failedAttempts = Integer.parseInt(failedAttemptsValue);
                if (failedAttempts < 0) throw new NumberFormatException("negative");
            } catch (NumberFormatException e) {
                showFormError(request, response, id == null ? null : new User(id,
                        username, null, null, null, null, null, null, null, null),
                        "users.error.attempts");
                return null;
            }

            User user = new User(id, username, passwordHash,
                    Boolean.valueOf(request.getParameter("active")),
                    Boolean.valueOf(request.getParameter("accountNonLocked")),
                    lastPasswordChange, lastPasswordChange.plusMonths(3), failedAttempts,
                    Boolean.valueOf(request.getParameter("emailVerified")),
                    Boolean.valueOf(request.getParameter("mustChangePassword")));
            return user;
        } finally {
            logger.exiting(getClass().getName(), "parseSubmittedUser");
        }
    }

    private void showFormError(HttpServletRequest request, HttpServletResponse response,
                               User user, String errorKey) throws ServletException, IOException {
        logger.entering(getClass().getName(), "showFormError");
        try {
            request.setAttribute("user", user);
            request.setAttribute("errorKey", errorKey);
            request.setAttribute("formUsername", trim(request.getParameter("username")));
            request.setAttribute("formLastPasswordChange", request.getParameter("lastPasswordChange"));
            request.setAttribute("formFailedLoginAttempts", request.getParameter("failedLoginAttempts"));
            request.setAttribute("formActive", request.getParameter("active") != null);
            request.setAttribute("formAccountNonLocked", request.getParameter("accountNonLocked") != null);
            request.setAttribute("formEmailVerified", request.getParameter("emailVerified") != null);
            request.setAttribute("formMustChangePassword", request.getParameter("mustChangePassword") != null);
            request.getRequestDispatcher("/views/user/user-form.jsp").forward(request, response);
        } finally {
            logger.exiting(getClass().getName(), "showFormError");
        }
    }

    private void prepareExistingUserForm(HttpServletRequest request, User user) {
        logger.entering(getClass().getName(), "prepareExistingUserForm");
        request.setAttribute("user", user);
        request.setAttribute("lastPasswordChangeInput", FORM_DATE_FORMAT.format(user.getLastPasswordChange()));
        request.setAttribute("formFailedLoginAttempts", user.getFailedLoginAttempts());
        request.setAttribute("formActive", user.getActive());
        request.setAttribute("formAccountNonLocked", user.getAccountNonLocked());
        request.setAttribute("formEmailVerified", user.getEmailVerified());
        request.setAttribute("formMustChangePassword", user.getMustChangePassword());
        logger.exiting(getClass().getName(), "prepareExistingUserForm");
    }

    private Long parseId(String value) {
        logger.entering(getClass().getName(), "parseId");
        try {
            long id = Long.parseLong(value);
            return id > 0 ? id : null;
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        } finally {
            logger.exiting(getClass().getName(), "parseId");
        }
    }

    private String trim(String value) {
        logger.entering(getClass().getName(), "trim");
        try {
            return value == null ? "" : value.trim();
        } finally {
            logger.exiting(getClass().getName(), "trim");
        }
    }

    private boolean isDuplicateUsername(SQLException exception) {
        logger.entering(getClass().getName(), "isDuplicateUsername");
        try {
            return "23000".equals(exception.getSQLState());
        } finally {
            logger.exiting(getClass().getName(), "isDuplicateUsername");
        }
    }

    private void redirectToList(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.entering(getClass().getName(), "redirectToList");
        try {
            response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/users"));
        } finally {
            logger.exiting(getClass().getName(), "redirectToList");
        }
    }

}
