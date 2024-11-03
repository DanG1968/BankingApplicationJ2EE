package com.banking.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import java.util.logging.Logger;

@WebListener
public class AppStartupListener implements ServletContextListener {

    // Logger for logging messages
    private static final Logger logger = Logger.getLogger(AppStartupListener.class.getName());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // This method is called when the web application is starting
        logger.info("Banking Application is starting...");

        // Example: Initialize application-wide resources
        // You can add code here to set up a database connection pool, initialize caches, etc.
        logger.info("Performing application initialization tasks");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // This method is called when the web application is shutting down
        logger.info("Banking Application is shutting down...");

        // Example: Clean up resources
        // You can add code here to close database connections, clear caches, etc.
        logger.info("Performing application cleanup tasks");
    }
}
