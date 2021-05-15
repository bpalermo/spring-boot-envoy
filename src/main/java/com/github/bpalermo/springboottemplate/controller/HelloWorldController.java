package com.github.bpalermo.springboottemplate.controller;

import com.github.bpalermo.springboottemplate.filters.JWTFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @GetMapping("/admin/hello")
    public String sayHelloAdmin(@RequestAttribute(JWTFilter.ATTRIBUTE_JWT_SUBJECT) String subject) {
        return hello(subject);
    }

    @GetMapping("/hello")
    public String sayHello(@RequestAttribute(JWTFilter.ATTRIBUTE_JWT_SUBJECT) String subject) {
        return hello(subject);
    }

    private String hello(String subject) {
        return "Hello " + subject;
    }
}
