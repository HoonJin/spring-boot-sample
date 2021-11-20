package com.sample.sample.config;

import com.sample.sample.util.RequestUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean handled = handler instanceof HandlerMethod;
        if (handled) {
            RequestUtils.getOrGenerateRequestId(request);
            String prefix = RequestUtils.generateLogPrefix(request);
            log.info(prefix + " started");
        }

        return handled;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        String prefix = RequestUtils.generateLogPrefix(request);
        log.info(prefix +  " responseStatus=" + response.getStatus());
    }
}
