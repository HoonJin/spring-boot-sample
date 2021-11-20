package com.sample.sample.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean handled = handler instanceof HandlerMethod;
        if (handled) {
            log.info(generateMessageTemplate(request));
        }

        return handled;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String message = generateMessageTemplate(request);
        log.info(message +  ": responseStatus=" + response.getStatus());
    }

    private String generateMessageTemplate(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String requestedSessionId = request.getRequestedSessionId();
        String remoteAddr = request.getRemoteAddr();

        return "[" + LocalDateTime.now() + "][" + method + " " + uri + "][" + remoteAddr + "][" + requestedSessionId + "]";
    }
}
