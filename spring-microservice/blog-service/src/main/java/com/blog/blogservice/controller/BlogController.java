package com.blog.blogservice.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/blog")
public class BlogController {

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping
    public String sayHello() {
        return "hello";
    }
}
