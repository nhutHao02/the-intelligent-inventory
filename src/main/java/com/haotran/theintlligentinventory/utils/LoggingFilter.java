package com.haotran.theintlligentinventory.utils;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Slf4j
public class LoggingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);

        String method = request.getMethod();
        String path = request.getRequestURI();

        try {
            log.info("Incoming request method={} path={}", method, path);

            filterChain.doFilter(request, response);

        } finally {
            long duration = System.currentTimeMillis() - start;
            int status = response.getStatus();

            log.info("HTTP Request completed method={} path={} status={} durationMs={}",
                    method, path, status, duration);

            MDC.clear();
        }
    }
}
