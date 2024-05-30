package ru.ob11to.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.GenericFilterBean;
import ru.ob11to.config.LoggingProperties;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class LoggingFilter extends GenericFilterBean {

    private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);
    private final LoggingProperties loggingProperties;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public LoggingFilter(LoggingProperties loggingProperties) {
        this.loggingProperties = loggingProperties;
        logger.info("Api-Logging filter initialized!");
    }


    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        long startTime = System.currentTimeMillis();
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        logRequest(httpRequest);
        chain.doFilter(request, response);
        long duration = System.currentTimeMillis() - startTime;
        logResponse(httpResponse, duration);
    }

    private void logRequest(HttpServletRequest request) {
        if (shouldLog(loggingProperties.getLevel())) {
            Map<String, Object> logData = new HashMap<>();
            logData.put("method", request.getMethod());
            logData.put("uri", request.getRequestURI());

            Map<String, String> headers = new HashMap<>();
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                headers.put(headerName, request.getHeader(headerName));
            }
            logData.put("headers", headers);
            logData.put("params", request.getParameterMap());

            log(logData, "Incoming Request");
        }
    }

    private void logResponse(HttpServletResponse response, long duration) {
        if (shouldLog(loggingProperties.getLevel())) {
            Map<String, Object> logData = new HashMap<>();
            logData.put("status", response.getStatus());

            Map<String, String> headers = new HashMap<>();
            for (String headerName : response.getHeaderNames()) {
                headers.put(headerName, response.getHeader(headerName));
            }
            logData.put("headers", headers);
            logData.put("duration", duration + "ms");

            log(logData, "Outgoing Response");
        }
    }

    private boolean shouldLog(String level) {
        return "DEBUG".equalsIgnoreCase(level) || "INFO".equalsIgnoreCase(level) ||
                "ERROR".equalsIgnoreCase(level) || "WARNING".equalsIgnoreCase(level);
    }

    private void log(Map<String, Object> logData, String logType) {
        String format = loggingProperties.getFormat();
        String message;
        try {
            if ("json".equalsIgnoreCase(format)) {
                message = objectMapper.writeValueAsString(logData);
            } else {
                message = logData.toString();
            }

            logByLevel(loggingProperties.getLevel(), logType + ": " + message);
        } catch (JsonProcessingException e) {
            logger.error("Failed to log {}: {}", logType, e.getMessage(), e);
        }
    }

    private void logByLevel(String level, String message) {
        switch (level.toUpperCase()) {
            case "DEBUG" -> logger.debug(message);
            case "WARNING" -> logger.warn(message);
            case "ERROR" -> logger.error(message);
            default -> logger.info(message);
        }
    }

}