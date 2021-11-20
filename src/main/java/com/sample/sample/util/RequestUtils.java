package com.sample.sample.util;

import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

public class RequestUtils {

    private static final String REQUEST_ID_KEY = "Request-Id";

    public static void getOrGenerateRequestId(HttpServletRequest request) {
        String requestId = Optional.ofNullable(request.getHeader(REQUEST_ID_KEY))
                .orElseGet(() -> UUID.randomUUID().toString());
        request.setAttribute(REQUEST_ID_KEY, requestId);
    }

    public static String generateLogPrefix(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String method = request.getMethod();
        String remoteAddr = request.getRemoteAddr();
        String requestId = String.valueOf(request.getAttribute(REQUEST_ID_KEY));

        return "[" + method + " " + uri + "][" + remoteAddr + "][" + requestId + "]";
    }

    public static String generateLogPrefix(WebRequest request) {
        String requestId = String.valueOf(request.getAttribute(REQUEST_ID_KEY, 0));
        return "[" + requestId + "]";
    }
}
