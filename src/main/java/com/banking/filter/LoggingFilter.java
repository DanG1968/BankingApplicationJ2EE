package com.banking.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

@WebFilter(
        filterName = "LoggingFilter",
        urlPatterns = {"/*"}
)
public class LoggingFilter implements Filter {

    // Create a Logger instance for logging
    private static final Logger logger = Logger.getLogger(LoggingFilter.class.getName());

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization logic if needed
        logger.info("LoggingFilter initialized");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Log request information
        String remoteAddr = request.getRemoteAddr();
        String requestUri = ((HttpServletRequest) request).getRequestURI();
        String method = ((HttpServletRequest) request).getMethod();
        Date requestDate = new Date();

        logger.info("Request received - Method: " + method + ", URI: " + requestUri
                + ", Client IP: " + remoteAddr + ", Time: " + requestDate);

        // Continue the request chain
        chain.doFilter(request, response);

        // Log after the response is sent
        logger.info("Response sent for URI: " + requestUri + ", Time: " + new Date());
    }

    @Override
    public void destroy() {
        // Cleanup logic if needed
        logger.info("LoggingFilter destroyed");
    }
}
