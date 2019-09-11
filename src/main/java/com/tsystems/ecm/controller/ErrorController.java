package com.tsystems.ecm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * MVC controller responsible for error pages.
 *
 * @author Yurii Shevelev
 * @version 1.0.0
 */
@Controller
@RequestMapping("/error")
public class ErrorController {

    /**
     * Shows error 403 page.
     *
     * @return the error 403 page
     */
    @GetMapping("/error403")
    public String errorUnauthorized() {

        return "error/error403";
    }

    /**
     * Shows error 400 page.
     *
     * @return the error 400 page
     */
    @GetMapping("/error400")
    public String errorBadRequest() {

        return "error/error400";
    }

}
