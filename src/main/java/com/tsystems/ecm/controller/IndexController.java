package com.tsystems.ecm.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collection;

/**
 * Index MVC controller responsible.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Controller
public class IndexController {

    /**
     * Redirects to page corresponding to user's role.
     *
     * @param authentication the Authentication reference
     * @return the corresponding page
     */
    @GetMapping("/")
    public String getIndex(Authentication authentication) {
        String viewName;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        if (authorities.contains(new SimpleGrantedAuthority("NURSE"))) {
            viewName = "redirect:/events/events";
        } else if (authorities.contains(new SimpleGrantedAuthority("DOCTOR"))) {
            viewName = "redirect:/patients";
        } else {
            viewName = "admin";
        }

        return viewName;
    }

    /**
     * Shows login form page.
     *
     * @return the login form page
     */
    @GetMapping("/login")
    public String login() {

        return "login";
    }

}