package org.iesalixar.daw2.rafael.listeners;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.iesalixar.daw2.rafael.daos.DataInitializer;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebListener
public class AppContextListener implements ServletContextListener {
    private static final Logger logger = Logger.getLogger(AppContextListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent event) {
        logger.entering(getClass().getName(), "contextInitialized");
        try (InputStream sql = event.getServletContext()
                .getResourceAsStream("/WEB-INF/classes/data.sql")) {
            DataInitializer.loadDataFromSQL(sql);
            logger.info("Aplicación inicializada y datos cargados.");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "No se pudo inicializar la base de datos.", e);
            throw new IllegalStateException("No se pudo inicializar la aplicación.", e);
        } finally {
            logger.exiting(getClass().getName(), "contextInitialized");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        logger.entering(getClass().getName(), "contextDestroyed");
        logger.info("Aplicación detenida.");
        logger.exiting(getClass().getName(), "contextDestroyed");
    }
}
