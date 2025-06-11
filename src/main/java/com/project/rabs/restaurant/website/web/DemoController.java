package com.project.rabs.restaurant.website.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String hello() {
        return "Hello World";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/hello-2")
    public String hello2() {
        return "Hello World-2";
    }
}
