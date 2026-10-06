package org.iesalixar.daw2.rafael.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.util.Locale;
import java.util.logging.Logger;

@WebServlet("/changeLanguage")
public class LanguageServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LanguageServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.entering(getClass().getName(), "doGet");
        try {
            String language = request.getParameter("lang");
            if (language != null) {
                Locale locale = "en".equalsIgnoreCase(language)
                        ? Locale.ENGLISH : Locale.forLanguageTag("es");
                request.getSession(true).setAttribute("locale", locale);
                logger.info(() -> "Idioma de sesión actualizado: " + locale.getLanguage());
            }

            String referer = request.getHeader("Referer");
            if (isSameOriginReferer(request, referer)) {
                response.sendRedirect(response.encodeRedirectURL(referer));
            } else {
                response.sendRedirect(request.getContextPath() + "/");
            }
        } finally {
            logger.exiting(getClass().getName(), "doGet");
        }
    }

    private boolean isSameOriginReferer(HttpServletRequest request, String referer) {
        logger.entering(getClass().getName(), "isSameOriginReferer");
        try {
            if (referer == null || referer.isBlank()) return false;
            URI uri = URI.create(referer);
            int port = uri.getPort() == -1
                    ? ("https".equalsIgnoreCase(uri.getScheme()) ? 443 : 80)
                    : uri.getPort();
            return uri.getHost() != null
                    && uri.getHost().equalsIgnoreCase(request.getServerName())
                    && port == request.getServerPort()
                    && ("http".equalsIgnoreCase(uri.getScheme())
                    || "https".equalsIgnoreCase(uri.getScheme()));
        } catch (IllegalArgumentException e) {
            logger.fine("La cabecera Referer no contiene una URI válida.");
            return false;
        } finally {
            logger.exiting(getClass().getName(), "isSameOriginReferer");
        }
    }
}
