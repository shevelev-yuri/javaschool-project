package com.tsystems.ecm.controller;

import com.tsystems.ecm.dto.UserSessionDto;
import com.tsystems.ecm.service.AuthenticationService;
import com.tsystems.ecm.service.AuthorizationSessionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthorizationController {

    private static final Logger log = LogManager.getLogger(AuthorizationController.class);
    private static final String LOGIN = "login";

    private final AuthenticationService authService;
    private final AuthorizationSessionService authorizationSessionService;

    @Autowired
    public AuthorizationController(AuthenticationService authService, AuthorizationSessionService authorizationSessionService) {
        this.authService = authService;
        this.authorizationSessionService = authorizationSessionService;
    }

    @GetMapping("/login")
    public String loginPage() {

        return LOGIN;
    }

    @PostMapping("/login")
    public String login(HttpServletResponse response,
                        HttpServletRequest request) {
        boolean result = authService.authenticate(request.getParameter(LOGIN), request.getParameter("password"));

        log.info("User with login \"{}\" authenticated? = {}", request.getParameter(LOGIN), result);

        if (!result) {
            return LOGIN;
        }

        UserSessionDto session = authorizationSessionService.createOrUpdateSession(request.getParameter(LOGIN));
        Cookie sessionCookie = new Cookie("AUTH_SESSION", session.getSid());
        Cookie userNameCookie = new Cookie("userName", session.getUserName().replaceAll("\\s","-"));
        sessionCookie.setMaxAge(60*60*24);
        userNameCookie.setMaxAge(60*60*24);
        sessionCookie.setHttpOnly(true);
        userNameCookie.setHttpOnly(true);
        response.addCookie(sessionCookie);
        response.addCookie(userNameCookie);

        return "redirect:/patients";
    }
}