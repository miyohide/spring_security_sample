package com.example.spring_security_sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    private static final Logger logger = LoggerFactory.getLogger(GreetingController.class);

    @RequestMapping("/")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        User principal = (User)securityContext.getAuthentication().getPrincipal();
        logger.info("---------- principal name = [" + principal.getUsername() + "] ----------");
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
