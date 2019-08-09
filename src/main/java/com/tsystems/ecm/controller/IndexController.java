package com.tsystems.ecm.controller;


import com.tsystems.ecm.service.AuthorizationSessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    private AuthorizationSessionService authorizationSessionService;

    @Autowired
    public IndexController(AuthorizationSessionService authorizationSessionService) {
        this.authorizationSessionService = authorizationSessionService;
    }

    @GetMapping("/")
    public String getIndex() {
        return "index";
    }

}