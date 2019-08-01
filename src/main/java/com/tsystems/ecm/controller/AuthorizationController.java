package com.tsystems.ecm.controller;

import com.tsystems.ecm.dto.UserSession;
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
    private final AuthorizationSessionService authSessionService;

    @Autowired
    public AuthorizationController(AuthenticationService authService, AuthorizationSessionService authSessionService) {
        this.authService = authService;
        this.authSessionService = authSessionService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return LOGIN;
    }

    @PostMapping("/login")
    public String login(HttpServletResponse response,
                        HttpServletRequest request) {
        boolean result = authService.authenticate(request.getParameter(LOGIN), request.getParameter("password"));
        log.info("Requested login and password: {}, {}. Authenticate = {}", request.getParameter(LOGIN), request.getParameter("password"), result);
        if (!result) {
            return LOGIN;
        }

        UserSession session = authSessionService.createOrUpdateSession(request.getParameter(LOGIN));
        response.addCookie(new Cookie("AUTH_SESSION", session.getSid()));

        return "redirect:/patients";
    }



}