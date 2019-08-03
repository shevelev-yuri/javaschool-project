package com.tsystems.ecm.interceptor;

import com.tsystems.ecm.service.AuthorizationSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class CookieSessionInterceptor implements HandlerInterceptor {

    private static final String LOGIN = "login";

    @Autowired
    private AuthorizationSessionService authorizationSessionService;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            response.sendRedirect(LOGIN);
            return false;
        }

        Optional<Cookie> possibleCookie = Arrays.stream(cookies)
                .filter(c -> c.getName().equals("AUTH_SESSION"))
                .findFirst();
        if (!possibleCookie.isPresent()) {
            response.sendRedirect(LOGIN);
            return false;
        }

        if (authorizationSessionService.isExpired(possibleCookie.get().getValue())) {
            response.sendRedirect(LOGIN);
            return false;
        }

        String login = authorizationSessionService.getLoginBySessionId(possibleCookie.get().getValue());
        authorizationSessionService.setAuthenticatedUser(login);

        return true;
    }
}
