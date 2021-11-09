package com.sample.sample.config;

import com.sample.sample.entity.User;
import com.sample.sample.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@Component
public class UserArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        Class<?> parameterType = parameter.getParameterType();
        return User.class.equals(parameterType) || User.class.isAssignableFrom(parameterType);
    }

    @Override
    public User resolveArgument(MethodParameter parameter,
                                ModelAndViewContainer mavContainer,
                                NativeWebRequest webRequest,
                                WebDataBinderFactory binderFactory) {
        HttpServletRequest nativeRequest = (HttpServletRequest) webRequest.getNativeRequest();
        String email = nativeRequest.getHeader("sample-email");

        if (email == null || email.equals("")) {
            throw new IllegalArgumentException("required email");
        }

        return userService.findByEmail(email);
    }
}
