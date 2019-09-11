package com.tsystems.ecm.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Map;

/**
 * This interceptor logs requests parameters for development purpose
 */
public class RequestParamsInterceptor implements HandlerInterceptor {

    /**
     * Log4j logger.
     */
    private static final Logger log = LogManager.getLogger(RequestParamsInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (log.isDebugEnabled()) {

            Map<String, String[]> paramMap = request.getParameterMap();
            if (!paramMap.isEmpty()) log.debug("Request parameters:");
            for (Map.Entry<String, String[]> entry : paramMap.entrySet()) {
                log.debug("{}={}", entry.getKey(), Arrays.toString(entry.getValue()));
            }
        }

        return true;
    }
}
